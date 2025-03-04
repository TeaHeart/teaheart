package com.seeyon.apps.app.controller;

import com.seeyon.ctp.common.controller.BaseController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AppController extends BaseController {
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("apps/app/save");
    }

    @Override
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("apps/app/index");
    }
}
