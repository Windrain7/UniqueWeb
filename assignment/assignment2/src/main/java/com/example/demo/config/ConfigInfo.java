package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "boot")
public class ConfigInfo {
    private String province;
    private String location;

    public void setProvince(String province) {
        this.province = province;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProvince() {
        return province;
    }

    public String getLocation() {
        return location;
    }
}
