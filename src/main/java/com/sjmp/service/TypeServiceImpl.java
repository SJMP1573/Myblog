package com.sjmp.service;


import com.sjmp.NotFoundException;
import com.sjmp.dao.TypeRepository;
import com.sjmp.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: sjmp1573
 * @date: 2020/10/1 11:10
 * @description:
 */

//在 spring 容器中实例化对象
@Service
public class TypeServiceImpl implements TypeService {

    //为什么他会拿到userServiceImpl？
    // @Autowired会帮你按UserService的类型去容器中找唯一bean对象
    // 1、容器没有该类型的对象：报错
    // 2、容器中有该类型的唯一bean对象，就将该唯一bean对象赋值给该属性
    ///3、容器中有多个【两个及以上】该类型的唯一bean对象，
    //     它会再根据该属性名去容器中找，
    //     看看容器中的哪个bean对象的id值和该属性名一致，
    //     如果有，就将容器中该对象赋值给该属性，如果没有报错。

//    TypeRepository 的实例化对象来自 JpaRepositoryFactoryBean 工厂，由JpaRepositoriesAutoConfiguration 实现自动配置
    @Autowired    // 先按类型找，然后按id为属性名去找，
    private TypeRepository typeRepository;

//    使用这个注解的类或者方法表示该类里面的所有方法或者这个方法的事务由spring处理，来保证事务的原子性，
// 即是方法里面对数据库操作，如果失败则spring负责回滚操作，成功则提交操作。

    @Transactional //增删改查都放在事务里面
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.getOne(id);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t = typeRepository.getOne(id);
        if( t == null){
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type,t);
        return typeRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }

//    查询数据库中是否有 Type
    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Override
    public List<Type> listType(){
        return typeRepository.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        return typeRepository.findTop(pageable);
    }
}
