package com.game.data.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad base para TODAS las entidades del juego en BD
 * Equivalente a tu Entity.java del motor
 */

// DEFINEN --------------------

// @Entity:  Indicador para Hibernate, indica que es una Entidad de Hibernate.
// @Table: Indica a Hibernate que "EntityData" se mapea en la tabla "entities"
// @Inheritance: Indica a Hibernate que "entities" es una única tabla por lo que toda clase
// que herede de "EntityData" se guardan en la tabla "entities"
// @DiscriminatorColumn: Hibernate necesita tener una subclase, por lo que esta anotación diferencia las clases que heredan
// de "EntityData" con el identificador ("dtype")

@Entity
@Table(name = "entities")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
public class EntityData {

// PROPIEDADES ---------------------
// @Id: PK (Clave Primaria)
// @GenerateValue: Indica a Hibernate que "ID" es una columna auto-incremental
// @Column: se usa en el campo name porque esta no puede ser nula, en el resto no se indica, ya que cada campo se mapea de manera automática
// @ManyToOne: Define la relación [.LAZY: hace que los datos de la base relación no se carguen (optimización/rendimiento)]
// @JoinColumn: FK [Clave Foreanea]

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // Posición
    private Double posX = 0.0;
    private Double posY = 0.0;

    // Escala
    private Double scaleX = 1.0;
    private Double scaleY = 1.0;

    // Rotación
    private Double rotation = 0.0;

    // Sprite/Imagen
    private String spritePath;

    // Estado
    private Boolean active = true;
    private Boolean visible = true;

    // Capa de renderizado
    private Integer layer = 0;

    // Tags (para búsquedas)
    private String tags;

    // Relaciones
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scene_id")
    private SceneData scene;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private ProjectData project;

    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

 // CICLO DE VIDA ------------------------------------------
 // @PrePersist: (creación) Se ejecuta antes que una nueva entidad se guarde en la BD por primera vez para establecer la fecha de creación.
 // @PreUpdatae: (ya creada)Se ejecuta antes para poder actualizar la fecha.

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

    public Double getPosX() { return posX; }
    public void setPosX(Double posX) { this.posX = posX; }

    public Double getPosY() { return posY; }
    public void setPosY(Double posY) { this.posY = posY; }

    public Double getScaleX() { return scaleX; }
    public void setScaleX(Double scaleX) { this.scaleX = scaleX; }

    public Double getScaleY() { return scaleY; }
    public void setScaleY(Double scaleY) { this.scaleY = scaleY; }

    public Double getRotation() { return rotation; }
    public void setRotation(Double rotation) { this.rotation = rotation; }

    public String getSpritePath() { return spritePath; }
    public void setSpritePath(String spritePath) { this.spritePath = spritePath; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public Boolean getVisible() { return visible; }
    public void setVisible(Boolean visible) { this.visible = visible; }

    public Integer getLayer() { return layer; }
    public void setLayer(Integer layer) { this.layer = layer; }

    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }

    public SceneData getScene() { return scene; }
    public void setScene(SceneData scene) { this.scene = scene; }

    public ProjectData getProject() { return project; }
    public void setProject(ProjectData project) { this.project = project; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}