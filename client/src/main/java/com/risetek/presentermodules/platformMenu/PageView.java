package com.risetek.presentermodules.platformMenu;

import javax.inject.Inject;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class PageView extends ViewWithUiHandlers<MyUiHandlers> implements PagePresenter.MyView {
	interface Binder extends UiBinder<Widget, PageView> {}
    interface Style extends CssResource {
    	String barRight();
    	String barMiddle();
    	String barLeft();
    }
    
    @UiField Style style;
    @UiField HTMLPanel barContainer, backdrop, overlayContainer;

    @Inject
    public PageView(final Binder binder) {
    	binder.createAndBindUi(this);
    	initWidget(barContainer);

		backdrop.addDomHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				getUiHandlers().hideChooser();
			}}, ClickEvent.getType());
    }

	@Override
	public void clearMenuPanel() {
		overlayContainer.clear();
	};

	@Override
	public void showChooser(AbstractDockMenu menu) {
		Panel panel = menu.getFunctionPanel();
		if(null == panel)
			return;

		overlayContainer.add(backdrop);
		overlayContainer.add(panel);
	}

	@Override
	public void showTip(AbstractDockMenu menu) {
		Panel tipPanel = menu.getTipPanel();
    	overlayContainer.add(tipPanel);
	}

	private void installMenu(String style, AbstractDockMenu... menus) {
    	FlowPanel container = new FlowPanel();
    	container.setStyleName(style);

    	for(AbstractDockMenu menu:menus) {
	    	menu.setUiHandlers(getUiHandlers());
	    	container.add(menu.getIconPanel());
    	}
		barContainer.add(container);
	}
	
	@Override
	public void installRightMenu(AbstractDockMenu... menus) {
		installMenu(style.barRight(), menus);
	}

	@Override
	public void installMiddleMenu(AbstractDockMenu... menus) {
		installMenu(style.barMiddle(), menus);
	}

	@Override
	public void installLeftMenu(AbstractDockMenu... menus) {
		installMenu(style.barLeft(), menus);
	}
}
