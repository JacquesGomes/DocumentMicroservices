package org.example.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Log1Controller {


    //Simple logging facade for Java
    private static final Logger log =
            LoggerFactory.getLogger(Log1Controller.class);

    @Autowired
    protected Environment environment = null;

    @GetMapping("log1")
    public void writeToConsole(){
        log.info("Log 1, get mapping");
    }

    @GetMapping("log1/{usernumber}")
    public void writeNumberToConsole(@PathVariable Long usernumber){
        log.info("Log 1, get mapping, user number: " + usernumber);
        usernumber += 333;
        log.debug("Log 1, get mapping, user number after adding: " + usernumber);
        log.warn("Warning message");
        log.error("Error message");

    }

    @GetMapping("log1/config")
    public void writeConfigToConsole(){
        String configValue = "";
        configValue = environment.getProperty("org.example.controller");

        log.info("Log 1, config value: " + configValue);
    }





}
