package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MenuScreen implements Screen {
    private final MyGame game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private FitViewport viewport;

    private final int MENU_WIDTH = 640;
    private final int MENU_HEIGHT = 480;


    public MenuScreen(MyGame game) {
        this.game = game;
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false, MENU_WIDTH, MENU_HEIGHT);
        
        batch = new SpriteBatch();
        font = new BitmapFont(); //uses default font of Arial
        font.getData().setScale(2f); //this makes the text bigger

	viewport = new FitViewport(MENU_WIDTH, MENU_HEIGHT, camera);
    }

    @Override
    public void show() {
        //called when this screen becomes the current screen
    }

    @Override
    public void render(float delta) {
        //clears the screen
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        //draws the menu
        batch.begin();
        font.draw(batch, "Game Title", 200, 350);
        font.draw(batch, "Press SPACE to Start", 150, 250);
        font.draw(batch, "Press ESC to Exit", 170, 200);
        batch.end();

        //handles inputs
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    @Override
    public void resize(int width, int height) {
       	viewport.update(width,height); 
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
