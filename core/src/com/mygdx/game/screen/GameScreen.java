package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.BravehearthGame;
import com.mygdx.game.config.GameConfig;
import com.mygdx.game.entities.avatar.*;
import com.mygdx.game.entities.monsters.DummyMonster;
import com.mygdx.game.network.ClientConnection;
import com.mygdx.game.util.CameraController;
import com.mygdx.game.util.InputHandler;
import java.util.HashMap;

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
    private TextureAtlas textureAtlas;
    final HashMap<String, Sprite> sprites;
    private float oneSecond;

    public GameScreen(BravehearthGame game) {
        inputHandler = new InputHandler();
        Gdx.input.setInputProcessor(inputHandler);
        this.game = game;
        batch = new SpriteBatch();
        healthBar = new Texture("blank.png");
        textureAtlas = new TextureAtlas("avatars/avatarSprites.txt");
        sprites = new HashMap<>();
    }

    @Override
    public void show() {
        camera = new OrthographicCamera(GameConfig.WIDTH, GameConfig.HEIGHT);
        camera.setToOrtho(false, 30, 20);
        //  viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        renderer = new ShapeRenderer();
        cameraController = new CameraController();

        cameraController.setStartPosition(ClientConnection.getInstance().getUser().getAvatar().getX(), ClientConnection.getInstance().getUser().getAvatar().getY());
        if (ClientConnection.getInstance().getUser().getAvatar().getCharacterClass() != null) {
            ClientConnection.getInstance().getUser()
                    .setAvatar(ClientConnection.getInstance().getUser().getAvatar());
        }
        tiledMap = new TmxMapLoader().load("worldMap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / 32f);
        collision = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        addSprites();
    }

    private void addSprites() {
        Array<AtlasRegion> regions = textureAtlas.getRegions();

        for (AtlasRegion region : regions) {
            Sprite sprite = textureAtlas.createSprite(region.name);

            sprites.put(region.name, sprite);
        }
    }

    @Override
    public void render(float delta) {
        delta = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glLineWidth(3);
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
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

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
        batch.begin();

        ClientConnection.getInstance().getActiveAvatars().forEach((Integer, avatar) -> {
            if (avatar.getHealth() < avatar.getMaxHealth() * 0.3) {
                batch.setColor(Color.RED);
            } else if (avatar.getHealth() < avatar.getMaxHealth() * 0.6) {
                batch.setColor(Color.YELLOW);
            } else {
                batch.setColor(Color.GREEN);
            }
            batch.draw(healthBar, avatar.getX() - 1, (float) (avatar.getY() + 1.2), (float) avatar.getHealth() / avatar.getMaxHealth(), (float) 0.2);
            batch.setColor(Color.WHITE);
            if (avatar.isHurt()) {
                renderAvatar(avatar, Color.RED);
                if (oneSecond > 1) {
                    avatar.setHurt(false);
                    oneSecond = Gdx.graphics.getDeltaTime();
                } else {
                    oneSecond += Gdx.graphics.getDeltaTime();
                }
            } else {
                renderAvatar(avatar, Color.WHITE);
            }
            batch.setColor(Color.WHITE);


//            if (ClientConnection.getInstance().getUser().getAvatar().getMarkedUnit() != null && ClientConnection.getInstance().getUser().getAvatar().getMarkedUnit().equals(dcs.getId())) {
//                renderer.rect((float) (avatar.getX() - 1.1), (float) (avatar.getY() - 1.1), (float) 2.2, (float) 2.2, Color.RED, Color.PINK, Color.RED, Color.PINK);
//            }
        });
        ClientConnection.getInstance().getActiveMonsters().forEach((uuid, monster) -> {
            DummyMonster dummyMonster = (DummyMonster) monster;
            dummyMonster.getSprite().setBounds(dummyMonster.getX(), dummyMonster.getY(), 1f, 1f);
            dummyMonster.getSprite().draw(batch);

            if (monster.getHp() < monster.getMaxHp() * 0.3) {
                batch.setColor(Color.RED);
            } else if (monster.getHp() < monster.getMaxHp() * 0.6) {
                batch.setColor(Color.YELLOW);
            } else {
                batch.setColor(Color.GREEN);
            }
            batch.draw(healthBar, monster.getX(), monster.getY() + 1.2f, (float) monster.getHp() / monster.getMaxHp(), 0.2f);
            batch.setColor(Color.WHITE);


            if (ClientConnection.getInstance().getUser().getAvatar().getMarkedUnit() != 0 && ClientConnection.getInstance().getUser().getAvatar().getMarkedUnit() == monster.getId()) {
                System.out.println(monster.getHp());
                System.out.println(monster.getMaxHp());
                renderer.rect((float) (monster.getX() - 0.1), (float) (monster.getY() - 0.1), (float) 1.2, (float) 1.2, Color.RED, Color.PINK, Color.RED, Color.PINK);
            }

        });
        batch.end();

        renderer.end();
    }

    private void update(float delta) {
        updatePlayer(delta);
        updateCamera();
    }

    private void renderAvatar(Avatar avatar, Color color) {
        switch (avatar.getCharacterClass()) {
            case SORCERER:
                Sorcerer sorc = (Sorcerer) avatar;
                sprite = sprites.get("sorcerer_front");
                sorc.setSprite(sprite);
                sorc.getSprite().setBounds(sorc.getX(), sorc.getY(), 1f, 1f);
                sorc.getSprite().setColor(color);
                sorc.getSprite().draw(batch);
                break;
            case WARRIOR:
                Warrior war = (Warrior) avatar;
                sprite = sprites.get("warrior_front");
                war.setSprite(sprite);
                war.getSprite().setBounds(war.getX(), war.getY(), 1f, 1f);
                war.getSprite().setColor(color);
                war.getSprite().draw(batch);
                break;
            case MARKSMAN:
                Marksman mark = (Marksman) avatar;
                sprite = sprites.get("marksman_front");
                mark.setSprite(sprite);
                sprite.setBounds(mark.getX(), mark.getY(), 1f, 1f);
                mark.getSprite().setColor(color);
                sprite.draw(batch);
                break;
        }

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
