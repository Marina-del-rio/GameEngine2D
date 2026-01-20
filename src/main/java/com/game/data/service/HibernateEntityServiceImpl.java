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
 * Implementación del servicio Hibernate/JPA para la gestión de entidades del motor.
 * @Service: Marca la clase como un servicio de Spring, haciéndola candidata para la inyección de dependencias.
 * @Transactional(readOnly = true): Por defecto, todas las operaciones son de solo lectura,
 * lo que optimiza las consultas. Los métodos que escriben en la BD (crear, actualizar, borrar)
 * deberán tener su propia anotación @Transactional sin readOnly.
 */
@Service
@Transactional(readOnly = true)
public class HibernateEntityServiceImpl implements HibernateEntityService {

    // @PersistenceContext: Inyecta el EntityManager, que es la interfaz principal de JPA
    // para interactuar con la base de datos (crear queries, persistir, etc.).
    @PersistenceContext
    private EntityManager entityManager;

    // @Autowired: Inyecta una instancia de nuestro repositorio.
    // Aunque podríamos hacer todo con el EntityManager, Spring Data JPA (el repositorio)
    // nos da métodos ya hechos como findAll(), simplificando el código.
    @Autowired
    private EntityDataRepository entityDataRepository;

    // Inyectamos la fábrica de entidades que creamos.
    // Esta fábrica se encargará de la lógica de crear el tipo correcto de entidad.
    @Autowired
    private EntityFactory entityFactory;

    // ========== APARTADO: Configuración y Conexión ORM ==========

    /**
     * Prueba la conexión con la base de datos a través del EntityManager.
     * Es una comprobación de "salud" para asegurar que la configuración de la BD es correcta.
     */
    @Override
    public String testEntityManager() {
        // Comprueba si el EntityManager está disponible.
        if (!entityManager.isOpen()) {
            throw new RuntimeException("EntityManager está cerrado");
        }

        // Ejecuta una consulta SQL nativa muy simple ("SELECT 1") para confirmar
        // que no solo está abierto, sino que puede comunicarse con la BD.
        Query query = entityManager.createNativeQuery("SELECT 1 as test, DATABASE() as db_name");
        Object[] result = (Object[]) query.getSingleResult();

        // Devuelve un mensaje con información de la conexión.
        return String.format("✓ EntityManager activo | Base de datos: %s | Test: %s",
                result[1], result[0]);
    }

    // ========== APARTADO: Operaciones CRUD (Crear, Leer, Actualizar, Borrar) ==========

    /**
     * Crea una nueva entidad en la base de datos.
     * @Transactional: Sobrescribe el `readOnly=true` de la clase. Este método SÍ escribe en la BD,
     * por lo que necesita una transacción de escritura. Spring la gestionará automáticamente.
     */
    @Override
    @Transactional
    public EntityData createEntity(EntityCreateDto dto) {
        // 1. Delega la creación de la instancia específica (CharacterData, EnemyData, etc.) a la fábrica.
        // Esto centraliza la lógica de qué clase crear según el "entityType" del DTO.
        EntityData entity = entityFactory.create(dto);

        // 2. Establece las propiedades comunes que toda `EntityData` tiene.
        // Se usan valores por defecto si el DTO no los proporciona (e.g., 0.0 para posiciones).
        entity.setName(dto.getName());
        entity.setPosX(dto.getPosX() != null ? dto.getPosX() : 0.0);
        entity.setPosY(dto.getPosY() != null ? dto.getPosY() : 0.0);
        entity.setSpritePath(dto.getSpritePath());
        entity.setLayer(dto.getLayer() != null ? dto.getLayer() : 0);
        entity.setTags(dto.getTags());
        entity.setActive(true);
        entity.setVisible(true);

        // 3. Gestiona las relaciones. Si el DTO incluye un ID de escena o proyecto,
        // se busca la entidad correspondiente y se establece la relación.
        if (dto.getSceneId() != null) {
            SceneData scene = entityManager.find(SceneData.class, dto.getSceneId());
            entity.setScene(scene);
        }
        if (dto.getProjectId() != null) {
            ProjectData project = entityManager.find(ProjectData.class, dto.getProjectId());
            entity.setProject(project);
        }

        // 4. Persiste la entidad. `entityManager.persist()` añade la nueva entidad al contexto
        // de persistencia. En el `commit` de la transacción, Hibernate generará el `INSERT` SQL.
        entityManager.persist(entity);
        return entity;
    }

    /**
     * Busca y devuelve una entidad por su clave primaria (ID).
     * Usa `entityManager.find()`, que es la forma más eficiente de obtener un objeto por su ID.
     */
    @Override
    public EntityData findEntityById(Long id) {
        // `find` devuelve el objeto si lo encuentra, o `null` si no existe.
        return entityManager.find(EntityData.class, id);
    }

