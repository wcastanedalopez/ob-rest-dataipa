package com.example.obrestdataipa.services;
import com.example.obrestdataipa.entities.Book;
public class BookPricesCalculator {
    public double calculatePrice(Book book){
    double price = book.getPrice();

    if(book.getPages() > 300){
        price += 5;
    }
    // envio
    price += 2.99;
    return price;
}



}