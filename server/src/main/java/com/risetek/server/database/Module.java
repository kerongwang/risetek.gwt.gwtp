package com.risetek.server.database;

import org.hibernate.SessionFactory;
import com.gwtplatform.dispatch.rpc.server.guice.HandlerModule;
import com.risetek.server.accounts.IAccountManagement;
import com.risetek.server.accounts.IProjectsManagement;
import com.risetek.server.accounts.IRoleManagement;
import com.risetek.server.bindery.AutoLoadModule;
import com.risetek.server.database.hsqldb.HibernateSessionFactoryProvider;

@AutoLoadModule
public class Module extends HandlerModule {
	@Override
	protected void configureHandlers() {
		// Roles
		bind(IRoleManagement.class).toProvider(DatabaseProvider.RoleManagement.class).asEagerSingleton();
		// Accounts
		bind(IAccountManagement.class).toProvider(DatabaseProvider.AccountManagement.class).asEagerSingleton();
		// Projects
		bind(IProjectsManagement.class).toProvider(DatabaseProvider.ProjectsManagement.class).asEagerSingleton();
		
		// Hibernate
		bind(SessionFactory.class).toProvider(HibernateSessionFactoryProvider.class);
	}
}
