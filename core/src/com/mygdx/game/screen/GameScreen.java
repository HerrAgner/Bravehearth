package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.BravehearthGame;
import com.mygdx.game.config.GameConfig;
import com.mygdx.game.entities.avatar.Avatar;
import com.mygdx.game.entities.avatar.DummyClass;
import com.mygdx.game.network.ClientConnection;
import com.mygdx.game.util.CameraController;
import com.mygdx.game.util.CharacterClass;
import com.mygdx.game.util.InputHandler;
import com.mygdx.game.util.ViewPortUtils;

public class GameScreen implements Screen {

    public static OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;
    private CameraController cameraController;
    private DummyClass dc;
    private SpriteBatch batch;
    private Texture healthBar;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private Sprite sprite;
    private TiledMapTileLayer collision;
    private InputHandler inputHandler;
    private BravehearthGame game;

    public GameScreen(BravehearthGame game) {
        inputHandler = new InputHandler();
        Gdx.input.setInputProcessor(inputHandler);
        this.game = game;
        batch = new SpriteBatch();
        healthBar = new Texture("blank.png");
    }

    @Override
    public void show() {
        sprite = new Sprite(new Texture("pik.png"));
        camera = new OrthographicCamera(GameConfig.WIDTH, GameConfig.HEIGHT);
        camera.setToOrtho(false, 30, 20);
        //  viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
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
        tiledMap = new TmxMapLoader().load("worldMap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / 32f);
        collision = (TiledMapTileLayer) tiledMap.getLayers().get(0);
    }

    @Override
    public void render(float delta) {
        delta = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cameraController.applyTo(camera);
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        update(delta);
        renderViewportUtils();
    }

    @Override
    public void resize(int width, int height) {
        //  viewport.update(width, height, true);
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
        batch.dispose();
        renderer.dispose();
        tiledMapRenderer.dispose();
    }

    private void renderViewportUtils() {
        renderer.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);
//        ViewPortUtils.drawGrid(viewport, renderer);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        ClientConnection.getInstance().getActiveAvatars().forEach((uuid, avatar) -> {
            DummyClass dcs = (DummyClass) avatar;
            // dcs.drawDebug(renderer);
            batch.begin();
            if (avatar.getHealth() < avatar.getMaxHealth() * 0.3) {
                batch.setColor(Color.RED);
            } else if (avatar.getHealth() < avatar.getMaxHealth() * 0.6) {
                batch.setColor(Color.YELLOW);
            } else {
                batch.setColor(Color.GREEN);
            }
            batch.draw(healthBar, avatar.getX() - 1, (float) (avatar.getY() + 1.2), (float) avatar.getHealth() * 2 / avatar.getMaxHealth(), (float) 0.2);
            batch.setColor(Color.WHITE);
//            try {
//                if (collision.getCell((int) avatar.getX(), (int) avatar.getY()).getTile().getProperties().containsKey("blocked")) {
//                    System.out.println("blocked");
//                    dcs.setPosition(10, 10);
//                }
//            } catch (NullPointerException e) {dcs.setPosition(10, 10);
//
//            }
            sprite.setBounds(dcs.getX(), dcs.getY(), 1f, 1f);

            sprite.draw(batch);
            batch.end();
            /*if (ClientConnection.getInstance().getUser().getAvatar().getMarkedUnit() != null && ClientConnection.getInstance().getUser().getAvatar().getMarkedUnit().equals(dcs.getId())) {
                renderer.rect((float) (avatar.getX() - 1.1), (float) (avatar.getY() - 1.1), (float) 2.2, (float) 2.2, Color.RED, Color.PINK, Color.RED, Color.PINK);
            }*/
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
