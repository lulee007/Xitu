package com.lulee007.xitu.services;

import com.lulee007.xitu.models.Account;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import rx.functions.Action1;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class AccountServiceTest {

    AccountService accountService = null;

    @Before
    public void setUp() throws Exception {
        accountService = new AccountService();
    }

    @After
    public void tearDown() throws Exception {
        accountService = null;
    }

    @Test
    public void testVerifyPhone() throws Exception {
        HashMap result=accountService.verifyPhone( 123)
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        fail();
                    }
                }).toBlocking().single();
        assertThat(result,not(null));
    }

    @Test
    public void testLogin() throws Exception {
        Account account=accountService.login("131222xxx56","xxxxx0")
                .toBlocking().single();
        assertThat(account.getObjectId(),equalTo("xxxxxxxb2c297d014f7d2"));
    }
}