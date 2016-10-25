/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PEs;

import cz.zcu.fav.kiv.jsim.*;
import static cz.zcu.fav.kiv.jsim.JSimSystem.gauss;
import static cz.zcu.fav.kiv.jsim.JSimSystem.uniform;
import cz.zcu.fav.kiv.jsim.ipc.JSimMessageBox;
import java.util.Random;
import util.ProcessingTime;
import util.Token;

/**
 *
 * @author Jorge.Cubillos
 */
public class Adapter extends JSimProcess {

    private ProcessingTime pt;
    private final Classifier clasificador;
    private final String[] texto = {"inscripcion", "localizacion", "sos"};
    private String name;
    private Random rdm;
    private JSimMessageBox box;

    public Adapter(String name, JSimSimulation sim, Classifier clasificador, JSimMessageBox box)
            throws JSimSimulationAlreadyTerminatedException,
            JSimInvalidParametersException,
            JSimTooManyProcessesException {
        super(name, sim);
        this.name = name;
        this.clasificador = clasificador;
        this.rdm = new Random();
        this.box = box;
    }

    public String getRdm() {
        return texto[(int) (this.rdm.nextDouble() * 2 + 0)];
    }

    public void sendMessage(Token token) {

    }

    @Override
    protected void life() {
        message("SOY EL ADAPTER Y ESTOY VIVO");
        try {
            double time;
            double random;
            
            while (true) {
                time = this.myParent.getCurrentTime();
                //Token token = new Token(this.getRdm(), 0.1);
                random = uniform(0.0, 1.0);
                Token token;
                double lambda = Math.abs(gauss(0, 0.2));
                //Inscripción
                if(random <= 0.001){
                    token = new Token(this.texto[0], Math.abs(gauss(0, 0.5)));
                }
                //SOS
                else if(random <= 0.15){
                    token = new Token(this.texto[2], Math.abs(gauss(0, 0.2)));
                }
                //Localizacion
                else{
                    token = new Token(this.texto[1], Math.abs(gauss(0, 1)));
                    
                }
               
                this.clasificador.receiveMessage(token);
                //message("-- Soy el adapter y envio este mensaje al clasificador: "+token.getTipo());
            }
        } catch (JSimException e) {
            e.printStackTrace(System.out);
            e.printComment();
        }
    }
}
