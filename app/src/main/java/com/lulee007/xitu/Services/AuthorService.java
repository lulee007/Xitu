package com.lulee007.xitu.services;

import com.lulee007.xitu.base.XTBaseService;
import com.lulee007.xitu.models.Author;

import java.util.HashMap;
import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.QueryMap;
import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;

/**
 * User: lulee007@live.com
 * Date: 2015-12-12
 * Time: 21:40
 */
public class AuthorService extends XTBaseService<AuthorService.IAuthorWebService> {


    public AuthorService() {
        super(IAuthorWebService.class);
    }

    public Observable<Author> getAuthor(String authorId) {
        return webService.getAuthor(authorId);
    }

    protected interface IAuthorWebService {
        @GET("/users")
        Observable<AuthorDataEnvelope> getAuthors(@QueryMap HashMap<String, String> params);

        @GET("/users/{authorId}")
        Observable<Author> getAuthor(@Path("authorId") String authorId);
    }

    private class AuthorDataEnvelope extends BaseDataEnvelope<List<Author>> {
    }
/**
 * order	-totalHotIndex
 * where	{"role":"editor"}
 * limit	50
 */

    /**
     * 获取掘金者列表
     * @param params
     * @return
     */
    public Observable<List<Author>> getAuthors(HashMap<String,String> params){
        return webService.getAuthors(params).map(new Func1<AuthorDataEnvelope, List<Author>>() {
            @Override
            public List<Author> call(AuthorDataEnvelope authorDataEnvelope) {
                return authorDataEnvelope.results;
            }
        });
    }


}
