package com.risetek.presentermodules.security.newacct;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.risetek.presentermodules.security.ui.TitlePanel;

class PageView extends ViewWithUiHandlers<MyUiHandlers> implements PagePresenter.MyView {

	interface MyUiBinder extends UiBinder<HTMLPanel, PageView> {}
	private static final MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	@UiField TitlePanel titlePanel;
	@UiField InputElement username;
	@UiField InputElement password;
	@UiField Button commit;
	
	@Inject
	public PageView() {
		initWidget(uiBinder.createAndBindUi(this));
		commit.addClickHandler(c->{
			if(username.getValue().isEmpty() || password.getValue().isEmpty())
				GWT.log("invalid account");
			else
				getUiHandlers().newAccount(username.getValue(), password.getValue());
		});
		titlePanel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				getUiHandlers().goContinue();
			}});
	}
}
