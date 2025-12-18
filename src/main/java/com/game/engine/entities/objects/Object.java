package com.game.engine.entities.objects;

import com.game.engine.entities.Entity;

public class Object extends Entity {
    private boolean isStatic;
    private String rutaSprite;

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }
}