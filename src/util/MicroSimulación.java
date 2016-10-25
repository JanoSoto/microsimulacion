/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import PEs.Adapter;
import PEs.Classifier;
import PEs.GenericPE;
import PEs.LastPEDataBase;
import PEs.LastPENotification;
import PEs.PE4;
import PEs.PE7;
import PEs.PECounter;
import cz.zcu.fav.kiv.jsim.*;
import cz.zcu.fav.kiv.jsim.ipc.JSimMessageBox;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import processors.Processor;

/**
 *
 * @author JAno
 */
public class MicroSimulación {

    public static final int NUMBER_OF_PROCESSES = 1;
    public static final double SIMULATION_FINAL_TIME = 0.3;
    public static final double lambda = 0.5; 

    /**
     * @param args the command line arguments
     */
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) {
        JSimSimulation simulation = null;
        JSimMessageBox box = null;
        
        try {   
            simulation = new JSimSimulation("Simulador 1");
            box = new JSimMessageBox("Caja de mensajes compartida");
            //Creación de los procesadores
            Processor procesador1 = new Processor("Procesador 1", simulation, box);
            Processor procesador2 = new Processor("Procesador 2", simulation, box);

            //Creación de los PEs
            GenericPE PE1 = new GenericPE(1, "PE1", "PE3", procesador1, simulation);
            GenericPE PE2 = new GenericPE(2, "PE2", "PE3", procesador1, simulation);
            GenericPE PE3 = new GenericPE(3, "PE3", "DataBase", procesador1, simulation);
            PECounter PE5 = new PECounter(5, "PE5", "PE9", procesador1, simulation);
            PECounter PE6 = new PECounter(6, "PE6", "PE9", procesador1, simulation);
            PECounter PE8 = new PECounter(8, "PE8", "PE9", procesador2, simulation);
            GenericPE PE9 = new GenericPE(9, "PE9", "PE10", procesador2, simulation);
            PE7 PE7 = new PE7(7, "PE7", "PE8 PE9", procesador1, PE9, PE8, simulation);
            PE4 PE4 = new PE4(4, "PE4", "PE5 PE6 P7", procesador1, PE5, PE6, PE7, simulation);
            GenericPE PE10 = new GenericPE(10, "PE10", "PE11", procesador2, simulation);
            GenericPE PE11 = new GenericPE(11, "PE11", "PE12", procesador2, simulation);
            GenericPE PE12 = new GenericPE(12, "PE12", "PE13", procesador2, simulation);
            GenericPE PE13 = new GenericPE(13, "PE13", "Notification", procesador2, simulation);
            LastPEDataBase PEDataBase = new LastPEDataBase(14, "DataBase", "", procesador2, simulation);
            LastPENotification PENotification = new LastPENotification(15, "Notification", "", procesador2, simulation);
            
            //Creación del clasifier
            Classifier classifier = new Classifier(0, "Classifier", "PE1 PE2 PE4", procesador1, PE1, PE2, PE4, simulation);

            //Creación del adapter
            Adapter adapter = new Adapter("Adaptador", simulation, classifier, box, procesador1, procesador2);
            //Adapter adapter = new Adapter("Adaptador", simulation, procesador1, box);
            
            //Asignación de los PEs a los procesadores
            procesador1.getPe_list().put("Classifier", classifier);
            procesador1.getPe_list().put("PE1", PE1);
            procesador1.getPe_list().put("PE2", PE2);
            procesador1.getPe_list().put("PE3", PE3);
            procesador1.getPe_list().put("PE4", PE4);
            procesador1.getPe_list().put("PE5", PE5);
            procesador1.getPe_list().put("PE6", PE6);
            procesador1.getPe_list().put("PE7", PE7);

            procesador2.getPe_list().put("PE8", PE8);
            procesador2.getPe_list().put("PE9", PE9);
            procesador2.getPe_list().put("PE10", PE10);
            procesador2.getPe_list().put("PE11", PE11);
            procesador2.getPe_list().put("PE12", PE12);
            procesador2.getPe_list().put("PE13", PE13);
            procesador2.getPe_list().put("DataBase", PEDataBase);
            procesador2.getPe_list().put("Notification", PENotification);

            //Creación de la tabla de ruteo
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

            RouteTable routeTable = new RouteTable(rt);

            procesador1.setRouteTable(routeTable);
            procesador2.setRouteTable(routeTable);

            // main simulation loop
            System.out.println("Inicio de la simulacion");
            
            //active procesor
            System.out.println("MAIN ACTIVA EL ADAPTER");
            adapter.activateNow();
            System.out.println("ACTIVANDO EL PROCESADOR 1");
            procesador1.activateNow();
            System.out.println("ACTIVANDO EL PROCESADOR 2");
            procesador2.activateNow();
            
            while(simulation.step());
            

        } catch (JSimException e) {
            e.printStackTrace();
            e.printComment(System.err);
        } finally {
            simulation.shutdown();
        }
    }

}
