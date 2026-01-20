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
 * Tests para HibernateEntityServiceImpl.
 * Esta clase contiene tests de integración que verifican el correcto funcionamiento del servicio
 * de persistencia con una base de datos de prueba.
 */
// @SpringBootTest: Carga el contexto completo de la aplicación Spring Boot.
// Esto es necesario para poder inyectar el servicio `HibernateEntityService` y que este
// a su vez tenga acceso al EntityManager, repositorios, etc.
@SpringBootTest
// @ActiveProfiles("test"): Activa el perfil "test" de Spring.
// Esto permite usar una configuración específica para los tests, como por ejemplo,
// una base de datos en memoria (H2) en lugar de la base de datos de producción.
// La configuración se encuentra en `src/test/resources/application-test.properties`.
@ActiveProfiles("test")
// @Transactional: Anotación clave para los tests de base de datos.
// Spring envuelve cada método de test en una transacción. Al final del test,
// hace un "rollback" de esa transacción.
// Esto significa que los datos creados, modificados o eliminados durante un test
// no afectan a los demás tests, garantizando que cada test se ejecute en un estado limpio
// y sea independiente de los otros.
@Transactional
class HibernateEntityServiceTest {

    // @Autowired: Inyección de dependencias. Spring se encarga de crear una instancia
    // de HibernateEntityService y asignarla a esta variable antes de ejecutar los tests.
    @Autowired
    private HibernateEntityService entityService;

    // ========== TEST DE CONEXIÓN ==========

    @Test
    // @DisplayName: Permite poner un nombre descriptivo al test, que se mostrará
    // en los informes de ejecución de tests. Es más legible que el nombre del método.
    @DisplayName("testEntityManager() - Verifica que EntityManager está activo")
    void testEntityManager_DebeRetornarMensajeExito() {
        // Arrange (Preparar): En este caso no se necesita preparación.

        // Act (Actuar): Se llama al método que queremos probar.
        String resultado = entityService.testEntityManager();

        // Assert (Afirmar): Se comprueba que el resultado es el esperado.
        // `assertNotNull`: Asegura que el resultado no es nulo.
        assertNotNull(resultado);
        // `assertTrue`: Asegura que la condición es verdadera. En este caso, que el mensaje
        // de éxito contiene la frase "EntityManager activo".
        assertTrue(resultado.contains("EntityManager activo"));
    }

    // ========== TESTS DE CREACIÓN (CREATE) ==========
    // Estos tests verifican que el método `createEntity` funciona correctamente
    // para cada tipo de entidad del juego.

    @Test
    @DisplayName("createEntity() - Debe crear un Character (personaje) correctamente")
    void createEntity_Character_DebeCrear() {
        // Arrange: Se crea un objeto DTO (Data Transfer Object) con los datos necesarios
        // para crear un nuevo personaje.
        EntityCreateDto dto = new EntityCreateDto();
        dto.setEntityType("CHARACTER"); // Se especifica el tipo de entidad
        dto.setName("Jugador 1");
        dto.setPosX(100.0);
        dto.setPosY(200.0);
        dto.setHealth(100);
        dto.setLives(3);

        // Act: Se llama al método `createEntity` con el DTO.
        EntityData resultado = entityService.createEntity(dto);

        // Assert: Se comprueba que la entidad se ha creado correctamente.
        assertNotNull(resultado, "La entidad creada no debería ser nula.");
        assertNotNull(resultado.getId(), "La entidad creada debe tener un ID asignado por la base de datos.");
        assertEquals("Jugador 1", resultado.getName(), "El nombre de la entidad debe ser el esperado.");
        // `isInstanceOf` (implícito en `assertTrue(resultado instanceof ...)`):
        // Verifica que el objeto creado es del tipo correcto (CharacterData).
        assertTrue(resultado instanceof CharacterData, "La entidad debe ser una instancia de CharacterData.");

        // Se hace un "casting" para poder acceder a los campos específicos de CharacterData.
        CharacterData character = (CharacterData) resultado;
        assertEquals(100, character.getHealth(), "La vida del personaje debe ser la esperada.");
        assertEquals(3, character.getLives(), "Las vidas del personaje deben ser las esperadas.");
    }

