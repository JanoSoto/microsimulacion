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
public class PE10 extends AbstractPE {

    public PE10(int id, String nombre, String next_pe, Processor processor) {
        super(id, nombre, next_pe, processor);
    }

    @Override
    public void sendMessage(Token token) {
        //Hace solicitud a la base de datos
        //Envia mensaje a PE11
    }

    @Override
    public void receiveMessage(Token token) {
        //Recibe mensaje desde PE9
    }

}
