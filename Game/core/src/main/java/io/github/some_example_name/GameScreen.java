package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen implements Screen {
    private final MyGame game;

    TiledMap tiledMap;
    OrthogonalTiledMapRenderer mapRenderer;
    OrthographicCamera camera;
    FitViewport viewport;

    private SpriteBatch batch;
    private Player player;

    private final int MAP_WIDTH = 640;
    private final int MAP_HEIGHT = 640;

    public GameScreen(MyGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, MAP_WIDTH, MAP_HEIGHT);
        camera.zoom=0.5f;
        camera.update();

        tiledMap = new TmxMapLoader().load("Tile Maps/Final Game Map - Maze.tmx");
        
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        viewport = new FitViewport(MAP_WIDTH, MAP_HEIGHT, camera);

        batch = new SpriteBatch();
        player = new Player(145, 120); // Player's starting position
    }

    @Override
    public void render(float delta) {
        handleInput();

        Gdx.gl.glClearColor(0, 0, 0, 1); // Clear screen to black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Make the camera follow the player
        camera.position.set(player.getPosition().x, player.getPosition().y, 0);
        camera.update();

        mapRenderer.setView(camera);
        mapRenderer.render();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        player.render(batch);
        batch.end();

        // Press ESC to return to the main menu
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MenuScreen(game));
        }
    }

    private void handleInput() {
        float moveSpeed = 1f;
        float newX = player.getPosition().x;
        float newY = player.getPosition().y;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            newY += moveSpeed;
            player.setDirection(Player.Direction.UP);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            newY -= moveSpeed;
            player.setDirection(Player.Direction.DOWN);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            newX -= moveSpeed;
            player.setDirection(Player.Direction.LEFT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            newX += moveSpeed;
            player.setDirection(Player.Direction.RIGHT);
        }

        if (!isCellBlocked(newX, newY)) {
            player.getPosition().set(newX, newY);
        }
    }

    private boolean isCellBlocked(float x, float y) {
        for (int i = 0; i < tiledMap.getLayers().getCount(); i++) {
            if (tiledMap.getLayers().get(i) instanceof TiledMapTileLayer) {
                TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(i);
                int tileX = (int) ((x + 8) / layer.getTileWidth());
                int tileY = (int) ((y + 8) / layer.getTileHeight());
                TiledMapTileLayer.Cell cell = layer.getCell(tileX, tileY);

                if (cell != null && cell.getTile() != null) {
                    if (cell.getTile().getProperties().containsKey("collidable") || layer.getProperties().containsKey("collidable")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        mapRenderer.dispose();
        batch.dispose();
        player.dispose();
    }

    @Override
    public void show() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}