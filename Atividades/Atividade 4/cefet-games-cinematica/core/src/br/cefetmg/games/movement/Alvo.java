package br.cefetmg.games.movement;

import com.badlogic.gdx.math.Vector3;

/**
 * Representa um objetivo, que pode ser uma posição no mapa ou um agente (e.g.,
 * sendo perseguido).
 *
 * @author Flávio Coutinho <fegemo@cefetmg.br>
 */
public class Alvo {

    /**
     * Uma posição no mundo que é o objetivo dos agentes.
     */
    private Vector3 objetivoEstatico;

    /**
     * Um agente que é o objetivo dos outros agentes.
     */
    private Agente agenteObjetivo;

    public Alvo(Vector3 posicao) {
        this.objetivoEstatico = posicao;
    }

    public void setObjetivo(Vector3 posicao) {
        this.objetivoEstatico = posicao;
        this.agenteObjetivo = null;
    }

    public void setObjetivo(Agente agente) {
        this.agenteObjetivo = agente;
        this.objetivoEstatico = null;
    }

    public boolean isSeguindoObjetivo() {
        return agenteObjetivo != null;
    }

    public Vector3 getObjetivo() {
        return isSeguindoObjetivo()
                ? agenteObjetivo.pose.posicao : objetivoEstatico;
    }
}
