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
public class LastPENotification extends AbstractPE{
    
    private int counter;
    
    public LastPENotification(int id, String nombre, String next_pe) {
        super(id, nombre, next_pe);
        this.counter = 0;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
    
    @Override
    public void sendMessage(double lambda) {
        
    }

    @Override
    public void receiveMessage(double lambda) {
        this.counter++;
    }
    
}
