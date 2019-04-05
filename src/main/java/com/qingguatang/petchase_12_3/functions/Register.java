package com.qingguatang.petchase_12_3.functions;

import com.qingguatang.petchase_12_3.tables.SimpleService;
import com.qingguatang.petchase_12_3.tables.UsersBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class Register {

    @SuppressWarnings("rawtypes")
    @Resource
    private SimpleService simpleService;
    private UsersBean user = new UsersBean();

    @RequestMapping(value = "/register")
    @ResponseBody
    public String register(@RequestParam(name = "uphone", required = true) String uphone,
                           @RequestParam(name = "upass", required = true) String upass) {
        user.setUid(Long.parseLong(uphone));
        user.setUname(uphone);
        user.setUphone(uphone);
        user.setUpass(upass);
        if (simpleService.insert(user)) {
            return "RegisterOK";
        } else {
            return "RegisterFailed";
        }
    }
}
