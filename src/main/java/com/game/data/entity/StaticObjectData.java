package com.game.data.entity;

import jakarta.persistence.*;

/**
 * Objeto estático - No se mueve
 */
@Entity
@DiscriminatorValue("STATIC_OBJECT")
public class StaticObjectData extends ObjectData {

    @Enumerated(EnumType.STRING)
    private StaticType staticType = StaticType.DECORATION;

    public enum StaticType {
        DECORATION,   // Solo visual
        OBSTACLE,     // Bloquea paso
        PLATFORM,     // Se puede pisar
        TRIGGER,      // Activa eventos
        COLLECTIBLE   // Se puede recoger
    }

    // ===== GETTERS Y SETTERS =====
    public StaticType getStaticType() { return staticType; }
    public void setStaticType(StaticType staticType) { this.staticType = staticType; }
}