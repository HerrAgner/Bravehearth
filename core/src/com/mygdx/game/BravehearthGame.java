package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.screen.TiledMapScreen;


public class BravehearthGame extends Game {


	@Override
	public void create() {
		setScreen(new GameScreen());

	}
}