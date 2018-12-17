package br.cefetmg.games.graphics;

import br.cefetmg.games.movement.Agente;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import java.util.HashMap;

/**
 * Renderiza um agente usando geometria e um caracter representando o seu
 * comportamento de movimentação.
 *
 * @author Flávio Coutinho <fegemo@cefetmg.br>
 */
public class RenderizadorAgente {

    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final Camera camera;

    private final HashMap<Character, GlyphLayout> cacheNomes = new HashMap<>();

    public static final float RAIO = 10;

    public RenderizadorAgente(Camera camera, SpriteBatch batch) {
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        this.batch = batch;
        this.camera = camera;
    }

    public void desenha(Agente agente) {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(agente.cor);
        shapeRenderer.translate(
                agente.pose.posicao.x,
                agente.pose.posicao.y, 0);
        shapeRenderer.rotate(0, 0, 1,
                agente.pose.orientacao * ((float) (180.0f / Math.PI)));
        shapeRenderer.circle(0, 0, RAIO);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.triangle(10, 2, 16, 0, 10, -2);
        shapeRenderer.identity();
        shapeRenderer.end();

        batch.begin();
        batch.setTransformMatrix(new Matrix4()
                .setToTranslation(agente.pose.posicao));
        GlyphLayout layout = getTextoNome(agente.getNomeComportamento());
        font.draw(batch, layout, -layout.width / 2.0f, layout.height / 2.0f);
        batch.end();
        batch.setTransformMatrix(new Matrix4().idt());
    }

    /**
     * Retorna o desenho do texto com o "nome" (a letra indicativa) deste tipo
     * de agente. Usa um cache para não ficar gerando esse texto o tempo todo.
     *
     * @param agente
     * @return
     */
    private GlyphLayout getTextoNome(char nomeComportamento) {
        GlyphLayout layout = cacheNomes.get(nomeComportamento);
        if (layout == null) {
            layout = new GlyphLayout(font, "" + nomeComportamento);
            cacheNomes.put(nomeComportamento, layout);
        }
        return layout;
    }

    public void dispose() {
        font.dispose();
        shapeRenderer.dispose();
    }
}
