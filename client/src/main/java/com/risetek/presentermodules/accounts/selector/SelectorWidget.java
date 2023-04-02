package com.risetek.presentermodules.accounts.selector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.risetek.share.accounts.AccountAction;
import com.risetek.share.accounts.AccountEntity;
import com.risetek.share.dispatch.GetResults;
import com.risetek.ui.infinitycard.CardPresenterWidget;
import com.risetek.ui.infinitycard.CardUiHandlers;
import com.risetek.ui.infinitycard.LoadRange;
import com.risetek.utils.ServerExceptionHandler;

public class SelectorWidget extends CardPresenterWidget<AccountEntity, SelectorView.Card, CardUiHandlers<AccountEntity>, SelectorView> {
	public interface MyView extends CardPresenterWidget.CardView<AccountEntity, CardUiHandlers<AccountEntity>> {
	}

	@Inject
	public SelectorWidget(
			final EventBus eventBus,
			final DispatchAsync dispatcher,
			final ServerExceptionHandler exceptionHandler,
			final SelectorView view) {
		super(eventBus, dispatcher, exceptionHandler, view);
		onRefresh();
	}

	private long lastsequence = 0;
	private boolean loading = false;
	
	@Override
	public void onLoadRange(final LoadRange loadRange) {
		if(loading) {
			getView().updateRowData(0, null, loadRange);
			return;
		}
		loading = true;
		GWT.log("loading from: " + loadRange.loadRange.getStart() + " length: " + loadRange.loadRange.getLength());
		// read more than require so to determine the end of data.
		AccountAction action = new AccountAction(null, loadRange.loadRange.getStart(), loadRange.loadRange.getLength(), getSearchKey());
		lastsequence = Math.max(lastsequence, action.sequence);

		dispatcher.execute(action, new AsyncCallback<GetResults<AccountEntity>>() {

			@Override
			public void onFailure(Throwable caught) {
				loading = false;
				// unlock scroll
				getView().updateRowData(0, null, loadRange);
				exceptionHandler.handler(caught);
			}

			@Override
			public void onSuccess(GetResults<AccountEntity> result) {
				loading = false;
				if (action.sequence < lastsequence) {
					GWT.log("discard action: " + action.sequence);
					return;
				}

				lastsequence = action.sequence;
				getView().updateRowData(loadRange.loadRange.getStart(), result.getResults(), loadRange);
			}
		});
	}
}
