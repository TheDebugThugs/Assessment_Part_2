package io.github.some_example_name;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class BusTicket {
    private Vector2 position;
    private Texture texture;
    private boolean isCollected;
    private boolean isDiscovered; // To control when it becomes visible

    public BusTicket(float x, float y) {
        this.position = new Vector2(x, y);
        // Make sure you have an image named "bus-ticket.png" in your assets folder
        this.texture = new Texture("bus-ticket.png"); 
        this.isCollected = false;
        this.isDiscovered = false;
    }

    public void render(SpriteBatch batch) {
        // Only draw the ticket if it has been discovered and not yet collected
        if (isDiscovered && !isCollected) {
            batch.draw(texture, position.x, position.y, 16, 16);
        }
    }

    public void renderAsIcon(SpriteBatch batch, OrthographicCamera camera) {
        if (isCollected) {
            float iconSize = 32f; 
            float padding = 20f;

            // Calculate the top-right corner of the camera's view
            // We must account for the camera's zoom level
            float cornerX = camera.position.x + (camera.viewportWidth / 2 * camera.zoom);
            float cornerY = camera.position.y + (camera.viewportHeight / 2 * camera.zoom);

            // Set the icon's position with padding
            float iconX = cornerX - iconSize - padding;
            float iconY = cornerY - iconSize - padding;

            batch.draw(texture, iconX, iconY, iconSize, iconSize);
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void collect() {
        this.isCollected = true;
    }

    public void discover() {
        this.isDiscovered = true;
    }

    public void dispose() {
        texture.dispose();
    }
}
