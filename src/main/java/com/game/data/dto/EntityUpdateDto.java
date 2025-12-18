package com.game.data.dto;

/**
 * DTO para actualizar entidades (todos los campos opcionales)
 */
public class EntityUpdateDto {

    private String name;
    private Double posX;
    private Double posY;
    private Double scaleX;
    private Double scaleY;
    private Double rotation;
    private String spritePath;
    private Boolean active;
    private Boolean visible;
    private Integer layer;
    private String tags;

    // Para Actores
    private Integer health;
    private Double speed;

    // (añadir más según necesites)

    // ===== GETTERS Y SETTERS =====
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getPosX() { return posX; }
    public void setPosX(Double posX) { this.posX = posX; }

    public Double getPosY() { return posY; }
    public void setPosY(Double posY) { this.posY = posY; }

    public Double getScaleX() { return scaleX; }
    public void setScaleX(Double scaleX) { this.scaleX = scaleX; }

    public Double getScaleY() { return scaleY; }
    public void setScaleY(Double scaleY) { this.scaleY = scaleY; }

    public Double getRotation() { return rotation; }
    public void setRotation(Double rotation) { this.rotation = rotation; }

    public String getSpritePath() { return spritePath; }
    public void setSpritePath(String spritePath) { this.spritePath = spritePath; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public Boolean getVisible() { return visible; }
    public void setVisible(Boolean visible) { this.visible = visible; }

    public Integer getLayer() { return layer; }
    public void setLayer(Integer layer) { this.layer = layer; }

    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }

    public Integer getHealth() { return health; }
    public void setHealth(Integer health) { this.health = health; }

    public Double getSpeed() { return speed; }
    public void setSpeed(Double speed) { this.speed = speed; }
}