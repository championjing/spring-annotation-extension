package com.example.demo;

import com.rongzhiweilai.extension.annotation.JSONParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : championjing
 * @ClassName: TestController
 * @Description: TODO
 * @Date: 3/11/2019 11:31 AM
 */
@RestController
public class TestController {
    
    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public String test(@JSONParam String str){
        System.out.println("str:"+str);
        return str;
    }
}
