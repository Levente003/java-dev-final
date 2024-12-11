package com.epam.training.ticketservice.core.movie.model;

public record MovieDto(String title,String category, Integer length) {
    @Override
    public String toString() {
        return title
                + " (" + category + ", "
                + length
                + " minutes)";
    }
}
