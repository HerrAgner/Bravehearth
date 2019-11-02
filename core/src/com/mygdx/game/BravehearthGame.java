package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.network.ClientConnection;
import com.mygdx.game.network.ClientNetworkListener;
import com.mygdx.game.network.networkMessages.Logout;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.screen.TiledMapScreen;
import com.mygdx.game.util.InputHandler;


public class BravehearthGame extends Game {
	private InputHandler inputHandler;


	@Override
	public void create() {
		init();
		inputHandler = new InputHandler();
		Gdx.input.setInputProcessor(inputHandler);
		setScreen(new GameScreen());
	}

	private void init() {
		ClientNetworkListener cnl = new ClientNetworkListener();
//		float startPlayerX = GameConfig.WORLD_WIDTH / 2f;
//		float startPlayerY = GameConfig.WORLD_HEIGHT / 2f;
//		ClientConnection.getInstance().setPlayer(player);
//		player.setPosition(startPlayerX, startPlayerY);

	}

	@Override
	public void dispose() {
		Logout logout = new Logout();
		logout.setAvatar(ClientConnection.getInstance().getUser().getAvatar().getId());
		ClientConnection.getInstance().getClient().sendTCP(logout);
	}
}
