package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@Table
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Size(max = 200, message = "Invalid name length")
    private String name;

    @Size(max = 200, message = "Invalid surname length")
    private String email;

}
