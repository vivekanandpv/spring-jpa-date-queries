package com.example.demo;

import com.example.demo.models.Product;
import com.example.demo.repositories.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public ResponseEntity<List<Product>> get() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @GetMapping("date-between")
    public ResponseEntity<List<Product>> getDateBetween(@RequestParam String from, @RequestParam String to) {
        try {
            LocalDate startDate = LocalDate.parse(from, DateTimeFormatter.ISO_DATE);
            LocalDate endDate = LocalDate.parse(to, DateTimeFormatter.ISO_DATE);
            return ResponseEntity.ok(productRepository.findByDateOfManufactureBetween(startDate, endDate));
        } catch (RuntimeException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("first-after-timestamp")
    public ResponseEntity<Product> getFirstAfter(@RequestParam String begin) {
        try {
            LocalDateTime timestamp = LocalDateTime.parse(begin, DateTimeFormatter.ISO_DATE_TIME);
            Product product = productRepository.findFirstByTimeStampIsAfterOrderByTimeStampAsc(timestamp)
                    .orElseThrow(RuntimeException::new);
            return ResponseEntity.ok(product);
        } catch (RuntimeException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("first-after-timestamp-jpql")
    public ResponseEntity<Product> getFirstAfterJpql(@RequestParam String begin) {
        try {
            LocalDateTime timestamp = LocalDateTime.parse(begin, DateTimeFormatter.ISO_DATE_TIME);
            Product product = productRepository.findCustomFirst(timestamp)
                    .orElseThrow(RuntimeException::new);
            return ResponseEntity.ok(product);
        } catch (RuntimeException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("list-after-timestamp-jpql")
    public ResponseEntity<List<Product>> getListAfterJpql(@RequestParam String begin) {
        try {
            LocalDateTime timestamp = LocalDateTime.parse(begin, DateTimeFormatter.ISO_DATE_TIME);
            return ResponseEntity.ok(productRepository.findCustomCollection(timestamp));
        } catch (RuntimeException exception) {
            return ResponseEntity.badRequest().build();
        }
    }
}
