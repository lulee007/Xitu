package com.lulee007.xitu.base;

import com.lulee007.xitu.BuildConfig;
import com.lulee007.xitu.util.LeanCloudMD5Util;
import com.squareup.okhttp.OkHttpClient;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * 网络请求：T 为自定义的webservice接口
 * BaseDataEnvelope里的T1 为返回数据的类型如Tag，List<Author>
 * User: lulee007@live.com
 * Date: 2015-12-08
 * Time: 09:26
 */
public abstract class XTBaseService<T> {

    public final static String BASE_URL = "https://api.leancloud.cn/1.1";

    protected T webService;

    protected RestAdapter restAdapter;

    public XTBaseService(Class<T> cls) {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("X-LC-Prod",BuildConfig.DEBUG ? "0" : "1");
                request.addHeader("X-LC-Session","");
                request.addHeader("X-LC-Id", BuildConfig.AVOSCloud_App_Id);
                request.addHeader("Accept", "application/json");
                request.addHeader("User-Agent", "AVOS Cloud android-v3.13.8 SDK");
                request.addHeader("X-LC-Sign", signRequest());
                request.addHeader("X-Android-RS","1");
                request.addHeader("Content-Type", "application/json;charset=utf-8");

            }
        };

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setRequestInterceptor(requestInterceptor)
                .setClient(getOkClient())
                .build();
        webService = restAdapter.create(cls);
    }

    private static String signRequest() {
        StringBuilder builder = new StringBuilder();
        long ts = System.currentTimeMillis();
        StringBuilder result = new StringBuilder().append(LeanCloudMD5Util.md5(builder.append(ts).append(BuildConfig.AVOSCloud_App_Key).toString()).toLowerCase());
        return result.append(',').append(ts).toString();
    }

    private static OkHttpClient getUnsafeOkHttpClient() {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }};

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext
                    .getSocketFactory();

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setSslSocketFactory(sslSocketFactory);
            okHttpClient.setHostnameVerifier(new HostnameVerifier() {

                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return hostname.equalsIgnoreCase("api.leancloud.cn");
                }
            });
            okHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
            okHttpClient.setReadTimeout(15, TimeUnit.SECONDS);
            okHttpClient.setReadTimeout(15, TimeUnit.SECONDS);
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static OkClient getOkClient() {
        OkHttpClient client1 = new OkHttpClient();
        client1 = getUnsafeOkHttpClient();
        OkClient _client = new OkClient(client1);
        return _client;
    }

    public class BaseDataEnvelope<T1> {
        public T1 results;
    }

}
