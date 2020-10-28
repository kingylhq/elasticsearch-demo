package com.lq.utils;

import org.dozer.DozerBeanMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 实现各层VO-Entiy-Model之间的复制
 *
 * @Title:
 * @Description:
 * @Author:liqian
 */
public class BeanMapperUtil {

    private static DozerBeanMapper dozer = new DozerBeanMapper();

    public static <T> T map(Object sourceObject, Class<T> destObjectclazz) {
        return sourceObject == null ? null : dozer.map(sourceObject, destObjectclazz);
    }

    public static <T, S> List<T> mapList(Collection<S> sourceList, Class<T> destObjectclazz) {
        if (sourceList == null) {
            return null;
        }
        List<T> destinationList = new ArrayList<T>();
        for (Iterator<S> it = sourceList.iterator(); it.hasNext(); ) {
            destinationList.add(map(it.next(), destObjectclazz));
        }
        return destinationList;
    }
}
