/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processors;

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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.AbstractPE;
import util.ProcessingTime;
import util.Token;

/**
 *
 * @author JAno
 */
public class Processor extends JSimProcess{
    
    private ProcessingTime pt;
    private JSimHead queue;
    private ArrayList<AbstractPE> pe_list;
    //TODO Hay que definir la tabla de ruteo

    public Processor(String name, JSimSimulation simulation) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException, JSimTooManyHeadsException {
        super(name, simulation);
        this.queue = new JSimHead("requests", simulation);
        this.pe_list = new ArrayList();
    }

    public JSimHead getQueue() {
        return queue;
    }

    public void setQueue(JSimHead queue) {
        this.queue = queue;
    }

    public ArrayList<AbstractPE> getPe_list() {
        return pe_list;
    }

    public void setPe_list(ArrayList<AbstractPE> pe_list) {
        this.pe_list = pe_list;
    }
    
    public void setProcessingTime(ProcessingTime pt){
        this.pt = pt;
    }
    
    public double getProcessingTime(double lambda){
        return pt.getProccesingTime(lambda);
    }
    
    @Override
    protected void life(){
        try{
            JSimLink link;
            double time;

            while(true){
                time = this.myParent.getCurrentTime();

                if(!queue.empty()){
                    link = queue.first();
                    Token token = (Token) link.getData();
                    link.out();
                    //TODO Escribir mensaje que emitirá el simulador
                    message(time + "algun mensaje");
                    hold(JSimSystem.negExp(token.getLambda()));
                }
            }
        }
        catch(JSimException e){
            e.printStackTrace();
            e.printComment();
        }
        
    }
}
