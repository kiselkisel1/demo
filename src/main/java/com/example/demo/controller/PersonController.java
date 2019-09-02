package com.example.demo.controller;

import com.example.demo.model.Person;
import com.example.demo.service.PersonService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public List<Person> getAllGenres() {
        return personService.findAll();
    }

    @GetMapping("{id}")
    public Person getGenreById(@PathVariable("id") Integer id) {
        return personService.getOne(id);
    }

    @PostMapping
    public Person save(@RequestBody @Valid Person person) {
        return personService.save(person);
    }

    @PutMapping("{id}")
    public Person update(@PathVariable("id") Integer id,
                         @RequestBody @Valid Person genreFromPerson) {
        Person genreFromDb = personService.getOne(id);
        BeanUtils.copyProperties(genreFromPerson,genreFromDb,"id");
        return personService.save(genreFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Person person) {
        personService.delete(person);
    }
}