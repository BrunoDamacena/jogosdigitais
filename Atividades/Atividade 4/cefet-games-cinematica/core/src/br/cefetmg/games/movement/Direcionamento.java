package br.cefetmg.games.movement;

import com.badlogic.gdx.math.Vector3;

/**
 * Direcionamento é um guia de movimentação que será feito por um agente.
 *
 * Ele consiste de uma componente linear e outra angular (velocidade e 
 * rotação)
 *
 * @author Flávio Coutinho <fegemo@cefetmg.br>
 */
public class Direcionamento {
    public Vector3 velocidade;  // velocidade linear
    public double rotacao;      // velocidade angular
    
    public Direcionamento() {
        velocidade = new Vector3();
        rotacao = 0;
    }
}
