package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.BravehearthGame;
import com.mygdx.game.config.GameConfig;
import com.mygdx.game.entities.Avatar;
import com.mygdx.game.entities.DummyClass;
import com.mygdx.game.network.ClientConnection;
import com.mygdx.game.network.networkMessages.Logout;
import com.mygdx.game.network.networkMessages.Position;
import com.mygdx.game.util.CameraController;
import com.mygdx.game.util.CharacterClass;
import com.mygdx.game.util.ViewPortUtils;

public class GameScreen implements Screen {

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;
    private CameraController cameraController;
    private DummyClass dc;
    private SpriteBatch batch;
    private Texture healthBar;


    public GameScreen(){
        batch = new SpriteBatch();
        healthBar = new Texture("blank.png");
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        renderer = new ShapeRenderer();
        cameraController = new CameraController();

        cameraController.setStartPosition(ClientConnection.getInstance().getUser().getAvatar().getX(), ClientConnection.getInstance().getUser().getAvatar().getY());
        if (ClientConnection.getInstance().getUser().getAvatar().getCharacterClass().equals(CharacterClass.DUMMYCLASS)) {

            dc = new DummyClass(ClientConnection.getInstance().getActiveAvatars().get(
                    ClientConnection.getInstance().getUser().getAvatar().getId()));
            ClientConnection.getInstance().getUser()
                    .setAvatar(new DummyClass(
                            ClientConnection.getInstance().getAvatar()));
        }
    }

    @Override
    public void render(float delta) {
        cameraController.applyTo(camera);
        delta = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);

        renderViewportUtils();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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

    }

    private void renderViewportUtils() {
        renderer.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);
        ViewPortUtils.drawGrid(viewport, renderer);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        ClientConnection.getInstance().getActiveAvatars().forEach((uuid, avatar) -> {
            DummyClass dcs = (DummyClass) avatar;
            dcs.drawDebug(renderer);
            batch.begin();
            batch.setColor(Color.GREEN);
            batch.draw(healthBar, avatar.getX()-1, (float)(avatar.getY() + 1.2), (float)avatar.getHealth() * (float) 2/avatar.getMaxHealth(), (float) 0.2);
            batch.setColor(Color.WHITE);
            batch.end();
        });
        renderer.end();
    }

    private void update(float delta) {
        updatePlayer(delta);
        updateCamera();
    }

    private void updatePlayer(float delta) {
        ClientConnection.getInstance().getActiveAvatars().forEach((o, o2) -> {

            o2.update(delta);
        });


    }

    private void updateCamera() {
        Avatar av = ClientConnection.getInstance().getActiveAvatars().get(ClientConnection.getInstance().getUser().getAvatar().getId());
       cameraController.updatePosition(
               av.getX(),
               av.getY());
     }


}
