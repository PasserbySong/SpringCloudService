package com.xiejs.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Administrator on 2017/5/19.
 */
@Service
public class TestService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "addFailureCallback")
    public String add(){
        return restTemplate.getForObject("http://COMPUTE-SERVICE/add?a=10&b=20", String.class);
    }

    public String addFailureCallback(){
        return "error";
    }

}
