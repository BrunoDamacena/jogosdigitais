/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cefetmg.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author aluno
 */
public class Goomba {
    
//    private Sprite goombaSprite;
    private Texture goombaSpriteSheet;
    private TextureRegion[][] goombaPossibilities;
    private Vector2 position;
    private Animation rightWalk;
    private Animation leftWalk;
    private Animation upWalk;
    private Animation downWalk;
    private Animation currentAnimation;
    float animationTime = 0;
    
    public Goomba(Texture texture) {
//        this.goombaSprite = new Sprite(texture);
//        this.goombaSprite.setPosition(30, 10);
        this.goombaSpriteSheet = new Texture("goomba-spritesheet.png");
        this.position = new Vector2(30, 10);
        this.goombaPossibilities = TextureRegion.split(goombaSpriteSheet, 21, 24);
        this.downWalk = new Animation(
            0.1f,
            goombaPossibilities[0][0],
            goombaPossibilities[0][1],
            goombaPossibilities[0][2],
            goombaPossibilities[0][3],
            goombaPossibilities[0][4]                
        );
        this.rightWalk = new Animation(
            0.1f,
            goombaPossibilities[1][0],
            goombaPossibilities[1][1],
            goombaPossibilities[1][2],
            goombaPossibilities[1][3],
            goombaPossibilities[1][4]                
        );
        this.upWalk = new Animation(
            0.1f,
            goombaPossibilities[2][0],
            goombaPossibilities[2][1],
            goombaPossibilities[2][2],
            goombaPossibilities[2][3],
            goombaPossibilities[2][4]                
        );
        this.leftWalk = new Animation(
            0.1f,
            goombaPossibilities[3][0],
            goombaPossibilities[3][1],
            goombaPossibilities[3][2],
            goombaPossibilities[3][3],
            goombaPossibilities[3][4]                
        );
        this.rightWalk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        this.leftWalk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        this.upWalk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        this.downWalk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        this.currentAnimation = this.downWalk;
    }
    
    public void update() {
        this.animationTime+=Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if(position.x >= 0) {
                currentAnimation = leftWalk;
                position.x--;
            }            
//            if(goombaSprite.getX() >= 0){
//                goombaSprite.setPosition(goombaSprite.getX() - 1, goombaSprite.getY());
//            }
            
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if(position.x <= Gdx.graphics.getWidth() - 21) {
                currentAnimation = rightWalk;
                position.x++;
            }
//            if(goombaSprite.getX() <= Gdx.graphics.getWidth() - goombaSprite.getWidth()){
//                goombaSprite.setPosition(goombaSprite.getX() + 1, goombaSprite.getY());
//            }
            
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if(position.y <= Gdx.graphics.getHeight() - 24) {
                currentAnimation = upWalk;
                position.y++;
            }
//            if(goombaSprite.getY() <= Gdx.graphics.getHeight()- goombaSprite.getHeight()){
//                goombaSprite.setPosition(goombaSprite.getX(), goombaSprite.getY() + 1);
//            }
            
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if(position.y >= 0) {
                currentAnimation = downWalk;
                position.y--;
            }
//            if(goombaSprite.getY() >= 0){
//                goombaSprite.setPosition(goombaSprite.getX(), goombaSprite.getY() - 1);
//            }
            
        }
        else {
            animationTime = 0.2f;
        }
    }
    
    public void render(SpriteBatch batch) {
//        this.goombaSprite.draw(batch);
        TextureRegion currentFrame = (TextureRegion) this.currentAnimation.getKeyFrame(animationTime);
        batch.draw(currentFrame, position.x, position.y);
    }
    
}
