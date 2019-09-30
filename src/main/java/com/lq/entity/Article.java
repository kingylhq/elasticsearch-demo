package com.lq.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: liqian
 * @Date: 2019-09-27
 * @Time: 17:17
 */
@Data
@Accessors(chain = true)
@Document(indexName = "springboot_elasticsearch_article", type = "com.lq.entity.Article")
public class Article implements Serializable {

    private static final long serialVersionUID = -5087658155687251393L;

    // 文章id
    private String id;

    // 文章标题
    private String title;

    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;


}
