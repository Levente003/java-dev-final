package com.epam.training.ticketservice.core.movie.persistence;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
@Table(name = "Movies")
@Data
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue
    private Integer id;
    @Setter
    @Column(unique = true)
    private String title;
    @Setter
    private String category;
    @Setter
    private Integer length;

    public Movie(String title, String category, Integer length) {
        this.title = title;
        this.category = category;
        this.length = length;
    }

}
