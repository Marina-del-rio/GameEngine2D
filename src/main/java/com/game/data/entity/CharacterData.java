package com.game.data.entity;

import jakarta.persistence.*;

/**
 * Personaje jugable
 */
@Entity
@DiscriminatorValue("CHARACTER")
public class CharacterData extends ActorData {

    private Boolean isMainCharacter = false;
    private Integer playerNumber = 1;
    private Integer lives = 3;
    private Integer score = 0;

    // Habilidades
    private Boolean canDoubleJump = false;
    private Boolean canDash = false;

    // Respawn
    private Double respawnX = 0.0;
    private Double respawnY = 0.0;

    // Inventario (JSON)
    @Column(columnDefinition = "TEXT")
    private String inventoryData;

    // ===== GETTERS Y SETTERS =====
    public Boolean getIsMainCharacter() { return isMainCharacter; }
    public void setIsMainCharacter(Boolean isMainCharacter) { this.isMainCharacter = isMainCharacter; }

    public Integer getPlayerNumber() { return playerNumber; }
    public void setPlayerNumber(Integer playerNumber) { this.playerNumber = playerNumber; }

    public Integer getLives() { return lives; }
    public void setLives(Integer lives) { this.lives = lives; }

    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }

    public Boolean getCanDoubleJump() { return canDoubleJump; }
    public void setCanDoubleJump(Boolean canDoubleJump) { this.canDoubleJump = canDoubleJump; }

    public Boolean getCanDash() { return canDash; }
    public void setCanDash(Boolean canDash) { this.canDash = canDash; }

    public Double getRespawnX() { return respawnX; }
    public void setRespawnX(Double respawnX) { this.respawnX = respawnX; }

    public Double getRespawnY() { return respawnY; }
    public void setRespawnY(Double respawnY) { this.respawnY = respawnY; }

    public String getInventoryData() { return inventoryData; }
    public void setInventoryData(String inventoryData) { this.inventoryData = inventoryData; }
}