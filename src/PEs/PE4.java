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
public class PE4 extends AbstractPE {

    private final PECounter PE5, PE6;
    private final PE7 PE7;
    private Random random;
    
    public PE4(int id, String nombre, String next_pe, Processor processor, PECounter PE5, PECounter PE6, PE7 PE7) {
        super(id, nombre, next_pe, processor);
        this.PE5 = PE5;
        this.PE6 = PE6;
        this.PE7 = PE7;
    }

    @Override
    public void sendMessage(Token token) throws JSimSecurityException {
        int random_number = (int) this.random.nextDouble()*3;
        switch(random_number){
            //Caso de incendio
            case 1:{
                this.PE5.receiveMessage(token);
                break;
            }
            //Caso de robo
            case 2:{
                this.PE6.receiveMessage(token);
                break;
            }
            //Caso de accidente
            case 3:{
                this.PE7.receiveMessage(token);
                break;
            }
            
        }
    }

    @Override
    public void receiveMessage(Token token) {
        //Recibe mensaje desde el clasificador
    }

}
