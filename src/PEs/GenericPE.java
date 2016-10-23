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
public class GenericPE extends AbstractPE{
    
    public GenericPE(int id, String nombre, String next_pe, Processor processor) {
        super(id, nombre, next_pe, processor);
    }
    
}
