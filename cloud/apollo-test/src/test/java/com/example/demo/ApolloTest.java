package com.example.demo;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;


/**
 * 适应于 Quick Start docker 项目
 *
 *      因为 docker 的 config service 注册的是 内部 ip ，没有办法在外边访问
 *      所以直接 配置的 config service 地址
 *
 *  一个 Config 就代表一个 命名空间
 *
 */
public class ApolloTest {

    @Before
    public void before() {
        System.setProperty("app.id", "SampleApp");
        System.setProperty("apollo.configService", "http://localhost:8080");
    }

    /**
     * 根据 key 获取 配置
     */
    @Test
    public void test01() {
        Config config = ConfigService.getAppConfig();
        String value = config.getProperty("timeout", "200");
        System.out.println(value);
    }

    /**
     * 获取指定的命名空间，可以是本应用下的，也可以是公共的
     */
    @Test
    public void test02() {
        Config config = ConfigService.getConfig("abc");
        String value = config.getProperty("cc", "bb");
        System.out.println(value);
    }

    /**
     * 监听 配置的 变化, 监听的 目标 是 一个 命名空间
     */
    @Test
    public void test03() throws InterruptedException {
        Config config = ConfigService.getAppConfig();

        //监听 命名空间的 变化
        config.addChangeListener(new ConfigChangeListener() {
            @Override
            public void onChange(ConfigChangeEvent changeEvent) {

                System.out.println("Changes for namespace " + changeEvent.getNamespace());
                for (String key : changeEvent.changedKeys()) {
                    ConfigChange change = changeEvent.getChange(key);
                    System.out.println(String.format("Found change - key: %s, oldValue: %s, newValue: %s, changeType: %s", change.getPropertyName(), change.getOldValue(), change.getNewValue(), change.getChangeType()));
                }
            }
        });

//        Thread.sleep(20); junit 测试线程不支持这个睡眠方式
        TimeUnit.SECONDS.sleep(300);
    }
}
