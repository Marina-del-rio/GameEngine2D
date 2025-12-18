package com.game.data.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Proyecto de juego
 */
@Entity
@Table(name = "projects")
public class ProjectData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Integer windowWidth = 800;
    private Integer windowHeight = 600;
    private Integer targetFps = 60;
    private String assetsPath;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SceneData> scenes = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getWindowWidth() { return windowWidth; }
    public void setWindowWidth(Integer windowWidth) { this.windowWidth = windowWidth; }

    public Integer getWindowHeight() { return windowHeight; }
    public void setWindowHeight(Integer windowHeight) { this.windowHeight = windowHeight; }

    public Integer getTargetFps() { return targetFps; }
    public void setTargetFps(Integer targetFps) { this.targetFps = targetFps; }

    public String getAssetsPath() { return assetsPath; }
    public void setAssetsPath(String assetsPath) { this.assetsPath = assetsPath; }

    public List<SceneData> getScenes() { return scenes; }
    public void setScenes(List<SceneData> scenes) { this.scenes = scenes; }

    public List<EntityData> getEntities() { return entities; }
    public void setEntities(List<EntityData> entities) { this.entities = entities; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}