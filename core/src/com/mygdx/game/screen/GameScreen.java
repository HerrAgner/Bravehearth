package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.config.GameConfig;
import com.mygdx.game.entities.DummyClass;
import com.mygdx.game.entities.monsters.DummyMonster;
import com.mygdx.game.entities.monsters.Monster;
import com.mygdx.game.network.ClientConnection;
import com.mygdx.game.util.CameraController;
import com.mygdx.game.util.CharacterClass;
import com.mygdx.game.util.ViewPortUtils;

public class GameScreen implements Screen {

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;
    private CameraController cameraController;
    private DummyClass dc;
    private DummyMonster dm;

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        renderer = new ShapeRenderer();
        cameraController = new CameraController();

        dm = new DummyMonster();

        cameraController.setStartPosition(ClientConnection.getInstance().getAvatar().getX(), ClientConnection.getInstance().getAvatar().getY());
        if (ClientConnection.getInstance().getAvatar().getCharacterClass().equals(CharacterClass.DUMMYCLASS)){

            dc = new DummyClass(ClientConnection.getInstance().getAvatar());
            ClientConnection.getInstance().setAvatar(new DummyClass(ClientConnection.getInstance().getAvatar()));
        }
    }

    @Override
    public void render(float delta) {
        cameraController.applyTo(camera);
        delta = Gdx.graphics.getDeltaTime();
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
        ViewPortUtils.drawGrid(viewport, renderer);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        DummyClass dcs = (DummyClass) ClientConnection.getInstance().getAvatar();
        dm.drawDebug(renderer);
        dcs.drawDebug(renderer);
        renderer.end();
    }

    private void update(float delta) {
        updatePlayer(delta);
        updateCamera();
    }

    private void updatePlayer(float delta) {
        ClientConnection.getInstance().getAvatar().update(delta);
    }

    private void updateCamera() {
       cameraController.updatePosition(ClientConnection.getInstance().getAvatar().getX(), ClientConnection.getInstance().getAvatar().getY());
     }


}
