package com.spring.springsecuritytest.service;

import org.springframework.stereotype.Component;

/**
 * @author ZQ
 * @create 2020-05-16
 */
@Component
public class TestGit {
    public String testGit() {
        return "hello git";

    }

    public String testGitPushToZQ() {
        return "git-pushto-zq";
    }

    public String test() {
        return "zq";

    }
}
