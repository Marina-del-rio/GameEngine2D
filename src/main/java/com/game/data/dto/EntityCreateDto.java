package com.game.data.dto;

/**
 * DTO para crear entidades
 */
public class EntityCreateDto {

    // Tipo de entidad
    private String entityType;  // CHARACTER, ENEMY, NPC, STATIC_OBJECT, DYNAMIC_OBJECT, AUDIO, UI_ELEMENT, TILE

    // Campos comunes
    private String name;
    private Double posX;
    private Double posY;
    private String spritePath;
    private Integer layer;
    private String tags;
    private Long sceneId;
    private Long projectId;

    // Para Actores
    private Integer health;
    private Double speed;

    // Para Character
    private Boolean isMainCharacter;
    private Integer lives;

    // Para Enemy
    private String enemyType;
    private Integer damage;
    private String aiBehavior;

    // Para NPC
    private String npcType;
    private String dialogueData;

    // Para Objects
    private Boolean solid;
    private String staticType;
    private String dynamicType;
    private Boolean hasPhysics;

    // Para Audio
    private String audioType;
    private String audioPath;
    private Double volume;
    private Boolean loop;

    // Para UI
    private String uiType;
    private String text;
    private Double width;
    private Double height;

    // Para Tile
    private Integer gridX;
    private Integer gridY;
    private String tilesetPath;
    private Boolean walkable;

    // ===== GETTERS Y SETTERS =====
    public String getEntityType() { return entityType; }
    public void setEntityType(String entityType) { this.entityType = entityType; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getPosX() { return posX; }
    public void setPosX(Double posX) { this.posX = posX; }

    public Double getPosY() { return posY; }
    public void setPosY(Double posY) { this.posY = posY; }

    public String getSpritePath() { return spritePath; }
    public void setSpritePath(String spritePath) { this.spritePath = spritePath; }

    public Integer getLayer() { return layer; }
    public void setLayer(Integer layer) { this.layer = layer; }

    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }

    public Long getSceneId() { return sceneId; }
    public void setSceneId(Long sceneId) { this.sceneId = sceneId; }

    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }

    public Integer getHealth() { return health; }
    public void setHealth(Integer health) { this.health = health; }

    public Double getSpeed() { return speed; }
    public void setSpeed(Double speed) { this.speed = speed; }

    public Boolean getIsMainCharacter() { return isMainCharacter; }
    public void setIsMainCharacter(Boolean isMainCharacter) { this.isMainCharacter = isMainCharacter; }

    public Integer getLives() { return lives; }
    public void setLives(Integer lives) { this.lives = lives; }

    public String getEnemyType() { return enemyType; }
    public void setEnemyType(String enemyType) { this.enemyType = enemyType; }

    public Integer getDamage() { return damage; }
    public void setDamage(Integer damage) { this.damage = damage; }

    public String getAiBehavior() { return aiBehavior; }
    public void setAiBehavior(String aiBehavior) { this.aiBehavior = aiBehavior; }

    public String getNpcType() { return npcType; }
    public void setNpcType(String npcType) { this.npcType = npcType; }

    public String getDialogueData() { return dialogueData; }
    public void setDialogueData(String dialogueData) { this.dialogueData = dialogueData; }

    public Boolean getSolid() { return solid; }
    public void setSolid(Boolean solid) { this.solid = solid; }

    public String getStaticType() { return staticType; }
    public void setStaticType(String staticType) { this.staticType = staticType; }

    public String getDynamicType() { return dynamicType; }
    public void setDynamicType(String dynamicType) { this.dynamicType = dynamicType; }

    public Boolean getHasPhysics() { return hasPhysics; }
    public void setHasPhysics(Boolean hasPhysics) { this.hasPhysics = hasPhysics; }

    public String getAudioType() { return audioType; }
    public void setAudioType(String audioType) { this.audioType = audioType; }

    public String getAudioPath() { return audioPath; }
    public void setAudioPath(String audioPath) { this.audioPath = audioPath; }

    public Double getVolume() { return volume; }
    public void setVolume(Double volume) { this.volume = volume; }

    public Boolean getLoop() { return loop; }
    public void setLoop(Boolean loop) { this.loop = loop; }

    public String getUiType() { return uiType; }
    public void setUiType(String uiType) { this.uiType = uiType; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public Double getWidth() { return width; }
    public void setWidth(Double width) { this.width = width; }

    public Double getHeight() { return height; }
    public void setHeight(Double height) { this.height = height; }

    public Integer getGridX() { return gridX; }
    public void setGridX(Integer gridX) { this.gridX = gridX; }

    public Integer getGridY() { return gridY; }
    public void setGridY(Integer gridY) { this.gridY = gridY; }

    public String getTilesetPath() { return tilesetPath; }
    public void setTilesetPath(String tilesetPath) { this.tilesetPath = tilesetPath; }

    public Boolean getWalkable() { return walkable; }
    public void setWalkable(Boolean walkable) { this.walkable = walkable; }
}