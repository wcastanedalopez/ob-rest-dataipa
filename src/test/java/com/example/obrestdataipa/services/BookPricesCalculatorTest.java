package com.example.obrestdataipa.services;

import com.example.obrestdataipa.entities.Book;
import jakarta.validation.constraints.AssertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookPricesCalculatorTest {

    @Test
    void calculatePrice() {
        Book book = new Book(1L, "La verdad de vivir", "Partmon" , 789, 777.4, LocalDate.now(),true);
        BookPricesCalculator calculator = new BookPricesCalculator();
        double price = calculator.calculatePrice(book);

        System.out.println(price);
        assertTrue( price > 0 );
        assertEquals(785.39, price);
    }
}