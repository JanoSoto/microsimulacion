/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatorUtil;

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
    private int counter;
    private double serviceTime;
    
    public AbstractPE(String nombre, JSimSimulation simulation, String next_pe, Processor processor) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException {
        super(nombre, simulation);
        this.next_pe = next_pe;
        this.processor = processor;
        this.counter = 0;
        this.serviceTime = 0.0;
    }
    
    public AbstractPE(String nombre, JSimSimulation simulation, String next_pe) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException {
        super(nombre, simulation);
        this.next_pe = next_pe;
        this.counter = 0;
        this.serviceTime = 0.0;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
    
    public void addToCounter(){
        this.counter++;
    }

    public double getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(double serviceTime) {
        this.serviceTime = serviceTime;
    }
    
    public void addToServiceTime(double time){
        this.serviceTime += time;
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
