/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PEs;

import cz.zcu.fav.kiv.jsim.JSimSecurityException;
import java.util.Random;
import processors.Processor;
import util.AbstractPE;
import util.Token;

/**
 *
 * @author JAno
 */
public class PE7 extends AbstractPE {

    private int counter;
    private final GenericPE PE9;
    private final PECounter PE8;
    private Random random;

    public PE7(int id, String nombre, String next_pe, Processor processor, GenericPE PE9, PECounter PE8) {
        super(id, nombre, next_pe, processor);
        this.PE8 = PE8;
        this.PE9 = PE9;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    @Override
    public void sendMessage(Token token) throws JSimSecurityException {
        int random_number = (int) this.random.nextDouble()*2;
        switch(random_number){
            //Notificar
            case 1:{
                this.PE9.receiveMessage(token);
                break;
            }
            //Llamar a emergencias
            case 2:{
                this.PE8.receiveMessage(token);
                break;
            }
            
        }
    }

    @Override
    public void receiveMessage(Token token) {
        //Recibe desde PE4
        counter++;
    }

}
