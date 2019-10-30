package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.screen.GameScreen;


public class BravehearthGame extends Game {


	@Override
	public void create () {
		setScreen(new GameScreen());
	}
}
