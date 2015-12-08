package com.lulee007.xitu.base;

import com.avos.avoscloud.AVUtils;
import com.lulee007.xitu.BuildConfig;
import com.squareup.okhttp.OkHttpClient;

import java.security.cert.CertificateException;

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
 * User: lulee007@live.com
 * Date: 2015-12-08
 * Time: 09:26
 */
public abstract class XTBaseService {

    public final static String BASE_URL="https://api.leancloud.cn/1.1/classes";


    protected RestAdapter restAdapter;

    public  XTBaseService(){
        RequestInterceptor requestInterceptor=new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("X-Uluru-Application-Production", BuildConfig.DEBUG?"0":"1");
                request.addHeader("X-avoscloud-Application-Id", BuildConfig.AVOSCloud_App_Id);
                request.addHeader("X-avoscloud-Session-Token", "");
                request.addHeader("Accept", "application/json");
                request.addHeader("Content-Type", "application/json");
                request.addHeader("User-Agent", "AVOS Cloud android-v3.12 SDK");
                request.addHeader("x-avoscloud-request-sign", signRequest());
            }
        };

        restAdapter=new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(requestInterceptor)
                .setClient(getOkClient())
                .build();
    }

    private static String signRequest() {
        StringBuilder builder = new StringBuilder();
        long ts = System.currentTimeMillis();
        StringBuilder result = new StringBuilder();
        result.append(AVUtils.md5(builder.append(ts).append(BuildConfig.AVOSCloud_App_Key).toString()).toLowerCase());
        return result.append(',').append(ts).toString();
    }

    private static OkHttpClient getUnsafeOkHttpClient() {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
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
            } };

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
                    if (hostname.equalsIgnoreCase("api.leancloud.cn"))
                        return true;
                    else
                        return false;
                }
            });

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static OkClient getOkClient (){
        OkHttpClient client1 = new OkHttpClient();
        client1 = getUnsafeOkHttpClient();
        OkClient _client = new OkClient(client1);
        return _client;
    }

    public class BaseDataEnvelope<T>{
        public T results;
    }

}
