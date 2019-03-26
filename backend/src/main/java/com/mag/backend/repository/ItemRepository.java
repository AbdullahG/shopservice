package com.mag.backend.repository;

import com.mag.backend.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.stream.Stream;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("select i from Item i where :categoryId is null or i.category.id = :categoryId")
    Stream<Item> findByCategoryId(@Param("categoryId") Long categoryId);
}
