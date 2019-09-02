package com.example.demo.service;

import com.example.demo.model.Person;

import java.util.List;


public interface PersonService {
    List<Person> findAll();
    Person getOne(Integer id);
    Person save (Person person);
    void delete (Person person);
 }
