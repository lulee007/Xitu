package com.lulee007.xitu.view;

import android.graphics.Bitmap;

import com.lulee007.xitu.models.Author;

public interface IAuthorHomeView {
    void onGetAuthorInfoDone(Author author);

    void onGetAuthorInfoError();

    void onUserBlurBgDownloaded(Bitmap bitmap);

    void onUserBlurBgDownloadError();

}
