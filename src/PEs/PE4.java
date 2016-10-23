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
public class PE4 extends AbstractPE{

    public PE4(int id, String nombre, String next_pe, Processor processor) {
        super(id, nombre, next_pe, processor);
    }

    @Override
    public void sendMessage(Token token) {
        //Calcula una probabilidad de enviar para incendio (PE5), robo (PE6) o accidente (PE7)
    }

    @Override
    public void receiveMessage(Token token) {
        //Recibe mensaje desde el clasificador
    }
    
}
