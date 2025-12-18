package com.game.data.repository;

import com.game.data.entity.EntityData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntityDataRepository extends JpaRepository<EntityData, Long> {

    List<EntityData> findBySceneIdAndActiveTrue(Long sceneId);

    List<EntityData> findByProjectIdOrderByLayerAsc(Long projectId);

    @Query("SELECT e FROM EntityData e WHERE e.tags LIKE %:tag% AND e.active = true")
    List<EntityData> findByTag(String tag);
}