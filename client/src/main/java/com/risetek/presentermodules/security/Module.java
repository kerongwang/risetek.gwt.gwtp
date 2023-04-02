package com.risetek.presentermodules.security;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.risetek.bindery.AutoLoadPresenterModule;
import com.risetek.presentermodules.security.newacct.NewAcctModule;
import com.risetek.presentermodules.security.resetpassword.ResetPasswordModule;

@AutoLoadPresenterModule
public class Module extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(PagePresenter.class, PagePresenter.MyView.class, PageView.class,
                PagePresenter.MyProxy.class);
        
    	install(new NewAcctModule());
    	install(new ResetPasswordModule());
    }
}
