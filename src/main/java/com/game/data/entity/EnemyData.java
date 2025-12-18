package com.game.data.entity;

import jakarta.persistence.*;

/**
 * Enemigo
 */
@Entity
@DiscriminatorValue("ENEMY")
public class EnemyData extends ActorData {

    @Enumerated(EnumType.STRING)
    private EnemyType enemyType = EnemyType.BASIC;

    // Combate
    private Integer damage = 10;
    private Double attackRange = 50.0;
    private Double attackCooldown = 1.0;

    // Detección
    private Double detectionRadius = 200.0;

    // IA
    @Enumerated(EnumType.STRING)
    private EnemyBehavior aiBehavior = EnemyBehavior.PATROL;

    @Column(columnDefinition = "TEXT")
    private String patrolPoints;

    // Recompensas
    private Integer experiencePoints = 10;

    @Column(columnDefinition = "TEXT")
    private String dropTable;

    // Boss
    private Boolean isBoss = false;

    public enum EnemyType {
        BASIC, RANGED, FLYING, TANK, FAST, BOSS
    }

    public enum EnemyBehavior {
        STATIONARY, PATROL, CHASE, GUARD
    }

    // ===== GETTERS Y SETTERS =====
    public EnemyType getEnemyType() { return enemyType; }
    public void setEnemyType(EnemyType enemyType) { this.enemyType = enemyType; }

    public Integer getDamage() { return damage; }
    public void setDamage(Integer damage) { this.damage = damage; }

    public Double getAttackRange() { return attackRange; }
    public void setAttackRange(Double attackRange) { this.attackRange = attackRange; }

    public Double getAttackCooldown() { return attackCooldown; }
    public void setAttackCooldown(Double attackCooldown) { this.attackCooldown = attackCooldown; }

    public Double getDetectionRadius() { return detectionRadius; }
    public void setDetectionRadius(Double detectionRadius) { this.detectionRadius = detectionRadius; }

    public EnemyBehavior getAiBehavior() { return aiBehavior; }
    public void setAiBehavior(EnemyBehavior aiBehavior) { this.aiBehavior = aiBehavior; }

    public String getPatrolPoints() { return patrolPoints; }
    public void setPatrolPoints(String patrolPoints) { this.patrolPoints = patrolPoints; }

    public Integer getExperiencePoints() { return experiencePoints; }
    public void setExperiencePoints(Integer experiencePoints) { this.experiencePoints = experiencePoints; }

    public String getDropTable() { return dropTable; }
    public void setDropTable(String dropTable) { this.dropTable = dropTable; }

    public Boolean getIsBoss() { return isBoss; }
    public void setIsBoss(Boolean isBoss) { this.isBoss = isBoss; }
}