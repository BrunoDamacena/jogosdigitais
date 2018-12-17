package br.cefetmg.games.collision;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

/**
 * Um objeto que pode sofrer colisão com outro.
 * @author fegemo <coutinho@decom.cefetmg.br>
 */
public interface Collidable {
    /**
     * Verifica se este objeto está colidindo com outro.
     * @param other outro objeto para verificar se este está colidindo.
     * @return true/false se está colidindo ou não.
     */
    boolean collidesWith(Collidable other);
    
    /**
     * Verifica se este objeto está fora de uma região retangular.
     * @param area área retangular.
     * @return true/false se está pelo menos parcialmente fora da região.
     */
    boolean isOutOfBounds(Rectangle area);
    
    
    
    /**
     * Retorna um retângulo mínimo que contenha a entidade.
     * @return um retângulo.
     */
    Rectangle getMinimumBoundingRectangle();
    
    /**
     * Retorna um círculo mínimo que contenha a entidade.
     * @return um círculo.
     */
    Circle getMinimumEnclosingBall();
}
