package com.game.data.entity;

import jakarta.persistence.*;

/**
 * Base para todos los actores (Character, Enemy, NPC)
 */
@Entity
@DiscriminatorValue("ACTOR")
public class    ActorData extends EntityData {

    private Integer health = 100;
    private Integer maxHealth = 100;
    private Double speed = 5.0;

    // Colisiones
    private Double colliderWidth = 32.0;
    private Double colliderHeight = 32.0;

    // Animación
    private String animationSet;
    private String currentState = "idle";
    private String facingDirection = "right";

    // ===== GETTERS Y SETTERS =====
    public Integer getHealth() { return health; }
    public void setHealth(Integer health) { this.health = health; }

    public Integer getMaxHealth() { return maxHealth; }
    public void setMaxHealth(Integer maxHealth) { this.maxHealth = maxHealth; }

    public Double getSpeed() { return speed; }
    public void setSpeed(Double speed) { this.speed = speed; }

    public Double getColliderWidth() { return colliderWidth; }
    public void setColliderWidth(Double colliderWidth) { this.colliderWidth = colliderWidth; }

    public Double getColliderHeight() { return colliderHeight; }
    public void setColliderHeight(Double colliderHeight) { this.colliderHeight = colliderHeight; }

    public String getAnimationSet() { return animationSet; }
    public void setAnimationSet(String animationSet) { this.animationSet = animationSet; }

    public String getCurrentState() { return currentState; }
    public void setCurrentState(String currentState) { this.currentState = currentState; }

    public String getFacingDirection() { return facingDirection; }
    public void setFacingDirection(String facingDirection) { this.facingDirection = facingDirection; }
}