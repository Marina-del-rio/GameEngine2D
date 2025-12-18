package com.game.data.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Escena/Nivel del juego
 */
@Entity
@Table(name = "scenes")
public class SceneData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer sceneOrder = 0;
    private Integer width = 1920;
    private Integer height = 1080;
    private String backgroundColor = "#87CEEB";
    private String backgroundImage;
    private Boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private ProjectData project;

    @OneToMany(mappedBy = "scene", cascade = CascadeType.ALL)
    private List<EntityData> entities = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ===== GETTERS Y SETTERS =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getSceneOrder() { return sceneOrder; }
    public void setSceneOrder(Integer sceneOrder) { this.sceneOrder = sceneOrder; }

    public Integer getWidth() { return width; }
    public void setWidth(Integer width) { this.width = width; }

    public Integer getHeight() { return height; }
    public void setHeight(Integer height) { this.height = height; }

    public String getBackgroundColor() { return backgroundColor; }
    public void setBackgroundColor(String backgroundColor) { this.backgroundColor = backgroundColor; }

    public String getBackgroundImage() { return backgroundImage; }
    public void setBackgroundImage(String backgroundImage) { this.backgroundImage = backgroundImage; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public ProjectData getProject() { return project; }
    public void setProject(ProjectData project) { this.project = project; }

    public List<EntityData> getEntities() { return entities; }
    public void setEntities(List<EntityData> entities) { this.entities = entities; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}