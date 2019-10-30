package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.mygdx.game.network.ClientConnection;
import com.mygdx.game.screen.GameScreen;


public class BravehearthGame extends Game {


	@Override
	public void create() {
		String asd = "D";
		ClientConnection.getInstance().getClient().sendTCP(asd);
		setScreen(new GameScreen());

	}
}