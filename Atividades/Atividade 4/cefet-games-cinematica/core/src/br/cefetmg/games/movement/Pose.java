package br.cefetmg.games.movement;

import com.badlogic.gdx.math.Vector3;

/**
 *
 * @author Flávio Coutinho <fegemo@cefetmg.br>
 */
public class Pose {

    public Vector3 posicao;
    public float orientacao;

    public Pose() {
        this(new Vector3(0, 0, 0), 0);
    }

    public Pose(Vector3 position) {
        this(position, 0);
    }

    public Pose(Vector3 posicao, float orientacao) {
        this.posicao = posicao;
        this.orientacao = orientacao;
    }

    /**
     * Retorna um vetor que representa a direção que o ângulo "orientação" 
     * desta pose representa.
     * @return um vetor na mesma direção que o ângulo.
     */
    public Vector3 getOrientacaoComoVetor() {
        return new Vector3(
                (float) Math.cos(orientacao),
                (float) Math.sin(orientacao), 0);
    }

    /**
     * Define a orientação de forma que ela seja a mesma da direção do vetor
     * velocidade.
     * @param velocidade vetor velocidade, indicando a direção para onde esta
     * pose deve se orientar (rotacionar).
     */
    public void olharNaDirecaoDaVelocidade(Vector3 velocidade) {
        if (velocidade.len2() > 0) {
            orientacao = (float) Math.atan2(velocidade.y, velocidade.x);
        }
    }

    public void atualiza(Direcionamento guia, float delta) {
        // em vez de usar as componentes (ficar "sujando as mãos"), sempre
        // prefira programar de forma encapsulada ;)
        posicao.add(guia.velocidade.scl(delta));
        posicao.x += guia.velocidade.x * delta;
        posicao.y += guia.velocidade.y * delta;
        posicao.z += guia.velocidade.z * delta;
        orientacao += guia.rotacao * delta;
        orientacao = orientacao % ((float) Math.PI * 2);
    }
}
