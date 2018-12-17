package br.cefetmg.games;

import br.cefetmg.games.collision.Collidable;
import br.cefetmg.games.collision.Collision;
import br.cefetmg.games.weapons.LaserShot;
import br.cefetmg.games.weapons.VortexShot;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Um asteróide.
 * @author fegemo <coutinho@decom.cefetmg.br>
 */
public class Asteroid implements Entity, Collidable {

    private final Rectangle bounds;
    private final Circle circle;
    private float[] vertices;
    private float radius;
    private final Vector2 position;
    private float speed;
    private final Rectangle area;
    private float orientation;
    private float angularSpeed;

    public Asteroid(Rectangle area) {
        this.area = area;
        position = new Vector2();
        bounds = new Rectangle();
        circle = new Circle();
        orientation = 0;
        recycle(0);
    }

    private void randomVertices() {
        int numberOfVertices = MathUtils.random(5, 10);
        vertices = new float[numberOfVertices * 2];
        for (int i = 0; i < vertices.length; i += 2) {
            float angle = ((float) i / vertices.length) * MathUtils.PI2;
            vertices[i] = MathUtils.cos(angle) * radius * (MathUtils.random(0.5f) + 0.5f);
            vertices[i + 1] = MathUtils.sin(angle) * radius * (MathUtils.random(0.5f) + 0.5f);
        }
    }

    @Override
    public void update(float dt) {
        position.y -= speed * dt;
        bounds.y = position.y - radius;
        circle.y = position.y;
        orientation += angularSpeed * dt;
    }

    @Override
    public void render(ShapeRenderer renderer) {
        renderer.setColor(Color.SLATE);
        renderer.identity();
        renderer.translate(position.x, position.y, 0);
        renderer.rotate(0, 0, 1, orientation);
        renderer.polygon(vertices);
        if (Config.debug) {
            renderer.identity();
            renderer.setColor(Color.YELLOW);
            renderer.circle(circle.x, circle.y, circle.radius);
            renderer.setColor(Color.RED);
            renderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    @Override
    public boolean isOutOfBounds(Rectangle area) {
        return !area.overlaps(bounds);
    }

    final void recycle(float minimumY) {
        position.set(
                MathUtils.random(area.width),
                MathUtils.random(minimumY, area.height));
        radius = MathUtils.random(15, 25);
        bounds.set(position.x - radius, position.y, radius * 2, radius * 2);
        circle.set(position, radius);
        speed = MathUtils.random(10f, 50f);
        angularSpeed = MathUtils.random(30f, 100f);
        randomVertices();
    }

    @Override
    public boolean collidesWith(Collidable other) {
        // Asteroid vs LaserShot: rect vs rect
        // Asteroid vs Asteroid: circle vs circle
        // Asteroid vs Ship: circle vs circle
        // Asteroid vs Vortex: circle vs circle

        if (other instanceof LaserShot) {
            return Collision.circleOverlapRect(circle, other.getMinimumBoundingRectangle());
        } else if (other instanceof Asteroid ||
                other instanceof Ship ||
                other instanceof VortexShot) {
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
