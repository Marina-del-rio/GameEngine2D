package com.game.data.entity;

import jakarta.persistence.*;

/**
 * Objeto dinámico - Se mueve o tiene física
 */
@Entity
@DiscriminatorValue("DYNAMIC_OBJECT")
public class DynamicObjectData extends ObjectData {

    @Enumerated(EnumType.STRING)
    private DynamicType dynamicType = DynamicType.PHYSICS_OBJECT;

    // Velocidad
    private Double velocityX = 0.0;
    private Double velocityY = 0.0;

    // Física
    private Boolean hasPhysics = true;
    private Double mass = 1.0;
    private Double gravity = 9.8;

    // Movimiento (para plataformas)
    private String movementPattern;
    private Double movementRange;

    public enum DynamicType {
        PROJECTILE,       // Bala, flecha
        MOVING_PLATFORM,  // Plataforma móvil
        PHYSICS_OBJECT,   // Caja, barril
        PARTICLE          // Partículas
    }

    // ===== GETTERS Y SETTERS =====
    public DynamicType getDynamicType() { return dynamicType; }
    public void setDynamicType(DynamicType dynamicType) { this.dynamicType = dynamicType; }

    public Double getVelocityX() { return velocityX; }
    public void setVelocityX(Double velocityX) { this.velocityX = velocityX; }

    public Double getVelocityY() { return velocityY; }
    public void setVelocityY(Double velocityY) { this.velocityY = velocityY; }

    public Boolean getHasPhysics() { return hasPhysics; }
    public void setHasPhysics(Boolean hasPhysics) { this.hasPhysics = hasPhysics; }

    public Double getMass() { return mass; }
    public void setMass(Double mass) { this.mass = mass; }

    public Double getGravity() { return gravity; }
    public void setGravity(Double gravity) { this.gravity = gravity; }

    public String getMovementPattern() { return movementPattern; }
    public void setMovementPattern(String movementPattern) { this.movementPattern = movementPattern; }

    public Double getMovementRange() { return movementRange; }
    public void setMovementRange(Double movementRange) { this.movementRange = movementRange; }
}