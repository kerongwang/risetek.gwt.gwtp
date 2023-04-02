package com.risetek.deployment.temporaryAccount;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.risetek.bindery.AutoLoadPresenterModule;

@AutoLoadPresenterModule
public class Module extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(PagePresenter.class, PagePresenter.MyView.class, PageView.class,
                PagePresenter.MyProxy.class);
    }
}
