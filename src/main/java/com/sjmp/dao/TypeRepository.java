package com.sjmp.dao;

import com.sjmp.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author: sjmp1573
 * @date: 2020/10/1 11:11
 * @description:
 */

public interface TypeRepository extends JpaRepository<Type,Long>{
    Type findByName(String name);

    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}

