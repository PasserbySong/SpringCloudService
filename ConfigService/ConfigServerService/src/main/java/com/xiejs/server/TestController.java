package com.xiejs.server;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/5/19.
 */
@RestController
public class TestController {

    private final Logger logger = Logger.getLogger(getClass());

//    @Autowired
//    private DiscoveryClient client;

    @RequestMapping(value = "/add" ,method = RequestMethod.GET)
    public Integer add(@RequestParam Integer a, @RequestParam Integer b) {
//        ServiceInstance instance = client.getLocalServiceInstance();
//        Integer r = a + b;
        logger.info("addd");
        return 2342;
    }

}
