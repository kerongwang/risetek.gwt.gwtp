package com.risetek.server;

import com.gwtplatform.dispatch.rpc.server.guice.HandlerModule;
import com.risetek.server.bindery.AutoLoadModule;
import com.risetek.share.dispatch.UnsecuredSerializableBatchAction;

@AutoLoadModule
public class Module extends HandlerModule {
	@Override
	protected void configureHandlers() {
		// Binder common UnsecuredSerializableBatchAction and it Handler
		bindHandler(UnsecuredSerializableBatchAction.class, SerializableBatchActionHandler.class);
	}
}
