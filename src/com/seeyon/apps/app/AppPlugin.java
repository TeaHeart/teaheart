package com.seeyon.apps.app;

import com.seeyon.ctp.common.AbstractSystemInitializer;
import com.seeyon.ctp.common.AppContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppPlugin extends AbstractSystemInitializer {
    @Override
    public void destroy() {
        log.info("destroy()");
    }

    @Override
    public void initialize() {
        log.info("initialize()");
        log.info("app.rest-server {}", AppContext.getSystemProperty("app.rest-server"));
    }
}
