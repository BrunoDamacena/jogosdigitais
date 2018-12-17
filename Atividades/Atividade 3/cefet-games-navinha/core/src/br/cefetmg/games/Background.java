package br.cefetmg.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Fundo com estrelinhas.
 * @author fegemo <coutinho@decom.cefetmg.br>
 */
public class Background {

    private final Array<Star> stars;
    private final int NUMBER_OF_STARS = 250;
    private float travelSpeed;

    public Background() {
        stars = new Array<Star>();
        Rectangle area = new Rectangle(
                0, 0,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        for (int i = 0; i < NUMBER_OF_STARS; i++) {
            stars.add(new Star(area));
        }
        travelSpeed = 0;
    }

    public void increaseTravelSpeed(float amount) {
        travelSpeed = Math.max(0, Math.min(travelSpeed + amount, 400));
    }

    public void update(float dt) {
        for (int i = 0; i < stars.size; i++) {
            stars.get(i).update(dt);
        }
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.identity();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(0, 0,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0; i < stars.size; i++) {
            stars.get(i).render(shapeRenderer);
        }
        shapeRenderer.end();
    }

    float getTravelSpeed() {
        return travelSpeed;
    }

    class Star {

        private final Vector2 position;
        private float speed;
        private final Color color;
        private final Rectangle area;
        private float lastDt;
        static final float MAX_SPEED = 20;

        Star(Rectangle area) {
            this.area = area;
            position = new Vector2();
            color = new Color();
            recycle(0);
        }

        private void pickColor() {
            float speedPercentage = speed / MAX_SPEED;
            float tone = speedPercentage / 3f + 0.15f;
            color.set(tone, tone, tone * 1.25f, 1);
        }

        private void recycle(float minimumY) {
            position.set(
                    MathUtils.random(area.width),
                    MathUtils.random(minimumY, area.height));
            speed = MathUtils.random(0.3f * MAX_SPEED, MAX_SPEED);
            pickColor();
        }

        void update(float dt) {
            lastDt = dt;
            position.add(0, -(speed + travelSpeed) * dt);

            if (!area.contains(position)) {
                recycle(area.height);
            }
        }

        void render(ShapeRenderer shapeRenderer) {
            shapeRenderer.setColor(color);
            if (speed + travelSpeed > 30) {
                shapeRenderer.line(
                        position.x, position.y,
                        position.x, position.y + (speed + travelSpeed) * lastDt);
            } else {
                shapeRenderer.point(position.x, position.y, 0);
            }

        }
    }
}
