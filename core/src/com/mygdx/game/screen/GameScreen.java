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

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        renderer = new ShapeRenderer();
        cameraController = new CameraController();

        System.out.println("testing getName " + ClientConnection.getInstance().getUser().getAvatar().getName());
        cameraController.setStartPosition(ClientConnection.getInstance().getUser().getAvatar().getX(), ClientConnection.getInstance().getUser().getAvatar().getY());
        if (ClientConnection.getInstance().getUser().getAvatar().getCharacterClass().equals(CharacterClass.DUMMYCLASS)){

            dc = new DummyClass(ClientConnection.getInstance().getUser().getAvatar());
            ClientConnection.getInstance().getUser().setAvatar(new DummyClass(ClientConnection.getInstance().getAvatar()));
        }
    }

    @Override
    public void render(float delta) {
        cameraController.applyTo(camera);
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
        DummyClass dcs = (DummyClass) ClientConnection.getInstance().getUser().getAvatar();
        dcs.drawDebug(renderer);
        renderer.end();
    }

    private void update(float delta) {
        updatePlayer();
        updateCamera();
    }

    private void updatePlayer() {

        ClientConnection.getInstance().getUser().getAvatar().update();
    }

    private void updateCamera() {
       cameraController.updatePosition(ClientConnection.getInstance().getUser().getAvatar().getX(), ClientConnection.getInstance().getUser().getAvatar().getY());
     }


}
