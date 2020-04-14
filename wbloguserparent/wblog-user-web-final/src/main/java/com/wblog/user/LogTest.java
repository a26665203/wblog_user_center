package com.wblog.user;

import com.wblog.user.controller.TestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;

public class LogTest {
    @Autowired
    TestController testController;
    private final static Logger logger = LoggerFactory.getLogger(LogTest.class);
    public static void main(String[] args){
        LogTest  logTest = new LogTest();
        logger.info("别啊");
    }
}
