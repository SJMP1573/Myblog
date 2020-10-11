package com.sjmp.dao;

import com.sjmp.po.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author: sjmp1573
 * @date: 2020/10/1 11:11
 * @description:
 */

public interface TagRepository extends JpaRepository<Tag,Long>{
    Tag findByName(String name);

    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}

