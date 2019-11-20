package com.lq.config;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ElasticsearchProperties properties;

    @Bean(name = "transportClient")
    public TransportClient transportClient () {
        log.info("开始初始化Elasticsearch..................");
        TransportClient transportClient = null;

        try {
            Settings build = Settings.builder()
                    // 集群名称
                    .put("cluster.name", properties.getClusterName())
                    // 增加嗅探机制，找到ES集群
                    .put("client.transport.sniff", true)
                    // 增加线程池个数，暂时设为5
                    .put("thread_pool.search.size", properties.getPoolSize())
//                    .put("searchguard.ssl.transport.enabled", true)
                    .build();

            transportClient = new PreBuiltTransportClient(build);
            String host = properties.getHost();
            InetAddress byName = InetAddress.getByName(host);
            // 5.6.9
//            TransportAddress transportAddress = new InetSocketTransportAddress(byName, properties.getPort());
            // 6.8.0
            TransportAddress transportAddress = new TransportAddress(byName, properties.getPort());
            transportClient.addTransportAddress(transportAddress);
        } catch (Exception e) {
            log.error("初始化异常：{}", e.getMessage());
            e.printStackTrace();
        }
        log.info("初始化完毕Elasticsearch..................");
        return transportClient;
    }


//    Settings settings = Settings.builder()
//            .put("path.home", ".")
//            .put("path.conf", "E:\\workspace_idea\\es_test\\src\\main\\resources")
//            .put("cluster.name", "es-cluster")
//            .put("searchguard.ssl.transport.enabled", true)
//            .put("searchguard.ssl.transport.keystore_filepath", "sgadmin-keystore.jks")
//            .put("searchguard.ssl.transport.truststore_filepath", "truststore.jks")
//            .put("searchguard.ssl.http.keystore_password", "password")
//            .put("searchguard.ssl.http.truststore_password", "password")
//            .put("searchguard.ssl.transport.keystore_password", "password")
//            .put("searchguard.ssl.transport.truststore_password", "password")
//            .put("searchguard.ssl.transport.enforce_hostname_verification", false)
//            .build();
//
//    TransportClient client = new PreBuiltTransportClient(settings, SearchGuardSSLPlugin.class)
//            .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("ip-1"), 9300))
//            .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("ip-2"), 9300))
//            .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("ip-3"), 9300));



}