    @Test
    @DisplayName("createEntity() - Debe crear un Enemy (enemigo) correctamente")
    void createEntity_Enemy_DebeCrear() {
        // Arrange: Se prepara el DTO para un enemigo.
        EntityCreateDto dto = new EntityCreateDto();
        dto.setEntityType("ENEMY");
        dto.setName("Goblin");
        dto.setDamage(10);
        dto.setEnemyType("BASIC");

        // Act: Se crea la entidad.
        EntityData resultado = entityService.createEntity(dto);

        // Assert: Se verifica que es un enemigo y su daño es el correcto.
        assertTrue(resultado instanceof EnemyData, "La entidad debe ser una instancia de EnemyData.");
        assertEquals(10, ((EnemyData) resultado).getDamage(), "El daño del enemigo debe ser el esperado.");
    }

    // (Los siguientes tests de creación siguen el mismo patrón para los diferentes tipos de entidades)

    @Test
    @DisplayName("createEntity() - Debe crear un NPC correctamente")
    void createEntity_NPC_DebeCrear() {
        EntityCreateDto dto = new EntityCreateDto();
        dto.setEntityType("NPC");
        dto.setName("Vendedor");
        dto.setNpcType("MERCHANT");
        EntityData resultado = entityService.createEntity(dto);
        assertTrue(resultado instanceof NPCData);
        assertEquals(NPCData.NPCType.MERCHANT, ((NPCData) resultado).getNpcType());
    }

    @Test
    @DisplayName("createEntity() - Debe crear un StaticObject correctamente")
    void createEntity_StaticObject_DebeCrear() {
        EntityCreateDto dto = new EntityCreateDto();
        dto.setEntityType("STATIC_OBJECT");
        dto.setName("Roca");
        dto.setSolid(true);
        dto.setStaticType("OBSTACLE");
        EntityData resultado = entityService.createEntity(dto);
        assertTrue(resultado instanceof StaticObjectData);
        assertTrue(((StaticObjectData) resultado).getSolid());
    }

    @Test
    @DisplayName("createEntity() - Debe crear un DynamicObject correctamente")
    void createEntity_DynamicObject_DebeCrear() {
        EntityCreateDto dto = new EntityCreateDto();
        dto.setEntityType("DYNAMIC_OBJECT");
        dto.setName("Caja");
        dto.setHasPhysics(true);
        EntityData resultado = entityService.createEntity(dto);
        assertTrue(resultado instanceof DynamicObjectData);
        assertTrue(((DynamicObjectData) resultado).getHasPhysics());
    }

    @Test
    @DisplayName("createEntity() - Debe crear un Audio correctamente")
    void createEntity_Audio_DebeCrear() {
        EntityCreateDto dto = new EntityCreateDto();
        dto.setEntityType("AUDIO");
        dto.setName("Música");
        dto.setAudioType("MUSIC");
        dto.setVolume(0.8);
        dto.setLoop(true);
        EntityData resultado = entityService.createEntity(dto);
        assertTrue(resultado instanceof AudioData);
        assertEquals(0.8, ((AudioData) resultado).getVolume());
        assertTrue(((AudioData) resultado).getLoop());
    }

    @Test
    @DisplayName("createEntity() - Debe crear un UI Element correctamente")
    void createEntity_UIElement_DebeCrear() {
        EntityCreateDto dto = new EntityCreateDto();
        dto.setEntityType("UI_ELEMENT");
        dto.setName("Botón");
        dto.setUiType("BUTTON");
        dto.setText("JUGAR");
        EntityData resultado = entityService.createEntity(dto);
        assertTrue(resultado instanceof UIElementData);
        assertEquals("JUGAR", ((UIElementData) resultado).getText());
    }

    @Test
    @DisplayName("createEntity() - Debe crear un Tile correctamente")
    void createEntity_Tile_DebeCrear() {
        EntityCreateDto dto = new EntityCreateDto();
        dto.setEntityType("TILE");
        dto.setName("Suelo");
        dto.setGridX(5);
        dto.setGridY(10);
        dto.setWalkable(true);
        EntityData resultado = entityService.createEntity(dto);
        assertTrue(resultado instanceof TileData);
        assertEquals(5, ((TileData) resultado).getGridX());
        assertEquals(10, ((TileData) resultado).getGridY());
    }

