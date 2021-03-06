package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.BravehearthGame;
import com.mygdx.game.GUI.Inventory;
import com.mygdx.game.config.GameConfig;
import com.mygdx.game.entities.Arrow;
import com.mygdx.game.entities.SlashAnimation;
import com.mygdx.game.entities.avatar.Avatar;
import com.mygdx.game.entities.avatar.Marksman;
import com.mygdx.game.entities.avatar.Sorcerer;
import com.mygdx.game.entities.avatar.Warrior;
import com.mygdx.game.entities.monsters.Monster;
import com.mygdx.game.network.ClientConnection;
import com.mygdx.game.network.networkMessages.MovementCommands;
import com.mygdx.game.util.CameraController;
import com.mygdx.game.util.InputHandler;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameScreen implements Screen {

    public static OrthographicCamera camera;
    private ShapeRenderer renderer;
    private CameraController cameraController;
    private SpriteBatch batch;
    private Texture healthBar;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private TiledMapTileLayer collision;
    private InputHandler inputHandler;
    private BravehearthGame game;
    private TextureAtlas textureAtlas;
    private TextureAtlas itemAtlas;
    private Skin itemSkin;
    private Skin uiSkin;
    final HashMap<String, Sprite> sprites;
    final HashMap<String, Sprite> monsterSprites;
    final HashMap<String, Sprite> itemSprites;
    private float oneSecond;
    private CopyOnWriteArrayList<Arrow> arrows;
    private CopyOnWriteArrayList<SlashAnimation> slashes;
    private TextButton respawn;
    private TextButton endGame;
    private Stage deathStage;
    private Inventory inventory;
    private InputMultiplexer im;
    private Sprite monsterSprite;
    private Monster mon;
    private Stage itemsOnGroundStage;
    float stateTime = 0f;
    Sprite item;
    BitmapFont name;
    BitmapFont nameShadow;
    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("calibrib.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    GlyphLayout layout = new GlyphLayout();
    GlyphLayout layoutShadow = new GlyphLayout();

    public GameScreen(BravehearthGame game) {
        im = new InputMultiplexer();
        inputHandler = new InputHandler();
        this.game = game;
        batch = new SpriteBatch();
        itemsOnGroundStage = new Stage();
        uiSkin = ClientConnection.getInstance().assetManager.get("terra-mother/skin/terra-mother-ui.json");
        healthBar = new Texture("blank.png");
        textureAtlas = ClientConnection.getInstance().assetManager.get("avatars/avatarSprites.txt");
        itemAtlas = ClientConnection.getInstance().assetManager.get("items/items.atlas");
        itemSkin = new Skin();
        itemSkin.addRegions(itemAtlas);
        sprites = new HashMap<>();
        monsterSprites = new HashMap<>();
        itemSprites = new HashMap<>();
        arrows = new CopyOnWriteArrayList<>();
        slashes = new CopyOnWriteArrayList<>();
        inventory = new Inventory(game.batch);
        parameter.size = 50;
        name = new BitmapFont();
        nameShadow = new BitmapFont();
        name = generator.generateFont(parameter);
        nameShadow = generator.generateFont(parameter);
        generator.dispose();
    }

    @Override
    public void show() {
        deathStage = new Stage();
        im.addProcessor(inputHandler);
        im.addProcessor(inventory.getStage());
        Gdx.input.setInputProcessor(im);
        camera = new OrthographicCamera(GameConfig.WIDTH, GameConfig.HEIGHT);
        camera.setToOrtho(false, GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
        renderer = new ShapeRenderer();

        cameraController = new CameraController();
        cameraController.setStartPosition(ClientConnection.getInstance().getUser().getAvatar().getX(), ClientConnection.getInstance().getUser().getAvatar().getY());

        tiledMap = new TmxMapLoader().load("worldMap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / 32f);
        collision = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        addSprites();
        addMonsterSprites();
        addItemSprites();
    }

    private void addSprites() {
        Array<AtlasRegion> regions = textureAtlas.getRegions();

        for (AtlasRegion region : regions) {
            Sprite sprite = textureAtlas.createSprite(region.name);
            sprites.put(region.name, sprite);
        }
    }

    private void addMonsterSprites() {
        TextureAtlas ta = ClientConnection.getInstance().assetManager.get("monsters/monsterSprites.txt");
        Array<AtlasRegion> regions = ta.getRegions();

        for (AtlasRegion region : regions) {
            Sprite sprite = ta.createSprite(region.name);
            monsterSprites.put(region.name, sprite);
        }
    }

    private void addItemSprites() {
        TextureAtlas ta = ClientConnection.getInstance().assetManager.get("items/items.atlas");

        Array<AtlasRegion> regions = ta.getRegions();
        for (AtlasRegion region : regions) {
            Sprite sprite = ta.createSprite(region.name);
            itemSprites.put(region.name, sprite);
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
        renderViewportUtils();
        update(delta);
        openInventory();
        stateTime += Gdx.graphics.getDeltaTime();
    }

    @Override
    public void resize(int width, int height) {
        inventory.resize(width,height);
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
        renderer.begin(ShapeRenderer.ShapeType.Line);
        batch.begin();
        renderItemsOnGround();


        ClientConnection.getInstance().getActiveMonsters().forEach((uuid, monster) -> {
            mon = monster;
            monsterSprite = monsterSprites.get(mon.getTexture());
            monsterSprite.setBounds(mon.getX(), mon.getY(), 1f, 1f);
            monsterSprite.draw(batch);

            if (monster.getHp() < monster.getMaxHp() * 0.3) {
                batch.setColor(Color.RED);
            } else if (monster.getHp() < monster.getMaxHp() * 0.6) {
                batch.setColor(Color.YELLOW);
            } else {
                batch.setColor(Color.GREEN);
            }
            batch.draw(healthBar, monster.getX(), monster.getY() + 1.2f, (float) monster.getHp() / monster.getMaxHp(), 0.2f);
            batch.setColor(Color.WHITE);

            if (ClientConnection.getInstance().getUser().getAvatar().getMarkedUnit() != -1 && ClientConnection.getInstance().getUser().getAvatar().getMarkedUnit() == monster.getId()) {
                renderer.rect((float) (monster.getX() - 0.1), (float) (monster.getY() - 0.1), (float) 1.2, (float) 1.2, Color.RED, Color.PINK, Color.RED, Color.PINK);
            }
        });

        ClientConnection.getInstance().getActiveAvatars().forEach((Integer, avatar) -> {
            if (ClientConnection.getInstance().getUser().getAvatar().getIsDead()) {
                youDiedPopUp();
            }

            if (avatar.getHealth() < avatar.getMaxHealth() * 0.3) {
                batch.setColor(Color.RED);
            } else if (avatar.getHealth() < avatar.getMaxHealth() * 0.6) {
                batch.setColor(Color.YELLOW);
            } else {
                batch.setColor(Color.GREEN);
            }
            batch.draw(healthBar, avatar.getX(), (float) (avatar.getY() + 1.1), (float) avatar.getHealth() / avatar.getMaxHealth(), (float) 0.2);
            batch.setColor(Color.WHITE);

            if (avatar.isAttacking().equals("ranged")) {
                Arrow arrow = new Arrow(Gdx.graphics.getDeltaTime());
                arrow.getPosition().x = avatar.getX();
                arrow.getPosition().y = avatar.getY();
                arrow.setTargetUnitType("monster");
                arrow.setTargetPosition(new Vector2(avatar.getTargetPosition()[0], avatar.getTargetPosition()[1]));
                this.arrows.add(arrow);
                avatar.setAttacking("");
            } else if (avatar.isAttacking().equals("melee")) {
                SlashAnimation slashAnimation = new SlashAnimation(Gdx.graphics.getDeltaTime());
                slashAnimation.setPosition(new Vector2(avatar.getX(), avatar.getY()));
                slashAnimation.setTargetUnitType("monster");
                slashAnimation.setTargetPosition(new Vector2(avatar.getTargetPosition()[0], avatar.getTargetPosition()[1]));
                this.slashes.add(slashAnimation);
                avatar.setAttacking("");
            }

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

            drawName(avatar.getName(), avatar.getX(), avatar.getY(), new Color(0.2f, 1f, 0.2f, 0.7f), 0.01f);
        });

        if (this.arrows != null && this.arrows.size() > 0) {
            this.arrows.forEach(arrow -> {
                batch.draw(arrow.getTexture(), arrow.getPosition().x, arrow.getPosition().y, 0.5f, 0.5f, 1, 1, 1, 1, arrow.getAngle());
                arrow.update(Gdx.graphics.getDeltaTime());
                if (arrow.shouldDispose(Gdx.graphics.getDeltaTime())) {
                    this.arrows.remove(arrow);
                } else {
                    arrow.increaseTimer(Gdx.graphics.getDeltaTime());
                }
            });
        }

        if (this.slashes != null && this.slashes.size() > 0) {
            this.slashes.forEach(slashAnimation -> {
                slashAnimation.updateAngle();
                slashAnimation.increaseTimer(Gdx.graphics.getDeltaTime());
                if (slashAnimation.shouldDispose()) {
                    this.slashes.remove(slashAnimation);
                } else {
                    batch.draw(slashAnimation.getTexture(), slashAnimation.getTargetPosition().x, slashAnimation.getTargetPosition().y, 0.5f, 0.5f, 1, 1, 1, 1, slashAnimation.getAngle());
                }
            });
        }
        batch.end();

        renderer.end();
    }

    private void update(float delta) {
        updateCamera();
    }

    private void renderAvatar(Avatar avatar, Color color) {
        switch (avatar.getCharacterClass()) {
            case SORCERER:
                Sorcerer sorc = (Sorcerer) avatar;
                sorc.getSprite().setRegion(sorc.getAnimation().getKeyFrame(stateTime));
                sorc.getSprite().setBounds(sorc.getX(), sorc.getY(), 0.8f, 1f);
                sorc.getSprite().setColor(color);
                sorc.getSprite().draw(batch);
                break;
            case WARRIOR:
                Warrior war = (Warrior) avatar;
                war.getSprite().setRegion(war.getAnimation().getKeyFrame(stateTime));
                war.getSprite().setBounds(war.getX(), war.getY(), 0.8f, 1f);
                war.getSprite().setColor(color);
                war.getSprite().draw(batch);
                break;
            case MARKSMAN:
                Marksman mark = (Marksman) avatar;
                mark.getSprite().setRegion(mark.getAnimation().getKeyFrame(stateTime));
                mark.getSprite().setBounds(mark.getX(), mark.getY(), 0.8f, 1f);
                mark.getSprite().setColor(color);
                mark.getSprite().draw(batch);
                break;
        }
    }

    private void drawName(String nameToPrint, float x, float y, Color color, float size) {

        layout.setText(name, nameToPrint);
        layoutShadow.setText(nameShadow, nameToPrint);
        final float fontX = x + (1 - layout.width) / 2;
        final float fontY = y + (3.4f + layout.height) / 2;
        nameShadow.getData().setScale(size, size);
        nameShadow.setUseIntegerPositions(false);
        nameShadow.setColor(0.1f, 0.1f, 0.1f, 1f);
        name.getData().setScale(size, size);
        name.setUseIntegerPositions(false);
        name.setColor(color);
        nameShadow.draw(batch, layoutShadow, fontX + 0.019f, fontY - 0.019f);
        name.draw(batch, layout, fontX, fontY);
    }

    private void renderItemsOnGround() {
        if (ClientConnection.getInstance().getItemsOnGround().size() > 0) {
            ClientConnection.getInstance().getItemsOnGround().forEach((floats, item1) -> {
                item = itemSprites.get(item1.getTexture());
                item.setBounds(floats[0], floats[1], 1, 1);
                item.draw(batch);
            });
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

    private void youDiedPopUp() {
        Gdx.input.setInputProcessor(deathStage);
        Skin skin = new Skin(Gdx.files.internal("terra-mother/skin/terra-mother-ui.json"));
        ClientConnection.getInstance().getClient().sendTCP(new MovementCommands(false, "W"));
        ClientConnection.getInstance().getClient().sendTCP(new MovementCommands(false, "A"));
        ClientConnection.getInstance().getClient().sendTCP(new MovementCommands(false, "S"));
        ClientConnection.getInstance().getClient().sendTCP(new MovementCommands(false, "D"));

        Window death = new Window("YOU DIED", skin);
        respawn = new TextButton("Respawn", skin, "default");
        float newWidth = 400, newHeight = 200;
        death.setBounds((Gdx.graphics.getWidth() - newWidth) / 2, (Gdx.graphics.getHeight() - newHeight) / 2, newWidth, newHeight);
        respawn.setWidth(80f);
        respawn.setHeight(40f);
        respawn.padRight(20f);
        respawn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.setInputProcessor(im);
                deathStage.unfocusAll();
                deathStage.dispose();
                ClientConnection.getInstance().getUser().getAvatar().setIsDead(false);
                deathStage = new Stage();
                inventory.clearInventory();
            }
        });

        endGame = new TextButton("End Game", skin, "default");
        endGame.setWidth(80f);
        endGame.setHeight(40f);
        endGame.padLeft(20f);
        endGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LoginScreen(game));
            }
        });

        death.add(respawn);
        death.add(endGame);

        deathStage.addActor(death);
        deathStage.draw();
    }

    private void openInventory() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            inventory.toggleInventory();
        }
        if (inventory.isOpen()) {
            inventory.getStage().act();
            inventory.getStage().draw();
            inventory.stats.draw();
        } else {
            inventory.getStage().unfocusAll();

        }
        if (ClientConnection.getInstance().getUser().getAvatar().getBackpack().isChanged()) {
            inventory.render();
            ClientConnection.getInstance().getUser().getAvatar().getBackpack().setChanged(false);
        }
        if (ClientConnection.getInstance().getUser().getAvatar().getEquippedItems().isChanged()) {
            inventory.render();
            ClientConnection.getInstance().getUser().getAvatar().getEquippedItems().setChanged(false);
        }
    }
}