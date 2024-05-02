package com.boom.voice.ui.data;
// Этот класс представляет информацию о пользователе, включая электронную почту, имя, идентификатор и год рождения.
public class user_info {
    // Адрес электронной почты пользователя.
    String email;
    // Имя пользователя.
    String name;
    // Уникальный идентификатор пользователя.
    String id;
    // Год рождения пользователя.
    String year;
    // Получает уникальный идентификатор пользователя.
    public String getId(){
        return id;
    }
    // Устанавливает уникальный идентификатор пользователя.
    public void setId(String id){
        this.id = id;
    }
    // Получает год рождения пользователя.
    public String getYear() {
        return year;
    }
    // Устанавливает год рождения пользователя.
    public void setYear(String year) {
        this.year = year;
    }
    // Получает адрес электронной почты пользователя.
    public String getEmail() {
        return email;
    }
    // Устанавливает адрес электронной почты пользователя.
    public void setEmail(String email) {
        this.email = email;
    }
    // Получает имя пользователя.
    public String getName() {
        return name;
    }
    // Устанавливает имя пользователя.
    public void setName(String name) {
        this.name = name;
    }
}