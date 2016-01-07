package com.lulee007.xitu.services;

import com.google.gson.internal.LinkedTreeMap;
import com.lulee007.xitu.models.Account;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import retrofit.RetrofitError;
import rx.functions.Action1;
import rx.functions.Func1;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class CommonSaveServiceTest {

    private CommonSaveService commonSaveService;

    @Test
    public void saveSubscription() throws Exception {
        Object o = commonSaveService.saveSubscription("5597a04de4b0xxxx8a686ce56e63")
                .map(new Func1<Object, Object>() {
                    @Override
                    public Object call(Object o) {
                        return o;
                    }
                }).toBlocking().single();
        assertThat(o.toString(), equalTo("{}"));
    }


    @Before
    public void setUp() throws Exception {
        commonSaveService = new CommonSaveService();
    }

    @After
    public void tearDown() throws Exception {
        commonSaveService = null;
    }

    @Test
    public void testSaveCollectEntry() throws Exception {
        Object o = commonSaveService.saveCollectEntry("567a1c8xxxf00b06f9f616f5134")
                .map(new Func1<Object, Object>() {
                    @Override
                    public Object call(Object o) {
                        return o;
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        System.out.println("on error");
                        throwable.printStackTrace();
                        fail();
                    }
                })
                .toBlocking().single();
        assertThat(o.toString(), not(equalTo("{}")));
    }

    @Test
    public void testSaveRegisterPhone() throws Exception {
        Account result = commonSaveService.saveRegisterPhone("13122211244", "21211111", "3")
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        try {
                            String result = inputStream2String(((RetrofitError) throwable).getResponse().getBody().in());
                            JSONObject jsonObject = new JSONObject(result);
                            System.out.println(result);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        fail();
                    }
                })
                .toBlocking().first();
        assertThat(result, not(equalTo(null)));
    }

    public static String inputStream2String(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = -1;
        while ((i = is.read()) != -1) {
            baos.write(i);
        }
        return baos.toString();
    }
}