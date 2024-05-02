package com.boom.voice.ui.data;
// Этот класс представляет информацию о голосе, включая его имя, идентификатор и тип.
public class MyvoiceInfo {
    // Уникальный идентификатор голоса.
    private String id;
    // Имя голоса.
    private String name;
    // Тип или характеристики голоса.
    private String voice;
    // Устанавливает идентификатор голоса.
    public void setId(String id) {
        this.id = id;
    }
    // Получает идентификатор голоса.
    public String getId() {
        return id;
    }
    // Устанавливает имя голоса.
    public void setName(String name) {
        this.name = name;
    }
    // Получает имя голоса.
    public String getName() {
        return name;
    }
    // Устанавливает тип или характеристики голоса.
    public void setVoice(String voice) {
        this.voice = voice;
    }
    // Получает тип или характеристики голоса.
    public String getVoice() {
        return voice;
    }
}

