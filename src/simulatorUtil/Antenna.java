/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatorUtil;

import cz.zcu.fav.kiv.jsim.JSimException;
import cz.zcu.fav.kiv.jsim.JSimHead;
import cz.zcu.fav.kiv.jsim.JSimInvalidParametersException;
import cz.zcu.fav.kiv.jsim.JSimLink;
import cz.zcu.fav.kiv.jsim.JSimProcess;
import cz.zcu.fav.kiv.jsim.JSimSecurityException;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import cz.zcu.fav.kiv.jsim.JSimSimulationAlreadyTerminatedException;
import cz.zcu.fav.kiv.jsim.JSimSystem;
import cz.zcu.fav.kiv.jsim.JSimTooManyHeadsException;
import cz.zcu.fav.kiv.jsim.JSimTooManyProcessesException;
import java.util.logging.Level;
import java.util.logging.Logger;
import processors.Processor;

/**
 *
 * @author JAno
 */
public class Antenna extends JSimProcess{
    
    private JSimHead queue;
    
    public Antenna(String name, JSimSimulation simulation) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException, JSimTooManyHeadsException {
        super(name, simulation);
        this.queue = new JSimHead("network", simulation);
    }

    public JSimHead getQueue() {
        return queue;
    }

    public void setQueue(JSimHead queue) {
        this.queue = queue;
    }
    
    @Override
    protected void life(){        
        try {
            JSimLink link;
            while (true) {
                if (!queue.empty()) {
                    
                    link = queue.first();
                    int cantAntenas = (int) link.getData();
                    link.out();
    
                    //Hace hold con una exponencial negativa para simular el tiempo de procesamiento
                    System.out.println("HOLD de la antena");
                    hold(JSimSystem.negExp(0.3)*cantAntenas);

                }
                else{
                    this.myParent.message("La antena se duerme");
                    this.passivate();                    
                    hold(0.001);
                }
            }
        } 
        catch (JSimException e) {
            e.printStackTrace(System.out);
            e.printComment();
        }
        
    }
}
