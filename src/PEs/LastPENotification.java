/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PEs;

import core.Coord;
import cz.zcu.fav.kiv.jsim.JSimInvalidParametersException;
import cz.zcu.fav.kiv.jsim.JSimLink;
import cz.zcu.fav.kiv.jsim.JSimSecurityException;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import cz.zcu.fav.kiv.jsim.JSimSimulationAlreadyTerminatedException;
import cz.zcu.fav.kiv.jsim.JSimTooManyProcessesException;
import java.util.Random;
import processors.Processor;
import simulatorUtil.AbstractPE;
import simulatorUtil.Antenna;
import simulatorUtil.Token;
import ui.DTNSimTextUI;

/**
 *
 * @author JAno
 */
public class LastPENotification extends AbstractPE {

    private DTNSimTextUI oneSimulator;
    private Coord data_center_loc;      //Coordenada del DataCenter en Helsinski
    private Antenna antenna;

    public LastPENotification(int id, String nombre, String next_pe, Processor processor, JSimSimulation simulation) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException{
        super(nombre, simulation, next_pe, processor);
        this.data_center_loc = new Coord(6000.275420, 2400.954759); // coordenada real del mapa de Helsinki
    }
    
    public LastPENotification(int id, String nombre, String next_pe, JSimSimulation simulation, Antenna antena) throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException{
        super(nombre, simulation, next_pe);
        this.data_center_loc = new Coord(6000.275420, 2400.954759); // coordenada real del mapa de Helsinki
        this.antenna = antena;
    }

    public Antenna getAntenna() {
        return antenna;
    }

    public void setAntenna(Antenna antenna) {
        this.antenna = antenna;
    }

    public DTNSimTextUI getOneSimulator() {
        return oneSimulator;
    }

    public void setOneSimulator(DTNSimTextUI oneSimulator) {
        this.oneSimulator = oneSimulator;
    }

    
    public Coord getDataCenterLoc() {
        return data_center_loc;
    }

    @Override
    public void sendMessage(Token token) throws JSimSecurityException {
        token.setSender(this.getName());
        token.setPosting("final");
        JSimLink link = new JSimLink(token);
        link.into(this.getProcessor().getQueue());
    }

    @Override
    public void receiveMessage(Token token) throws JSimSecurityException, JSimInvalidParametersException {
        System.out.println("Llega el siguiente token al PE de notificacion: " + token.getTipo());
        this.addToCounter();
        token.setT_end(this.myParent.getCurrentTime());
        System.out.println("Tiempo de inicio: " + token.getT_init());
        System.out.println("Tiempo de fin: " + token.getT_end());
        
        //Aquí obtiene la ubicación para enviar un mensaje a través de la red 4g
        Coord location = getNodeLocation();
        System.out.println("## LAST PE: ("+location.getX()+", "+location.getY()+")");
        
        sendMessage(token);
        sendThroughNetwork(location);
    }    
    
    /**
     * Obtiene la ubicación de uno de los nodos del modelo de movilidad
     * @return Objeto Coord con la ubicación del nodo
     */
    public Coord getNodeLocation(){
        int length = oneSimulator.getWorld().getHosts().size();
        Random random = new Random();
        
        return oneSimulator.getWorld().getHosts().get(random.nextInt(length)).getLocation();
    }

    /**
     * Calcula la distancia entre 2 coordenadas.
     * @return distancia entre ambas coordenadas
     */
    public static double haversineDistance(Coord location1, Coord location2) {
        
        double R = 6372.8; // en kilometros
        //double R = 6371e3; // en metros
        double dLat = Math.toRadians(location2.getX() - location1.getX());
        double dLon = Math.toRadians(location2.getY() - location1.getY());
 
        double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(Math.toRadians(location1.getX())) * Math.cos(Math.toRadians(location2.getX()));
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }
    
    /**
     * Simula envio de mensaje a traves de la red 4g.
     * @param location coordenada del token.
     */
    public void sendThroughNetwork(Coord location) throws JSimSecurityException, JSimInvalidParametersException {
        
        int i,          // iterador for
            j=0,        // iterador metros antenas
            antenas=1;  // cantidad de antenas
        double distancia = haversineDistance(getDataCenterLoc(), location);
        
        System.out.println("## Distancia desde el DataCenter: " + distancia);
        
        for(i=0; i<distancia ;i++) {
            if(j==6000) {
                antenas++;
                j=0;
            }
            j++;
        }
        
        JSimLink link = new JSimLink(antenas);
        if(this.antenna.isIdle()){
            System.out.println("Despertando a la antena");
            this.antenna.activateNow();
        }
        link.into(this.antenna.getQueue());
        
        System.out.println("## Cantidad de antenas para llegar al DataCenter: " + antenas);
    }
}
