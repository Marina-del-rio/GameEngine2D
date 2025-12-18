package com.game.engine.entities;


public abstract class Entity {

    private Long id;

    //posicion en el mapa
    private int posX;
    private int posY;

    //nombre de la entidad
    private String tag;

    private String rutaEntity;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public int getPosX() {
        return posX;
    }
    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }
    public void setPosY(int posY) {
        this.posY = posY;
    }

    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getRutaEntity() {
        return rutaEntity;
    }
    public void setRutaEntity(String rutaEntity) {
        this.rutaEntity = rutaEntity;
    }
}
