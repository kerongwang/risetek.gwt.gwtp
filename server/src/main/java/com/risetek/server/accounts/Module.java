package com.risetek.server.accounts;

import com.gwtplatform.dispatch.rpc.server.guice.HandlerModule;
import com.risetek.server.bindery.AutoLoadModule;
import com.risetek.server.shiro.MyAuthorizingRealm;
import com.risetek.share.accounts.AccountAction;
import com.risetek.share.accounts.AuthenticationAction;
import com.risetek.share.accounts.AuthorizationAction;
import com.risetek.share.accounts.SubjectAction;
import com.risetek.share.accounts.projects.ProjectAction;
import com.risetek.share.accounts.roles.RoleAction;

@AutoLoadModule
public class Module extends HandlerModule {
	@Override
	protected void configureHandlers() {
		bindHandler(RoleAction.class, RoleActionHandler.class);
		// Accounts
		bindHandler(AccountAction.class, AccountActionHandler.class);
		// Projects
		bindHandler(ProjectAction.class, ProjectActionHandler.class);

		// Authorizing
		bind(IAuthorizingHandler.class).to(MyAuthorizingRealm.class).asEagerSingleton();
		bind(ISubjectManagement.class).to(MyAuthorizingRealm.class).asEagerSingleton();
		bind(TemporaryAccount.class).toProvider(TemporaryAccount.deployAccountProvider.class);
		bindHandler(SubjectAction.class, SubjectActionHandler.class);
		bindHandler(AuthenticationAction.class, AuthenticationActionHandler.class);
		bindHandler(AuthorizationAction.class, AuthorizationActionHandler.class);
		
		bind(AccessTokenManagement.class);
	}
}