    // ========== TESTS DE LECTURA (READ) ==========

    @Test
    @DisplayName("findEntityById() - Debe encontrar una entidad que sí existe")
    void findEntityById_Existe_DebeRetornar() {
        // Arrange: Se crea una entidad primero para asegurarnos de que existe en la BD.
        EntityData creada = crearEntidad("CHARACTER", "Test");

        // Act: Se busca la entidad usando el ID que nos devolvió la creación.
        EntityData encontrada = entityService.findEntityById(creada.getId());

        // Assert: Se verifica que la entidad encontrada no es nula y tiene el nombre correcto.
        assertNotNull(encontrada, "Se debería haber encontrado la entidad.");
        assertEquals("Test", encontrada.getName(), "El nombre de la entidad encontrada debe ser correcto.");
    }

    @Test
    @DisplayName("findEntityById() - Debe devolver null si la entidad no existe")
    void findEntityById_NoExiste_DebeRetornarNull() {
        // Arrange: No se crea nada. Se usa un ID que es muy improbable que exista.

        // Act: Se busca una entidad con un ID заведомо inexistente.
        EntityData resultado = entityService.findEntityById(99999L);

        // Assert: Se comprueba que el resultado es nulo.
        assertNull(resultado, "No se debería encontrar una entidad, el resultado debe ser null.");
    }

    @Test
    @DisplayName("findAll() - Debe devolver una lista con todas las entidades creadas")
    void findAll_DebeRetornarTodas() {
        // Arrange: Se crean varias entidades de distintos tipos para poblar la BD.
        crearEntidad("CHARACTER", "Char 1");
        crearEntidad("ENEMY", "Enemy 1");
        crearEntidad("NPC", "NPC 1");

        // Act: Se llama a `findAll()` para obtener todas las entidades.
        List<EntityData> todas = entityService.findAll();

        // Assert: Se comprueba que la lista contiene al menos las 3 entidades que creamos.
        // No se pone `assertEquals(3, ...)` porque la BD de test podría tener ya otros datos
        // si no se usara @Transactional. Con @Transactional, siempre partimos de 0.
        assertTrue(todas.size() >= 3, "La lista debe contener al menos las 3 entidades creadas.");
    }

    // ========== TESTS DE ACTUALIZACIÓN (UPDATE) ==========

    @Test
    @DisplayName("updateEntity() - Debe actualizar los campos de una entidad existente")
    void updateEntity_DebeActualizar() {
        // Arrange: Se crea una entidad inicial y un DTO con los nuevos datos.
        EntityData creada = crearEntidad("CHARACTER", "Original");

        EntityUpdateDto updateDto = new EntityUpdateDto();
        updateDto.setName("Actualizado");
        updateDto.setPosX(100.0);
        updateDto.setActive(false);

        // Act: Se llama al método de actualización.
        EntityData actualizada = entityService.updateEntity(creada.getId(), updateDto);

        // Assert: Se comprueba que los campos de la entidad devuelta tienen los valores nuevos.
        assertEquals("Actualizado", actualizada.getName());
        assertEquals(100.0, actualizada.getPosX());
        assertFalse(actualizada.getActive());
    }

    @Test
    @DisplayName("updateEntity() - Debe lanzar una excepción si la entidad no existe")
    void updateEntity_NoExiste_DebeLanzarExcepcion() {
        // Arrange: Se crea un DTO de actualización pero no se crea la entidad.
        EntityUpdateDto dto = new EntityUpdateDto();
        dto.setName("Test");

        // Act & Assert: Se usa `assertThrows` para verificar que el código dentro de la lambda
        // lanza una excepción del tipo esperado (RuntimeException). Esto es útil para probar
        // el manejo de errores.
        assertThrows(RuntimeException.class, () -> {
            entityService.updateEntity(99999L, dto);
        }, "Debería lanzarse una RuntimeException al intentar actualizar una entidad que no existe.");
    }

    // ========== TESTS DE ELIMINACIÓN (DELETE) ==========

