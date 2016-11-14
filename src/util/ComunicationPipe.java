/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

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
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import processors.Processor;

/**
 *
 * @author JAno
 */
public class ComunicationPipe extends JSimProcess{
    
    private RouteTable routeTable;
    private JSimHead queue;
    
    public ComunicationPipe(String nombre, JSimSimulation simulation, Processor procesador1, Processor procesador2) 
            throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException, JSimTooManyHeadsException {
        super(nombre, simulation);
        //Route table
        HashMap<String, Processor> rt = new HashMap<>();
        rt.put("classifier", procesador1);
        rt.put("PE1", procesador1);
        rt.put("PE2", procesador1);
        rt.put("PE3", procesador1);
        rt.put("PE4", procesador1);
        rt.put("PE5", procesador1);
        rt.put("PE6", procesador1);
        rt.put("PE7", procesador1);
        rt.put("PE8", procesador2);
        rt.put("PE9", procesador2);
        rt.put("PE10", procesador2);
        rt.put("PE11", procesador2);
        rt.put("PE12", procesador2);
        rt.put("PE13", procesador2);
        rt.put("DataBase", procesador2);
        rt.put("Notification", procesador2);
        this.routeTable = new RouteTable(rt);
        this.queue = new JSimHead("requests_pipe", simulation);
    }
    
    
    @Override
    public void life(){
        JSimLink link;
        while(true){
            if(!this.queue.empty()){
                link = this.queue.first();
                Token token = (Token) link.getData();
                try {
                    Processor processor = this.routeTable.getRouteTable().get(token.getPosting());
                    if(processor.isIdle()){
                        System.out.println("DESPERTANDO AL "+processor.getName());
                        processor.activateNow();
                    }
                    processor.getPe_list().get(token.getPosting()).receiveMessage(token);
                    hold(JSimSystem.negExp(token.getLambda()));
                } catch (JSimSecurityException | JSimInvalidParametersException ex) {
                    Logger.getLogger(ComunicationPipe.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else{
                message("LA COLA DEL PIPE ESTÁ VACÍA");
                try {
                    hold(0.001);
                } catch (JSimSecurityException ex) {
                    Logger.getLogger(ComunicationPipe.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JSimInvalidParametersException ex) {
                    Logger.getLogger(ComunicationPipe.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void receiveMessage(Token token) throws JSimSecurityException{
        JSimLink link = new JSimLink(token);
        link.into(this.queue);
    }
    
    public void send(AbstractPE posting, Token token) throws JSimSecurityException, JSimInvalidParametersException{
        posting.receiveMessage(token);
    }
}
