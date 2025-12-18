package com.game.engine.entities.actors;

import com.game.engine.entities.Entity;

public abstract class Actor extends Entity {
    private int vida;
    private int velocidad;

    public int getVida() {
        return vida;
    }
    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getVelocidad() {
        return velocidad;
    }
    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }
}
