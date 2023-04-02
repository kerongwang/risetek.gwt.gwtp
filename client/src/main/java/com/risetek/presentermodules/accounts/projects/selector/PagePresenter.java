package com.risetek.presentermodules.accounts.projects.selector;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.presenter.slots.Slot;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.risetek.entry.LoggedInGatekeeper;
import com.risetek.place.root.RootPresenter;
import com.risetek.presentermodules.accounts.TokenNames;

public class PagePresenter extends Presenter<PagePresenter.MyView, PagePresenter.MyProxy> implements MyUiHandlers {

	public interface MyView extends View, HasUiHandlers<MyUiHandlers> {
		String getSearchKey();
		void bindSlot(Slot<PresenterWidget<?>> slot);
	}

	@ProxyCodeSplit
	@NameToken(TokenNames.projects)
	@UseGatekeeper(LoggedInGatekeeper.class)
	public interface MyProxy extends ProxyPlace<PagePresenter> {}

	public static final Slot<PresenterWidget<?>> SLOT = new Slot<>();

	private final PlaceManager placeManager;
	private final SelectorWidget cardPresenter;
	@Inject
	public PagePresenter(final EventBus eventBus,
			             final MyView view,
			             final MyProxy proxy,
			             final PlaceManager placeManager,
			             final SelectorWidget cardPresenter) {
		super(eventBus, view, proxy, RootPresenter.SLOT_MainContent);
		getView().setUiHandlers(this);
		getView().bindSlot(SLOT);
		this.placeManager = placeManager;
		this.cardPresenter = cardPresenter;
		cardPresenter.selectedConsumer = c-> {
			String project = c.getEntity().getName();
			if(null != project) {
				String selector = placeManager.getCurrentPlaceRequest().getParameter(TokenNames.selector, TokenNames.project);
				PlaceRequest place = new PlaceRequest.Builder().nameToken(selector).with(TokenNames.selectProject, project).build();
				placeManager.revealPlace(place, false);
			} else
				onGoBackPlace();
		};
		cardPresenter.searchKeyProvider = () -> {
			return getView().getSearchKey();
		};
		setInSlot(SLOT, cardPresenter);
	}

	@Override
	public void onGoBackPlace() {
		String selector = placeManager.getCurrentPlaceRequest().getParameter(TokenNames.selector, TokenNames.project);
		PlaceRequest place = new PlaceRequest.Builder().nameToken(selector).build();
		placeManager.revealPlace(place);
	}

	@Override
	public void onSearch() {
		cardPresenter.onRefresh();
	}
}
