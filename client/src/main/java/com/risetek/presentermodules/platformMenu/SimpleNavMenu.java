package com.risetek.presentermodules.platformMenu;

import java.util.List;
import java.util.function.Consumer;

import javax.inject.Singleton;
import javax.validation.constraints.NotNull;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.risetek.bindery.IBuilderStamp;
import com.risetek.bindery.PlainMenuLoader;
import com.risetek.bindery.PlainMenuLoader.PlainMenuContent;
import yun74.gwt.icons.Yun74Icons;

@Singleton
public class SimpleNavMenu extends AbstractDockMenu {
	private final static PlainMenuLoader menuloader = GWT.create(PlainMenuLoader.class);
	private final FunctionPanel chooserPanel;
	@Override
	public String getTipString() {
		return "\u5bfc\u822a\u83dc\u5355";
	}

	@Inject
	public SimpleNavMenu(final Binder binder, final FunctionPanel chooserPanel) {
		super(binder);
		this.chooserPanel = chooserPanel;
		setMenuIcon(chooserPanel.iconMenu);
		setFunctionPanelPositionLeft(20);

		functionPanel.add(chooserPanel);

		List<PlainMenuContent> menus = menuloader.getMenus();
		
		FlowPanel navBarContainer = null;
		int index = 0;
		for(PlainMenuContent menu: menus) {
			if(index++ % 3 == 0) {
				navBarContainer = new FlowPanel();
				chooserPanel.add(navBarContainer);
			}
				
			Panel panel = new MenuItem(menu, c->getUiHandlers().gotoPlace(c));
			navBarContainer.add(panel);
		}
	}

	@Override
	public void onAttach() {
		super.onAttach();
		chooserPanel.setUiHandlers(getUiHandlers());
	}

	@Override
	public Panel getFunctionPanel() {
		return boundingboxMenu;
	}

	static class FunctionPanel extends ViewWithUiHandlers<MyUiHandlers> {
		private final static IBuilderStamp stamp = GWT.create(IBuilderStamp.class);
		private static String copyrightText = "© 2000-" + stamp.getYear() + " Chengdu Risetek Corp.  &nbsp;&nbsp;&nbsp;Build at:" + stamp.getBuilderStamp();

		interface AccountBinder extends UiBinder<Widget, FunctionPanel> {}
		@UiField FlowPanel navChooserDetail;
		@UiField DivElement labelCopyright;
		@UiField Element iconMenu;
		@Inject
		public FunctionPanel(final AccountBinder binder) {
			initWidget(binder.createAndBindUi(this));
			labelCopyright.setInnerHTML(copyrightText);
		}
		
		public void add(Panel panel) {
			navChooserDetail.add(panel);
		}
	}

	static class MenuItem extends FocusPanel {
		interface Binder extends UiBinder<Widget, MenuItem> {}
	    private static final Binder binder = GWT.create(Binder.class);
	    
	    @UiField DivElement iconContainer;
	    @UiField Label title;

	    public MenuItem(PlainMenuContent menu, Consumer<String> consumer) {
			add(binder.createAndBindUi(this));
			if(null != menu.iconName) {
				iconContainer.appendChild(Yun74Icons.getElement(menu.iconName));
			}
			else if(null != menu.icon)
				iconContainer.appendChild(menu.icon.getElement());

			title.setText(menu.title);

			if(null == menu.token)
				addClickHandler(c->{consumer.accept(menu.title);});
			else
				addClickHandler(c->{consumer.accept(menu.token);});
		}
	}
}
