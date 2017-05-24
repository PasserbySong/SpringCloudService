package com.xiejs.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Administrator on 2017/5/19.
 */
@RestController
@RefreshScope
public class TestController {
    @Value("${foo}")
    String bar;

    @Autowired
    private TestService testService;

    @RequestMapping("/")
    String hello() {
        return "Hello " + bar + "!";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return testService.add();
    }

}
