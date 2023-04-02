package com.risetek.presentermodules.home.cards.simple;

import com.google.inject.Inject;
import com.risetek.presentermodules.home.cards.BaseHomeCardView;
import com.risetek.utils.Icons;

public class HomeCardView extends BaseHomeCardView<MyUiHandlers> implements
		HomeCardPresenter.MyView {

	@Inject
	public HomeCardView() {
		setTitle("应用信息", Icons.compassIcon());
	}
}
