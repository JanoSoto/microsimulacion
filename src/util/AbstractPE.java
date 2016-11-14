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
    private Token token;

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

    public abstract void sendMessage(Token token) throws JSimSecurityException, JSimInvalidParametersException;

    public abstract void receiveMessage(Token token) throws JSimSecurityException, JSimInvalidParametersException;
    
    @Override
    public void life(){
        try {
            this.sendMessage(token);
            hold(0.1);
        } 
        catch (JSimSecurityException | JSimInvalidParametersException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
