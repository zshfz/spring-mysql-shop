package com.basic_shop.shop.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findPageBy(Pageable pageable);

    List<Item> findAllByNameContains(String name);

    @Query(value = "select * from item where match(name) against(?1)", nativeQuery = true)
    List<Item> rawQuery1(String name);
}
