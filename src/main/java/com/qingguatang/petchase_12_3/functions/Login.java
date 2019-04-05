package com.qingguatang.petchase_12_3.functions;

import com.qingguatang.petchase_12_3.tables.SimpleService;
import com.qingguatang.petchase_12_3.tables.UsersBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class Login {
    @SuppressWarnings("rawtypes")
    @Resource
    private SimpleService simpleService;
    private UsersBean user=new UsersBean();

    @RequestMapping(value="/TestLogin")
    @ResponseBody
    public String testLogin(@RequestParam (name="uphone")String uphone,
                            @RequestParam(name = "upass")String upass){
//        String upass1=Md5.md5Decode16(upass);
        UsersBean obj= (UsersBean) simpleService.findById(user,Long.parseLong(uphone));
        if(obj.getUpass().equals(upass)){
            return "OK";
        }else{
            return "NOTOK";
        }
//        return obj.getUpass()+"";
    }
}
