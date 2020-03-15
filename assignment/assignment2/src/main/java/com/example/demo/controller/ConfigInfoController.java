package com.example.demo.controller;

import com.example.demo.config.ConfigInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ConfigInfoController {

    @Value("${boot.province}")
    private String province;

    @Value("${boot.location}")
    private String location;

    @Autowired
    private ConfigInfo configInfo;

    @RequestMapping("/boot/location")
    public @ResponseBody String config() {
        return province + location + "=" + configInfo.getProvince() + configInfo.getLocation();
    }
}
