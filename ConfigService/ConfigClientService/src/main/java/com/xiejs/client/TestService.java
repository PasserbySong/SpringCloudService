package com.xiejs.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * Created by Administrator on 2017/5/19.
 */
@Service
public class TestService {

    private Logger logger= LoggerFactory.getLogger(TestService.class);

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "addFailureCallback")
    public String add(){

        logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        return restTemplate.getForObject("http://COMPUTE-SERVICE/add?a=10&b=20", String.class);
    }

    @Retryable(value = Exception.class,maxAttempts = 3,backoff = @Backoff(value = 1000l))
    public String test(){
        logger.trace("I am trace log.");
        logger.debug("I am debug log.");
        logger.warn("I am warn log.");
        logger.error("I am error log.");
        logger.info("**********");
        try{
            return restTemplate.getForObject("http://COMPUTE-SERVICE/add?a=10&b=20", String.class);
        }catch(Exception e){
            throw e;
        }
    }

    @Recover
    public String error(){
        return "error";
    }


    public String addFailureCallback(){
        return "error";
    }

}
