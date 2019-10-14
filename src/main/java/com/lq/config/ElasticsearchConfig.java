package com.lq.config;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;

/**
 * Created with IntelliJ IDEA.
 * Description: ElasticsearchConfig 配置
 *
 * @author: liqian
 * @Date: 2019-09-27
 * @Time: 17:18
 */
@Slf4j
@Configuration
public class ElasticsearchConfig {

//    private String hostName = "192.168.0.113";
    private String hostName = "localhost";

//    java端口9300，http端口9200，不要把 9200 端口放代码中去连接...
    private String port = "9300";

    private String clusterName = "my-application" ;

    private Integer poolSize = 5;

    @Bean(name = "transportClient")
    public TransportClient transportClient () {
        log.info("开始初始化Elasticsearch..................");
        TransportClient transportClient = null;

        try {
            Settings build = Settings.builder()
                    // 集群名称
                    .put("cluster.name", clusterName)
                    // 增加嗅探机制，找到ES集群
                    .put("client.transport.sniff", true)
                    // 增加线程池个数，暂时设为5
                    .put("thread_pool.search.size", poolSize)
                    .build();

            transportClient = new PreBuiltTransportClient(build);
            TransportAddress transportAddress = new TransportAddress(InetAddress.getByName(hostName), Integer.valueOf(port));
            transportClient.addTransportAddress(transportAddress);
        } catch (Exception e) {
            log.error("初始化异常：{}", e.getMessage());
            e.printStackTrace();
        }
        log.info("初始化完毕Elasticsearch..................");
        return transportClient;
    }


}
