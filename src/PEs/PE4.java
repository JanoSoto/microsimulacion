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
public class PE4 extends AbstractPE {

    private final PECounter PE5, PE6;
    private final PE7 PE7;
    private Random random;
    
    public PE4(int id, String nombre, String next_pe, Processor processor, PECounter PE5, PECounter PE6, PE7 PE7, JSimSimulation simulation) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException {
        super(nombre, simulation, next_pe, processor);
        this.PE5 = PE5;
        this.PE6 = PE6;
        this.PE7 = PE7;
    }
    
    public PE4(int id, String nombre, String next_pe, PECounter PE5, PECounter PE6, PE7 PE7, JSimSimulation simulation) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException {
        super(nombre, simulation, next_pe);
        this.PE5 = PE5;
        this.PE6 = PE6;
        this.PE7 = PE7;
    }

    @Override
    public void sendMessage(Token token) throws JSimSecurityException, JSimInvalidParametersException {
        //int random_number = (int) this.random.nextDouble()*2;
        double random_number = uniform(0.5, 3.5);
        switch((int) Math.round(random_number)){
            //Caso de incendio
            case 1:{
                //this.PE5.receiveMessage(token);
                System.out.println("** Enviando mensaje desde PE4 hacia PE5");
                token.setSender(this.getName());
                token.setPosting("PE5");
                JSimLink link = new JSimLink(token);
                link.into(this.getProcessor().getQueue());
                break;
            }
            //Caso de robo
            case 2:{
                //this.PE6.receiveMessage(token);
                System.out.println("** Enviando mensaje desde PE4 hacia PE6");
                token.setSender(this.getName());
                token.setPosting("PE6");
                JSimLink link = new JSimLink(token);
                link.into(this.getProcessor().getQueue());
                break;
            }
            //Caso de accidente
            case 3:{
                //this.PE7.receiveMessage(token);
                System.out.println("** Enviando mensaje desde PE4 hacia PE7");
                token.setSender(this.getName());
                token.setPosting("PE7");
                JSimLink link = new JSimLink(token);
                link.into(this.getProcessor().getQueue());
                break;
            }
            
        }
    }

    @Override
    public void receiveMessage(Token token) {
        try {
            sendMessage(token);
        } catch (JSimSecurityException | JSimInvalidParametersException ex) {
            ex.printStackTrace(System.out);
        }
    }

}
