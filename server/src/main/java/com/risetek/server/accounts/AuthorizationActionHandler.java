package com.risetek.server.accounts;

import javax.inject.Inject;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import com.risetek.share.accounts.AuthorizationAction;
import com.risetek.share.accounts.AuthorizationEntity;
import com.risetek.share.dispatch.GetResult;

public class AuthorizationActionHandler implements ActionHandler<AuthorizationAction, GetResult<AuthorizationEntity>> {
	
	@Inject
	IAuthorizingHandler authorizing;

	@Override
	public GetResult<AuthorizationEntity> execute(AuthorizationAction action, ExecutionContext context) throws ActionException {
		return authorizing.doAuthorizationAction(action);
	}

	@Override
	public Class<AuthorizationAction> getActionType() {
		return AuthorizationAction.class;
	}

	@Override
	public void undo(AuthorizationAction action, GetResult<AuthorizationEntity> result, ExecutionContext context)
			throws ActionException {}
}
