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
import static cz.zcu.fav.kiv.jsim.JSimSystem.uniform;
import cz.zcu.fav.kiv.jsim.JSimTooManyProcessesException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import processors.Processor;
import simulatorUtil.AbstractPE;
import simulatorUtil.Token;

/**
 *
 * @author JAno
 */
public class PE7 extends AbstractPE {

    private int counter;
    private final GenericPE PE9;
    private final PECounter PE8;
    private Random random;

    public PE7(int id, String nombre, String next_pe, Processor processor, GenericPE PE9, PECounter PE8, JSimSimulation simulation) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException{
        super(nombre, simulation, next_pe, processor);
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
    public void sendMessage(Token token) throws JSimSecurityException, JSimInvalidParametersException {
        //int random_number = (int) this.random.nextDouble()*2;
        double random_number = uniform(0.5, 3.5);
        switch((int) Math.round(random_number)){
            //Notificar
            case 1:{
                //this.PE9.receiveMessage(token);
                System.out.println("** Enviando mensaje desde PE7 hacia PE9");
                token.setSender(this.getName());
                token.setPosting("PE9");
                JSimLink link = new JSimLink(token);
                link.into(this.getProcessor().getQueue());
                break;
            }
            //Llamar a emergencias
            case 2:{
                //this.PE8.receiveMessage(token);
                System.out.println("** Enviando mensaje desde PE7 hacia PE8");
                token.setSender(this.getName());
                token.setPosting("PE8");
                JSimLink link = new JSimLink(token);
                link.into(this.getProcessor().getQueue());
                break;
            }
            
        }
        
    }

    @Override
    public void receiveMessage(Token token) {
        //Recibe desde PE4
        counter++;
        try {
            sendMessage(token);
        } catch (JSimSecurityException | JSimInvalidParametersException ex) {
            ex.printStackTrace(System.out);
        }
    }

}
