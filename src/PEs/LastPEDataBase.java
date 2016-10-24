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
public class LastPEDataBase extends AbstractPE {

    public LastPEDataBase(int id, String nombre, String next_pe, Processor processor) {
        super(id, nombre, next_pe, processor);
    }

    @Override
    public void sendMessage(Token token) {

    }

    @Override
    public void receiveMessage(Token token) {
        //TODO Función hold con el valor de guardar en disco
    }

}
