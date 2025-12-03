package io.github.some_example_name;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * <code> LeaderBoard </code> implements a static screen showing the user the leaderboard, containing the top 5 scores
 * user final score is passed to the JSON handler class to read and write to the file.
 * @see com.badlogic.gdx.Screen Screen.
 */

public class LeaderBoard implements Screen{
	private final MyGame game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont font;

	// scoring system variables
	private int finalScore;

	/**
	 * Constructor for <code> WinScreen </code>, using the game creator in
	 * <code> MyGame </code> to create the leaderboard screen.
	 * @param game Game creator.
	 * @param finalScore The calculated final score
	 */
	public LeaderBoard(MyGame game, int finalScore) {
		this.game = game;
		this.finalScore = finalScore;

        // screen setup
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 640, 480);
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.getData().setScale(2.5f);
	}

	/**
	 * Process input then render new frame, including dialog, for the win menu.
	 * @param delta Time in seconds since last frame finished rendering.
	 * @see com.badlogic.gdx.Screen#render Screen.render().
	 */
	@Override
	public void render(float delta) {
        // colours need to be in range of 0-1
		Gdx.gl.glClearColor(0.3f,0.4f,0.55f, 0.8f); // blue colour (72,90,140,0.8) RGBA
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();

		// read the leaderboard from the JSON file
		JSONHandler file = new JSONHandler();
		font.draw(batch, "Leaderboard:", 215, 430);

		try {
			file.writeLeaderboard(finalScore); // pass the final score to the JSON handler to be compared to existing scores
			String[] topScores = file.readLeaderboard();
			for (int i = 0; i < 5; i++) {
			    if (topScores[i] != null) {
			        font.draw(batch, topScores[i], 140, 350 - (i * 40));
			    }
			}
		} catch (IOException e) {
			e.printStackTrace(); // printing error message
		}

        font.draw(batch, "Your Score: " + finalScore, 205, 150); // show user their own score
		font.draw(batch, "press SPACE to return to menu", 65, 80);
		batch.end();

		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
		    game.setScreen(new MenuScreen(game)); // space key event to return to menu
		}
	}

   	/**
	 * Dispose win menu assets when menu is exited or program is quit.
	 * @see com.badlogic.gdx.Screen#dispose Screen.dispose().
	 */
	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}

	// complete unimplemented methods below

	/** Unimplemented */
	@Override
	public void show() {}

	/** Unimplemented */
	@Override
	public void resize(int width, int height) {}

	/** Unimplemented */
	@Override
	public void pause() {}

	/** Unimplemented */
	@Override
	public void resume() {}

	/** Unimplemented */
	@Override
	public void hide() {}
}
