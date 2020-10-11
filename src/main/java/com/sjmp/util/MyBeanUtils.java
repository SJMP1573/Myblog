package com.sjmp.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author SJMP1573
 * @Date 2020/10/3 18:58
 * @Description 
 * @Since version-1.0
 */

public class MyBeanUtils {


/**
 * @Author SJMP1573
 * @Date 2020/10/3 18:59
 * @Description 
 * @Param [source]
 * @Return java.lang.String[]
 * @Since version-1.0
 */
    public static String[] getNullPropertyNames(Object source) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds =  beanWrapper.getPropertyDescriptors();
        List<String> nullPropertyNames = new ArrayList<>();
        for (PropertyDescriptor pd : pds) {
            String propertyName = pd.getName();
            if (beanWrapper.getPropertyValue(propertyName) == null) {
                nullPropertyNames.add(propertyName);
            }
        }
        return nullPropertyNames.toArray(new String[nullPropertyNames.size()]);
    }

}
