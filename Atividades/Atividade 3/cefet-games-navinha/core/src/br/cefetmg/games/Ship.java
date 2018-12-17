package br.cefetmg.games;

import br.cefetmg.games.collision.Collidable;
import br.cefetmg.games.collision.Collision;
import br.cefetmg.games.weapons.LaserWeapon;
import br.cefetmg.games.weapons.Shot;
import br.cefetmg.games.weapons.VortexWeapon;
import br.cefetmg.games.weapons.Weapon;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * A nave do jogador.
 *
 * @author fegemo <coutinho@decom.cefetmg.br>
 */
public class Ship implements Entity, Collidable {

    private boolean isMovingLeft, isMovingRight;
    private static final float[] VERTICES = new float[]{
        1.00f, 0.00f,
        0.50f, 0.85f, // origem do canhão laser direito
        0.25f, 0.15f,
        0.00f, 0.15f, // origem do canhão vortex
        -0.25f, 0.15f,
        -0.50f, 0.85f, // origem do canhão laser esquerdo
        -1.00f, 0.00f,
        -0.50f, -0.70f,
        0.00f, -0.50f,
        0.50f, -0.70f
    };

    private final Vector2 position;
    private final float speed;
    private final float scale;
    private final Rectangle area;
    private final Circle circle;
    private final Array<Weapon> weapons;
    private int currentWeaponIndex;
    private long lastShotMillis;

    public Ship(Rectangle area) {
        this.area = area;
        speed = 100;
        scale = 20;
        position = new Vector2();
        area.getCenter(position);
        position.scl(1, 0.15f);
        circle = new Circle(position, scale);

        weapons = new Array<Weapon>(new Weapon[]{
            new LaserWeapon(
            new Vector2(VERTICES[1 * 2] * scale, VERTICES[1 * 2 + 1] * scale),
            new Vector2(VERTICES[5 * 2] * scale, VERTICES[5 * 2 + 1] * scale)
            ),
            new VortexWeapon(
            new Vector2(VERTICES[3 * 2] * scale, VERTICES[3 * 2 + 1] * scale)
            )
        });
        lastShotMillis = 0;
    }

    void startMovingLeft() {
        isMovingLeft = true;
    }

    void startMovingRight() {
        isMovingRight = true;
    }

    void stopMovingLeft() {
        isMovingLeft = false;
    }

    void stopMovingRight() {
        isMovingRight = false;
    }

    Array<Shot> shoot() {
        Array<Shot> shots = new Array<Shot>(5);
        Weapon currentWeapon = weapons.get(currentWeaponIndex);
        if (TimeUtils.timeSinceMillis(lastShotMillis)
                >= currentWeapon.getCadenceInMillis()) {
            lastShotMillis = TimeUtils.millis();
            shots = currentWeapon.createShot(position);
        }
        return shots;
    }

    private void keepWithinBounds() {
        position.x = Math.max(scale, Math.min(area.width - scale, position.x));
    }

    @Override
    public void update(float dt) {
        if (isMovingRight) {
            position.add(speed * dt, 0);
        }
        if (isMovingLeft) {
            position.add(-1 * speed * dt, 0);
        }
        keepWithinBounds();
        circle.x = position.x;
    }

    @Override
    public void render(ShapeRenderer renderer) {
        renderer.identity();
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.translate(position.x, position.y, 0);
        renderer.scale(scale, scale, 1);
        renderer.setColor(Color.WHITE);
        renderer.polygon(VERTICES, 0, VERTICES.length);
        renderer.identity();
        if (Config.debug) {
            renderer.setColor(Color.YELLOW);
            renderer.circle(circle.x, circle.y, circle.radius);
        }
        renderer.end();
    }

    void switchWeapon() {
        currentWeaponIndex = (currentWeaponIndex + 1) % weapons.size;
    }

    @Override
    public boolean collidesWith(Collidable other) {
        // Ship vs Laser: nada
        // Ship vs Vortex: nada
        // Ship vs Asteroid: circle vs circle
        if (other instanceof Asteroid) {
            return Collision.circlesOverlap(circle, other.getMinimumEnclosingBall());
        } else {
            return false;
        }
    }

    @Override
    public boolean isOutOfBounds(Rectangle area) {
        return false;
    }

    @Override
    public Rectangle getMinimumBoundingRectangle() {
        return null;
    }

    @Override
    public Circle getMinimumEnclosingBall() {
        return circle;
    }
}
