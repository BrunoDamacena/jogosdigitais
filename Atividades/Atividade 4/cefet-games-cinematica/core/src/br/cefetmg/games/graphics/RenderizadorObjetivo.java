package br.cefetmg.games.graphics;

import br.cefetmg.games.movement.Alvo;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

/**
 *
 * @author Flávio Coutinho <fegemo@cefetmg.br>
 */
public class RenderizadorObjetivo {
    
    private final ShapeRenderer shapeRenderer;
    private final Camera camera;
    private float parametroEscala;
    private float parametroRotacao;
    
    public RenderizadorObjetivo(Camera camera) {
        shapeRenderer = new ShapeRenderer();
        this.camera = camera;
        parametroEscala = 1;
        parametroRotacao = 45;
    }
    
    public void update(float dt) {
        parametroEscala = (parametroEscala + dt*8) % MathUtils.PI2;
        parametroRotacao = (parametroRotacao + dt*2f) % MathUtils.PI2;
    }
    
    public void desenha(Alvo alvo) {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.translate(alvo.getObjetivo().x, alvo.getObjetivo().y, 0);
        shapeRenderer.rotate(0, 0, 1, MathUtils.radDeg * parametroRotacao);
        float escala = (float) (1.5 + Math.sin(parametroEscala)/2);
        shapeRenderer.scale(escala, escala, 1);
        shapeRenderer.rect(-3, -3, 6, 6);
        shapeRenderer.identity();
        shapeRenderer.end();
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
