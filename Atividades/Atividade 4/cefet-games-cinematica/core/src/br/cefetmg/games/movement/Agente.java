package br.cefetmg.games.movement;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

/**
 * É uma entidade do jogo que se movimenta com algum comportamento.
 * @author Flávio Coutinho <fegemo@cefetmg.br>
 */
public class Agente {

    public Pose pose;
    private AlgoritmoMovimentacao comportamento;

    public Color cor;

    public Agente(Vector3 posicao, Color cor) {
        this.pose = new Pose(posicao, 0);
        this.cor = cor;
    }

    public void atualiza(float delta) {
        if (comportamento != null) {
            // pergunta ao algoritmo de movimento (comportamento) 
            // para onde devemos ir
            Direcionamento direcionamento = comportamento.guiar(this.pose);

            // faz a simulação física usando novo estado da entidade cinemática
            pose.atualiza(direcionamento, delta);
        }
    }

    /**
     * @param comportamento o novo comportamento de movimentação
     */
    public void defineComportamento(AlgoritmoMovimentacao comportamento) {
        this.comportamento = comportamento;
    }

    public char getNomeComportamento() {
        return comportamento != null ? comportamento.getNome() : '-';
    }
}
