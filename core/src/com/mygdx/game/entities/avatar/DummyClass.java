package com.mygdx.game.entities.avatar;

public class DummyClass extends Avatar {

    public DummyClass(String name){
        super(name);
    }

    public DummyClass(Avatar avatar){
        super(avatar);
        super.setBoundsRadius(1);
        super.setMaxXspeed(4);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }
}
