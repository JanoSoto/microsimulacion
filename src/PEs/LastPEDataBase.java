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
import simulatorUtil.AbstractPE;
import simulatorUtil.Token;

/**
 *
 * @author JAno
 */
public class LastPEDataBase extends AbstractPE {

    public LastPEDataBase(int id, String nombre, String next_pe, Processor processor, JSimSimulation simulation) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException{
        super(nombre, simulation, next_pe, processor);
    }
    
    public LastPEDataBase(int id, String nombre, String next_pe, JSimSimulation simulation) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException{
        super(nombre, simulation, next_pe);
    }

    @Override
    public void sendMessage(Token token) throws JSimSecurityException {
        token.setSender(this.getName());
        token.setPosting("final");
        JSimLink link = new JSimLink(token);
        link.into(this.getProcessor().getQueue());
    }

    @Override
    public void receiveMessage(Token token) throws JSimSecurityException {
        System.out.println("Llega el siguiente token a la DB: " + token.getTipo() + "desde " + token.getSender());
        this.addToCounter();
        token.setT_end(this.myParent.getCurrentTime());
        sendMessage(token);
    }

}
