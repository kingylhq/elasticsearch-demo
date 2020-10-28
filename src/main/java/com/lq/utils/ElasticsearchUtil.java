package com.lq.utils;

import com.alibaba.fastjson.JSONObject;
import com.lq.model.ElasticsearchPage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: liqian
 * @Date: 2019-09-29
 * @Time: 14:26
 */

@Slf4j
@Data
@Component
public class ElasticsearchUtil {

    @Autowired(required = false)
    private TransportClient transportClient;

    private static TransportClient client;

    @PostConstruct
    public void init() {
        client = this.transportClient;
    }

    /**
     * 判断索引是否存在
     *
     * @param index 索引
     * @return
     */
    public static Boolean checkIndexExists(String index) {
        // 获取返回数据
        IndicesExistsResponse response = client.admin().indices().exists(new IndicesExistsRequest(index)).actionGet();
        boolean exists = response.isExists();
        if (exists) {
            log.info("索引 :{} 已经存在", index);
        }
        return exists;
    }

    /**
     * 创建索引(类似mysql数据库)
     *
     * @param index
     * @return
     */
    public static Boolean createIndex(String index) {
        if (checkIndexExists(index)) {
            return false;
        }
        CreateIndexResponse response = client.admin().indices().prepareCreate(index).execute().actionGet();
        boolean acknowledged = response.isAcknowledged();
        log.info("索引：{}创建成功：{}", index, acknowledged);
        return acknowledged;
    }

    /**
     * 删除索引
     *
     * @param index
     * @return
     */
    public static Boolean deleteIndex(String index) {
        if (checkIndexExists(index)) {
            log.info("删除索引失败......");
            return false;
        }
        AcknowledgedResponse acknowledgedResponse = client.admin().indices().prepareDelete(index).execute().actionGet();
        boolean acknowledged = acknowledgedResponse.isAcknowledged();
        log.info("删除索引：{} 成功？：{}", index, acknowledged);
        return acknowledged;
    }

    /**
     * 判断指定type在指定索引下是否存在
     *
     * @param index
     * @param type
     * @return
     */
    public static Boolean checkTypeExists(String index, String type) {
        return checkIndexExists(index) ? client.admin()
                .indices()
                .prepareTypesExists(index)
                .setTypes(type)
                .execute()
                .actionGet()
                .isExists() : false;
    }

    /**
     * 添加数据
     *
     * @param jsonObject 具体数据文档
     * @param index      索引
     * @param type       类型
     * @param id         id
     * @return
     */
    public static String addData(JSONObject jsonObject, String index, String type, String id) {
        IndexResponse response = client.prepareIndex(index, type, id).setSource(jsonObject).get();
        log.info("数据添加成功 : {}, id : {}", response.status().getStatus(), response.getId());
        return response.getId();
    }

    /**
     * 根据id值删除数据
     *
     * @param index
     * @param type
     * @param id
     */
    public static void deleteDataById(String index, String type, String id) {
        log.info("根据索引 ：{}， 类型 ：{}， id : {}删除数据", index, type, id);
        DeleteResponse response = client.prepareDelete(index, type, id).execute().actionGet();
        log.info("删除数据成功状态 ：{}", response.status().getStatus());
    }

    /**
     * 通过ID 更新数据
     *
     * @param jsonObject 要增加的数据
     * @param index      索引，类似数据库
     * @param type       类型，类似表
     * @param id         数据ID
     * @return
     */
    public static void updateDataById(JSONObject jsonObject, String index, String type, String id) {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(index).type(type).id(id).doc(jsonObject);
        client.update(updateRequest);
    }

    /**
     * 通过ID获取数据
     *
     * @param index  索引，类似数据库
     * @param type   类型，类似表
     * @param id     数据ID
     * @param fields 需要显示的字段，逗号分隔（缺省为全部字段）
     * @return
     */
    public static Map<String, Object> searchDataById(String index, String type, String id, String fields) {

        GetRequestBuilder getRequestBuilder = client.prepareGet(index, type, id);

        if (!StringUtils.isEmpty(fields)) {
            getRequestBuilder.setFetchSource(fields.split(","), null);
        }

        GetResponse getResponse = getRequestBuilder.execute().actionGet();
        return getResponse.getSource();
    }

    /**
     * 使用分词查询,并分页
     *
     * @param index          索引名称
     * @param type           类型名称,可传入多个type逗号分隔
     * @param pageNumber     当前页
     * @param pageSize       每页显示条数
     * @param query          查询条件
     * @param fields         需要显示的字段，逗号分隔（缺省为全部字段）
     * @param sortField      排序字段
     * @param highlightField 高亮字段
     * @return
     */
    public static ElasticsearchPage searchDataPage(String index, String type, Integer pageNumber, Integer pageSize,
                                                   QueryBuilder query, String fields, String sortField,
                                                   String highlightField) {

        // 构建查询请求
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index);
        if (!StringUtils.isEmpty(type)) {
            searchRequestBuilder.setTypes(type);
        }

