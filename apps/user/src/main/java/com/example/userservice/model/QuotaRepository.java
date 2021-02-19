package com.example.userservice.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuotaRepository extends JpaRepository<Quota, Long> {

    Quota findByUserIdAndItemId(String userId, String itemId);

    List<Quota> findAllByUserId(String userId);

    @Modifying
    @Query("update Quota q " +
            "set q.count = q.count - :count " +
            "where q.userId = :userId " +
            "and q.itemId = :itemId " +
            "and q.count >= :count")
    int decrement(@Param("userId") String userId, @Param("itemId") String itemId, @Param("count") Integer count);
}
