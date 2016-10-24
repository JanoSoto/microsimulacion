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
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.AbstractPE;
import util.ProcessingTime;
import util.RouteTable;
import util.Token;

/**
 *
 * @author JAno
 */
public class Processor extends JSimProcess {

    private ProcessingTime pt;
    private JSimHead queue;
    private HashMap<String, AbstractPE> pe_list;
    private RouteTable routeTable;

    public Processor(String name, JSimSimulation simulation) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException, JSimTooManyHeadsException {
        super(name, simulation);
        this.queue = new JSimHead("requests", simulation);
        this.pe_list = new HashMap<>();
        this.routeTable = null;
    }

    public JSimHead getQueue() {
        return queue;
    }

    public void setQueue(JSimHead queue) {
        this.queue = queue;
    }

    public HashMap<String, AbstractPE> getPe_list() {
        return pe_list;
    }

    public void setPe_list(HashMap<String, AbstractPE> pe_list) {
        this.pe_list = pe_list;
    }

    public void setProcessingTime(ProcessingTime pt) {
        this.pt = pt;
    }

    public double getProcessingTime(double lambda) {
        return pt.getProccesingTime(lambda);
    }

    public RouteTable getRouteTable() {
        return routeTable;
    }

    public void setRouteTable(RouteTable routeTable) {
        this.routeTable = routeTable;
    }

    @Override
    protected void life() {
        try {
            JSimLink link;
            double time;

            while (true) {
                time = this.myParent.getCurrentTime();

                if (!queue.empty()) {
                    link = queue.first();
                    Token token = (Token) link.getData();
                    link.out();

                    //TODO Escribir mensaje que emitirá el simulador
                    message(time + "algun mensaje");

                    //Hace hold con una exponencial negativa
                    hold(JSimSystem.negExp(token.getLambda()));

                    //Envía el mensaje al siguiente PE. Si no lo encuentra, lo envía al otro procesador.
                    if (pe_list.containsKey(token.getPosting())) {
                        pe_list.get(token.getPosting()).receiveMessage(token);
                    } else {
                        Processor postingProc = this.routeTable.getRouteTable().get(token.getPosting());
                        postingProc.getPe_list().get(token.getPosting()).receiveMessage(token);
                        //Simulación del tiempo de comunicación
                        hold(1);
                    }

                }
            }
        } catch (JSimException e) {
            e.printStackTrace(System.out);
            e.printComment();
        }

    }
}
