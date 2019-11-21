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
    private FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("calibrib.ttf"));
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private GlyphLayout layout;
    private SpriteBatch batch;


    public Stats(SpriteBatch batch) {
        atlas = ClientConnection.getInstance().assetManager.get("hud.atlas");
        this.batch = batch;
        stage = new Stage();
        skin = new Skin();
        skin.addRegions(atlas);
        font = new BitmapFont();
        parameter.size = 22;
        font = generator.generateFont(parameter);
        generator.dispose();
        layout = new GlyphLayout();

        initWindow();
    }

    private void initWindow() {
        window = new Image();
        window.setDrawable(skin, "hudboxmiddlegray");
        window.setSize(200, 280);
        window.setPosition(Gdx.graphics.getWidth() / 2 - 520f, Gdx.graphics.getHeight() / 2 - 130f);
        stage.addActor(window);
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    public void draw() {
        Avatar av = ClientConnection.getInstance().getUser().getAvatar();
        Avatar currentAv = ClientConnection.getInstance().getActiveAvatars().get(av.getId());
        stage.getBatch().begin();
        window.draw(stage.getBatch(), 100);

        layout.setText(font, av.getName());
        font.draw(stage.getBatch(), layout, Gdx.graphics.getWidth() / 2f - 500f, Gdx.graphics.getHeight() / 2f + 110f);

//        layout = new GlyphLayout(font, "lvl: " + av.getLevel());
        layout.setText(font, "lvl: " + av.getLevel());
        font.draw(stage.getBatch(), layout, Gdx.graphics.getWidth() / 2f - 500f, Gdx.graphics.getHeight() / 2f + 90f);

        layout.setText(font, "hp: " + av.getHealth() + " / " + av.getMaxHealth());
        font.draw(stage.getBatch(), layout, Gdx.graphics.getWidth() / 2f - 500f, Gdx.graphics.getHeight() / 2f + 70f);

        layout.setText(font, "xp: " + av.getExperiencePoints());
        font.draw(stage.getBatch(), layout, Gdx.graphics.getWidth() / 2f - 500f, Gdx.graphics.getHeight() / 2f + 50f);

        layout.setText(font, "xp to level: " + ((25 * currentAv.getLevel() * (1 + av.getLevel())) - av.getExperiencePoints()));
        font.draw(stage.getBatch(), layout, Gdx.graphics.getWidth() / 2f - 500f, Gdx.graphics.getHeight() / 2f + 30f);

        layout.setText(font, "str: " + av.getStrength());
        font.draw(stage.getBatch(), layout, Gdx.graphics.getWidth() / 2f - 500f, Gdx.graphics.getHeight() / 2f + 10f);

        layout.setText(font, "int: " + av.getIntelligence());
        font.draw(stage.getBatch(), layout, Gdx.graphics.getWidth() / 2f - 500f, Gdx.graphics.getHeight() / 2f - 10f);

        layout.setText(font, "dex: " + av.getDexterity());
        font.draw(stage.getBatch(), layout, Gdx.graphics.getWidth() / 2f - 500f, Gdx.graphics.getHeight() / 2f - 30f);

        layout.setText(font, "dmg: " + av.getAttackDamage());
        font.draw(stage.getBatch(), layout, Gdx.graphics.getWidth() / 2f - 500f, Gdx.graphics.getHeight() / 2f - 50f);

        layout.setText(font, "defence: " + av.getDefence());
        font.draw(stage.getBatch(), layout, Gdx.graphics.getWidth() / 2f - 500f, Gdx.graphics.getHeight() / 2f - 70f);

        stage.getBatch().end();
    }
}
