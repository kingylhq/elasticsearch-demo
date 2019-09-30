package com.lq.config;

import org.elasticsearch.client.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

/**
 * Created with IntelliJ IDEA.
 * Description:  注入ElasticsearchTemplate Bean
 *
 * @author: liqian
 * @Date: 2019-09-30
 * @Time: 16:52
 */
@Configuration
public class ElasticsearchTemplateConfig {

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate (Client client) {
        return new ElasticsearchTemplate(client);
    }
}
