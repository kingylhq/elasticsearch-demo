package com.lq.config;

import org.elasticsearch.client.Client;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;

/**
 * Created with IntelliJ IDEA.
 * Description:  注入ElasticsearchTemplate Bean
 *
 * @author: liqian
 * @Date: 2019-09-30
 * @Time: 16:52
 */
@Configuration
@ConditionalOnClass({Client.class, ElasticsearchTemplate.class})
@AutoConfigureAfter(ElasticsearchAutoConfiguration.class)
public class ElasticsearchTemplateConfig {

    @Bean

    @ConditionalOnMissingBean
    @ConditionalOnBean(Client.class)
    public ElasticsearchTemplate elasticsearchTemplate (Client client, ElasticsearchConverter converter) {
        return new ElasticsearchTemplate(client, converter);
    }
}
