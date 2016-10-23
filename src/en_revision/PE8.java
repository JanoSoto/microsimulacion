/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package en_revision;

import processors.Processor;
import util.AbstractPE;
import util.Token;

/**
 *
 * @author JAno
 */
public class PE8 extends AbstractPE{

    private int counter;

    public PE8(int id, String nombre, String next_pe, Processor processor) {
        super(id, nombre, next_pe, processor);
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    @Override
    public void sendMessage(Token token) {
        //Envia mensaje a PE9
    }

    @Override
    public void receiveMessage(Token token) {
        //Recibe desde PE8
        counter++;
    }
    
}
