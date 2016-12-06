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
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import cz.zcu.fav.kiv.jsim.JSimSimulationAlreadyTerminatedException;
import cz.zcu.fav.kiv.jsim.JSimSystem;
import cz.zcu.fav.kiv.jsim.JSimTooManyHeadsException;
import cz.zcu.fav.kiv.jsim.JSimTooManyProcessesException;
import cz.zcu.fav.kiv.jsim.ipc.JSimMessageBox;
import java.util.HashMap;
import simulatorUtil.AbstractPE;
import simulatorUtil.ComunicationPipe;
import simulatorUtil.ProcessingTime;
import simulatorUtil.RouteTable;
import simulatorUtil.Token;

/**
 *
 * @author JAno
 */
public class Processor extends JSimProcess {

    private ProcessingTime pt;
    private JSimHead queue;
    private HashMap<String, AbstractPE> pe_list;
    private RouteTable routeTable;
    private JSimMessageBox box;
    private ComunicationPipe pipe;
    private double busyTime;
    private int counter;

    public Processor(String name, JSimSimulation simulation, JSimMessageBox box) 
            throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException, JSimTooManyHeadsException {
        super(name, simulation);
        this.queue = new JSimHead("requests_"+name, simulation);
        this.pe_list = new HashMap<>();
        this.routeTable = null;
        this.box = box;
        this.busyTime = 0.0;
        this.counter = 0;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public double getBusyTime() {
        return busyTime;
    }

    public void setBusyTime(double busyTime) {
        this.busyTime = busyTime;
    }
    
    public void setPipe(ComunicationPipe pipe){
        this.pipe = pipe;
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
            message("SOY UN PROCESADOR " + this.getName() + " Y ESTOY VIVO");
            int count = 0;
            double initialTime = this.myParent.getCurrentTime();
            double peTime;
            //this.myParent.message("**** Tiempo inicial del "+this.getName()+": "+initialTime);
            while (true) {
                if (!queue.empty()) {
                    this.counter++;
                    
                    link = queue.first();
                    Token token = (Token) link.getData();
                    link.out();
                    
                    peTime = this.myParent.getCurrentTime();
                    
                    //Hace hold con una exponencial negativa para simular el tiempo de procesamiento
                    hold(JSimSystem.negExp(token.getLambda()));
                    
                    peTime = this.myParent.getCurrentTime() - peTime;
                    pe_list.get(token.getSender()).addToServiceTime(peTime);
                    //Envía el mensaje al siguiente PE. Si no lo encuentra, lo envía al otro procesador.
                    if (pe_list.containsKey(token.getPosting())) {
                        this.myParent.message("-- " + this.getName() + ": Enviando token desde " + token.getSender() + " hacia " + token.getPosting() + "[" + this.getName() + "]");
                        pe_list.get(token.getPosting()).receiveMessage(token);
                        hold(0.1);
                        //pipe.receiveMessage(token);
                    } 
                    else {                        
                        Processor postingProc = this.routeTable.getRouteTable().get(token.getPosting());                        
                        System.out.println("-- " + this.getName() + ": Enviando token desde " + token.getSender() + " hacia " + token.getPosting() + "[" + postingProc.getName() + "]");
                        if(postingProc.isIdle()){
                            initialTime = this.myParent.getCurrentTime();
                            System.out.println(this.getName() + " DESPIERTA AL " + postingProc.getName());
                            postingProc.activateNow();
                        }
                        postingProc.getPe_list().get(token.getPosting()).receiveMessage(token);
                        
                        //Simulación del tiempo de comunicación
                        hold(1);
                        //pipe.receiveMessage(token);
                    }
                    count = 0;
                }
                else{
                    if(count < 10){                        
                        message(this.getName() + ": LA COLA ESTA VACIA");
                        count++;
                    }
                    else{
                        message("EL " + this.getName() + " SE DUERME");
                        double aux = this.myParent.getCurrentTime() - initialTime;
                        this.myParent.message("Tiempo agregado al "+this.getName()+": "+aux+"Tiempo anterior: "+this.busyTime+", Tiempo total: "+(this.busyTime+aux));
                        this.busyTime += aux;
                        this.passivate();
                    }
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
