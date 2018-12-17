/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import game.YouRock;
import player.Player;

/**
 *
 * @author bruno
 */
public class MainGame implements Screen {
    
    YouRock game;
    private boolean isPaused;
    private boolean isPassingWeek;
    private boolean willUpdate;
    private int actualWeek;
    private int actualActivity;
    private int gigsThisWeek;
    private Player player;
    private int[][] week; //week[hour][day]
    Texture pause;
    Texture[] play;
    Texture[] continueGame;
    private final Texture[] activity;
    private final Texture[] days;
    private final Texture[] hours;
    
    
    
    /*
        Activities you can do:
        Idle = 0
        Sleep = 1        
        Work = 2
        Practice instrument = 3
        Rehearsal = 4
        Gig = 5
        Play Video Game = 6
    */
    public MainGame(YouRock game) {
        this.game = game;
        isPaused = false;
        isPassingWeek = false;
        willUpdate = true;
        actualWeek = 1;
        actualActivity = 0;
        gigsThisWeek = 0;
        player = new Player();
        week = new int[24][7];
        activity = new Texture[7];
        days = new Texture[7];
        hours = new Texture[24];   
        play = new Texture[2];
        continueGame = new Texture[2];
        setBasicWeek();
    }
    
    private void setBasicWeek() {
        //idle
        for(int i = 0; i < 24; i++) 
            for(int j = 0; j < 7; j++)
                week[i][j] = 0;
        //sleeping everyday from 0 to 7
        for(int i = 0; i < 7; i++)
            for(int j = 0; j < 7; j++)
                week[i][j] = 1;
        //sleeping everyday 11pm
        for(int j = 0; j < 7; j++) 
            week[23][j] = 1;
    }
    
    @Override
    public void show() {
        pause = new Texture("screens/pause.png");
        play[0] = new Texture("buttons/play_week.png");
        play[1] = new Texture("buttons/play_week_hover.png");
        continueGame[0] = new Texture("buttons/continue_button.png");
        continueGame[1] = new Texture("buttons/continue_button_hover.png");
        setDaysTexture();
        setHoursTexture();
        setActivitiesTexture();
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(isPassingWeek) {
            if(willUpdate) {
                player.updatePlayer(week);
                willUpdate = false;
                if(player.winCondition()) game.setScreen(new WinCondition(game, actualWeek));
                else if(player.lossCondition(actualWeek)) game.setScreen(new LossCondition(game, player.bankrupt()));
                actualWeek++;
                setBasicWeek();
                actualActivity = 0;
                gigsThisWeek = 0;
            }            
            drawPassingWeekScreen();
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if(!isPaused) {
                drawPauseScreen();
                isPaused = true;
            }
            else {
                drawMainGame();
                isPaused = false;
            }
        }
        else if(!isPaused){
            if(!willUpdate) willUpdate = true;
            drawMainGame();
            if(Gdx.input.isButtonPressed(Buttons.LEFT)) checkClick(mousePosition(), true);
            else if(Gdx.input.isButtonPressed(Buttons.RIGHT)) checkClick(mousePosition(), false);
        }
        else drawPauseScreen();
        
    }
    
    //start drawing methods  
    private void drawPauseScreen() {        
        game.batch.begin();
        game.batch.draw(pause, YouRock.SCREEN_WIDTH/2 - YouRock.PAUSE_WIDTH/2, YouRock.SCREEN_HEIGHT - YouRock.PAUSE_HEIGHT - 50, YouRock.PAUSE_WIDTH, YouRock.PAUSE_HEIGHT);
        game.batch.end();
    }
    
    private void drawMainGame() {        
        game.batch.begin();
        drawWeeks();
        drawHours();
        drawActivities();
        drawOptions();
        drawPlayerStatus();
        game.batch.end();
    }
    
    private void drawWeeks() {
        for (int i = 1, j = 6; i <= 7; i++, j--) {
            game.batch.draw(days[j], YouRock.SCREEN_WIDTH - i*YouRock.ACTIVITY_WIDTH, YouRock.SCREEN_HEIGHT - YouRock.ACTIVITY_HEIGHT, YouRock.ACTIVITY_WIDTH, YouRock.ACTIVITY_HEIGHT);
        }
    }
    
