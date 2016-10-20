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
public abstract class AbstractPE {
    
    private int id;
    private String nombre;
    private String next_pe;
    
    public AbstractPE(int id, String nombre, String next_pe){
        this.id = id;
        this.nombre = nombre;
        this.next_pe = next_pe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNext_pe() {
        return next_pe;
    }

    public void setNext_pe(String next_pe) {
        this.next_pe = next_pe;
    }
    
    public abstract void sendMessage(double lambda);
    
    public abstract void receiveMessage(double lambda);
}
