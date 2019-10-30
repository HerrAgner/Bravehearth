package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.mygdx.game.network.ClientConnection;
import com.mygdx.game.network.ClientNetworkListener;
import com.mygdx.game.screen.GameScreen;


public class BravehearthGame extends Game {


	@Override
	public void create() {
		ClientConnection.getInstance();
		ClientNetworkListener cnl = new ClientNetworkListener();

		setScreen(new GameScreen());

	}
}