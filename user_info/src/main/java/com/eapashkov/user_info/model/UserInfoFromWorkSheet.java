package com.eapashkov.user_info.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoFromWorkSheet {

    @Id
    private UUID id;

    private String firstname;
    private String lastname;
    private String city;
    private int age;

}
