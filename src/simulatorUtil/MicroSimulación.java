/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatorUtil;

import PEs.Adapter;
import PEs.Classifier;
import PEs.GenericPE;
import PEs.LastPEDataBase;
import PEs.LastPENotification;
import PEs.PE4;
import PEs.PE7;
import PEs.PECounter;
import core.DTNSim;
import cz.zcu.fav.kiv.jsim.*;
import cz.zcu.fav.kiv.jsim.ipc.JSimMessageBox;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import processors.Processor;
import ui.DTNSimTextUI;

/**
 *
 * @author JAno
 */
public class MicroSimulación {

    public static final int NUMBER_OF_PROCESSES = 1;
    public static final double SIMULATION_FINAL_TIME = 0.3;
    public static final double lambda = 0.5;
    private static List<Processor> processorsList = new ArrayList();
    private static List<AbstractPE> peList = new ArrayList();

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

            //Creación del objeto que simula la red 4G
            Antenna network = new Antenna("Network", simulation);
            
            //Creación de los PEs
            GenericPE PE1 = new GenericPE(1, "PE1", "PE3", simulation);
            GenericPE PE2 = new GenericPE(2, "PE2", "PE3", simulation);
            GenericPE PE3 = new GenericPE(3, "PE3", "DataBase", simulation);
            PECounter PE5 = new PECounter(5, "PE5", "PE9", simulation);
            PECounter PE6 = new PECounter(6, "PE6", "PE9", simulation);
            PECounter PE8 = new PECounter(8, "PE8", "PE9", simulation);
            GenericPE PE9 = new GenericPE(9, "PE9", "PE10", simulation);
            PE7 PE7 = new PE7(7, "PE7", "PE8 PE9", PE9, PE8, simulation);
            PE4 PE4 = new PE4(4, "PE4", "PE5 PE6 P7", PE5, PE6, PE7, simulation);
            GenericPE PE10 = new GenericPE(10, "PE10", "PE11", simulation);
            GenericPE PE11 = new GenericPE(11, "PE11", "PE12", simulation);
            GenericPE PE12 = new GenericPE(12, "PE12", "PE13", simulation);
            GenericPE PE13 = new GenericPE(13, "PE13", "Notification", simulation);
            LastPEDataBase PEDataBase = new LastPEDataBase(14, "DataBase", "", simulation);
            LastPENotification PENotification = new LastPENotification(15, "Notification", "", simulation, network);

            //Guardado de PEs en la lista
            peList.add(PE1);
            peList.add(PE2);
            peList.add(PE3);
            peList.add(PE4);
            peList.add(PE5);
            peList.add(PE6);
            peList.add(PE7);
            peList.add(PE8);
            peList.add(PE9);
            peList.add(PE10);
            peList.add(PE11);
            peList.add(PE12);
            peList.add(PE13);
            peList.add(PEDataBase);
            peList.add(PENotification);

            //Creación del clasifier
            Classifier classifier = new Classifier(0, "Classifier", "PE1 PE2 PE4", PE1, PE2, PE4, simulation);
            peList.add(0, classifier);

            //Creación del adapter
            Adapter adapter = new Adapter("Adaptador", simulation, classifier, box);
            //Adapter adapter = new Adapter("Adaptador", simulation, procesador1, box);

            //Cargando la distribución de PEs en procesadores
            //setDefaultLoadDistribution(simulation, box);
            //setRoundRobinDistribution(simulation, box, 4);
            setRandomDistribution(simulation, box, 4);

            //Creación de la simulación del modelo de movilidad con The ONE
            DTNSimTextUI oneSim = DTNSim.initTheOne();
            adapter.setOneSimulator(oneSim);
            PENotification.setOneSimulator(oneSim);

            //Inicio de la simulación del modelo de movilidad
            oneSim.start();

            // main simulation loop
            System.out.println("Inicio de la simulacion");
            double initialTime = simulation.getCurrentTime();

            System.out.println("MAIN ACTIVA EL ADAPTER");
            adapter.activateNow();
            for (Processor proc : processorsList) {
                System.out.println("ACTIVANDO EL " + proc.getName());
                proc.activateNow();
            }

            while (simulation.step());
            //while(simulation.getCurrentTime() < 100.0);

            double finalTime = simulation.getCurrentTime();

            simulation.message("Tiempo total simulacion (inicial: " + initialTime + ", final: " + finalTime + "): " + (finalTime - initialTime));
            
            for (Processor proc : processorsList) {
                simulation.message("Tiempo de uso del " + proc.getName() + ": " + proc.getBusyTime());
                simulation.message("[Estadistica de colas] Cola: "+proc.getName()+" Lw = " + proc.getQueue().getLw() + ", Tw = " + proc.getQueue().getTw() + ", Tw all = " + proc.getQueue().getTwForAllLinks());
                simulation.message("Cantidad de tokens en el "+proc.getName()+": "+proc.getCounter());
            }
            
