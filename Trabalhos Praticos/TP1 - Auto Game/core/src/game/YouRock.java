package game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import screens.MainMenu;

public class YouRock extends Game {
    
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 700;
    
    public static final int GAME_LOGO_WIDTH = 305;
    public static final int GAME_LOGO_HEIGHT = 214;
    
    public static final int BAND_LOGO_WIDTH = 712;
    public static final int BAND_LOGO_HEIGHT = 106;
    
    public static final int BUTTON_WIDTH = 270;
    public static final int BUTTON_HEIGHT = 105;
    
    public static final int PAUSE_WIDTH = 720;
    public static final int PAUSE_HEIGHT = 250;
    
    public static final int ACTIVITY_WIDTH = 100;
    public static final int ACTIVITY_HEIGHT = 28;
    
    public static final int PLAY_WIDTH = 237;
    public static final int PLAY_HEIGHT = 211;

    public SpriteBatch batch;
    public BitmapFont font;
    private Pixmap mouse;
    private Music music;
    public static Texture game_logo;
    public static Texture band_logo;
    
    public YouRock() {
        super();
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        //set mouse icon
        mouse = new Pixmap(Gdx.files.internal("mouse/cursor.png"));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(mouse, 0, 0));
        //set main music
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/songs/main_menu.ogg"));
        music.setLooping(true);
        //i like to code listening music, so uncomment this when is done
        music.play();
        //set main font
        font = new BitmapFont();
        font.setColor(0, 0, 0, 1);
        //set logos
        game_logo = new Texture("game_logo.png");
        band_logo = new Texture("band_logo.png");
        //set screen to main menu
        this.setScreen(new MainMenu(this));
    }

    @Override
    public void render() {
        super.render();
    }
}
