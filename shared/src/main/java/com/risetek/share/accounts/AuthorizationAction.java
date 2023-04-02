package com.risetek.share.accounts;

import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;
import com.risetek.share.dispatch.GetResult;

/**
 * AuthorizationAction require server side check subject and return roles it authorized.
 * 
 * @author wangyc@risetek.com
 *
 */
public class AuthorizationAction extends UnsecuredActionImpl<GetResult<AuthorizationEntity>> {
	public AuthorizationAction() {
	}
}
