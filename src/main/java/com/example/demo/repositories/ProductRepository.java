package com.example.demo.repositories;

import com.example.demo.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByDateOfManufactureBetween(LocalDate dateOfManufactureStart, LocalDate dateOfManufactureEnd);
    Optional<Product> findFirstByTimeStampIsAfterOrderByTimeStampAsc(LocalDateTime timeStamp);

    @Query("select p from Product p where p.timeStamp > ?1 order by p.id desc")
    List<Product> findCustomCollection(LocalDateTime timeStamp);

    @Query(value = "SELECT * FROM public.product ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Optional<Product> findCustomFirst(LocalDateTime timeStamp);
}
