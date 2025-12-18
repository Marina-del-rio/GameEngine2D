package com.game.data.service;

import com.game.data.entity.*;
import com.game.data.dto.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para HibernateEntityServiceImpl
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class HibernateEntityServiceTest {

    @Autowired
    private HibernateEntityService entityService;

    // ========== TEST CONEXIÓN ==========

    @Test
    @DisplayName("testEntityManager() - Verifica que EntityManager está activo")
    void testEntityManager_DebeRetornarMensajeExito() {
        // Act
        String resultado = entityService.testEntityManager();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.contains("EntityManager activo"));
    }

    // ========== TEST CREATE ==========

    @Test
    @DisplayName("createEntity() - Debe crear Character correctamente")
    void createEntity_Character_DebeCrear() {
        // Arrange
        EntityCreateDto dto = new EntityCreateDto();
        dto.setEntityType("CHARACTER");
        dto.setName("Jugador 1");
        dto.setPosX(100.0);
        dto.setPosY(200.0);
        dto.setHealth(100);
        dto.setLives(3);

        // Act
        EntityData resultado = entityService.createEntity(dto);

        // Assert
        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertEquals("Jugador 1", resultado.getName());
        assertTrue(resultado instanceof CharacterData);

        CharacterData character = (CharacterData) resultado;
        assertEquals(100, character.getHealth());
        assertEquals(3, character.getLives());
    }

    @Test
    @DisplayName("createEntity() - Debe crear Enemy correctamente")
    void createEntity_Enemy_DebeCrear() {
        // Arrange
        EntityCreateDto dto = new EntityCreateDto();
        dto.setEntityType("ENEMY");
        dto.setName("Goblin");
        dto.setDamage(10);
        dto.setEnemyType("BASIC");

        // Act
        EntityData resultado = entityService.createEntity(dto);

        // Assert
        assertTrue(resultado instanceof EnemyData);
        assertEquals(10, ((EnemyData) resultado).getDamage());
    }

    @Test
    @DisplayName("createEntity() - Debe crear NPC correctamente")
    void createEntity_NPC_DebeCrear() {
        // Arrange
        EntityCreateDto dto = new EntityCreateDto();
        dto.setEntityType("NPC");
        dto.setName("Vendedor");
        dto.setNpcType("MERCHANT");

        // Act
        EntityData resultado = entityService.createEntity(dto);

        // Assert
        assertTrue(resultado instanceof NPCData);
        assertEquals(NPCData.NPCType.MERCHANT, ((NPCData) resultado).getNpcType());
    }

    @Test
    @DisplayName("createEntity() - Debe crear StaticObject correctamente")
    void createEntity_StaticObject_DebeCrear() {
        // Arrange
        EntityCreateDto dto = new EntityCreateDto();
        dto.setEntityType("STATIC_OBJECT");
        dto.setName("Roca");
        dto.setSolid(true);
        dto.setStaticType("OBSTACLE");

        // Act
        EntityData resultado = entityService.createEntity(dto);

        // Assert
        assertTrue(resultado instanceof StaticObjectData);
        assertTrue(((StaticObjectData) resultado).getSolid());
    }

    @Test
    @DisplayName("createEntity() - Debe crear DynamicObject correctamente")
    void createEntity_DynamicObject_DebeCrear() {
        // Arrange
        EntityCreateDto dto = new EntityCreateDto();
        dto.setEntityType("DYNAMIC_OBJECT");
        dto.setName("Caja");
        dto.setHasPhysics(true);

        // Act
        EntityData resultado = entityService.createEntity(dto);

        // Assert
        assertTrue(resultado instanceof DynamicObjectData);
        assertTrue(((DynamicObjectData) resultado).getHasPhysics());
    }

    @Test
    @DisplayName("createEntity() - Debe crear Audio correctamente")
    void createEntity_Audio_DebeCrear() {
        // Arrange
        EntityCreateDto dto = new EntityCreateDto();
        dto.setEntityType("AUDIO");
        dto.setName("Música");
        dto.setAudioType("MUSIC");
        dto.setVolume(0.8);
        dto.setLoop(true);

        // Act
        EntityData resultado = entityService.createEntity(dto);

        // Assert
        assertTrue(resultado instanceof AudioData);
        assertEquals(0.8, ((AudioData) resultado).getVolume());
        assertTrue(((AudioData) resultado).getLoop());
    }

    @Test
    @DisplayName("createEntity() - Debe crear UI Element correctamente")
    void createEntity_UIElement_DebeCrear() {
        // Arrange
        EntityCreateDto dto = new EntityCreateDto();
        dto.setEntityType("UI_ELEMENT");
        dto.setName("Botón");
        dto.setUiType("BUTTON");
        dto.setText("JUGAR");

        // Act
        EntityData resultado = entityService.createEntity(dto);

        // Assert
        assertTrue(resultado instanceof UIElementData);
        assertEquals("JUGAR", ((UIElementData) resultado).getText());
    }

    @Test
    @DisplayName("createEntity() - Debe crear Tile correctamente")
    void createEntity_Tile_DebeCrear() {
        // Arrange
        EntityCreateDto dto = new EntityCreateDto();
        dto.setEntityType("TILE");
        dto.setName("Suelo");
        dto.setGridX(5);
        dto.setGridY(10);
        dto.setWalkable(true);

        // Act
        EntityData resultado = entityService.createEntity(dto);

        // Assert
        assertTrue(resultado instanceof TileData);
        assertEquals(5, ((TileData) resultado).getGridX());
        assertEquals(10, ((TileData) resultado).getGridY());
    }

    // ========== TEST READ ==========

    @Test
    @DisplayName("findEntityById() - Debe encontrar entidad existente")
    void findEntityById_Existe_DebeRetornar() {
        // Arrange
        EntityCreateDto dto = new EntityCreateDto();
        dto.setEntityType("CHARACTER");
        dto.setName("Test");
        EntityData creada = entityService.createEntity(dto);

        // Act
        EntityData encontrada = entityService.findEntityById(creada.getId());

        // Assert
        assertNotNull(encontrada);
        assertEquals("Test", encontrada.getName());
    }

    @Test
    @DisplayName("findEntityById() - Debe retornar null si no existe")
    void findEntityById_NoExiste_DebeRetornarNull() {
        // Act
        EntityData resultado = entityService.findEntityById(99999L);

        // Assert
        assertNull(resultado);
    }

    @Test
    @DisplayName("findAll() - Debe retornar todas las entidades")
    void findAll_DebeRetornarTodas() {
        // Arrange
        crearEntidad("CHARACTER", "Char 1");
        crearEntidad("ENEMY", "Enemy 1");
        crearEntidad("NPC", "NPC 1");

        // Act
        List<EntityData> todas = entityService.findAll();

        // Assert
        assertTrue(todas.size() >= 3);
    }

    // ========== TEST UPDATE ==========

    @Test
    @DisplayName("updateEntity() - Debe actualizar campos")
    void updateEntity_DebeActualizar() {
        // Arrange
        EntityData creada = crearEntidad("CHARACTER", "Original");

        EntityUpdateDto updateDto = new EntityUpdateDto();
        updateDto.setName("Actualizado");
        updateDto.setPosX(100.0);
        updateDto.setActive(false);

        // Act
        EntityData actualizada = entityService.updateEntity(creada.getId(), updateDto);

        // Assert
        assertEquals("Actualizado", actualizada.getName());
        assertEquals(100.0, actualizada.getPosX());
        assertFalse(actualizada.getActive());
    }

    @Test
    @DisplayName("updateEntity() - Debe lanzar excepción si no existe")
    void updateEntity_NoExiste_DebeLanzarExcepcion() {
        // Arrange
        EntityUpdateDto dto = new EntityUpdateDto();
        dto.setName("Test");

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            entityService.updateEntity(99999L, dto);
        });
    }

    // ========== TEST DELETE ==========

    @Test
    @DisplayName("deleteEntity() - Debe eliminar entidad existente")
    void deleteEntity_Existe_DebeRetornarTrue() {
        // Arrange
        EntityData creada = crearEntidad("ENEMY", "A eliminar");
        Long id = creada.getId();

        // Act
        boolean resultado = entityService.deleteEntity(id);

        // Assert
        assertTrue(resultado);
        assertNull(entityService.findEntityById(id));
    }

    @Test
    @DisplayName("deleteEntity() - Debe retornar false si no existe")
    void deleteEntity_NoExiste_DebeRetornarFalse() {
        // Act
        boolean resultado = entityService.deleteEntity(99999L);

        // Assert
        assertFalse(resultado);
    }

    // ========== TEST BÚSQUEDA JPQL ==========

    @Test
    @DisplayName("searchEntities() - Debe filtrar por criterios")
    void searchEntities_DebeAplicarFiltros() {
        // Arrange
        EntityCreateDto dto1 = new EntityCreateDto();
        dto1.setEntityType("CHARACTER");
        dto1.setName("Player");
        dto1.setLayer(1);
        dto1.setTags("player");
        entityService.createEntity(dto1);

        EntityCreateDto dto2 = new EntityCreateDto();
        dto2.setEntityType("ENEMY");
        dto2.setName("Enemy");
        dto2.setLayer(2);
        entityService.createEntity(dto2);

        // Act
        EntityQueryDto query = new EntityQueryDto();
        query.setLayer(1);
        List<EntityData> resultado = entityService.searchEntities(query);

        // Assert
        assertTrue(resultado.stream().allMatch(e -> e.getLayer() == 1));
    }

    // ========== TEST TRANSACCIONES ==========

    @Test
    @DisplayName("transferData() - Debe guardar múltiples entidades")
    void transferData_DebeGuardarTodas() {
        // Arrange
        CharacterData char1 = new CharacterData();
        char1.setName("Char 1");
        char1.setActive(true);

        EnemyData enemy1 = new EnemyData();
        enemy1.setName("Enemy 1");
        enemy1.setActive(true);

        List<EntityData> entities = List.of(char1, enemy1);

        // Act
        boolean resultado = entityService.transferData(entities);

        // Assert
        assertTrue(resultado);
    }

    // ========== TEST COUNT ==========

    @Test
    @DisplayName("executeCountByScene() - Debe contar entidades")
    void executeCountByScene_DebeContar() {
        // Act - Sin escena, debe retornar 0 o no fallar
        long count = entityService.executeCountByScene(1L);

        // Assert
        assertTrue(count >= 0);
    }

    // ========== MÉTODO HELPER ==========

    private EntityData crearEntidad(String tipo, String nombre) {
        EntityCreateDto dto = new EntityCreateDto();
        dto.setEntityType(tipo);
        dto.setName(nombre);
        return entityService.createEntity(dto);
    }
}