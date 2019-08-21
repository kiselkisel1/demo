package com.example.demo.model;

import com.example.demo.utils.GenderType;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 200, message = "Invalid name length")
    private String name;

    @Size(max = 200, message = "Invalid surname length")
    private String surname;

    @Min(0)
    @Max(100)
    private Integer age;
//
//    @Enumerated(value = EnumType.STRING)
//    private GenderType gender;

}
