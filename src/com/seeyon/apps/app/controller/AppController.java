package com.seeyon.apps.app.controller;

import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.controller.BaseController;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.springframework.web.servlet.ModelAndView;
import shade.okhttp3.OkHttpClient;
import shade.okhttp3.Request;
import shade.okhttp3.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AppController extends BaseController {
    private static final String url = "http://localhost:3000/api/sso/bind/seeyon/checkTicket";
    private final OkHttpClient client = new OkHttpClient();

    public ModelAndView save(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("apps/app/save");
    }

    @Override
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("apps/app/index");
    }

    @SneakyThrows
    public void bind(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String ticket = request.getParameter("ticket");
        Request req = new Request.Builder()
                .url(url + "?ticket=" + ticket + "&oaLoginName=" + AppContext.currentUserLoginName())
                .build();
        @Cleanup Response resp = client.newCall(req).execute();
        response.getWriter().write(resp.body().string());
    }
}
