/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PEs;

import cz.zcu.fav.kiv.jsim.JSimInvalidParametersException;
import cz.zcu.fav.kiv.jsim.JSimLink;
import cz.zcu.fav.kiv.jsim.JSimSecurityException;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import cz.zcu.fav.kiv.jsim.JSimSimulationAlreadyTerminatedException;
import cz.zcu.fav.kiv.jsim.JSimTooManyProcessesException;
import processors.Processor;
import util.AbstractPE;
import util.Token;

/**
 *
 * @author JAno
 */
public class PECounter extends AbstractPE {

    private int counter;

    public PECounter(int id, String nombre, String next_pe, Processor processor, JSimSimulation simulation) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException{
        super(nombre, simulation, next_pe, processor);
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    @Override
    public void sendMessage(Token token) throws JSimSecurityException, JSimInvalidParametersException {
        System.out.println("** Enviando mensaje desde " + this.getName() + " hacia " + this.getNext_pe());
        token.setSender(this.getName());
        token.setPosting(this.getNext_pe());
        JSimLink link = new JSimLink(token);
        link.into(this.getProcessor().getQueue());
        
    }
    
    @Override
    public void receiveMessage(Token token) {
        System.out.println("Soy el "+ this.getName() + " y me ha llegado el siguiente token: "+token.getTipo());
        counter++;
        System.out.println("Han llegado "+counter+" al "+this.getName());

    }

}
