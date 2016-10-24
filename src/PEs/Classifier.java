/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PEs;

import processors.Processor;
import util.AbstractPE;
import util.Token;

/**
 *
 * @author JAno
 */
public class Classifier extends AbstractPE {

    public Classifier(int id, String nombre, String next_pe, Processor processor) {
        super(id, nombre, next_pe, processor);
    }

    @Override
    public void sendMessage(Token token) {
        switch (token.getTipo()) {
            case "inscripcion": {
                //Enviar al PE1
                break;
            }
            case "localizacion": {
                //Enviar al PE2
                break;
            }
            case "sos": {
                //Enviar al PE4
                break;
            }
            default:
                System.out.println("Tipo de token inválido");
        }
    }
    /*
    @Override
    public void receiveMessage(Token token) {
        //Recibe mensaje desde el adapter
    }
     */
}