    /**
     * Actualiza una entidad existente.
     * @Transactional: Necesario porque este método modifica datos.
     */
    @Override
    @Transactional
    public EntityData updateEntity(Long id, EntityUpdateDto dto) {
        // Primero, se busca la entidad existente.
        EntityData existing = findEntityById(id);
        if (existing == null) {
            throw new RuntimeException("No se encontró entidad con ID " + id);
        }

        // Se actualizan solo los campos que vienen en el DTO (los que no vienen son nulos).
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

        // Si la entidad es un tipo específico (como ActorData), se actualizan también
        // sus campos propios.
        if (existing instanceof ActorData) {
            ActorData actor = (ActorData) existing;
            if (dto.getHealth() != null) actor.setHealth(dto.getHealth());
            if (dto.getSpeed() != null) actor.setSpeed(dto.getSpeed());
        }

        // `entityManager.merge()` actualiza la entidad en el contexto de persistencia.
        // Hibernate detectará los cambios y generará el `UPDATE` SQL correspondiente.
        return entityManager.merge(existing);
    }

    /**
     * Elimina una entidad de la base de datos.
     * @Transactional: Necesario porque es una operación de escritura.
     */
    @Override
    @Transactional
    public boolean deleteEntity(Long id) {
        EntityData entity = findEntityById(id);
        if (entity == null) {
            // Si no se encuentra, no se puede borrar.
            return false;
        }
        // `entityManager.remove()` marca la entidad para ser eliminada.
        // Hibernate generará el `DELETE` SQL en el `commit`.
        entityManager.remove(entity);
        return true;
    }

    /**
     * Devuelve una lista con todas las entidades de la tabla.
     * Se delega la operación al repositorio de Spring Data JPA, que nos da este método ya implementado.
     */
    @Override
    public List<EntityData> findAll() {
        return entityDataRepository.findAll();
    }

    // ========== APARTADO: Consultas JPQL (Lenguaje de Consulta de Persistencia de Jakarta) ==========

    /**
     * Busca todas las entidades activas que pertenecen a una escena específica.
     * Usa JPQL, que es similar a SQL pero opera sobre entidades (objetos) en vez de tablas.
     */
    @Override
    public List<EntityData> findEntitiesByScene(Long sceneId) {
        // `e` es un alias para `EntityData`. La consulta se parece a SQL:
        // "Selecciona entidades `e` donde el ID de su escena sea `:sceneId`..."
        String jpql = "SELECT e FROM EntityData e WHERE e.scene.id = :sceneId AND e.active = true ORDER BY e.layer, e.name";

        // Se crea una consulta "tipada" (TypedQuery), lo que significa que sabemos que devolverá objetos EntityData.
        TypedQuery<EntityData> query = entityManager.createQuery(jpql, EntityData.class);
        // Se asigna un valor al parámetro `:sceneId` de la consulta.
        query.setParameter("sceneId", sceneId);

        // Se ejecuta la consulta y se devuelve la lista de resultados.
        return query.getResultList();
    }

    /**
     * Realiza una búsqueda dinámica construyendo una consulta JPQL basada en los filtros proporcionados.
     */
    @Override
    public List<EntityData> searchEntities(EntityQueryDto queryDto) {
        // Se usa un StringBuilder para construir la consulta paso a paso.
        StringBuilder jpql = new StringBuilder("SELECT e FROM EntityData e WHERE 1=1");

        // Por cada filtro que no sea nulo en el DTO, se añade una condición `AND` a la consulta.
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

        // Se crea la consulta final a partir del String construido.
        TypedQuery<EntityData> query = entityManager.createQuery(jpql.toString(), EntityData.class);

        // Se asignan los valores a los parámetros que se hayan añadido a la consulta.
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

    // ========== APARTADO: Transacciones ==========

    /**
     * Guarda una lista de entidades en una única transacción.
     * @Transactional: Si algo falla (p.ej., una entidad es inválida), Spring deshará
     * todas las inserciones (`rollback`), asegurando la consistencia de los datos.
     * Si todo va bien, hará `commit` al final.
     */
    @Override
    @Transactional
    public boolean transferData(List<EntityData> entities) {
        for (EntityData entity : entities) {
            entityManager.persist(entity);
        }
        return true;
    }

    // ========== APARTADO: Consultas de Agregación ==========

    /**
     * Cuenta el número de entidades activas en una escena específica.
     * Usa `COUNT(e)` en JPQL, que es mucho más eficiente que traer todas las entidades
     * a memoria y luego contarlas (`getResultList().size()`).
     */
    @Override
    public long executeCountByScene(Long sceneId) {
        String jpql = "SELECT COUNT(e) FROM EntityData e WHERE e.scene.id = :sceneId AND e.active = true";

        // La consulta es de tipo Long, porque `COUNT` devuelve un número.
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("sceneId", sceneId);

        // `getSingleResult()` se usa cuando sabemos que la consulta devolverá un único valor (un número, en este caso).
        return query.getSingleResult();
    }
}