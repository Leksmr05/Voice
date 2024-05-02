package com.boom.voice.ui.data;
import java.io.Serializable;
import java.util.List;
// Этот класс представляет информацию о голосовании, включая название, пользователей, список элементов,
// список "за", список "против" и идентификатор.
public class VoiceInfo implements Serializable {
    // Название голосования.
    String name;
    // Пользователи.
    String users;
    // Список элементов.
    List<String> item;
    // Список "за".
    List<String> za;
    // Список "против".
    List<String> protiv;
    // Идентификатор голосования.
    String id;
    // Получает список "против".
    public List<String> getProtiv() {
        return protiv;
    }
    // Устанавливает список "против".
    public void setProtiv(List<String> protiv) {
        this.protiv = protiv;
    }
    // Получает список "за".
    public List<String> getZa() {
        return za;
    }
    // Устанавливает список "за".
    public void setZa(List<String> za) {
        this.za = za;
    }
    // Устанавливает название голосования.
    public void setName(String name) {
        this.name = name;
    }
    // Получает название голосования.
    public String getName() {
        return name;
    }
    // Получает список элементов.
    public List<String> getItem() {
        return item;
    }
    // Устанавливает список элементов.
    public void setItem(List<String> item ) {
        this.item = item;
    }
    // Получает пользователей.
    public String getUsers() {
        return users;
    }
    // Устанавливает пользователей.
    public void setUsers(String users) {
        this.users = users;
    }
    // Устанавливает идентификатор голосования.
    public void setId(String id) {
        this.id = id;
    }
    // Получает идентификатор голосования.
    public String getId() {
        return id;
    }
}
