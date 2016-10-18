/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package micro.simulación;


import cz.zcu.fav.kiv.jsim.*;

/**
 *
 * @author JAno
 */
public class MicroSimulación {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JSimSimulation        simulation = null;
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
    
}
