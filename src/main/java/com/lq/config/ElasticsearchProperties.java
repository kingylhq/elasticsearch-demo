package com.lq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: liqian
 * @Date: 2019-10-31
 * @Time: 11:08
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticsearchProperties {

    private String host;

    // java端口9300，http端口9200，不要把 9200 端口放代码中去连接...
    private Integer port;

    private String clusterName;

    private Integer poolSize;


}
