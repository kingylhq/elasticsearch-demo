package com.lq.controller;

import com.alibaba.fastjson.JSONObject;
import com.lq.entity.Article;
import com.lq.model.ElasticsearchPage;
import com.lq.repository.ArticleRepository;
import com.lq.utils.ElasticsearchUtil;
import com.lq.utils.UUIDUtil;
import com.lq.vo.ArticleVO;
import com.lq.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
@RequestMapping("/es")
public class ElasticsearchController {

    // 测试索引（数据库）
    private String indexName = "index_article";

    // 测试类型（表）
    private String type = "article";

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

//    @Resource
//    private ArticleRepository articleRepository;

    /**
     * 添加索引
     * @return
     */
    @PostMapping("/createIndex")
    public R createIndex (@RequestParam String index) {
//        Boolean index = ElasticsearchUtil.createIndex(indexName);
//        boolean b = elasticsearchTemplate.createIndex(index);

        elasticsearchTemplate.createIndex(Article.class);
        return R.ok(null);
    }

    /**
     * 删除索引
     * @return
     */
    @PostMapping("/deleteIndex")
    public R deleteIndex (@RequestParam String index) {
        boolean b = elasticsearchTemplate.deleteIndex(index);
        return R.ok(b);
    }

    /**
     * 删除索引
     * @return
     */
    @GetMapping("/getMapping")
    public R getMapping (@RequestParam String index, @RequestParam String type) {
        Map<String, Object> map = elasticsearchTemplate.getMapping(index, type);
        return R.ok(map);
    }

    /**
     * 保存数据
     * @RequestBody ArticleVO model
     * @return
     */
    @PostMapping("/save")
    public R save (@RequestBody ArticleVO model) {

        Article article = new Article();
        String salt = UUIDUtil.salt(32);
        article.setId(salt);
        article.setTitle(model.getTitle());
        article.setArticleSource(model.getArticleSource());
        article.setCreateTime(new Date().getTime());
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSON(article).toString());
        log.info("添加数据: {} ", jsonObject);
        String s = ElasticsearchUtil.addData(jsonObject, indexName, type, salt);

//        boolean b = elasticsearchTemplate.putMapping("index_article", "article", jsonObject);
        return R.ok(null);
    }

    /**
     * 根据id数据
     * @param id，
     * @return
     */
    @GetMapping("/info/{id}")
    public R getData(@PathVariable(name = "id") String id) {
        log.info("根据id：{}查询数据", id);
        if (StringUtils.isEmpty(id)) {
            return R.error(1, "参数不能为空");
        }
        Map<String, Object> map = ElasticsearchUtil.searchDataById(indexName, type, id, null);
        return R.ok(map);
    }

    /**
     * 查询数据
     * 模糊查询
     * @param articleVO
     * @return
     */
    @PostMapping("/query")
    public R query (@RequestBody ArticleVO articleVO) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
//        范围查询
//        queryBuilder.must(QueryBuilders.rangeQuery("createTime").from("").to(""));
//        不进行分词搜索
//        boolQueryBuilder.must(QueryBuilders.matchPhraseQuery("title", articleVO.getTitle()));
        queryBuilder.must(QueryBuilders.matchQuery("title", articleVO.getTitle()));
        List<Map<String, Object>> mapList = ElasticsearchUtil.searchListData(indexName, type, queryBuilder, 10,
                "title,articleSource", null, "title");
        return R.ok(mapList);
    }
    /**
     * 通配符查询数据
     * 通配符查询 ? 用来匹配1个任意字符，* 用来匹配零个或者多个字符
     *
     * @return
     */
    @PostMapping("/queryWildcardData")
    public R queryWildcardData(@RequestBody ArticleVO articleVO) {
        QueryBuilder queryBuilder = QueryBuilders.wildcardQuery("title.keyword", "*"+articleVO.getTitle()+"*");

        List<Map<String, Object>> mapList = ElasticsearchUtil.searchListData(indexName, type, queryBuilder, 10,
                "title,articleSource", null, null);
        return R.ok(mapList);
    }

    /**
     * 分页查询
     * @param jsonObject
     * @return
     */
    @RequestMapping("/queryPage")
    public R queryPage(@RequestBody JSONObject jsonObject) {

//        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
//        long start = 1569772800L;
//        long end = 1569855600L;
//        boolQuery.must(QueryBuilders.rangeQuery("createTime").from(start)
//                .to(end));
        String source = jsonObject.getString("articleSource");
        QueryBuilder queryBuilder = QueryBuilders.wildcardQuery("articleSource.keyword", "*"+source+"*");
        ElasticsearchPage list = ElasticsearchUtil.searchDataPage(indexName, type, jsonObject.getInteger("pageNumber"),
                jsonObject.getInteger("pageSize"), queryBuilder, null, null, null);

        return R.ok(list);
    }








}
