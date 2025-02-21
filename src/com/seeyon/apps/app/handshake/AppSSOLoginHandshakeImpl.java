package com.seeyon.apps.app.handshake;

import com.seeyon.ctp.portal.sso.SSOLoginHandshakeAbstract;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import shade.okhttp3.OkHttpClient;
import shade.okhttp3.Request;
import shade.okhttp3.Response;

@Slf4j
public class AppSSOLoginHandshakeImpl extends SSOLoginHandshakeAbstract {
    private final OkHttpClient client = new OkHttpClient();

    @SneakyThrows
    @Override
    public String handshake(String ticket) {
        Request request = new Request.Builder()
                .url(getUrl() + "?ticket=" + ticket)
                .build();
        @Cleanup Response response = client.newCall(request).execute();
        return response.body().string(); // oaLoginName
    }

    @SneakyThrows
    @Override
    public void logoutNotify(String ticket) {
        Request request = new Request.Builder()
                .url(getLogoutUrl() + "?ticket=" + ticket)
                .build();
        @Cleanup Response response = client.newCall(request).execute();
        String result = response.body().string();
    }
}
