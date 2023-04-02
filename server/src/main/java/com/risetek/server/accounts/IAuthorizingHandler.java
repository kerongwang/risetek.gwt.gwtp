package com.risetek.server.accounts;

import com.gwtplatform.dispatch.shared.ActionException;
import com.risetek.share.accounts.AuthenticationAction;
import com.risetek.share.accounts.AuthorizationAction;
import com.risetek.share.accounts.AuthorizationEntity;
import com.risetek.share.dispatch.GetNoResult;
import com.risetek.share.dispatch.GetResult;

public interface IAuthorizingHandler {
	GetNoResult doAuthenticationAction(AuthenticationAction action) throws ActionException;
	GetResult<AuthorizationEntity> doAuthorizationAction(AuthorizationAction action) throws ActionException;
	String encryptRealmPassword(Object password);
	boolean passwordsMatch(Object submittedPlaintext, String encrypted);
}
