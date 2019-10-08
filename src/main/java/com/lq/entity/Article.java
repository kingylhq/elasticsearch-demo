package com.lq.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

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
@Document(indexName = "index_article", type = "article")
public class Article implements Serializable {

    private static final long serialVersionUID = -5087658155687251393L;

    // 文章id
    @Id
    private String id;

    // 文章标题
    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String articleSource;

    // 创建时间
//    @Field(type = FieldType.Date, format = DateFormat.basic_date)
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Long createTime;


}
