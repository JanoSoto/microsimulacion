/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author JAno
 */
public class Token {

    //Tipo puede ser: "insripcion", "localizacion", "sos"
    private String tipo;
    private double lambda;
    //Destino
    private String posting;

    public Token(String tipo, double lambda) {
        this.tipo = tipo;
        this.lambda = lambda;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getLambda() {
        return lambda;
    }

    public void setLambda(double lambda) {
        this.lambda = lambda;
    }

    public String getPosting() {
        return posting;
    }

    public void setPosting(String posting) {
        this.posting = posting;
    }

}
