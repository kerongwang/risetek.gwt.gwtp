package com.risetek.presentermodules.home.cards.state;

import java.util.List;
import java.util.Vector;

import javax.inject.Inject;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.risetek.presentermodules.home.cards.AbstractHomeCardPresenter;
import com.risetek.presentermodules.home.cards.IHomeCardView;
import com.risetek.presentermodules.home.cards.InfoItem;
import com.risetek.presentermodules.home.cards.RevealHomeCardEvent;
import com.risetek.share.container.StateAction;
import com.risetek.share.container.StateEntity;
import com.risetek.share.dispatch.GetResults;
import com.risetek.utils.ServerExceptionHandler;

public class HomeCardPresenter extends AbstractHomeCardPresenter<HomeCardPresenter.MyView, HomeCardPresenter.MyProxy>
		implements MyUiHandlers, RevealHomeCardEvent.HomeCardRevealHandler {
	public interface MyView extends IHomeCardView, HasUiHandlers<MyUiHandlers> {
	}

	@ProxyStandard
	@NoGatekeeper
	public interface MyProxy extends Proxy<HomeCardPresenter> {
	}

	private final DispatchAsync dispatcher;
	private final ServerExceptionHandler exceptionHandler;

	@Inject
	public HomeCardPresenter(EventBus eventBus, DispatchAsync dispatcher, MyView view, MyProxy proxy, ServerExceptionHandler exceptionHandler) {
		super(eventBus, view, proxy);
		this.dispatcher = dispatcher;
		this.exceptionHandler = exceptionHandler;
		getView().setUiHandlers(this);
	}

	private void updateStateInfoCard() {
		dispatcher.execute(new StateAction(), new AsyncCallback<GetResults<StateEntity>>() {

			@Override
			public void onFailure(Throwable caught) {
				exceptionHandler.handler(caught);
			}

			@Override
			public void onSuccess(GetResults<StateEntity> result) {
				getView().clear();
				List<InfoItem> items = new Vector<>();
				for (StateEntity entity : result.getResults()) {
					InfoItem item = new InfoItem();
					item.infoText = entity.getTitle();
					item.infoTextSecondary = entity.getMessage();
					items.add(item);
				}

				getView().updateInfoItems(items);
			}
		});
	}

	@Override
	public boolean update() {
		updateStateInfoCard();
		return true;
	}

	@Override
	public Integer getOrder() {
		return 8000;
	}
}
