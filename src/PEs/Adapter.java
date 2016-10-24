/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PEs;

import cz.zcu.fav.kiv.jsim.*;
import java.util.Random;
import util.ProcessingTime;
import util.Token;

/**
 *
 * @author Jorge.Cubillos
 */
public class Adapter extends JSimProcess {

    private ProcessingTime pt;
    private Classifier clasificador;
    private String[] texto = {"inscripcion", "localizacion", "sos"};
    private String name;
    private Random rdm;

    public Adapter(String name, JSimSimulation sim, Classifier clasificador)
            throws JSimSimulationAlreadyTerminatedException,
            JSimInvalidParametersException,
            JSimTooManyProcessesException {
        super(name, sim);
        this.name = name;
        this.clasificador = clasificador;
        this.rdm = new Random();
    }

    public String getRdm() {
        return texto[(int) (this.rdm.nextDouble() * 2 + 0)];
    }

    public void sendMessage(Token token) {

    }

    @Override
    protected void life() {
        try {
            double time;

            while (true) {
                time = this.myParent.getCurrentTime();
                Token token = new Token(this.getRdm(), 0.1);

                //TODO Escribir mensaje que emitirá el simulador
                message(time + "algun mensaje");

                //Hace hold con una exponencial negativa
                hold(JSimSystem.negExp(token.getLambda()));
                this.clasificador.receiveMessage(token);
            }
        } catch (JSimException e) {
            e.printStackTrace(System.out);
            e.printComment();
        }
    }
}
