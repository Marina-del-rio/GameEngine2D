package com.game.data.entity;

import jakarta.persistence.*;

/**
 * Tile - Elemento del mapa/tilemap
 */
@Entity
@DiscriminatorValue("TILE")
public class TileData extends EntityData {

    // Posición en el grid (no en pixeles)
    private Integer gridX = 0;
    private Integer gridY = 0;

    // Tamaño del tile
    private Integer tileWidth = 32;
    private Integer tileHeight = 32;

    // Referencia al tileset
    private String tilesetPath;
    private Integer tilesetIndex = 0;

    // Propiedades
    private Boolean walkable = true;
    private Boolean climbable = false;
    private Boolean liquid = false;

    // Daño (lava, pinchos, etc.)
    private Integer damageOnContact = 0;

    // Capa del tilemap
    private Integer tilemapLayer = 0;

    // ===== GETTERS Y SETTERS =====
    public Integer getGridX() { return gridX; }
    public void setGridX(Integer gridX) { this.gridX = gridX; }

    public Integer getGridY() { return gridY; }
    public void setGridY(Integer gridY) { this.gridY = gridY; }

    public Integer getTileWidth() { return tileWidth; }
    public void setTileWidth(Integer tileWidth) { this.tileWidth = tileWidth; }

    public Integer getTileHeight() { return tileHeight; }
    public void setTileHeight(Integer tileHeight) { this.tileHeight = tileHeight; }

    public String getTilesetPath() { return tilesetPath; }
    public void setTilesetPath(String tilesetPath) { this.tilesetPath = tilesetPath; }

    public Integer getTilesetIndex() { return tilesetIndex; }
    public void setTilesetIndex(Integer tilesetIndex) { this.tilesetIndex = tilesetIndex; }

    public Boolean getWalkable() { return walkable; }
    public void setWalkable(Boolean walkable) { this.walkable = walkable; }

    public Boolean getClimbable() { return climbable; }
    public void setClimbable(Boolean climbable) { this.climbable = climbable; }

    public Boolean getLiquid() { return liquid; }
    public void setLiquid(Boolean liquid) { this.liquid = liquid; }

    public Integer getDamageOnContact() { return damageOnContact; }
    public void setDamageOnContact(Integer damageOnContact) { this.damageOnContact = damageOnContact; }

    public Integer getTilemapLayer() { return tilemapLayer; }
    public void setTilemapLayer(Integer tilemapLayer) { this.tilemapLayer = tilemapLayer; }
}