package com.game.data.entity;

import jakarta.persistence.*;

/**
 * NPC - Personaje no jugable
 */
@Entity
@DiscriminatorValue("NPC")
public class NPCData extends ActorData {

    @Enumerated(EnumType.STRING)
    private NPCType npcType = NPCType.GENERIC;

    // Interacción
    private Boolean isInteractable = true;
    private Double interactionRadius = 50.0;

    // Diálogos (JSON)
    @Column(columnDefinition = "TEXT")
    private String dialogueData;

    // Tienda (JSON)
    @Column(columnDefinition = "TEXT")
    private String shopData;

    // Misiones (JSON)
    @Column(columnDefinition = "TEXT")
    private String questData;

    // Comportamiento
    @Enumerated(EnumType.STRING)
    private NPCBehavior behavior = NPCBehavior.STATIONARY;

    public enum NPCType {
        GENERIC, MERCHANT, QUEST_GIVER, STORY, GUIDE
    }

    public enum NPCBehavior {
        STATIONARY, PATROL, WANDER, FOLLOW_PLAYER
    }

    // ===== GETTERS Y SETTERS =====
    public NPCType getNpcType() { return npcType; }
    public void setNpcType(NPCType npcType) { this.npcType = npcType; }

    public Boolean getIsInteractable() { return isInteractable; }
    public void setIsInteractable(Boolean isInteractable) { this.isInteractable = isInteractable; }

    public Double getInteractionRadius() { return interactionRadius; }
    public void setInteractionRadius(Double interactionRadius) { this.interactionRadius = interactionRadius; }

    public String getDialogueData() { return dialogueData; }
    public void setDialogueData(String dialogueData) { this.dialogueData = dialogueData; }

    public String getShopData() { return shopData; }
    public void setShopData(String shopData) { this.shopData = shopData; }

    public String getQuestData() { return questData; }
    public void setQuestData(String questData) { this.questData = questData; }

    public NPCBehavior getBehavior() { return behavior; }
    public void setBehavior(NPCBehavior behavior) { this.behavior = behavior; }
}