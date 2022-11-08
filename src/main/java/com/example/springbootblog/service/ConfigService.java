package com.example.springbootblog.service;

import java.util.Map;

public interface ConfigService {

    int updateConfig(String configName, String configValue);

    Map<String, String> getAllConfigs();
}