    private void drawHours() {
        for(int i = 0; i < 24; i++) {
            game.batch.draw(hours[i], YouRock.SCREEN_WIDTH - 8*YouRock.ACTIVITY_WIDTH, YouRock.SCREEN_HEIGHT - (i+2)*YouRock.ACTIVITY_HEIGHT, YouRock.ACTIVITY_WIDTH, YouRock.ACTIVITY_HEIGHT);
        }
    }
    
    private void drawActivities() {
        for(int i = 0; i < 24; i++) {
            for(int j = 0, k = 7; j < 7; j++, k--) {
                game.batch.draw(activity[week[i][j]], YouRock.SCREEN_WIDTH - k*YouRock.ACTIVITY_WIDTH, YouRock.SCREEN_HEIGHT - (i+2)*YouRock.ACTIVITY_HEIGHT, YouRock.ACTIVITY_WIDTH, YouRock.ACTIVITY_HEIGHT);
            }
        }
    }
    
    private void drawOptions() {        
        if(mouseInPlay()) {
            if(Gdx.input.isButtonPressed(Buttons.LEFT)) {
                isPassingWeek = true;
            }
            game.batch.draw(play[1], 87, YouRock.SCREEN_HEIGHT - 575, YouRock.GAME_LOGO_WIDTH, YouRock.GAME_LOGO_HEIGHT);
        }
        else {
            game.batch.draw(play[0], 87, YouRock.SCREEN_HEIGHT - 575, YouRock.GAME_LOGO_WIDTH, YouRock.GAME_LOGO_HEIGHT);
        }
        game.font.draw(game.batch, "Choose action to do below", 240 - new GlyphLayout(game.font, "Choose action to do below").width/2, YouRock.SCREEN_HEIGHT - 20);
        game.font.draw(game.batch, "Right click to erase activity from your schedule", 240 - new GlyphLayout(game.font, "Right click to erase activity from your schedule").width/2, YouRock.SCREEN_HEIGHT -40);
        for(int i = 1; i < 6; i++) {
            game.batch.draw(activity[i+1], 240 - YouRock.ACTIVITY_WIDTH*1.3f/2, YouRock.SCREEN_HEIGHT - i*(YouRock.ACTIVITY_HEIGHT*1.3f + 20) - 50, YouRock.ACTIVITY_WIDTH*1.3f, YouRock.ACTIVITY_HEIGHT*1.3f);
        }       
        
    }
    
    private void drawPlayerStatus() {
        game.font.getData().setScale(1);
        game.font.draw(game.batch, "Currency: $" + player.getCurrency(), 25, 25);
        game.font.draw(game.batch, "Billboard: #" + player.getBillboard(), 175, 25);
        game.font.draw(game.batch, "Gigs available: " + gigsAvailable(), 25, 50);
        game.font.draw(game.batch, "Week " + actualWeek, 25, 75);
        game.font.draw(game.batch, "Selected action", 350, 75);
        game.batch.draw(activity[actualActivity], 350, 25, YouRock.ACTIVITY_WIDTH, YouRock.ACTIVITY_HEIGHT);
    }
    
    private void drawPassingWeekScreen() {
        game.batch.begin();
        game.font.getData().setScale(1.5f);
        game.font.draw(game.batch, "Week #" + (actualWeek - 1) + " completed!", YouRock.SCREEN_WIDTH/2 - 100, YouRock.SCREEN_HEIGHT - 75);
        //game.batch.draw(YouRock.game_logo, 200, 200);
        
        if(mouseInContinue()) {
            game.batch.draw(continueGame[1], YouRock.SCREEN_WIDTH/2 - YouRock.BUTTON_WIDTH/2, 50, YouRock.BUTTON_WIDTH, YouRock.BUTTON_HEIGHT);
            if(Gdx.input.isButtonPressed(Buttons.LEFT)) {
                isPassingWeek = false;
            }
        }
        else {
            game.batch.draw(continueGame[0], YouRock.SCREEN_WIDTH/2 - YouRock.BUTTON_WIDTH/2, 50, YouRock.BUTTON_WIDTH, YouRock.BUTTON_HEIGHT);
        }
        game.batch.end();
    }
    //end drawing methods 
    
