/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;


import PEs.Classifier;
import PEs.GenericPE;
import PEs.LastPEDataBase;
import PEs.LastPENotification;
import PEs.PE4;
import PEs.PE7;
import PEs.PECounter;
import cz.zcu.fav.kiv.jsim.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import processors.Processor;

/**
 *
 * @author JAno
 */
public class MicroSimulación {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            JSimSimulation simulation = null;
            
            //Creación de los procesadores
            Processor procesador1 = new Processor("Procesador 1", simulation);
            Processor procesador2 = new Processor("Procesador 2", simulation);
             
            //Creación de los PEs
            Classifier classifier = new Classifier(0, "Clasificador", "PE1 PE2 PE4", procesador1);
            GenericPE PE1 = new GenericPE(1, "PE1", "PE3", procesador1);
            GenericPE PE2 = new GenericPE(2, "PE2", "PE3", procesador1);
            GenericPE PE3 = new GenericPE(3, "PE3", "DataBase", procesador1);
            PE4 PE4 = new PE4(4, "PE4", "PE5 PE6 P7", procesador1);
            PECounter PE5 = new PECounter(5, "PE5", "PE9", procesador1);
            PECounter PE6 = new PECounter(6, "PE6", "PE9", procesador1);
            PE7 PE7 = new PE7(7, "PE7", "PE8 PE9", procesador1);
            PECounter PE8 = new PECounter(8, "PE8", "PE9", procesador2);
            GenericPE PE9 = new GenericPE(9, "PE9", "PE10", procesador2);
            GenericPE PE10 = new GenericPE(10, "PE10", "PE11", procesador2);
            GenericPE PE11 = new GenericPE(11, "PE11", "PE12", procesador2);
            GenericPE PE12 = new GenericPE(12, "PE12", "PE13", procesador2);
            GenericPE PE13 = new GenericPE(13, "PE13", "Notification", procesador2);
            LastPEDataBase PEDataBase = new LastPEDataBase(14, "DataBase", "", procesador2);
            LastPENotification PENotification = new LastPENotification(15, "Notification", "", procesador2);
            
            
            
            try{
                simulation = new JSimSimulation("First simulation");
            }
            catch (JSimException e){
                e.printStackTrace();
                e.printComment(System.err);
            }
            finally{
                simulation.shutdown();
            }
        } 
        catch (JSimSimulationAlreadyTerminatedException | JSimInvalidParametersException | JSimTooManyProcessesException | JSimTooManyHeadsException ex){
            Logger.getLogger(MicroSimulación.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
