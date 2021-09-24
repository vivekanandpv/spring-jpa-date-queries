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
    //  Automatic (convention-based) query-generation by methods (aka automatic query methods)
    //  https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
    List<Product> findByDateOfManufactureBetween(LocalDate dateOfManufactureStart, LocalDate dateOfManufactureEnd);
    Optional<Product> findFirstByTimeStampIsAfterOrderByTimeStampAsc(LocalDateTime timeStamp);

    //  JPQL: Java Persistence Query Language
    //  https://docs.oracle.com/javaee/6/tutorial/doc/bnbtg.html
    @Query("select p from Product p where p.timeStamp > ?1 order by p.id desc")
    List<Product> findCustomCollection(LocalDateTime timeStamp);

    //  Raw SQL (not-recommended in most circumstances, as it hardcodes for a particular database product, such as PostgreSQL)
    @Query(value = "SELECT * FROM public.product WHERE time_stamp > ?1 ORDER BY id ASC LIMIT 1", nativeQuery = true)
    Optional<Product> findCustomFirst(LocalDateTime timeStamp);
}
