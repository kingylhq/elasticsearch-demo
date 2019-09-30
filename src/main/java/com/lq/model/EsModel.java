package com.lq.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: liqian
 * @Date: 2019-09-29
 * @Time: 15:57
 */
@Data
@ToString
@NoArgsConstructor
public class EsModel {

    private String id;
    private String name;
    private int age;
    private Date date;


}
