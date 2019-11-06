package com.mygdx.game.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.mygdx.game.BravehearthGame;
import com.mygdx.game.network.ClientConnection;

import java.util.Arrays;
import java.util.stream.Collectors;

public class LoginScreen implements Screen {

    private final BravehearthGame game;
    private OrthographicCamera camera;
    private Stage stage;
    Stage stage2;
    TextField usernameTextField;
    TextField passwordTextField;
    Image usernameWindow;
    Image passwordWindow;
    Image buttonWindow;
    Image failedWindow;
    Skin skin;
    TextButton button;
    TextButton failed;
    Image backgroundImage;
    Image logo;
    Music music;

    public LoginScreen(BravehearthGame game) {
        this.game = game;
        this.stage = new Stage();
        stage2 = new Stage();
        camera = new OrthographicCamera();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("terra-mother/skin/terra-mother-ui.json"));
        initBackground();
        initTextField();
        initWindows();
        initButtons();
        initMusic();
    }

    private void initMusic() {
        music = Gdx.audio.newMusic(Gdx.files.internal("audio/bravehearth.mp3"));
        music.play();
        music.setVolume(0.3f);
    }

    private void initTextField() {
        usernameTextField = new TextField("", skin);
        usernameTextField.setPosition(Gdx.graphics.getWidth() / 2 - 100f, Gdx.graphics.getHeight() / 2 - 10f);
        usernameTextField.setSize(200, 40);
        usernameTextField.setMaxLength(12);

        passwordTextField = new TextField("", skin);
        passwordTextField.setPosition(Gdx.graphics.getWidth() / 2 - 100f, Gdx.graphics.getHeight() / 2 - 60f);
        passwordTextField.setSize(200, 30);
        passwordTextField.setPasswordMode(true);
        passwordTextField.setPasswordCharacter('*');
        passwordTextField.setMaxLength(12);

        stage.addActor(usernameTextField);
        stage.addActor(passwordTextField);

    }

    private void initWindows() {
        usernameWindow = new Image();
        usernameWindow.setDrawable(skin, "window");
        usernameWindow.setSize(220, 40);
        usernameWindow.setPosition(Gdx.graphics.getWidth() / 2 - 110f, Gdx.graphics.getHeight() / 2 - 10f);

        passwordWindow = new Image();
        passwordWindow.setDrawable(skin, "window");
        passwordWindow.setSize(220, 40);
        passwordWindow.setPosition(Gdx.graphics.getWidth() / 2 - 110f, Gdx.graphics.getHeight() / 2 - 60f);

        buttonWindow = new Image();
        buttonWindow.setDrawable(skin, "window");
        buttonWindow.setSize(80, 40);
        buttonWindow.setPosition(Gdx.graphics.getWidth() / 2 - 40f, Gdx.graphics.getHeight() / 2 - 110f);

        failedWindow = new Image();
        failedWindow.setDrawable(skin, "window");
        failedWindow.setSize(200, 40);
        failedWindow.setPosition(Gdx.graphics.getWidth() / 2 - 100f, Gdx.graphics.getHeight() / 2 - 110f);

        stage2.addActor(buttonWindow);
        stage2.addActor(usernameWindow);
        stage2.addActor(passwordWindow);
    }

    private void initButtons() {
        button = new TextButton("Login", skin, "default");
        button.setWidth(80f);
        button.setHeight(40f);
        button.setPosition(Gdx.graphics.getWidth() / 2 - 40f, Gdx.graphics.getHeight() / 2 - 110f);

        failed = new TextButton("Login failed", skin, "default");
        failed.setWidth(200f);
        failed.setHeight(40f);
        failed.setPosition(Gdx.graphics.getWidth() / 2 - 100f, Gdx.graphics.getHeight() / 2 - 110f);


        stage.addActor(button);

    }

    private void initBackground() {
        backgroundImage = new Image();
        backgroundImage.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("fantasy-wallpaper-1920x1080.jpg")))));
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        backgroundImage.setScaling(Scaling.fill);

        logo = new Image();
        logo.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("bravehearth-logo.png")))));
        logo.setSize(605, 89);
        logo.setPosition(Gdx.graphics.getWidth() / 2 - 300, Gdx.graphics.getHeight() - 200f);


        stage2.addActor(backgroundImage);
        stage2.addActor(logo);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        stage2.draw();
        stage.draw();
        game.batch.end();

        if (Gdx.input.isButtonJustPressed(0)) {
            if (this.button.getClickListener().isPressed()) {
                ClientConnection.getInstance().login(usernameTextField.getText(), passwordTextField.getText());
                try {

                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (ClientConnection.getInstance().getUser() != null) {
                    music.dispose();
                    game.setScreen(new GameScreen(game));
                } else {
                    button.remove();
                    buttonWindow.remove();
                    stage.addActor(failed);
                    stage2.addActor(failedWindow);
                }
            }
            if (this.failed.getClickListener().isPressed()) {
                failed.remove();
                failedWindow.remove();
                stage.addActor(button);
                stage2.addActor(buttonWindow);
            }
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        stage2.dispose();
        game.dispose();
        music.dispose();

    }
}
