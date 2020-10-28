package com.lq.test;

import java.util.*;

/**
 * 鸟欲高飞先振翅
 * Created with IntelliJ IDEA.
 * Description: HashMap 4种 遍历方式
 *
 * @author: liqian
 * @email 857264134@qq.com
 * @Date: 2019-11-19
 * @Time: 18:36
 * 人求上进先读书
 */
public class MapTest {
    public static void main(String[] args) {
        //   Map.entrySet() 这个方法返回的是一个Set<Map.Entry<K,V>>，Map.Entry 是Map中的一个接口，
        //   他的用途是表示一个映射项（里面有Key和Value），而Set<Map.Entry<K,V>>表示一个映射项的Set。
        //   Map.Entry里有相应的getKey和getValue方法，即JavaBean，让我们能够从一个项中取出Key和Value。
        Map<String, String> map = new HashMap<>(3);
        map.put("1", "value1");
        map.put("2", "value2");
        map.put("3", "value3");

        List<String> list = new ArrayList(20);

        // 第一种：普遍使用，二次取值
        System.out.println("通过Map.keySet遍历key和value：");
        for (String key : map.keySet()) {
            System.out.println("key= " + key + " and value= " + map.get(key));
        }

        // 第二种
        System.out.println("通过Map.entrySet使用iterator遍历key和value：");
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }

        // 第三种：推荐，尤其是容量大时
        System.out.println("通过Map.entrySet遍历key和value");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }

        // 第四种
        System.out.println("通过Map.values()遍历所有的value，但不能遍历key");
        for (String v : map.values()) {
            System.out.println("value= " + v);
        }
    }
}
