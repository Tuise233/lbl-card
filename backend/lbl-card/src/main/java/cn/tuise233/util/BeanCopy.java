package cn.tuise233.util;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopy {

    public static <V> V copyBean(Object source, Class<V> clazz){
        V result = null;
        try {
            result = clazz.newInstance();
            BeanUtils.copyProperties(source, result);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    public static <O, V> List<V> copyBeanList(List<O> list, Class<V> clazz){
        return list.stream().map(o -> copyBean(o, clazz)).collect(Collectors.toList());
    }
}
