package br.cefetmg.games.weapons;

import br.cefetmg.games.Asteroid;
import br.cefetmg.games.Config;
import br.cefetmg.games.collision.Collidable;
import br.cefetmg.games.collision.Collision;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Um tiro de vórtice.
 * @author fegemo <coutinho@decom.cefetmg.br>
 */
public class VortexShot implements Shot {

    private final Vector2 position;
    private final float speed;
    private final float radius;
    private final Rectangle bounds;
    private final Circle circle;

    VortexShot(Vector2 position) {
        radius = 5;
        this.position = new Vector2(position.x, position.y + radius);
        speed = 220;
        bounds = new Rectangle(position.x, position.y, radius * 2, radius * 2);
        circle = new Circle(position, radius);
    }

    @Override
    public void update(float dt) {
        position.y += speed * dt;
        bounds.y = position.y;
        circle.y = position.y;
    }

    @Override
    public void render(ShapeRenderer renderer) {
        renderer.setColor(Color.BLUE);
        renderer.identity();
        renderer.translate(position.x, position.y, 0);
        renderer.circle(0, 0, radius);
        if (Config.debug) {
            renderer.setColor(Color.YELLOW);
            renderer.identity();
            renderer.circle(circle.x, circle.y, circle.radius);
        }
    }

    @Override
    public boolean isOutOfBounds(Rectangle area) {
        return !area.overlaps(bounds);
    }

    @Override
    public boolean collidesWith(Collidable other) {
        // Vortex vs Laser: rect vs rect
        // Vortex vs Asteroid: circle vs circle
        if (other instanceof LaserShot) {
            return Collision.rectsOverlap(bounds, other.getMinimumBoundingRectangle());
        } else if (other instanceof Asteroid) {
            return Collision.circlesOverlap(circle, other.getMinimumEnclosingBall());
        } else {
            return false;
        }
    }

    @Override
    public Rectangle getMinimumBoundingRectangle() {
        return bounds;
    }

    @Override
    public Circle getMinimumEnclosingBall() {
        return circle;
    }
}
