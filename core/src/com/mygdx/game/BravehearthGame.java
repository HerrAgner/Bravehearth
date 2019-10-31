package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.mygdx.game.network.ClientNetworkListener;
import com.mygdx.game.screen.GameScreen;


public class BravehearthGame extends Game {

	@Override
	public void create() {
		init();
		setScreen(new GameScreen());

	}

	private void init() {
		ClientNetworkListener cnl = new ClientNetworkListener();
//		float startPlayerX = GameConfig.WORLD_WIDTH / 2f;
//		float startPlayerY = GameConfig.WORLD_HEIGHT / 2f;
//		ClientConnection.getInstance().setPlayer(player);
//		player.setPosition(startPlayerX, startPlayerY);

	}
}