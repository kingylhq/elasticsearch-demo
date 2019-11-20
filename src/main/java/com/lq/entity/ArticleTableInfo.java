package com.lq.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:  早报文章 实体类
 *
 * @author: liqian
 * @Date: 2019-03-26
 * @Time: 15:15
 */
@Data
@Document(collection = "c_article")
public class ArticleTableInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Id
    private String id;

    /**
     * 文章标题
     */
    @Field("article_title")
    private String articleTitle;

    /**
     * 文章分类
     */
    @Field("article_type")
    private ObjectId articleType;

    /************************新增字段开始****************************/

    /**
     * 渠道id
     */
    @Field("channel_id")
    private Integer[] channelIds;

    /**
     * 微信文章 来源
     */
    @Field("weixin_name")
    private String weixinName;

    /**
     * 微信账户id
     */
    @Field("weixin_account")
    private String weixinAccount;

    /**
     * 微信账户名称
     */
    @Field("weixin_summary")
    private String weixinSummary;

    /**
     * 微信喜欢总量
     */
    @Field("like_num")
    private Long likeNum;

    /**
     *  微信文章url
     */
    @Field("url")
    private String url;

    /**
     *  来源，OUT：外部，内部：INSIDE
     */
    @Field("source")
    private String source;

    /**
     *  表态子项id(存数组，两个id)
     */
    @Field("declare")
    private Long[] declare;

    /**
     *  表态父id
     */
    @Field("declareId")
    private Long declareId;

    /************************新增字段结束****************************/

    /**
     * 文章头图
     */
    @Field("article_picture_url")
    private String articlePictureUrl;

    /**
     * 文章来源
     */
    @Field("article_source")
    private String articleSource;

    /**
     * 文章内容
     */
    @Field("article_content")
    private String articleContent;

    /**
     * 文章摘要
     */
    @Field("article_abstract")
    private String articleAbstract;

    /**
     * 热词
     */
    @Field("hot_words")
    private String[] hotWords;

    /**
     * 是否推荐(1:是，0:否),默认不推荐，若推荐，则展示在tab中
     */
    @Field("is_recommend")
    private Integer isRecommend;

    /**
     * 是否发布(1:是，0:否)，默认为发布，若为发布，前端则展示，未发布，前端不展示
     */
    @Field("is_publish")
    private Integer isPublish;

    /**
     * 是否删除(1:是，0:否)，新增默认为0，删除后列表不显示
     */
    @Field("is_delete")
    private Integer isDelete;

    /**
     * 分享总量
     */
    @Field("total_share")
    private Long totalShare;

    /**
     * 阅读总量
     */
    @Field("total_reading")
    private Long totalReading;

    /**
     * 模拟阅读量 数量
     */
    @Field("simulation_total_reading")
    private Long simulationTotalReading;

    /**
     * 阅读时长
     */
    @Field("reading_duration")
    private String readingDuration;

    /**
     * 发布时间
     */
    @Field("publish_time")
    private Date publishTime;

    /**
     * 下架时间
     */
    @Field("off_shelf_time")
    private Date offShelfTime;

    /**
     * 文章创建时间
     */
    @Field("create_time")
    private Date createTime;

    public String getArticleType() {
        if (articleType != null) {
            return articleType.toString();
        }
        return null;
    }
}
