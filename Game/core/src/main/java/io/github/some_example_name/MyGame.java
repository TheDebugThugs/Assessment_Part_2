package io.github.some_example_name;

import com.badlogic.gdx.Game;

public class MyGame extends Game {
    @Override
    public void create() {
        //this tells the game to start by showing the menu screen.
        setScreen(new MenuScreen(this));
    }
}