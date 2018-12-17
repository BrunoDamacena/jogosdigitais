package br.cefetmg.games;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Um objeto do jogo que se atualiza e se desenha.
 * @author fegemo <coutinho@decom.cefetmg.br>
 */
public interface Entity {
    void update(float dt);
    void render(ShapeRenderer renderer);
}
