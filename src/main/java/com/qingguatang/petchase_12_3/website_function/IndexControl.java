package com.qingguatang.petchase_12_3.website_function;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller//告诉spring这是一个webBean
public class IndexControl {

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }


    @RequestMapping(value = "index2")
    public String index2() {
        return "index2";
    }

    @RequestMapping(value = "AboutUs")
    public String aboutUs(){
        return "AboutUs";
    }
}
