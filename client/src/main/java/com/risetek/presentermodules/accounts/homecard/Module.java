package com.risetek.presentermodules.accounts.homecard;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.risetek.bindery.AutoLoadPresenterModule;

@AutoLoadPresenterModule
public class Module extends AbstractPresenterModule {
	@Override
	protected void configure() {
		bindPresenter(HomeCardPresenter.class, HomeCardPresenter.MyView.class, HomeCardView.class,
				HomeCardPresenter.MyProxy.class);
	}
}
