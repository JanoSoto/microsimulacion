/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package micro.simulación;

/**
 *
 * @author JAno
 */
public class LastPEDataBase extends AbstractPE{

    public LastPEDataBase(int id, String nombre, String next_pe) {
        super(id, nombre, next_pe);
    }

    @Override
    public void sendMessage(double lambda) {
        
    }

    @Override
    public void receiveMessage(double lambda) {
        //TODO Función hold con el valor de guardar en disco
    }
    
    
}
