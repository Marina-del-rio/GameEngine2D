package com.game.data.service;

import com.game.data.entity.EntityData;
import com.game.data.dto.*;
import java.util.List;

public interface HibernateEntityService {

    // Prueba de conexión
    String testEntityManager();

    // CRUD
    EntityData createEntity(EntityCreateDto dto);
    EntityData findEntityById(Long id);
    EntityData updateEntity(Long id, EntityUpdateDto dto);
    boolean deleteEntity(Long id);

    // Búsquedas
    List<EntityData> findAll();
    List<EntityData> findEntitiesByScene(Long sceneId);
    List<EntityData> searchEntities(EntityQueryDto queryDto);

    // Transacciones
    boolean transferData(List<EntityData> entities);

    // Conteos
    long executeCountByScene(Long sceneId);
}