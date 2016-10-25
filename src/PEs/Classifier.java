/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PEs;

import cz.zcu.fav.kiv.jsim.JSimSecurityException;
import processors.Processor;
import util.AbstractPE;
import util.Token;

/**
 *
 * @author JAno
 */
public class Classifier extends AbstractPE {
    
    private final GenericPE PE1;
    private final GenericPE PE2;
    private final PE4 PE4;
    
    public Classifier(int id, String nombre, String next_pe, Processor processor, GenericPE PE1, GenericPE PE2, PE4 PE4) {
        super(id, nombre, next_pe, processor);
        this.PE1 = PE1;
        this.PE2 = PE2;
        this.PE4 = PE4;
    }

    @Override
    public void sendMessage(Token token) throws JSimSecurityException {
        switch (token.getTipo()) {
            case "inscripcion": {
                //Enviar al PE1
                //System.out.println("Envio un mensaje al PE 1");
                this.PE1.receiveMessage(token);
                break;
            }
            case "localizacion": {
                //Enviar al PE2
                //System.out.println("Envio un mensaje al PE 2");
                this.PE2.receiveMessage(token);
                break;
            }
            case "sos": {
                //Enviar al PE4
                //System.out.println("Envio un mensaje al PE 4");
                this.PE4.receiveMessage(token);
                break;
            }
            default:
                System.out.println("Tipo de token inválido");
        }
    }
    
    @Override
    public void receiveMessage(Token token) throws JSimSecurityException {
        //System.out.println("Soy el clasificador y recibo un mensaje");
        this.sendMessage(token);
    }
    
}
