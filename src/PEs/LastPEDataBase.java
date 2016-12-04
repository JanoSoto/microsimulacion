/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PEs;

import cz.zcu.fav.kiv.jsim.JSimInvalidParametersException;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import cz.zcu.fav.kiv.jsim.JSimSimulationAlreadyTerminatedException;
import cz.zcu.fav.kiv.jsim.JSimTooManyProcessesException;
import processors.Processor;
import simulatorUtil.AbstractPE;
import simulatorUtil.Token;

/**
 *
 * @author JAno
 */
public class LastPEDataBase extends AbstractPE {

    private int counter;
    
    public LastPEDataBase(int id, String nombre, String next_pe, Processor processor, JSimSimulation simulation) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException{
        super(nombre, simulation, next_pe, processor);
        this.counter = 0;
    }
    
    public LastPEDataBase(int id, String nombre, String next_pe, JSimSimulation simulation) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException{
        super(nombre, simulation, next_pe);
        this.counter = 0;
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
        System.out.println("Llega el siguiente token a la DB: " + token.getTipo() + "desde " + token.getSender());
        counter++;
        token.setT_end(this.myParent.getCurrentTime());
        //System.out.println("Han llegado "+counter+" a la DB");
        //System.out.println("Tiempo de inicio: " + token.getT_init());
        //System.out.println("Tiempo de fin: " + token.getT_end());
    }

}
