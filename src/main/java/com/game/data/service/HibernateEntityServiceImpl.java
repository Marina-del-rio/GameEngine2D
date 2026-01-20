package com.game.data.service;

import com.game.data.dto.EntityCreateDto;
import com.game.data.dto.EntityQueryDto;
import com.game.data.dto.EntityUpdateDto;
import com.game.data.entity.*;
import com.game.data.factory.EntityFactory;
import com.game.data.repository.EntityDataRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private EntityFactory entityFactory; // <-- Inyectamos la nueva fábrica única

    // ========== CE3.a: Configuración y Conexión ORM ==========

    /**
     *  Prueba EntityManager
     */

    // Verifica el que el EntityManager esté activo y conectado a la base de datos (usando una query sencilla para confirmarlo) //

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
     * INSERT con persist() usando la fábrica única.
     * El método delega la creación de la instancia específica a la fábrica,
     * y luego se encarga de las propiedades comunes y la persistencia.
     */

    // Este método crea una entidad especifica en la base de datos. //

    @Override
    @Transactional
    public EntityData createEntity(EntityCreateDto dto) {
        // 1. Usar la fábrica para crear la instancia de la subclase correcta (CharacterData, EnemyData, etc.).
        //    La fábrica se encarga de toda la lógica de instanciación y de setear las propiedades específicas.
        // Creamos la entidad //
        EntityData entity = entityFactory.create(dto);

        // 2. Establecer las propiedades comunes que toda EntityData tiene.
        // (Agregamos el contenido a la entidad creada) //
        entity.setName(dto.getName());
        entity.setPosX(dto.getPosX() != null ? dto.getPosX() : 0.0);
        entity.setPosY(dto.getPosY() != null ? dto.getPosY() : 0.0);
        entity.setSpritePath(dto.getSpritePath());
        entity.setLayer(dto.getLayer() != null ? dto.getLayer() : 0);
        entity.setTags(dto.getTags());
        entity.setActive(true);
        entity.setVisible(true);

        // 3. Asociar con escena y proyecto (si se proporcionan los IDs).
        //  (Si el ID de la escena o el ID del proyecto no es nulo, le asociamos con una escena o con un proyecto) //
        if (dto.getSceneId() != null) {
            SceneData scene = entityManager.find(SceneData.class, dto.getSceneId());
            entity.setScene(scene);
        }

        if (dto.getProjectId() != null) {
            ProjectData project = entityManager.find(ProjectData.class, dto.getProjectId());
            entity.setProject(project);
        }

        // 4. Persistir la entidad. Hibernate generará el INSERT apropiado.
        // (Guardamos la entidad) //
        entityManager.persist(entity);
        return entity;
    }

    /**
     * SELECT por ID con find()
     */

    // Busca una entidad y la devuelve mediante el ID [Búsqueda directa con el find()] //

    @Override
    public EntityData findEntityById(Long id) {
        return entityManager.find(EntityData.class, id);
    }

    /**
     * UPDATE con merge()
     */

    // Este método actualiza los campos de una entidad que ya existe con los valores nuevos //

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

        // Sí es un Actor, actualizar campos específicos
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

    // Esté método elimina la entidad por su ID //

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

    // Esté método recupera todas las entidades de la base de datos //

    @Override
    public List<EntityData> findAll() {
        return entityDataRepository.findAll();
    }

    // ========== CE3.f: Consultas JPQL ==========

    /**
     *  JPQL básico - Buscar por escena
     */

    // Esté método busca las entidades activas por escena y las ordena por el layer y el nombre //

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

    // Esté método hace búsquedas dinámicas con varios filtros, mediante van pasando los filtros se va construyendo la consulta //

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

    // Esté método permite

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

    // Cuenta la cantidad de entidades activas que existen en una escena específica, las busca sin cargarla las entidades en memoria //

    @Override
    public long executeCountByScene(Long sceneId) {
        String jpql = "SELECT COUNT(e) FROM EntityData e WHERE e.scene.id = :sceneId AND e.active = true";

        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("sceneId", sceneId);

        return query.getSingleResult();
    }
}