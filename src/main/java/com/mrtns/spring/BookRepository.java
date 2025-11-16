package com.mrtns.spring;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer>{

    List<Book> findByAuthor(String author);

    List<Book> findByTitleContaining(String keyword);

    List<Book> findByPriceBetween(double minPrice, double maxPrice);
}
