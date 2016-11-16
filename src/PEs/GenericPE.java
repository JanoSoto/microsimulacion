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
public class GenericPE extends AbstractPE {

    public GenericPE(int id, String nombre, String next_pe, Processor processor, JSimSimulation simulation) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException {
        super(nombre, simulation, next_pe, processor);
    }
    
    
    @Override
    public void sendMessage(Token token) throws JSimSecurityException, JSimInvalidParametersException {
        System.out.println("GENERIC PE");
        System.out.println("** Enviando mensaje desde " + this.getName() + " hacia " + this.getNext_pe());
        token.setSender(this.getName());
        token.setPosting(this.getNext_pe());
        JSimLink link = new JSimLink(token);
        link.into(this.getProcessor().getQueue());
        
    }

    @Override
    public void receiveMessage(Token token) throws JSimSecurityException, JSimInvalidParametersException {
        sendMessage(token);     
    }

}
