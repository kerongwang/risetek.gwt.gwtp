package com.risetek.presentermodules.devops;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

class PageView extends ViewWithUiHandlers<MyUiHandlers> implements PagePresenter.MyView {

	interface Binder extends UiBinder<Widget, PageView> {}
	@UiField Element panelSlot;
	@Inject
	public PageView(final EventBus eventBus,
			        final Binder uiBinder) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("btnGoback")
	public void onGobackClick(ClickEvent e) {
		getUiHandlers().onGoBackPlace();
	}
}
