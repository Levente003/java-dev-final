package com.epam.training.ticketservice.core.movie.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieDtoTest {
    MovieDto underTest = new MovieDto("Test Movie","animation",90);

    @Test
    void testToStringMethod() {
        String actual = underTest.toString();
        String expected = "Test Movie (animation, 90 minutes)";
        assertEquals(expected,actual);
    }

    @Test
    void title() {
        String actual = underTest.title();
        String expected = "Test Movie";
        assertEquals(expected,actual);
    }

    @Test
    void category() {
        String actual = underTest.category();
        String expected = "animation";
        assertEquals(expected,actual);
    }

    @Test
    void length() {
        int actual = underTest.length();
        int expected = 90;
        assertEquals(expected,actual);
    }
}