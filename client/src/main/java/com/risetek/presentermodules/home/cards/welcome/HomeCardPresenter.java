package com.risetek.presentermodules.home.cards.welcome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.risetek.NameTokens;
import com.risetek.entry.Subject;
import com.risetek.presentermodules.home.cards.AbstractHomeCardPresenter;
import com.risetek.presentermodules.home.cards.IHomeCardView;
import com.risetek.presentermodules.home.cards.InfoItem;
import com.risetek.presentermodules.home.cards.RevealHomeCardEvent;
import com.risetek.share.accounts.hosts.HostProjectRBAC;
import com.risetek.share.templates.Project;

public class HomeCardPresenter extends AbstractHomeCardPresenter<HomeCardPresenter.MyView, HomeCardPresenter.MyProxy>
		implements MyUiHandlers, RevealHomeCardEvent.HomeCardRevealHandler {
	public interface MyView extends IHomeCardView, HasUiHandlers<MyUiHandlers> {
	}

	@ProxyStandard
	@NoGatekeeper
	public interface MyProxy extends Proxy<HomeCardPresenter> {
	}

	private final Subject subject;
	private final PlaceManager placeManager;

	@Inject
	public HomeCardPresenter(final EventBus eventBus, final Subject subject, 
			final MyView view, final MyProxy proxy, final PlaceManager placeManager) {
		super(eventBus, view, proxy);
		this.subject = subject;
		this.placeManager = placeManager;
		getView().setUiHandlers(this);
	}

	private final PlaceRequest securityPlaceRequest = new PlaceRequest.Builder().nameToken(NameTokens.security).build();

	@Override
	public boolean update() {
		getView().clear();

		List<InfoItem> items = new ArrayList<>();
		InfoItem item = new InfoItem();
		item.infoText = "\u767b\u5f55\u72b6\u6001";
		item.infoTextSecondary = Arrays.asList(subject.isLogin() ? "已登录" : "未登录");
		items.add(item);
		if (!subject.isLogin()) {
			item = new InfoItem();
			item.infoText = "登录后拥有更多操作权限";
			items.add(item);
			getView().addAction("用户登录", c-> subject.Login());
		} else {
			item = new InfoItem();
			item.infoText = "操作权限";
			Set<String> roles = subject.getRoles();
			StringBuffer sb = new StringBuffer();
			for (String role : roles) {
				HostProjectRBAC e = null;
				try {
				e = Enum.valueOf(HostProjectRBAC.class, role);
				}catch(Exception ex) {
					// do nothing.
				}
				if(null == e)
					sb.append(" " + role);
				else
					sb.append(" " + e.toString());
			}

			item.infoTextSecondary = Arrays.asList(sb.toString());
			items.add(item);

			item = new InfoItem();
			item.infoText = "项目";
			item.infoTextSecondary = Arrays.asList(Project.name);
			items.add(item);
			
			getView().addAction("我的账户信息", c->{placeManager.revealPlace(securityPlaceRequest);});
		}

		getView().updateInfoItems(items);
		return true;
	}

	@Override
	public Integer getOrder() {
		return 2;
	}
}
