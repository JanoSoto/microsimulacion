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
public class PE13 extends AbstractPE{

    public PE13(int id, String nombre, String next_pe, Processor processor) {
        super(id, nombre, next_pe, processor);
    }

    @Override
    public void sendMessage(Token token) {
        //Envia mensaje al LastPENotificaton
    }

    @Override
    public void receiveMessage(Token token) {
        //Recibe desde el PE12
    }
    
}
