package com.lulee007.xitu.services;

import com.lulee007.xitu.base.XTBaseService;
import com.lulee007.xitu.models.Account;

import java.util.HashMap;

import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

/**
 * User: lulee007@live.com
 * Date: 2015-12-31
 * Time: 13:58
 */
public class AccountService extends XTBaseService<AccountService.AccountWebService> {

    public AccountService() {
        super(AccountWebService.class);
    }

    protected interface AccountWebService {
        @POST("/verifyMobilePhone/{code}")
        Observable<HashMap> verifyPhone(@Body HashMap body, @Path("code") int code);

        @POST("/login")
        Observable<Account> login(@Body HashMap body);

    }

    public Observable<HashMap> verifyPhone( int code){
        return webService.verifyPhone(new HashMap(),code);
    }

    public Observable<Account> login(String phone,String pwd){
        HashMap<String,String> body=new HashMap<>();
        body.put("mobilePhoneNumber",phone);
        body.put("password",pwd);
        return webService.login(body);
    }

}
