package com.risetek.presentermodules.development.homecard;

import com.google.inject.Inject;
import com.risetek.presentermodules.home.cards.BaseHomeCardView;

public class HomeCardView extends BaseHomeCardView<MyUiHandlers> implements
		HomeCardPresenter.MyView {

	@Inject
	public HomeCardView() {
		setTitle("开发者乐园", "state-machine");
	}
}