    //start set texture methods
    private void setDaysTexture() {
        days[0] = new Texture("week/days/sunday.png");
        days[1] = new Texture("week/days/monday.png");
        days[2] = new Texture("week/days/tuesday.png");
        days[3] = new Texture("week/days/wednesday.png");
        days[4] = new Texture("week/days/thursday.png");
        days[5] = new Texture("week/days/friday.png");
        days[6] = new Texture("week/days/saturday.png");
    }
    
    private void setHoursTexture() {
        for(int i = 0; i < 24; i++) {
            hours[i] = new Texture("week/hours/" + i + ".png");
        }
    }
    
    private void setActivitiesTexture() {
        activity[0] = new Texture("week/activities/idle.png");
        activity[1] = new Texture("week/activities/sleep.png");
        activity[2] = new Texture("week/activities/work.png");
        activity[3] = new Texture("week/activities/practice.png");
        activity[4] = new Texture("week/activities/rehearsal.png");
        activity[5] = new Texture("week/activities/gig.png");
        activity[6] = new Texture("week/activities/play_games.png");
    }
    //end set textures methods
    
    //start click handle methods    
    private Vector2 mousePosition() {
        return new Vector2(Gdx.input.getX(), Gdx.input.getY());
    }
    
    private void checkClick(Vector2 position, boolean left) {
        if(left) {
            if(position.x >= 175 && position.x <= 175 + YouRock.ACTIVITY_WIDTH) {
                changeActivity(position);
            }
            else {
                Vector2 ij = screenToMatrix(position);
                if(ij != null) {
                    if(actualActivity == 5) {
                        if(gigsAvailable() > 0) {
                            week[(int) ij.x][(int) ij.y] = actualActivity;
                            gigsThisWeek++;  
                        }
                        else{

                        }
                    }
                    else week[(int) ij.x][(int) ij.y] = actualActivity;   
                }
            }   
        }
        else {
            Vector2 ij = screenToMatrix(position);
            if(ij != null) week[(int) ij.x][(int) ij.y] = 0;   
                
        }             
    }
    
    private int gigsAvailable() {
        int gigs = 0;
        for(int i = 7; i < 23; i++) {
            for(int j = 0; j < 7; j++) {
                if(week[i][j] == 5) {
                    gigs++;
                }
            }
        }        
        return player.getGigsAvailable() - gigs;
    }
    
    private Vector2 screenToMatrix(Vector2 mousePosition) {
        Vector2 begin = new Vector2(580,224);
        Vector2 end = new Vector2(1280, 672);
        //if out of day matrix, return null
        if(mousePosition.x < begin.x || mousePosition.x >= end.x || mousePosition.y < begin.y || mousePosition.y >= end.y) return null;
        int i = (int)(mousePosition.y - begin.y)/YouRock.ACTIVITY_HEIGHT + 7;
        int j = (int)(mousePosition.x - begin.x)/YouRock.ACTIVITY_WIDTH;        
        //if out of bounds, return null
        if(i < 7 || i > 23 || j < 0 || j > 6) return null;
        return new Vector2(i, j);        
    }
    
    private void changeActivity(Vector2 mousePosition) {
        if(mousePosition.y >= 71 && mousePosition.y <= 106) { //work
            actualActivity = 2;
        }
        else if(mousePosition.y >= 126 && mousePosition.y <= 161) {
            actualActivity = 3;
        }
        else if(mousePosition.y >= 181 && mousePosition.y <= 216) {
            actualActivity = 4;
        }
        else if(mousePosition.y >= 236 && mousePosition.y <= 271) {
            actualActivity = 5;
        }
        else if(mousePosition.y >= 291 && mousePosition.y <= 326) {
            actualActivity = 6;
        }
    }
    
    private boolean mouseInPlay() {
        boolean isOnX = Gdx.input.getX() > 85 && Gdx.input.getX() < 385;
        boolean isOnY = Gdx.input.getY() > 375 && Gdx.input.getY() < 575;
        return isOnX && isOnY;
    }
    
    private boolean mouseInContinue() {
        System.out.println("X: " + Gdx.input.getX() + ", Y: " + Gdx.input.getY());
        boolean isOnX = Gdx.input.getX() > 510 && Gdx.input.getX() < 760;
        boolean isOnY = Gdx.input.getY() > 570 && Gdx.input.getY() < 630;
        return isOnX && isOnY;
    }
    //end click handle methods
    
    //start non-used abstract Screen methods
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
    //end non-used abstract Screen methods
}
