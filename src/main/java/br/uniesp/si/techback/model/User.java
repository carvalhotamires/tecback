package br.uniesp.si.techback.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;
    private String email;
    private String password;

}
