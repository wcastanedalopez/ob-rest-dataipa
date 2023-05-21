package com.example.obrestdataipa.controller;

import com.example.obrestdataipa.entities.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    private TestRestTemplate testRestTemplate;
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri( "http://localhost:" + port );
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    @Test
    void hello () {
        ResponseEntity<String> response = testRestTemplate.getForEntity("/hello", String.class);
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("Hello world actualizado", response.getBody());
    }
    @Test
    void findAllBooks() {
        ResponseEntity<Book[]> response = testRestTemplate.getForEntity("/api/books", Book[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(200, response.getStatusCodeValue());
        List<Book> books = Arrays.asList(response.getBody());
        System.out.println(books.size());
    }

    @Test
    void findOneById() {
        ResponseEntity<Book[]> response = testRestTemplate.getForEntity("/api/books/1", Book[].class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    @DisplayName("check that a Book type entity is being created correctly")
    void create() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        String json = """
                {
                    "title": "Libro creado desde Spring Boot",
                    "author":"William",
                    "pages": 780,
                    "price": 600.0,
                    "releaseDate": "2023-05-20",
                    "online":false
                }
                """;
        HttpEntity<String> request = new HttpEntity<>(json, header);
        ResponseEntity<Book> response =
                testRestTemplate.exchange("/api/books", HttpMethod.POST, request, Book.class);
        assertEquals(1L, response.getBody().getId());
        assertEquals("Libro creado desde Spring Boot", response.getBody().getTitle());
    }
}