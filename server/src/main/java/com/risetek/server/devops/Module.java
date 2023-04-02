package com.risetek.server.devops;

import com.gwtplatform.dispatch.rpc.server.guice.HandlerModule;
import com.risetek.server.bindery.AutoLoadModule;
import com.risetek.share.container.StateAction;
import com.risetek.share.devops.DevOpsAction;
import com.risetek.share.devops.DevOpsTasksAction;

@AutoLoadModule
public class Module extends HandlerModule {
	@Override
	protected void configureHandlers() {
		bind(ServicesManagement.class).asEagerSingleton();
		bindHandler(DevOpsAction.class, DevOpsActionHandler.class);
		bindHandler(DevOpsTasksAction.class, DevOpsTasksActionHandler.class);
		bindHandler(StateAction.class, StateActionHandler.class);
	}
}
