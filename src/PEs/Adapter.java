/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PEs;

import core.Coord;
import cz.zcu.fav.kiv.jsim.*;
import static cz.zcu.fav.kiv.jsim.JSimSystem.gauss;
import static cz.zcu.fav.kiv.jsim.JSimSystem.uniform;
import cz.zcu.fav.kiv.jsim.ipc.JSimMessageBox;
import java.util.Random;
import processors.Processor;
import simulatorUtil.ProcessingTime;
import simulatorUtil.Token;
import ui.DTNSimTextUI;

/**
 *
 * @author Jorge.Cubillos
 */
public class Adapter extends JSimProcess {

    private ProcessingTime pt;
    //private final Processor procesador;
    private final Classifier clasificador;
    private final String[] texto = {"inscripcion", "localizacion", "sos"};
    private String name;
    private Random rdm;
    private JSimMessageBox box;
    private Processor procesador1, procesador2;
    private DTNSimTextUI oneSimulator;

    public Adapter(String name, JSimSimulation sim, Classifier clasificador, 
                JSimMessageBox box, Processor procesador1, Processor procesador2)
    //public Adapter(String name, JSimSimulation sim, Processor procesador, JSimMessageBox box)
            throws JSimSimulationAlreadyTerminatedException,
            JSimInvalidParametersException,
            JSimTooManyProcessesException {
        super(name, sim);
        this.name = name;
        //this.procesador = procesador;
        this.rdm = new Random();
        this.box = box;
        this.procesador1 = procesador1;
        this.procesador2 = procesador2;
        this.clasificador = clasificador;
    }

    public DTNSimTextUI getOneSimulator() {
        return oneSimulator;
    }

    public void setOneSimulator(DTNSimTextUI oneSimulator) {
        this.oneSimulator = oneSimulator;
    }

    public String getRdm() {
        return texto[(int) (this.rdm.nextDouble() * 2 + 0)];
    }

    public void sendMessage(Token token) throws JSimSecurityException {
        JSimLink element;
        element = new JSimLink( token );
        //element.into(this.procesador.getQueue());
    }

    @Override
    protected void life() {
        //message("SOY EL ADAPTER Y ESTOY VIVO");
        try {
            double time, random;
            
            //Probabilidades de ocurrencia de cada evento
            double prob_inscripcion = 0.001;
            double prob_sos = 0.151;
            double prob_localizacion = 1 - prob_inscripcion - prob_sos;
            
            //Desviación estándar para el cálculo de la función Gauss
            double sd_inscripcion = 0.5;
            double sd_sos = 0.2;
            double sd_localizacion = 1;
                    
            int i = 0, limite = 1000, mensajes = 0;
            
            while (true && i < limite) {
                time = this.myParent.getCurrentTime();
                //Token token = new Token(this.getRdm(), 0.1);
                random = uniform(0.0, 1.0);
                Token token;
                double lambda;
                //Inscripción
                if(random <= prob_inscripcion){
                    token = new Token(this.texto[0], Math.abs(gauss(0, sd_inscripcion)));
                    token.setT_init(this.myParent.getCurrentTime());
                }
                //SOS
                else if(random <= prob_sos){
                    //Aquí recibe la ubicación de un usuario que envía un mensaje a través de la red 4g
                    Coord location = getNodeLocation();
                    System.out.println("## SOS: ("+location.getX()+", "+location.getY()+")");
                    token = new Token(this.texto[2], Math.abs(gauss(0, sd_sos)));
                    token.setT_init(this.myParent.getCurrentTime());
                }
                //Localizacion
                else{
                    //Aquí recibe la ubicación de un usuario que envía un mensaje a través de la red 4g
                    Coord location = getNodeLocation();
                    System.out.println("## LOCATION: ("+location.getX()+", "+location.getY()+")");
                    token = new Token(this.texto[1], Math.abs(gauss(0, sd_localizacion)));
                    token.setT_init(this.myParent.getCurrentTime());
                    
                }
                mensajes++;
                this.clasificador.receiveMessage(token);
                
                i++;
                if(i == limite){
                    System.out.println("--- SE ENVIARON "+mensajes+" MENSAJES");
                }
                
                hold(0.1);
            }
        } 
        catch (JSimException e) {
            e.printStackTrace(System.out);
            e.printComment();
        }
    }
    
    /**
     * Obtiene la ubicación de uno de los nodos del modelo de movilidad
     * @return Objeto Coord con la ubicación del nodo
     */
    public Coord getNodeLocation(){
        int length = oneSimulator.getWorld().getHosts().size();
        System.out.println("CANTIDAD DE HOSTS: "+length);
        Random random = new Random();
        
        return oneSimulator.getWorld().getHosts().get(random.nextInt(length)).getLocation();
    }
}
