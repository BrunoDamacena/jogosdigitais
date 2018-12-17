/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import game.YouRock;

/**
 *
 * @author bruno
 */
public class Introduction implements Screen {
    
    YouRock game;
    Texture[] startButton;
    
    public Introduction(YouRock game) {
        this.game = game;
        startButton = new Texture[2];
    }

    @Override
    public void show() {
        startButton[0] = new Texture("buttons/start_button.png");
        startButton[1] = new Texture("buttons/start_button_hover.png");
    }

    @Override
    public void render(float f) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainGame(game));
        }
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(YouRock.game_logo, YouRock.SCREEN_WIDTH/2 - YouRock.GAME_LOGO_WIDTH/2, YouRock.SCREEN_HEIGHT - YouRock.GAME_LOGO_HEIGHT - 50, YouRock.GAME_LOGO_WIDTH, YouRock.GAME_LOGO_HEIGHT);
        drawIntroText();
        if(mouseInStartButton()) {
            game.batch.draw(startButton[1], YouRock.SCREEN_WIDTH/2 - YouRock.BUTTON_WIDTH/2, 25, YouRock.BUTTON_WIDTH, YouRock.BUTTON_HEIGHT);
            if(Gdx.input.justTouched()) {
                game.setScreen(new MainGame(game));
            }
        }
        else {
            game.batch.draw(startButton[0], YouRock.SCREEN_WIDTH/2 - YouRock.BUTTON_WIDTH/2, 25, YouRock.BUTTON_WIDTH, YouRock.BUTTON_HEIGHT);
        }           
        game.batch.end();
    }
    
    private void drawIntroText() {
        String[] lines = setStrings();
        game.font.draw(game.batch, lines[0], YouRock.SCREEN_WIDTH/2 - new GlyphLayout(game.font, lines[0]).width/2, YouRock.SCREEN_HEIGHT - YouRock.GAME_LOGO_HEIGHT - 75);
        game.font.draw(game.batch, lines[1], YouRock.SCREEN_WIDTH/2 - new GlyphLayout(game.font, lines[1]).width/2, YouRock.SCREEN_HEIGHT - YouRock.GAME_LOGO_HEIGHT - 95);
        game.batch.draw(YouRock.band_logo, YouRock.SCREEN_WIDTH/2 - YouRock.BAND_LOGO_WIDTH/4, YouRock.SCREEN_HEIGHT - YouRock.GAME_LOGO_HEIGHT - YouRock.BAND_LOGO_HEIGHT/2 - 115, YouRock.BAND_LOGO_WIDTH/2, YouRock.BAND_LOGO_HEIGHT/2);
        game.font.draw(game.batch, lines[2], YouRock.SCREEN_WIDTH/2 - new GlyphLayout(game.font, lines[2]).width/2, YouRock.SCREEN_HEIGHT - YouRock.GAME_LOGO_HEIGHT - YouRock.BAND_LOGO_HEIGHT/2 -135);
        game.font.draw(game.batch, lines[3], YouRock.SCREEN_WIDTH/2 - new GlyphLayout(game.font, lines[3]).width/2, YouRock.SCREEN_HEIGHT - YouRock.GAME_LOGO_HEIGHT - YouRock.BAND_LOGO_HEIGHT/2 -155);
        game.font.draw(game.batch, lines[4], YouRock.SCREEN_WIDTH/2 - new GlyphLayout(game.font, lines[4]).width/2, YouRock.SCREEN_HEIGHT - YouRock.GAME_LOGO_HEIGHT - YouRock.BAND_LOGO_HEIGHT/2 -175);
        game.font.draw(game.batch, lines[5], YouRock.SCREEN_WIDTH/2 - new GlyphLayout(game.font, lines[5]).width/2, YouRock.SCREEN_HEIGHT - YouRock.GAME_LOGO_HEIGHT - YouRock.BAND_LOGO_HEIGHT/2 -195);

    }
    
    private String[] setStrings() {
        String[] lines = new String[6];
        lines[0] = "Welcome to You Rock! You are Matthew, a rockstar!";
        lines[1] = "You and your friends started a metal band named";
        lines[2] = "and you want to grind to the Top #1 Billboard";
        lines[3] = "In order to do that, you need to rock to the top";
        lines[4] = "But remember: you have bills to pay, and skill to earn";
        lines[5] = "GOOD LUCK!";
        return lines;
    }
    
    private boolean mouseInStartButton() {
        boolean isOnX = Gdx.input.getX() > 530 && Gdx.input.getX() < 740;
        boolean isOnY = Gdx.input.getY() > 580 && Gdx.input.getY() < 660;
        return isOnX && isOnY;
    }

    @Override
    public void resize(int i, int i1) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
    
}
