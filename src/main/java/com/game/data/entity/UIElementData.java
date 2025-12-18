package com.game.data.entity;

import jakarta.persistence.*;

/**
 * Elemento de interfaz de usuario
 */
@Entity
@DiscriminatorValue("UI_ELEMENT")
public class UIElementData extends EntityData {

    @Enumerated(EnumType.STRING)
    private UIType uiType = UIType.IMAGE;

    // Dimensiones
    private Double width = 100.0;
    private Double height = 50.0;

    // Texto (para TEXT, BUTTON)
    private String text;
    private String fontName;
    private Integer fontSize = 16;
    private String fontColor = "#FFFFFF";

    // Interacción
    private Boolean interactive = false;
    private String onClickAction;

    // Anclaje en pantalla
    @Enumerated(EnumType.STRING)
    private UIAnchor anchor = UIAnchor.TOP_LEFT;

    public enum UIType {
        IMAGE,      // Imagen estática
        TEXT,       // Texto
        BUTTON,     // Botón
        PANEL,      // Panel contenedor
        PROGRESS_BAR,// Barra de progreso
        SLIDER      // Slider
    }

    public enum UIAnchor {
        TOP_LEFT, TOP_CENTER, TOP_RIGHT,
        CENTER_LEFT, CENTER, CENTER_RIGHT,
        BOTTOM_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT
    }

    // ===== GETTERS Y SETTERS =====
    public UIType getUiType() { return uiType; }
    public void setUiType(UIType uiType) { this.uiType = uiType; }

    public Double getWidth() { return width; }
    public void setWidth(Double width) { this.width = width; }

    public Double getHeight() { return height; }
    public void setHeight(Double height) { this.height = height; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getFontName() { return fontName; }
    public void setFontName(String fontName) { this.fontName = fontName; }

    public Integer getFontSize() { return fontSize; }
    public void setFontSize(Integer fontSize) { this.fontSize = fontSize; }

    public String getFontColor() { return fontColor; }
    public void setFontColor(String fontColor) { this.fontColor = fontColor; }

    public Boolean getInteractive() { return interactive; }
    public void setInteractive(Boolean interactive) { this.interactive = interactive; }

    public String getOnClickAction() { return onClickAction; }
    public void setOnClickAction(String onClickAction) { this.onClickAction = onClickAction; }

    public UIAnchor getAnchor() { return anchor; }
    public void setAnchor(UIAnchor anchor) { this.anchor = anchor; }
}