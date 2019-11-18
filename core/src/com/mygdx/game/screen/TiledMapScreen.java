package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.entities.TiledPlayer;

public class TiledMapScreen implements Screen {

    TiledMap tiledMap;
    OrthographicCamera camera;
    OrthogonalTiledMapRenderer tiledMapRenderer;
    TiledPlayer tiledPlayer;
    SpriteBatch sb;


    @Override
    public void show() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        sb = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        camera.update();
        tiledMap = new TmxMapLoader().load("worldMap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);



        tiledPlayer = new TiledPlayer();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        tiledPlayer.update(delta);


        tiledMapRenderer.getBatch().begin();

        tiledMapRenderer.getBatch().end();
        sb.begin();
        sb.draw(tiledPlayer.getTexture(), tiledPlayer.getPosition().x, tiledPlayer.getPosition().y);
        sb.end();

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
        tiledMapRenderer.dispose();
    }
}
