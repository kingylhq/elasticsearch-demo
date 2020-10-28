package com.lq.controller;

import com.alibaba.fastjson.JSONObject;
import com.lq.entity.Article;
import com.lq.entity.ArticleTableInfo;
import com.lq.utils.ElasticsearchUtil;
import com.lq.utils.UUIDUtil;
import com.lq.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: liqian
 * @Date: 2019-09-27
 * @Time: 17:16
 */

@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController {


    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * 查询文章
     *
     * @return
     */
    @PostMapping("/getArticle")
    public R getArticle() {


        return R.ok("very good");
    }


}
