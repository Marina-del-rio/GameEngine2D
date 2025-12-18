package com.game.data.entity;

import jakarta.persistence.*;

/**
 * Base para objetos (Static y Dynamic)
 */
@Entity
@DiscriminatorValue("OBJECT")
public class ObjectData extends EntityData {

    private Boolean solid = false;
    private Boolean destructible = false;
    private Integer durability = 100;

    // Colisiones
    private Double colliderWidth = 32.0;
    private Double colliderHeight = 32.0;

    // ===== GETTERS Y SETTERS =====
    public Boolean getSolid() { return solid; }
    public void setSolid(Boolean solid) { this.solid = solid; }

    public Boolean getDestructible() { return destructible; }
    public void setDestructible(Boolean destructible) { this.destructible = destructible; }

    public Integer getDurability() { return durability; }
    public void setDurability(Integer durability) { this.durability = durability; }

    public Double getColliderWidth() { return colliderWidth; }
    public void setColliderWidth(Double colliderWidth) { this.colliderWidth = colliderWidth; }

    public Double getColliderHeight() { return colliderHeight; }
    public void setColliderHeight(Double colliderHeight) { this.colliderHeight = colliderHeight; }
}