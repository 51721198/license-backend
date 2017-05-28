package com.vico.license.controller;

import com.alibaba.fastjson.JSON;
import com.vico.license.pojo.ProcessResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: BounceController
 * @Description: 处理页面跳转
 * @author: Liu.Dun
 * @date: 2016年7月5日 下午5:38:47
 */

@Controller
@RequestMapping(value = "/")
@PropertySource("classpath:hosts.properties")
public class BounceController {

    @Autowired
    private Environment env;

    @RequestMapping(value = "/")
    @ResponseBody
    public String defaultPage(){
        ProcessResult result = new ProcessResult();
        result.setResultdesc(env.getProperty("master") + " : " + env.getProperty("masterpass"));
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "tocreatecode")
    public String toCreate(HttpServletRequest request) {
        return "creatcode";
    }

    @RequestMapping(value = "toshowallcodes")
    public String showAll(HttpServletRequest request, HttpServletResponse response) {
        return "showallcodes";
    }


    @RequestMapping(value = "toshowallhospital")
    public String hospital() {
        return "showallhospitals";
    }

    @RequestMapping(value = "toaddhospital")
    public String toAdd() {
        return "addhospital";
    }
}
