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
public class Classifier extends AbstractPE {
    
    //private int counter;
    
    public Classifier(int id, String nombre, String next_pe, Processor processor, GenericPE PE1, GenericPE PE2, PE4 PE4, JSimSimulation simulation) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException {
        super(nombre, simulation, next_pe, processor);        
    }
    
    public Classifier(int id, String nombre, String next_pe, GenericPE PE1, GenericPE PE2, PE4 PE4, JSimSimulation simulation) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException {
        super(nombre, simulation, next_pe);       
    }
/*
    public int getCounter() {
        return counter;
    }

    public void setCounter(int count) {
        this.counter = count;
    }
*/ 
    @Override
    public void sendMessage(Token token) throws JSimSecurityException {
        switch (token.getTipo()) {
            case "inscripcion": {
                //Enviar al PE1
                //System.out.println("Envio un mensaje al PE1");
                //this.PE1.receiveMessage(token);
                System.out.println("** Enviando mensaje desde " + this.getName() + " hacia PE1");
                token.setSender(this.getName());
                token.setPosting("PE1");
                JSimLink link = new JSimLink(token);
                link.into(this.getProcessor().getQueue());
                break;
            }
            case "localizacion": {
                //Enviar al PE2
                //System.out.println("Envio un mensaje al PE2");
                //this.PE2.receiveMessage(token);
                System.out.println("** Enviando mensaje desde " + this.getName() + " hacia PE2");
                token.setSender(this.getName());
                token.setPosting("PE2");
                JSimLink link = new JSimLink(token);
                link.into(this.getProcessor().getQueue());
                break;
            }
            case "sos": {
                //Enviar al PE4
                //System.out.println("Envio un mensaje al PE4");
                //this.PE4.receiveMessage(token);
                System.out.println("** Enviando mensaje desde " + this.getName() + " hacia PE4");
                token.setSender(this.getName());
                token.setPosting("PE4");
                JSimLink link = new JSimLink(token);
                link.into(this.getProcessor().getQueue());
                break;
            }
            default:
                System.out.println("Tipo de token inválido");
        }
    }
    
    public void receiveMessage(Token token) throws JSimSecurityException {
        //System.out.println("Soy el clasificador y recibo un mensaje");
        this.addToCounter();
        this.sendMessage(token);
    }
    
}
