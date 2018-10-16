package com.cardill.sports.stattracker.user;

import com.cardill.sports.stattracker.AuthService;
import com.cardill.sports.stattracker.network.CardillService;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import timber.log.Timber;

/**
 * Created by vithushan on 10/16/18.
 */

public class UnauthorizedInterceptor implements Interceptor {
    private Session session;
    private AuthService cardillService;

    public UnauthorizedInterceptor(Session session, AuthService cardillService) {
        this.session = session;
        this.cardillService = cardillService;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response mainResponse = chain.proceed(chain.request());
        Request mainRequest = chain.request();

        if (mainResponse.code() == 401 || mainResponse.code() == 403) {
            //session.invalidate();

            FirebaseAuth.getInstance()
                    .getCurrentUser()
                    .getIdToken(true)
                    .addOnCompleteListener(task -> {
                        String tokenString = task.getResult().getToken();
                        cardillService.authenticate(new AuthRequestBody(tokenString))
                                .map(authResponseBody -> {
                                    Request.Builder builder = mainRequest.newBuilder()
                                            .header("Authorization", authResponseBody.getId_token())
                                            .method(mainRequest.method(), mainRequest.body());

                                    return chain.proceed(builder.build());
                                })
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(response -> Timber.tag("VITHUSHAN").d(response.body().string()),
                                        throwable -> Timber.tag("VITHUSHAN").e(throwable));
                    });


        }
        return mainResponse;
    }
}
