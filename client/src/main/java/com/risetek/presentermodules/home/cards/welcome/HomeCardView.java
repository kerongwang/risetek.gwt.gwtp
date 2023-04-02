package com.risetek.presentermodules.home.cards.welcome;

import com.google.inject.Inject;
import com.risetek.presentermodules.home.cards.BaseHomeCardView;
import com.risetek.utils.Icons;

public class HomeCardView extends BaseHomeCardView<MyUiHandlers> implements
		HomeCardPresenter.MyView {

	@Inject
	public HomeCardView() {
		setTitle("欢迎使用", new Icons.Login());
	}
}
