package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.network.ClientConnection;
import com.mygdx.game.network.ClientNetworkListener;
import com.mygdx.game.network.networkMessages.Logout;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.screen.LoginScreen;
import com.mygdx.game.screen.TiledMapScreen;
import com.mygdx.game.util.AttackLoop;
import com.mygdx.game.util.InputHandler;


public class BravehearthGame extends Game {
	public SpriteBatch batch;
	public BitmapFont font;


	@Override
	public void create() {
		init();

		setScreen(new LoginScreen(this));
//		setScreen(new GameScreen());
	}

	public void render() {
		super.render();
	}

	private void init() {
		new ClientNetworkListener();
		batch = new SpriteBatch();
		font = new BitmapFont();
	}

	@Override
	public void dispose() {
		if (ClientConnection.getInstance().getUser() != null) {
			Logout logout = new Logout();
			logout.setAvatar(ClientConnection.getInstance().getUser().getAvatar().getId());
			ClientConnection.getInstance().getClient().sendTCP(logout);
		} else {
			ClientConnection.getInstance().getClient().stop();
		}
		batch.dispose();
		font.dispose();
	}
}
