package com.cardill.sports.stattracker.common.auth;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by vithushan on 10/16/18.
 */

public class AuthorizationInterceptor implements Interceptor {
    private Session session;

    public AuthorizationInterceptor(Session session) {
        this.session = session;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request authorizedRequest = request.newBuilder()
                .addHeader("Authorization", session.getToken())
                .build();

        return chain.proceed(authorizedRequest);
    }
}
