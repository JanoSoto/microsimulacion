/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulatorUtil;

/**
 *
 * @author JAno
 */
public abstract class AbstractProcessor {

    private int id;
    private String nombre;
    private ProcessingTime pt;
    //TODO Hay que definir la tabla de ruteo

    public AbstractProcessor(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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

    public void setProcessingTime(ProcessingTime pt) {
        this.pt = pt;
    }

    public double getProcessingTime(double lambda) {
        return pt.getProccesingTime(lambda);
    }
}
