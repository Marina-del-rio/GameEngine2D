package com.game.data.dto;

/**
 * DTO para buscar/filtrar entidades
 */
public class EntityQueryDto {

    private String entityType;  // Filtrar por tipo
    private Long sceneId;       // Filtrar por escena
    private Long projectId;     // Filtrar por proyecto
    private Boolean active;     // Solo activos/inactivos
    private String tags;        // Filtrar por tags
    private Integer layer;      // Filtrar por capa

    // ===== GETTERS Y SETTERS =====
    public String getEntityType() { return entityType; }
    public void setEntityType(String entityType) { this.entityType = entityType; }

    public Long getSceneId() { return sceneId; }
    public void setSceneId(Long sceneId) { this.sceneId = sceneId; }

    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }

    public Integer getLayer() { return layer; }
    public void setLayer(Integer layer) { this.layer = layer; }
}