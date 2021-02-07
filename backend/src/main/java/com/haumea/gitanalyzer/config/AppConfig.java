package com.haumea.gitanalyzer.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.gitlab4j.api.GitLabApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

    @Value("http://cmpt373-1211-11.cmpt.sfu.ca/gitlab")
    String hostUrl;

    @Bean
    @Scope(value = "prototype")
    public GitLabApi getGitLabApi(String personalAccessToken){
        return new GitLabApi(hostUrl, personalAccessToken);
    }
}
