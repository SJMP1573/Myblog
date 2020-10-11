package com.sjmp.service;

import com.sjmp.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {
//    新增完，返回一个 Type
    Type saveType(Type type);
//    按 id 查询 Type
    Type getType(Long id);
//    按页查询
    Page<Type> listType(Pageable pageable);
//    更新
    Type updateType(Long id,Type type);
//    删除
    void deleteType(Long id);

    Type getTypeByName(String name);

    List<Type> listType();
//    size 控制首页分类的数量
    List<Type> listTypeTop(Integer size);
}
