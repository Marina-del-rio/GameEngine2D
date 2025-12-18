package com.game.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.game.engine.entities.actors.Character;
import jakarta.persistence.TypedQuery;
import java.util.List;

public abstract class EntityService {

    @PersistenceContext
    protected EntityManager entityManager;

    public String testEntityManager() {
        if (!entityManager.isOpen()) {
            throw new RuntimeException("EntityManager está cerrado");
        }
        return "✓ EntityManager está activo y funcionando.";
    }

    public Character createNewCharacter(String tag, int vida, int velocidad,
                                        int posx, int posy, String rutaArchivo) {
        Character nuevoChar = new Character();
        nuevoChar.setTag(tag);
        nuevoChar.setVida(vida);
        nuevoChar.setVelocidad(velocidad);
        nuevoChar.setPosX(posx);
        nuevoChar.setPosY(posy);
        nuevoChar.setRutaEntity(rutaArchivo);

        entityManager.persist(nuevoChar);
        return nuevoChar;
    }

    // Buscar por ID
    public Character findCharacterById(Long id) {
        return entityManager.find(Character.class, id);
    }

    // Buscar por nombre/tag - CORREGIDO
    public Character findCharacterByName(String name) {
        TypedQuery<Character> query = entityManager.createQuery(
                "SELECT c FROM Character c WHERE c.tag = :name",
                Character.class
        );
        query.setParameter("name", name);

        List<Character> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    // Buscar todos
    public List<Character> findAllCharacters() {
        TypedQuery<Character> query = entityManager.createQuery(
                "SELECT c FROM Character c",
                Character.class
        );
        return query.getResultList();
    }

    // Opcional: Buscar múltiples por nombre (si puede haber varios con el mismo tag)
    public List<Character> findCharactersByName(String name) {
        TypedQuery<Character> query = entityManager.createQuery(
                "SELECT c FROM Character c WHERE c.tag = :name",
                Character.class
        );
        query.setParameter("name", name);
        return query.getResultList();
    }
}