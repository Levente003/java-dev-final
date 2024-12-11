package com.epam.training.ticketservice.core.movie.persistence;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;


@Entity
@Table(name = "Movies")
@Data
@NoArgsConstructor
public class Movie {

    @Id
    @Column(unique = true)
    private String title;
    private String category;
    private Integer length;

    public Movie(String title, String category, Integer length) {
        this.title = title;
        this.category = category;
        this.length = length;
    }

}
