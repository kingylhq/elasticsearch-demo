package com.lq.vo;

import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: liqian
 * @Date: 2019-09-27
 * @Time: 17:18
 */
@Data
public class ArticleVO {

    private String id;

    private String title;

    private String articleSource;

    private Date createTime;

}
