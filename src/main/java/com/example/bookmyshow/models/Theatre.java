package com.example.bookmyshow.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;
@Data
@Entity(name="theatres")
public class Theatre extends BaseModel {
    private String name;
    @OneToMany
    private List<Screen> screens;
    @ManyToOne
    private City city;

}
