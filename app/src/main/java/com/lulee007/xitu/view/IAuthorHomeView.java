package com.lulee007.xitu.view;

import com.lulee007.xitu.models.Author;

public interface IAuthorHomeView {
    void onGetAuthorInfoDone(Author author);

    void onGetAuthorInfoError();
}
