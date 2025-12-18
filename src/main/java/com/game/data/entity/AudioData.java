package com.game.data.entity;

import jakarta.persistence.*;

/**
 * Recurso de audio
 */
@Entity
@DiscriminatorValue("AUDIO")
public class AudioData extends EntityData {

    @Enumerated(EnumType.STRING)
    private AudioType audioType = AudioType.SFX;

    private String audioPath;
    private Double volume = 1.0;
    private Boolean loop = false;
    private Boolean playOnStart = false;

    // Para audio 3D/espacial
    private Boolean spatial = false;
    private Double minDistance = 1.0;
    private Double maxDistance = 500.0;

    public enum AudioType {
        MUSIC,      // Música de fondo
        SFX,        // Efecto de sonido
        AMBIENT,    // Sonido ambiental
        VOICE       // Voz/Diálogo
    }

    // ===== GETTERS Y SETTERS =====
    public AudioType getAudioType() { return audioType; }
    public void setAudioType(AudioType audioType) { this.audioType = audioType; }

    public String getAudioPath() { return audioPath; }
    public void setAudioPath(String audioPath) { this.audioPath = audioPath; }

    public Double getVolume() { return volume; }
    public void setVolume(Double volume) { this.volume = volume; }

    public Boolean getLoop() { return loop; }
    public void setLoop(Boolean loop) { this.loop = loop; }

    public Boolean getPlayOnStart() { return playOnStart; }
    public void setPlayOnStart(Boolean playOnStart) { this.playOnStart = playOnStart; }

    public Boolean getSpatial() { return spatial; }
    public void setSpatial(Boolean spatial) { this.spatial = spatial; }

    public Double getMinDistance() { return minDistance; }
    public void setMinDistance(Double minDistance) { this.minDistance = minDistance; }

    public Double getMaxDistance() { return maxDistance; }
    public void setMaxDistance(Double maxDistance) { this.maxDistance = maxDistance; }
}