/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import cz.zcu.fav.kiv.jsim.JSimInvalidParametersException;
import cz.zcu.fav.kiv.jsim.JSimProcess;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import cz.zcu.fav.kiv.jsim.JSimSimulationAlreadyTerminatedException;
import cz.zcu.fav.kiv.jsim.JSimSystem;
import cz.zcu.fav.kiv.jsim.JSimTooManyProcessesException;

/**
 *
 * @author JAno
 */
public class Adapter extends JSimProcess{
    
    private int total_messages;
    private AbstractPE pe_inicial;
    
    public Adapter(String string, JSimSimulation jss, int total_messages, AbstractPE pe_inicial) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException {
        super(string, jss);
        this.total_messages = total_messages;
        this.pe_inicial = pe_inicial;
    }

    public int getTotal_messages() {
        return total_messages;
    }

    public void setTotal_messages(int total_messages) {
        this.total_messages = total_messages;
    }

    public AbstractPE getPe_inicial() {
        return pe_inicial;
    }

    public void setPe_inicial(AbstractPE pe_inicial) {
        this.pe_inicial = pe_inicial;
    }
    
    public void generateMessages(){
        double value = JSimSystem.uniform(0, 1);
    }
    
}
