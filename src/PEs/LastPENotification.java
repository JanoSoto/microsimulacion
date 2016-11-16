/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PEs;

import core.Coord;
import cz.zcu.fav.kiv.jsim.JSimInvalidParametersException;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import cz.zcu.fav.kiv.jsim.JSimSimulationAlreadyTerminatedException;
import cz.zcu.fav.kiv.jsim.JSimTooManyProcessesException;
import java.util.Random;
import processors.Processor;
import simulatorUtil.AbstractPE;
import simulatorUtil.Token;
import ui.DTNSimTextUI;

/**
 *
 * @author JAno
 */
public class LastPENotification extends AbstractPE {

    private int counter;
    private DTNSimTextUI oneSimulator;

    public LastPENotification(int id, String nombre, String next_pe, Processor processor, JSimSimulation simulation) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException{
        super(nombre, simulation, next_pe, processor);
        this.counter = 0;
    }

    public DTNSimTextUI getOneSimulator() {
        return oneSimulator;
    }

    public void setOneSimulator(DTNSimTextUI oneSimulator) {
        this.oneSimulator = oneSimulator;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    @Override
    public void sendMessage(Token token) {

    }

    @Override
    public void receiveMessage(Token token) {
        System.out.println("Llega el siguiente token al PE de notificacion: " + token.getTipo());
        counter++;
        token.setT_end(this.myParent.getCurrentTime());
        System.out.println("Han llegado "+counter+" al PE de notificacion");
        System.out.println("Tiempo de inicio: " + token.getT_init());
        System.out.println("Tiempo de fin: " + token.getT_end());
        
        //Aquí obtiene la ubicación para enviar un mensaje a través de la red 4g
        Coord location = getNodeLocation();
        System.out.println("## LAST PE: ("+location.getX()+", "+location.getY()+")");
    }    
    
    /**
     * Obtiene la ubicación de uno de los nodos del modelo de movilidad
     * @return Objeto Coord con la ubicación del nodo
     */
    public Coord getNodeLocation(){
        int length = oneSimulator.getWorld().getHosts().size();
        Random random = new Random();
        
        return oneSimulator.getWorld().getHosts().get(random.nextInt(length)).getLocation();
    }
}
