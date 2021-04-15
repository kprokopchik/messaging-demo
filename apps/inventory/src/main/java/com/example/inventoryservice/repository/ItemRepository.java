package com.example.inventoryservice.repository;


import com.example.inventoryservice.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {

    @Modifying
    @Query("update Item i " +
            "set i.count = i.count - :count " +
            "where i.id = :itemId and i.count >= :count")
    int decrementItems(@Param("itemId") String itemId, @Param("count") Integer count);

    @Modifying
    @Query("update Item i " +
            "set i.count = i.count + :count " +
            "where i.id = :itemId")
    int incrementItems(@Param("itemId") String itemId, @Param("count") Integer count);
}
