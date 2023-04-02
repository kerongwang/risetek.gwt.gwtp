package com.risetek.place.root;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.risetek.bindery.AutoLoadPresenterModule;

@AutoLoadPresenterModule
public class Module extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(RootPresenter.class,
        		RootPresenter.MyView.class,
        		PageView.class,
                RootPresenter.MyProxy.class);
    }
}
