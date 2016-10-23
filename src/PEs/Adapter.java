/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PEs;

import cz.zcu.fav.kiv.jsim.*;

/**
 *
 * @author Jorge.Cubillos
 */
public class Adapter extends JSimProcess {
    
    private long _id;
    private double _contenido;
    private String name;

    public Adapter(String name, JSimSimulation sim, long _id, double _contenido)
           throws JSimSimulationAlreadyTerminatedException,
                  JSimInvalidParametersException,
                  JSimTooManyProcessesException
    {
        super(name,sim);
        this.name = name;
        this._id = _id;
        this._contenido = _contenido ;
    }
    
    
}
