package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * the locker inside the computer science building can be searched through by the player pressing e
 * tha player will fins a "sweet treat" which gives them a temporary speed boost
 */

public class Locker {
    private Texture texture;
    private Vector2 position;
    private Rectangle bounds;
    private boolean searched = false;
    private boolean showMessage = false;
    private float messageTimer = 0f;
    private final float messageDuration = 5f; //5 seconds
    private final float speedBoostAmount = 200f;
    private final float speedBoostDuration = 10f; //10seconds
    private float speedBoostTimer = 0f;
    private BitmapFont font;

    public Locker(float x, float y) {
        texture = new Texture("locker.png"); //set a new texture equal to the locker's png
        position = new Vector2(x, y);
        bounds = new Rectangle(x,y,texture.getWidth(), texture.getHeight());
        font = new BitmapFont();
    }

    public void update(Player player, float delta) {
        if (!searched && Gdx.input.isKeyJustPressed(Input.Keys.E)){
            //check if the player is in the proximity of the locker 
            if (player.getPosition().dst(position) < 50f) {
                searched = true;
                showMessage = true;
                messageTimer = 0f;
                speedBoostTimer = speedBoostDuration;
            }
        }

        //manage the speed boost timer
        if (speedBoostTimer > 0){
            speedBoostTimer -= delta;
            //this has been integrated with the movement logic in GameScreen
        }

        //manage the message timer
        if (showMessage) {
            messageTimer += delta;
            if (messageTimer > messageDuration){
                showMessage = false;
            }
        }
    }

    public void render(SpriteBatch batch){
        batch.draw(texture, position.x, position.y);
        if (showMessage) {
            font.draw(batch, "You found a sweet treat, \n enjoy the sugar induced speed boost!", position.x - 100, position.y + texture.getHeight() + 40); //prints the message above the locker
        }
    }

    public boolean isBoostActive() {
        return speedBoostTimer > 0;
    }

    public void dispose(){
        texture.dispose();
        font.dispose();
    }

}