        searchRequestBuilder.setSearchType(SearchType.QUERY_THEN_FETCH);

        // 需要显示的字段，逗号分隔，缺省为全部字段
        if (!StringUtils.isEmpty(fields)) {
            searchRequestBuilder.setFetchSource(fields.split(","), null);
        }
        // 排序字段，进行倒序
        if (!StringUtils.isEmpty(sortField)) {
            searchRequestBuilder.addSort(sortField, SortOrder.DESC);
        }
        // 高亮
        if (!StringUtils.isEmpty(highlightField)) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            // 设置高亮字段
            highlightBuilder.field(highlightField);
            // 将对象highlightBuilder添加到 searchRequestBuilder
            searchRequestBuilder.highlighter(highlightBuilder);
        }
//        searchRequestBuilder.setQuery(QueryBuilders.matchAllQuery());
        searchRequestBuilder.setQuery(query);

        // 分页、求偏移量(从哪个位置开始)
        Integer offect = (pageNumber - 1) * pageSize;
        searchRequestBuilder.setFrom(offect).setSize(pageSize);
        // 设置是否按查询匹配度排序
        searchRequestBuilder.setExplain(true);

        // 打印内容，可以在Elasticsearch head 和kibana执行
        log.info("\n\r :{} ", searchRequestBuilder);

        // 执行搜索,返回响应结果
        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
        // 数据总数
        long totalHits = searchResponse.getHits().getTotalHits();
        // 返回的条数
        int length = searchResponse.getHits().getHits().length;
        log.info("共查询到【{}】条数据,处理数据条数【{}】", totalHits, length);

        if (searchResponse.status().getStatus() == 200) {
            // 处理数据
            List<Map<String, Object>> mapList = setSearchResponse(searchResponse, highlightField);
            return new ElasticsearchPage(pageNumber, pageSize, (int) totalHits, mapList);
        }
        return null;
    }

    /**
     * 使用分词查询
     *
     * @param index          索引名称
     * @param type           类型名称,可传入多个type逗号分隔
     * @param query          查询条件
     * @param size           文档大小限制
     * @param fields         需要显示的字段，逗号分隔（缺省为全部字段）
     * @param sortField      排序字段
     * @param highlightField 高亮字段
     * @return
     */
    public static List<Map<String, Object>> searchListData(String index, String type, QueryBuilder query, Integer size,
                                                           String fields, String sortField, String highlightField) {

        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index);
        if (!StringUtils.isEmpty(type)) {
            searchRequestBuilder.setTypes(type);
        }

        if (!StringUtils.isEmpty(highlightField)) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            // 设置高亮字段
            highlightBuilder.field(highlightField);
            searchRequestBuilder.highlighter(highlightBuilder);
        }

        searchRequestBuilder.setQuery(query);

        if (!StringUtils.isEmpty(fields)) {
            searchRequestBuilder.setFetchSource(fields.split(","), null);
        }
        searchRequestBuilder.setFetchSource(true);

        // 排序字段，倒序
        if (!StringUtils.isEmpty(sortField)) {
            searchRequestBuilder.addSort(sortField, SortOrder.DESC);
        }

        if (size != null && size > 0) {
            searchRequestBuilder.setSize(size);
        }

        //打印的内容 可以在 Elasticsearch head 和 Kibana  上执行查询
        log.info("\n{}", searchRequestBuilder);

        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();

        long totalHits = searchResponse.getHits().getTotalHits();
        long length = searchResponse.getHits().getHits().length;

        log.info("共查询到【{}】条数据, 处理数据条数【{}】", totalHits, length);

        if (searchResponse.status().getStatus() == 200) {
            // 解析对象
            return setSearchResponse(searchResponse, highlightField);
        }
        return null;

    }

    /**
     * 高亮结果集 特殊处理
     *
     * @param searchResponse
     * @param highlightField
     */
    private static List<Map<String, Object>> setSearchResponse(SearchResponse searchResponse, String highlightField) {
        List<Map<String, Object>> sourceList = new ArrayList<>();
        StringBuffer stringBuffer = new StringBuffer();

        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            searchHit.getSourceAsMap().put("id", searchHit.getId());

            if (!StringUtils.isEmpty(highlightField)) {

                log.info("遍历 【高亮结果集】，覆盖 【正常结果集】 :{}", searchHit.getSourceAsMap());
                Text[] text = searchHit.getHighlightFields().get(highlightField).getFragments();

                if (text != null) {
                    for (Text str : text) {
                        stringBuffer.append(str.string());
                    }
                    //遍历 高亮结果集，覆盖 正常结果集
                    searchHit.getSourceAsMap().put(highlightField, stringBuffer.toString());
                }
            }
            sourceList.add(searchHit.getSourceAsMap());
        }
        return sourceList;
    }
}
