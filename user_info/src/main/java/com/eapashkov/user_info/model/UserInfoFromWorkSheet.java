package com.eapashkov.user_info.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoFromWorkSheet {

    @Id
    private Long id;

    private String firstname;
    private String lastname;
    private String city;
    private int age;

    // каждые 15 минут идет в сервис по скачиванию и проверяет не
    // появлось ли ничего нового в бд, если появилось то скачивает и парсит на сущность, сохраняет в бд



}
