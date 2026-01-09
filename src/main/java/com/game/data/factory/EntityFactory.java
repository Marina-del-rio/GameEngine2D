package com.game.data.factory;

import com.game.data.dto.EntityCreateDto;
import com.game.data.entity.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Fábrica única para la creación de todas las entidades.
 * Utiliza un mapa de estrategias de creación para evitar múltiples clases factory.
 */
@Component
public class EntityFactory {

    // Un mapa que asocia un String (ej: "CHARACTER") con una función que sabe cómo crear la entidad
    private static final Map<String, Function<EntityCreateDto, EntityData>> creationStrategies = new HashMap<>();

    // Bloque estático para inicializar el mapa una sola vez con toda la lógica de creación
    static {
        creationStrategies.put("CHARACTER", dto -> {
            CharacterData character = new CharacterData();
            if (dto.getHealth() != null) character.setHealth(dto.getHealth());
            if (dto.getSpeed() != null) character.setSpeed(dto.getSpeed());
            if (dto.getIsMainCharacter() != null) character.setIsMainCharacter(dto.getIsMainCharacter());
            if (dto.getLives() != null) character.setLives(dto.getLives());
            return character;
        });

        creationStrategies.put("ENEMY", dto -> {
            EnemyData enemy = new EnemyData();
            if (dto.getHealth() != null) enemy.setHealth(dto.getHealth());
            if (dto.getSpeed() != null) enemy.setSpeed(dto.getSpeed());
            if (dto.getDamage() != null) enemy.setDamage(dto.getDamage());
            if (dto.getEnemyType() != null) {
                enemy.setEnemyType(EnemyData.EnemyType.valueOf(dto.getEnemyType().toUpperCase()));
            }
            if (dto.getAiBehavior() != null) {
                enemy.setAiBehavior(EnemyData.EnemyBehavior.valueOf(dto.getAiBehavior().toUpperCase()));
            }
            return enemy;
        });

        creationStrategies.put("NPC", dto -> {
            NPCData npc = new NPCData();
            if (dto.getHealth() != null) npc.setHealth(dto.getHealth());
            if (dto.getSpeed() != null) npc.setSpeed(dto.getSpeed());
            if (dto.getNpcType() != null) {
                npc.setNpcType(NPCData.NPCType.valueOf(dto.getNpcType().toUpperCase()));
            }
            if (dto.getDialogueData() != null) npc.setDialogueData(dto.getDialogueData());
            return npc;
        });

        creationStrategies.put("STATIC_OBJECT", dto -> {
            StaticObjectData staticObj = new StaticObjectData();
            if (dto.getSolid() != null) staticObj.setSolid(dto.getSolid());
            if (dto.getStaticType() != null) {
                staticObj.setStaticType(StaticObjectData.StaticType.valueOf(dto.getStaticType().toUpperCase()));
            }
            return staticObj;
        });

        creationStrategies.put("DYNAMIC_OBJECT", dto -> {
            DynamicObjectData dynamicObj = new DynamicObjectData();
            if (dto.getHasPhysics() != null) dynamicObj.setHasPhysics(dto.getHasPhysics());
            if (dto.getDynamicType() != null) {
                dynamicObj.setDynamicType(DynamicObjectData.DynamicType.valueOf(dto.getDynamicType().toUpperCase()));
            }
            return dynamicObj;
        });

        creationStrategies.put("AUDIO", dto -> {
            AudioData audio = new AudioData();
            if (dto.getAudioPath() != null) audio.setAudioPath(dto.getAudioPath());
            if (dto.getVolume() != null) audio.setVolume(dto.getVolume());
            if (dto.getLoop() != null) audio.setLoop(dto.getLoop());
            if (dto.getAudioType() != null) {
                audio.setAudioType(AudioData.AudioType.valueOf(dto.getAudioType().toUpperCase()));
            }
            return audio;
        });

        creationStrategies.put("UI_ELEMENT", dto -> {
            UIElementData ui = new UIElementData();
            if (dto.getText() != null) ui.setText(dto.getText());
            if (dto.getWidth() != null) ui.setWidth(dto.getWidth());
            if (dto.getHeight() != null) ui.setHeight(dto.getHeight());
            if (dto.getUiType() != null) {
                ui.setUiType(UIElementData.UIType.valueOf(dto.getUiType().toUpperCase()));
            }
            return ui;
        });

        creationStrategies.put("TILE", dto -> {
            TileData tile = new TileData();
            if (dto.getGridX() != null) tile.setGridX(dto.getGridX());
            if (dto.getGridY() != null) tile.setGridY(dto.getGridY());
            if (dto.getTilesetPath() != null) tile.setTilesetPath(dto.getTilesetPath());
            if (dto.getWalkable() != null) tile.setWalkable(dto.getWalkable());
            return tile;
        });

        creationStrategies.put("ENTITY", dto -> new EntityData());
    }

    /**
     * Crea una instancia de una subclase de EntityData utilizando la estrategia correcta
     * encontrada en el mapa.
     * @param dto El DTO con los datos para la creación.
     * @return una instancia de EntityData (CharacterData, EnemyData, etc.).
     */
    public EntityData create(EntityCreateDto dto) {
        if (dto.getEntityType() == null) {
            throw new IllegalArgumentException("El tipo de entidad (entityType) no puede ser nulo.");
        }
        String type = dto.getEntityType().toUpperCase();
        Function<EntityCreateDto, EntityData> strategy = creationStrategies.get(type);

        if (strategy == null) {
            // Si no se encuentra una estrategia, se puede lanzar un error o usar una por defecto.
            // Lanzar un error es más seguro para evitar crear entidades incorrectas.
            throw new IllegalArgumentException("Tipo de entidad no soportado: " + type);
        }

        // Ejecuta la función lambda para crear la instancia
        return strategy.apply(dto);
    }
}
