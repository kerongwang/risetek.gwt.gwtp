package com.risetek.presentermodules.accounts.selector;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.risetek.bindery.AutoLoadPresenterModule;

@AutoLoadPresenterModule
public class Module extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(PagePresenter.class, PagePresenter.MyView.class, PageView.class,
                PagePresenter.MyProxy.class);
        bindPresenterWidget(SelectorWidget.class,
        		SelectorWidget.MyView.class, SelectorView.class);
    }
}
