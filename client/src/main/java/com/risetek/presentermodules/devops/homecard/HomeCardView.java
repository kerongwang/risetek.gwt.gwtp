package com.risetek.presentermodules.devops.homecard;

import com.google.inject.Inject;
import com.risetek.presentermodules.home.cards.BaseHomeCardView;
import com.risetek.utils.Icons;

public class HomeCardView extends BaseHomeCardView<MyUiHandlers> implements
		HomeCardPresenter.MyView {

	@Inject
	public HomeCardView() {
		setTitle("DevOps", Icons.helpIcon());
	}
}
