package com.mygdx.game.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.entities.avatar.Avatar;
import com.mygdx.game.network.ClientConnection;

public class Stats {
    private Stage stage;
    private Skin skin;
    private Image window;
    private BitmapFont font;
    private TextureAtlas atlas;
    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("calibrib.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    GlyphLayout layout;
    SpriteBatch batch;


    public Stats(SpriteBatch batch) {
        atlas = ClientConnection.getInstance().assetManager.get("hud.atlas");
        this.batch = batch;
        stage = new Stage();
        skin = new Skin();
        skin.addRegions(atlas);
//        skin = ClientConnection.getInstance().assetManager.get("terra-mother/skin/terra-mother-ui.json");
        font = new BitmapFont();
        parameter.size = 22;
        font = generator.generateFont(parameter);
        generator.dispose();

        initWindow();
        initText();
    }

    private void initWindow() {
        window = new Image();
        window.setDrawable(skin, "hudboxmiddlegray");
        window.setSize(200, 280);
        window.setPosition(Gdx.graphics.getWidth() / 2 - 520f, Gdx.graphics.getHeight() / 2 - 130f);
        stage.addActor(window);


    }

    private void initText() {


//        stage.draw();
    }

    public void draw() {
        Avatar av = ClientConnection.getInstance().getUser().getAvatar();
        stage.getBatch().begin();
        window.draw(stage.getBatch(), 100);

        layout = new GlyphLayout(font, av.getName());
        font.draw(stage.getBatch(), layout, Gdx.graphics.getWidth() / 2 - 500f, Gdx.graphics.getHeight() / 2 + 110f);

        layout = new GlyphLayout(font, "lvl: " + av.getLevel());
        font.draw(stage.getBatch(), layout, Gdx.graphics.getWidth() / 2 - 500f, Gdx.graphics.getHeight() / 2 + 90f);

        layout = new GlyphLayout(font, "hp: " + av.getHealth() + " / " + av.getMaxHealth());
        font.draw(stage.getBatch(), layout, Gdx.graphics.getWidth() / 2 - 500f, Gdx.graphics.getHeight() / 2 + 70f);

        layout = new GlyphLayout(font, "xp: " + av.getExperiencePoints());
        font.draw(stage.getBatch(), layout, Gdx.graphics.getWidth() / 2 - 500f, Gdx.graphics.getHeight() / 2 + 50f);

        layout = new GlyphLayout(font, "xp to level: " + ((25 * av.getLevel() * (1 + av.getLevel())) - av.getExperiencePoints()));
        font.draw(stage.getBatch(), layout, Gdx.graphics.getWidth() / 2 - 500f, Gdx.graphics.getHeight() / 2 + 30f);

        layout = new GlyphLayout(font, "str: " + av.getStrength());
        font.draw(stage.getBatch(), layout, Gdx.graphics.getWidth() / 2 - 500f, Gdx.graphics.getHeight() / 2 + 10f);

        layout = new GlyphLayout(font, "int: " + av.getIntelligence());
        font.draw(stage.getBatch(), layout, Gdx.graphics.getWidth() / 2 - 500f, Gdx.graphics.getHeight() / 2 - 10f);

        layout = new GlyphLayout(font, "dex: " + av.getDexterity());
        font.draw(stage.getBatch(), layout, Gdx.graphics.getWidth() / 2 - 500f, Gdx.graphics.getHeight() / 2 - 30f);

        layout = new GlyphLayout(font, "dmg: " + av.getAttackDamage());
        font.draw(stage.getBatch(), layout, Gdx.graphics.getWidth() / 2 - 500f, Gdx.graphics.getHeight() / 2 - 50f);

        layout = new GlyphLayout(font, "defence: " + av.getDefence());
        font.draw(stage.getBatch(), layout, Gdx.graphics.getWidth() / 2 - 500f, Gdx.graphics.getHeight() / 2 - 70f);

        stage.getBatch().end();
    }
}
