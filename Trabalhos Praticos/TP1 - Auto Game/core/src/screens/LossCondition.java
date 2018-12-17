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
import game.YouRock;

/**
 *
 * @author aluno
 */
public class LossCondition implements Screen {
    
    YouRock game;
    private Texture motive;
    private Texture[] playAgain;
    private Texture[] exit;
    
    public LossCondition(YouRock game, boolean bankrupt) {
        this.game = game;
        if(bankrupt) {
            motive = new Texture("screens/bankrupt.png");
        }
        else {
            motive = new Texture("screens/kicked_out.png");
        }
        playAgain = new Texture[2];
        exit = new Texture[2];
    }

    @Override
    public void show() {
        playAgain[0] = new Texture("buttons/play_again.png");
        playAgain[1] = new Texture("buttons/play_again_hover.png");
        exit[0] = new Texture("buttons/exit_button.png");
        exit[1] = new Texture("buttons/exit_button_hover.png");
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(motive, 0, 0);
        if(mouseOnPlayAgain()) {
            game.batch.draw(playAgain[1], 3*YouRock.SCREEN_WIDTH/4 - YouRock.BUTTON_WIDTH/2, 25, YouRock.BUTTON_WIDTH, YouRock.BUTTON_HEIGHT);
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                game.setScreen(new Introduction(game));
            }
        }
        else {
            game.batch.draw(playAgain[0], 3*YouRock.SCREEN_WIDTH/4 - YouRock.BUTTON_WIDTH/2, 25, YouRock.BUTTON_WIDTH, YouRock.BUTTON_HEIGHT);
        }
        if(mouseOnExit()) {
            game.batch.draw(exit[1], YouRock.SCREEN_WIDTH/4 - YouRock.BUTTON_WIDTH/2, 25, YouRock.BUTTON_WIDTH, YouRock.BUTTON_HEIGHT);
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                Gdx.app.exit();
            }
        }
        else {
            game.batch.draw(exit[0], YouRock.SCREEN_WIDTH/4 - YouRock.BUTTON_WIDTH/2, 25, YouRock.BUTTON_WIDTH, YouRock.BUTTON_HEIGHT);
        }
        game.batch.end();
    }
    
    private boolean mouseOnPlayAgain() {
        System.out.println("X: "+Gdx.input.getX()+", Y: "+Gdx.input.getY());
        boolean isOnX = Gdx.input.getX() > 830 && Gdx.input.getX() < 1080;
        boolean isOnY = Gdx.input.getY() > 595 && Gdx.input.getY() < 655;
        return isOnX && isOnY;
    }
    
    private boolean mouseOnExit() {
        boolean isOnX = Gdx.input.getX() > 240 && Gdx.input.getX() < 380;
        boolean isOnY = Gdx.input.getY() > 595 && Gdx.input.getY() < 655;
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
