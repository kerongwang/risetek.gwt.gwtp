package com.risetek.share.accounts;

import java.util.List;

import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;
import com.risetek.share.dispatch.GetNoResult;

public class AuthenticationAction extends UnsecuredActionImpl<GetNoResult> {

	// For Logout
	public AuthenticationAction(){}

	// For Login
	public AuthenticationAction(List<String> principals, String credential, boolean remember, String project) {
		this.principals = principals;
		this.credential = credential;
		this.remember = remember;
		this.project = project;
	}
	public List<String> principals;
	public String credential;
	public boolean remember;
	public String project;
}
