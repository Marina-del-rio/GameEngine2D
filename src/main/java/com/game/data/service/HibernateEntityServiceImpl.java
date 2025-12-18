package com.game.data.service;

import com.game.data.entity.*;
import com.game.data.dto.*;
import com.game.data.repository.EntityDataRepository;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementación del servicio Hibernate/JPA para gestión de entidades del motor
 */
@Service
@Transactional(readOnly = true)
public class HibernateEntityServiceImpl implements HibernateEntityService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private EntityDataRepository entityDataRepository;

    // ========== CE3.a: Configuración y Conexión ORM ==========

    /**
     *  Prueba EntityManager
     */
    @Override
    public String testEntityManager() {
        if (!entityManager.isOpen()) {
            throw new RuntimeException("EntityManager está cerrado");
        }

        Query query = entityManager.createNativeQuery("SELECT 1 as test, DATABASE() as db_name");
        Object[] result = (Object[]) query.getSingleResult();

        return String.format("✓ EntityManager activo | Base de datos: %s | Test: %s",
                result[1], result[0]);
    }

    // ========== CE3.d, CE3.e: Operaciones CRUD ==========

    /**
     *  INSERT con persist()
     */
    @Override
    @Transactional
    public EntityData createEntity(EntityCreateDto dto) {
        EntityData entity = createEntityByType(dto);

        // Campos comunes
        entity.setName(dto.getName());
        entity.setPosX(dto.getPosX() != null ? dto.getPosX() : 0.0);
        entity.setPosY(dto.getPosY() != null ? dto.getPosY() : 0.0);
        entity.setSpritePath(dto.getSpritePath());
        entity.setLayer(dto.getLayer() != null ? dto.getLayer() : 0);
        entity.setTags(dto.getTags());
        entity.setActive(true);
        entity.setVisible(true);

        // Asociar con escena
        if (dto.getSceneId() != null) {
            SceneData scene = entityManager.find(SceneData.class, dto.getSceneId());
            entity.setScene(scene);
        }

        // Asociar con proyecto
        if (dto.getProjectId() != null) {
            ProjectData project = entityManager.find(ProjectData.class, dto.getProjectId());
            entity.setProject(project);
        }

        entityManager.persist(entity);
        return entity;
    }

    /**
     * Crea la entidad del tipo correcto según el DTO
     */
    private EntityData createEntityByType(EntityCreateDto dto) {
        String type = dto.getEntityType().toUpperCase();

        switch (type) {
            case "CHARACTER":
                CharacterData character = new CharacterData();
                if (dto.getHealth() != null) character.setHealth(dto.getHealth());
                if (dto.getSpeed() != null) character.setSpeed(dto.getSpeed());
                if (dto.getIsMainCharacter() != null) character.setIsMainCharacter(dto.getIsMainCharacter());
                if (dto.getLives() != null) character.setLives(dto.getLives());
                return character;

            case "ENEMY":
                EnemyData enemy = new EnemyData();
                if (dto.getHealth() != null) enemy.setHealth(dto.getHealth());
                if (dto.getSpeed() != null) enemy.setSpeed(dto.getSpeed());
                if (dto.getDamage() != null) enemy.setDamage(dto.getDamage());
                if (dto.getEnemyType() != null) {
                    enemy.setEnemyType(EnemyData.EnemyType.valueOf(dto.getEnemyType()));
                }
                if (dto.getAiBehavior() != null) {
                    enemy.setAiBehavior(EnemyData.EnemyBehavior.valueOf(dto.getAiBehavior()));
                }
                return enemy;

            case "NPC":
                NPCData npc = new NPCData();
                if (dto.getHealth() != null) npc.setHealth(dto.getHealth());
                if (dto.getSpeed() != null) npc.setSpeed(dto.getSpeed());
                if (dto.getNpcType() != null) {
                    npc.setNpcType(NPCData.NPCType.valueOf(dto.getNpcType()));
                }
                if (dto.getDialogueData() != null) npc.setDialogueData(dto.getDialogueData());
                return npc;

            case "STATIC_OBJECT":
                StaticObjectData staticObj = new StaticObjectData();
                if (dto.getSolid() != null) staticObj.setSolid(dto.getSolid());
                if (dto.getStaticType() != null) {
                    staticObj.setStaticType(StaticObjectData.StaticType.valueOf(dto.getStaticType()));
                }
                return staticObj;

            case "DYNAMIC_OBJECT":
                DynamicObjectData dynamicObj = new DynamicObjectData();
                if (dto.getHasPhysics() != null) dynamicObj.setHasPhysics(dto.getHasPhysics());
                if (dto.getDynamicType() != null) {
                    dynamicObj.setDynamicType(DynamicObjectData.DynamicType.valueOf(dto.getDynamicType()));
                }
                return dynamicObj;

            case "AUDIO":
                AudioData audio = new AudioData();
                if (dto.getAudioPath() != null) audio.setAudioPath(dto.getAudioPath());
                if (dto.getVolume() != null) audio.setVolume(dto.getVolume());
                if (dto.getLoop() != null) audio.setLoop(dto.getLoop());
                if (dto.getAudioType() != null) {
                    audio.setAudioType(AudioData.AudioType.valueOf(dto.getAudioType()));
                }
                return audio;

            case "UI_ELEMENT":
                UIElementData ui = new UIElementData();
                if (dto.getText() != null) ui.setText(dto.getText());
                if (dto.getWidth() != null) ui.setWidth(dto.getWidth());
                if (dto.getHeight() != null) ui.setHeight(dto.getHeight());
                if (dto.getUiType() != null) {
                    ui.setUiType(UIElementData.UIType.valueOf(dto.getUiType()));
                }
                return ui;

            case "TILE":
                TileData tile = new TileData();
                if (dto.getGridX() != null) tile.setGridX(dto.getGridX());
                if (dto.getGridY() != null) tile.setGridY(dto.getGridY());
                if (dto.getTilesetPath() != null) tile.setTilesetPath(dto.getTilesetPath());
                if (dto.getWalkable() != null) tile.setWalkable(dto.getWalkable());
                return tile;

            default:
                return new EntityData();
        }
    }

    /**
     * SELECT por ID con find()
     */
    @Override
    public EntityData findEntityById(Long id) {
        return entityManager.find(EntityData.class, id);
    }

    /**
     * UPDATE con merge()
     */
    @Override
    @Transactional
    public EntityData updateEntity(Long id, EntityUpdateDto dto) {
        EntityData existing = findEntityById(id);
        if (existing == null) {
            throw new RuntimeException("No se encontró entidad con ID " + id);
        }

        // Actualizar campos comunes
        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getPosX() != null) existing.setPosX(dto.getPosX());
        if (dto.getPosY() != null) existing.setPosY(dto.getPosY());
        if (dto.getScaleX() != null) existing.setScaleX(dto.getScaleX());
        if (dto.getScaleY() != null) existing.setScaleY(dto.getScaleY());
        if (dto.getRotation() != null) existing.setRotation(dto.getRotation());
        if (dto.getSpritePath() != null) existing.setSpritePath(dto.getSpritePath());
        if (dto.getActive() != null) existing.setActive(dto.getActive());
        if (dto.getVisible() != null) existing.setVisible(dto.getVisible());
        if (dto.getLayer() != null) existing.setLayer(dto.getLayer());
        if (dto.getTags() != null) existing.setTags(dto.getTags());

        // Si es un Actor, actualizar campos específicos
        if (existing instanceof ActorData) {
            ActorData actor = (ActorData) existing;
            if (dto.getHealth() != null) actor.setHealth(dto.getHealth());
            if (dto.getSpeed() != null) actor.setSpeed(dto.getSpeed());
        }

        return entityManager.merge(existing);
    }

    /**
     *  DELETE con remove()
     */
    @Override
    @Transactional
    public boolean deleteEntity(Long id) {
        EntityData entity = findEntityById(id);
        if (entity == null) {
            return false;
        }
        entityManager.remove(entity);
        return true;
    }

    /**
     *  SELECT all con Repository
     */
    @Override
    public List<EntityData> findAll() {
        return entityDataRepository.findAll();
    }

    // ========== CE3.f: Consultas JPQL ==========

    /**
     *  JPQL básico - Buscar por escena
     */
    @Override
    public List<EntityData> findEntitiesByScene(Long sceneId) {
        String jpql = "SELECT e FROM EntityData e WHERE e.scene.id = :sceneId AND e.active = true ORDER BY e.layer, e.name";

        TypedQuery<EntityData> query = entityManager.createQuery(jpql, EntityData.class);
        query.setParameter("sceneId", sceneId);

        return query.getResultList();
    }

    /**
     *  JPQL dinámico - Búsqueda con filtros
     */
    @Override
    public List<EntityData> searchEntities(EntityQueryDto queryDto) {
        StringBuilder jpql = new StringBuilder("SELECT e FROM EntityData e WHERE 1=1");

        if (queryDto.getSceneId() != null) {
            jpql.append(" AND e.scene.id = :sceneId");
        }
        if (queryDto.getProjectId() != null) {
            jpql.append(" AND e.project.id = :projectId");
        }
        if (queryDto.getActive() != null) {
            jpql.append(" AND e.active = :active");
        }
        if (queryDto.getLayer() != null) {
            jpql.append(" AND e.layer = :layer");
        }
        if (queryDto.getTags() != null) {
            jpql.append(" AND e.tags LIKE :tags");
        }

        jpql.append(" ORDER BY e.layer, e.name");

        TypedQuery<EntityData> query = entityManager.createQuery(jpql.toString(), EntityData.class);

        if (queryDto.getSceneId() != null) {
            query.setParameter("sceneId", queryDto.getSceneId());
        }
        if (queryDto.getProjectId() != null) {
            query.setParameter("projectId", queryDto.getProjectId());
        }
        if (queryDto.getActive() != null) {
            query.setParameter("active", queryDto.getActive());
        }
        if (queryDto.getLayer() != null) {
            query.setParameter("layer", queryDto.getLayer());
        }
        if (queryDto.getTags() != null) {
            query.setParameter("tags", "%" + queryDto.getTags() + "%");
        }

        return query.getResultList();
    }

    // ========== CE3.g: Transacciones ==========

    /**
     *  Transacción múltiple
     */
    @Override
    @Transactional
    public boolean transferData(List<EntityData> entities) {
        for (EntityData entity : entities) {
            entityManager.persist(entity);
        }
        // Si todo OK, Spring hace commit automáticamente
        // Si hay error, Spring hace rollback automáticamente
        return true;
    }

    /**
     *  COUNT con JPQL
     */
    @Override
    public long executeCountByScene(Long sceneId) {
        String jpql = "SELECT COUNT(e) FROM EntityData e WHERE e.scene.id = :sceneId AND e.active = true";

        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("sceneId", sceneId);

        return query.getSingleResult();
    }
}