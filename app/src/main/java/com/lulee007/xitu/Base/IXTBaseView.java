package com.lulee007.xitu.base;

import java.util.List;

/**
 * 通用IView接口，满足以列表数据的页面。需要给activity实现这些接口，并作为参数传给Presenter
 * @param <T> model/bean
 */
public interface IXTBaseView<T> {

    void refresh(List<T> entries);

    void refreshNoContent();

    void refreshError();

    void addMore(List<T> moreItems);
    void addNew(List<T> newItems);

    void addNewError();
    void addMoreError();

    void noMore();
    void noData();


}