    @Test
    @DisplayName("deleteEntity() - Debe eliminar una entidad que sí existe")
    void deleteEntity_Existe_DebeRetornarTrue() {
        // Arrange: Se crea una entidad para poder eliminarla.
        EntityData creada = crearEntidad("ENEMY", "A eliminar");
        Long id = creada.getId();

        // Act: Se llama al método de eliminación.
        boolean resultado = entityService.deleteEntity(id);

        // Assert: Se comprueba que el método devolvió `true` y que, si intentamos
        // buscar la entidad de nuevo, el resultado es `null`.
        assertTrue(resultado, "El método debe devolver true cuando la eliminación es exitosa.");
        assertNull(entityService.findEntityById(id), "La entidad ya no debería existir en la base de datos.");
    }

    @Test
    @DisplayName("deleteEntity() - Debe devolver false si la entidad no existe")
    void deleteEntity_NoExiste_DebeRetornarFalse() {
        // Arrange: No se crea ninguna entidad.

        // Act: Se intenta eliminar una entidad con un ID que no existe.
        boolean resultado = entityService.deleteEntity(99999L);

        // Assert: Se comprueba que el resultado es `false`.
        assertFalse(resultado, "El método debe devolver false si la entidad a eliminar no existe.");
    }

    // ========== TEST DE BÚSQUEDA AVANZADA (JPQL) ==========

    @Test
    @DisplayName("searchEntities() - Debe filtrar correctamente por los criterios dados")
    void searchEntities_DebeAplicarFiltros() {
        // Arrange: Se crean varias entidades con diferentes propiedades (en este caso, capas).
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

        // Act: Se crea un DTO de búsqueda y se le aplica un filtro (capa 1).
        EntityQueryDto query = new EntityQueryDto();
        query.setLayer(1);
        List<EntityData> resultado = entityService.searchEntities(query);

        // Assert: Se verifica que la lista de resultados solo contiene entidades que cumplen el filtro.
        // Se usa `stream().allMatch()` para comprobar que TODAS las entidades en la lista
        // tienen la capa 1.
        assertTrue(resultado.stream().allMatch(e -> e.getLayer() == 1), "Todas las entidades encontradas deben estar en la capa 1.");
    }

    // ========== TEST DE TRANSACCIONES ==========

    @Test
    @DisplayName("transferData() - Debe guardar múltiples entidades en una sola transacción")
    void transferData_DebeGuardarTodas() {
        // Arrange: Se crea una lista de entidades (sin guardarlas todavía).
        CharacterData char1 = new CharacterData();
        char1.setName("Char 1");
        char1.setActive(true);

        EnemyData enemy1 = new EnemyData();
        enemy1.setName("Enemy 1");
        enemy1.setActive(true);

        List<EntityData> entities = List.of(char1, enemy1);

        // Act: Se llama al método `transferData`.
        boolean resultado = entityService.transferData(entities);

        // Assert: Se comprueba que el método devuelve `true`.
        // Gracias a la anotación @Transactional del test, si algo fallara dentro de `transferData`
        // se produciría un rollback y el test fallaría, que es lo que se busca.
        assertTrue(resultado, "La transferencia de datos debería ser exitosa.");
    }

    // ========== TEST DE CONTEO (COUNT) ==========

    @Test
    @DisplayName("executeCountByScene() - Debe contar las entidades de una escena")
    void executeCountByScene_DebeContar() {
         // Arrange: No creamos entidades asociadas a una escena, el conteo debería ser 0.

        // Act: Se llama al método de conteo para una escena ficticia.
        long count = entityService.executeCountByScene(1L);

        // Assert: Se comprueba que el resultado es 0.
        assertEquals(0, count, "El conteo de entidades para una escena vacía debe ser 0.");
    }

    // ========== MÉTODOS HELPER (AYUDA) ==========

    /**
     * Método privado para reducir la duplicación de código en los tests.
     */
    private EntityData crearEntidad(String tipo, String nombre) {
        EntityCreateDto dto = new EntityCreateDto();
        dto.setEntityType(tipo);
        dto.setName(nombre);
        return entityService.createEntity(dto);
    }
}