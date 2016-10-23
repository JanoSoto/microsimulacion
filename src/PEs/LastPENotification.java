/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PEs;

import processors.Processor;
import util.AbstractPE;
import util.Token;

/**
 *
 * @author JAno
 */
public class LastPENotification extends AbstractPE{
    
    private int counter;
    
    public LastPENotification(int id, String nombre, String next_pe, Processor processor) {
        super(id, nombre, next_pe, processor);
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
        this.counter++;
    }
    
}
