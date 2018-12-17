package br.cefetmg.games.collision;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Utilitário para verificação de colisão.
 *
 * @author fegemo <coutinho@decom.cefetmg.br>
 */
public class Collision {

    /**
     * Verifica se dois círculos em 2D estão colidindo.
     * @param c1 círculo 1
     * @param c2 círculo 2
     * @return true se há colisão ou false, do contrário.
     */
    public static final boolean circlesOverlap(Circle c1, Circle c2) {
        Vector2 distance = new Vector2(c2.x - c1.x, c2.y - c1.y);
        return Math.pow(c1.radius + c2.radius, 2) >= distance.len2();
    }

    /**
     * Verifica se dois retângulos em 2D estão colidindo.
     * Esta função pode verificar se o eixo X dos dois objetos está colidindo
     * e então se o mesmo ocorre com o eixo Y.
     * @param r1 retângulo 1
     * @param r2 retângulo 2
     * @return true se há colisão ou false, do contrário.
     */
    public static final boolean rectsOverlap(Rectangle r1, Rectangle r2) {
        //checking x axis, then y axis
        if(r1.x + r1.width >= r2.x && r1.x <= r2.x + r2.width)
            if(r1.y + r1.height >= r2.y && r1.y <= r2.y + r2.height) 
                return true;
        return false;
    }
    
    public static final boolean circleOverlapRect(Circle c, Rectangle r) {
        //gets vector to center of rectangle
        Vector2 rectangleCenter = new Vector2();
        r.getCenter(rectangleCenter);
        //get distance betwenn circle center and rectangle center
        Vector2 centerDistance = new Vector2(c.x - rectangleCenter.x, c.y - rectangleCenter.y);
        //decomposes centerDistance vector
        Vector2 distanceX = new Vector2(centerDistance.x, 0);
        Vector2 distanceY = new Vector2(0, centerDistance.y);
        //clamp the decomposed ones
        distanceX = distanceX.clamp(0, r.width/2); 
        distanceY = distanceY.clamp(0, r.height/2);
        //gets vector from circle to point
        Vector2 distance = new Vector2(rectangleCenter.x + distanceX.x - c.x, rectangleCenter.y + distanceY.y - c.y);
        //check if radius >= distance
        return Math.pow(c.radius, 2) >= distance.len2();
    }
}