            for(AbstractPE pe : peList){
                simulation.message("Cantidad de tokens procesados en "+pe.getName()+": "+pe.getCounter());
                simulation.message("Tiempo de procesamiento "+pe.getName()+": "+pe.getServiceTime());
                simulation.message("Tiempo promedio de servicio del "+pe.getName()+": "+ (pe.getServiceTime() / pe.getCounter()));
            }
            
            simulation.message("[Estadistica de colas - Red] Cola: "+network.getName()+" Lw = " + network.getQueue().getLw() + ", Tw = " + network.getQueue().getTw() + ", Tw all = " + network.getQueue().getTwForAllLinks());
            
            simulation.message("Cantidad de tokens entrantes: "+classifier.getCounter());
            simulation.message("Cantidad de mensajes PEDataBase: " + PEDataBase.getCounter());
            simulation.message("Cantidad de mensajes PENotification: " + PENotification.getCounter());
            

        } catch (JSimException e) {
            e.printStackTrace();
            e.printComment(System.err);
        } finally {
            simulation.shutdown();
        }
    }

    public static void setRandomDistribution(JSimSimulation simulation, JSimMessageBox box, int cantProcesadores) {
        HashMap<String, Processor> rt = new HashMap<>();
        Random random = new Random();
        try {
            for (int i = 0; i < cantProcesadores; i++) {
                processorsList.add(new Processor("Procesador " + (i + 1), simulation, box));
            }
            for (AbstractPE pe : peList) {
                int proc = random.nextInt(cantProcesadores);
                pe.setProcessor(processorsList.get(proc));
                processorsList.get(proc).getPe_list().put(pe.getName(), pe);
                rt.put(pe.getName(), processorsList.get(proc));

                simulation.message(">>Asignando " + pe.getName() + " al " + processorsList.get(proc).getName());
            }

            RouteTable routeTable = new RouteTable(rt);
            for (Processor proc : processorsList) {
                proc.setRouteTable(routeTable);
            }

        } catch (JSimSimulationAlreadyTerminatedException | JSimInvalidParametersException | JSimTooManyProcessesException | JSimTooManyHeadsException ex) {
            ex.printStackTrace(System.out);
        }

    }

    public static void setRoundRobinDistribution(JSimSimulation simulation, JSimMessageBox box, int cantProcesadores) {
        HashMap<String, Processor> rt = new HashMap<>();
        try {
            for (int i = 0; i < cantProcesadores; i++) {
                processorsList.add(new Processor("Procesador " + (i + 1), simulation, box));
            }
            for (int i = 0; i < peList.size(); i++) {
                peList.get(i).setProcessor(processorsList.get(i % cantProcesadores));
                processorsList.get(i % cantProcesadores).getPe_list().put(peList.get(i).getName(), peList.get(i));
                rt.put(peList.get(i).getName(), processorsList.get(i % cantProcesadores));

                simulation.message(">> Asignando " + peList.get(i).getName() + " al " + processorsList.get(i % cantProcesadores).getName());
            }

            RouteTable routeTable = new RouteTable(rt);
            for (Processor proc : processorsList) {
                proc.setRouteTable(routeTable);
            }

        } catch (JSimSimulationAlreadyTerminatedException | JSimInvalidParametersException | JSimTooManyProcessesException | JSimTooManyHeadsException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public static void setDefaultLoadDistribution(JSimSimulation simulation, JSimMessageBox box) {
        HashMap<String, Processor> rt = new HashMap<>();
        try {
            processorsList.add(new Processor("Procesador 1", simulation, box));
            processorsList.add(new Processor("Procesador 2", simulation, box));

            int size = peList.size();
            for (int i = 0; i < size; i++) {
                if (i < (size / 2)) {
                    peList.get(i).setProcessor(processorsList.get(0));
                    processorsList.get(0).getPe_list().put(peList.get(i).getName(), peList.get(i));
                    rt.put(peList.get(i).getName(), processorsList.get(0));
                    
                    simulation.message(">> Asignando " + peList.get(i).getName() + " al " + processorsList.get(0).getName());
                } else {
                    peList.get(i).setProcessor(processorsList.get(1));
                    processorsList.get(1).getPe_list().put(peList.get(i).getName(), peList.get(i));
                    rt.put(peList.get(i).getName(), processorsList.get(1));
                    
                    simulation.message(">> Asignando " + peList.get(i).getName() + " al " + processorsList.get(1).getName());
                }
            }

            RouteTable routeTable = new RouteTable(rt);
            processorsList.get(0).setRouteTable(routeTable);
            processorsList.get(1).setRouteTable(routeTable);

        } catch (JSimSimulationAlreadyTerminatedException | JSimInvalidParametersException | JSimTooManyProcessesException | JSimTooManyHeadsException ex) {
            ex.printStackTrace(System.out);
        }
    }

}
