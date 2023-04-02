package com.risetek.presentermodules.development.homecard;

import javax.inject.Inject;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.risetek.entry.Subject;
import com.risetek.presentermodules.development.TokenNames;
import com.risetek.presentermodules.home.cards.AbstractHomeCardPresenter;
import com.risetek.presentermodules.home.cards.IHomeCardView;
import com.risetek.presentermodules.home.cards.RevealHomeCardEvent;
import com.risetek.share.accounts.hosts.HostProjectRBAC;

public class HomeCardPresenter extends AbstractHomeCardPresenter<HomeCardPresenter.MyView, HomeCardPresenter.MyProxy>
		implements MyUiHandlers, RevealHomeCardEvent.HomeCardRevealHandler {
	public interface MyView extends IHomeCardView, HasUiHandlers<MyUiHandlers> {}

	@ProxyStandard
	@NoGatekeeper
	public interface MyProxy extends Proxy<HomeCardPresenter> {}
	private final PlaceManager placeManager;
	private final Subject subject;

	@Inject
	public HomeCardPresenter(final EventBus eventBus,
			final MyView view,
			final MyProxy proxy,
			final Subject subject,
			final PlaceManager placeManager) {
		super(eventBus, view, proxy);
		this.subject = subject;
		this.placeManager = placeManager;
		getView().setUiHandlers(this);
	}

	private final PlaceRequest iconsPlace = new PlaceRequest.Builder().nameToken(TokenNames.icons).build();

	@Override
	public Integer getOrder() {
		return 92;
	}

	@Override
	public boolean update() {
		if(!(subject.isLogin() && subject.checkRole(HostProjectRBAC.DEVELOPER)))
			return false;
		getView().clear();

		getView().addAction("Icon 展示", c-> placeManager.revealPlace(iconsPlace));
		return true;
	}
}
