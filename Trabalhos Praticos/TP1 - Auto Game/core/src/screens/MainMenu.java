/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import game.YouRock;

/**
 *
 * @author bruno
 */
public class MainMenu implements Screen {

    YouRock game;
    Texture screen;
    Texture[] playBtn;
    Texture[] exitBtn;

    public MainMenu(YouRock game) {
        this.game = game;
        screen = new Texture("screens/menu.png");
        playBtn = new Texture[2];
        exitBtn = new Texture[2];
    }

    @Override
    public void show() {
        playBtn[0] = new Texture("buttons/play_button.png");
        playBtn[1] = new Texture("buttons/play_button_hover.png");
        exitBtn[0] = new Texture("buttons/exit_button.png");
        exitBtn[1] = new Texture("buttons/exit_button_hover.png");
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        game.batch.begin();
        game.batch.draw(screen, 0, 0, 1280, 720);
        
        if (mouseInPlayButton()) {
            game.batch.draw(playBtn[1], 100, 100+YouRock.BUTTON_HEIGHT, YouRock.BUTTON_WIDTH, YouRock.BUTTON_HEIGHT);
            if(Gdx.input.justTouched()) {
                game.setScreen(new Introduction(game));
            }
        }
        else {
            game.batch.draw(playBtn[0], 100, 100+YouRock.BUTTON_HEIGHT, YouRock.BUTTON_WIDTH, YouRock.BUTTON_HEIGHT);
        }
        if (mouseInExitButton()) {
            game.batch.draw(exitBtn[1], 100, 100, YouRock.BUTTON_WIDTH, YouRock.BUTTON_HEIGHT);
            if(Gdx.input.justTouched()) {
                Gdx.app.exit();
            }
        }
        else {
            game.batch.draw(exitBtn[0], 100, 100, YouRock.BUTTON_WIDTH, YouRock.BUTTON_HEIGHT);
        }
        
        game.batch.end();
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }

    private boolean mouseInPlayButton() {
        boolean isOnX = Gdx.input.getX() > 100 && Gdx.input.getX() < 370;
        boolean isOnY = Gdx.input.getY() > 400 && Gdx.input.getY() < 490;
        return isOnX && isOnY;
    }

    private boolean mouseInExitButton() {
        boolean isOnX = Gdx.input.getX() > 100 && Gdx.input.getX() < 370;
        boolean isOnY = Gdx.input.getY() > 520 && Gdx.input.getY() < 600;
        return isOnX && isOnY;
    }

}
