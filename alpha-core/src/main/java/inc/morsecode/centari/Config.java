package inc.morsecode.centari;

import inc.morsecode.centari.data.FormStore;
import inc.morsecode.web.resource.FileFormStore;
import org.apache.curator.RetryPolicy;
import org.apache.curator.RetrySleeper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by morsecode on 7/16/2017.
 */
@ComponentScan(
{"inc.morsecode.centari"
,"inc.morsecode.web.resource"
,"inc.morsecode.web.service"
,"inc.morsecode.web.control"
,"inc.morsecode.web.security"
,"inc.morsecode.web.helper"
})
@Configuration
@EnableAutoConfiguration
public class Config extends WebMvcConfigurerAdapter {

    private static final Logger LOG= LoggerFactory.getLogger(Config.class);

    @Autowired
    private FileFormStore formStore;

    @Autowired
    public ViewResolver viewResolver;
    @Bean
    public FormStore formStore() {
        return formStore;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(viewResolver);
    }

    @Bean(name="zookeeper")
    public CuratorFramework zookeeper() {

        RetryPolicy retryPolicy= new RetryPolicy() {
            @Override
            public boolean allowRetry(int i, long l, RetrySleeper retrySleeper) {
                return i < 5;
            }
        };
        CuratorFramework zk= CuratorFrameworkFactory.newClient("zk11.hdfs,zk12.hdfs,zk13.hdfs", 3000, 9000, retryPolicy);
        return zk;
    }


}
