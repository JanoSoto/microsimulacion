/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import cz.zcu.fav.kiv.jsim.JSimInvalidParametersException;
import cz.zcu.fav.kiv.jsim.JSimLink;
import cz.zcu.fav.kiv.jsim.JSimProcess;
import cz.zcu.fav.kiv.jsim.JSimSecurityException;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import cz.zcu.fav.kiv.jsim.JSimSimulationAlreadyTerminatedException;
import cz.zcu.fav.kiv.jsim.JSimTooManyProcessesException;
import processors.Processor;

/**
 *
 * @author JAno
 */
public abstract class AbstractPE extends JSimProcess{

    private String next_pe;
    private Processor processor;

    public AbstractPE(String nombre, JSimSimulation simulation, String next_pe, Processor processor) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException {
        super(nombre, simulation);
        this.next_pe = next_pe;
        this.processor = processor;
    }

    public String getNext_pe() {
        return next_pe;
    }

    public void setNext_pe(String next_pe) {
        this.next_pe = next_pe;
    }

    public Processor getProcessor() {
        return processor;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    public void sendMessage(Token token) throws JSimSecurityException, JSimInvalidParametersException {
        System.out.println("-- Soy el " + this.getName() + " y estoy enviando un mensaje hacia " + this.next_pe);
        token.setSender(this.getName());
        token.setPosting(this.next_pe);
        JSimLink link = new JSimLink(token);
        link.into(this.processor.getQueue());
        
    }

    public void receiveMessage(Token token) throws JSimSecurityException, JSimInvalidParametersException {
        sendMessage(token);
    }
}
