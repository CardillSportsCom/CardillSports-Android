package com.cardill.sports.stattracker.common.auth;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by vithushan on 10/16/18.
 */


public interface AuthService {
    @POST("auth")
    Observable<AuthResponseBody> authenticate(@Body AuthRequestBody token);
}
