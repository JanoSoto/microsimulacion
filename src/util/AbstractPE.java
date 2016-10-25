/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import cz.zcu.fav.kiv.jsim.JSimLink;
import cz.zcu.fav.kiv.jsim.JSimSecurityException;
import processors.Processor;

/**
 *
 * @author JAno
 */
public abstract class AbstractPE {

    private int id;
    private String nombre;
    private String next_pe;
    private Processor processor;

    public AbstractPE(int id, String nombre, String next_pe, Processor processor) {
        this.id = id;
        this.nombre = nombre;
        this.next_pe = next_pe;
        this.processor = processor;
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

    public Processor getProcessor() {
        return processor;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    public void sendMessage(Token token) throws JSimSecurityException {
        //System.out.println("Soy el " + this.nombre + " y estoy enviando un mensaje");
        token.setSender(this.nombre);
        token.setPosting(this.next_pe);
        JSimLink link = new JSimLink(token);
        link.into(this.processor.getQueue());
    }

    public void receiveMessage(Token token) throws JSimSecurityException {
        sendMessage(token);
    }
}
