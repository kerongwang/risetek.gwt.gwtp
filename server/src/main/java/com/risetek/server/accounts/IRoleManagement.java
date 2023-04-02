package com.risetek.server.accounts;

import java.util.Set;

import com.gwtplatform.dispatch.shared.ActionException;
import com.risetek.share.accounts.hosts.HostProjectRBAC;

public interface IRoleManagement {
	Set<String> getRoleSet(Object principal, Object project) throws ActionException;
	// Replace roleSet with new one
	void setRoleSet(Object principal, Object project, Set<String> roles) throws ActionException;

	// Host Roles
	Set<HostProjectRBAC> getRoleSet(Object principal) throws ActionException;
	void setRoleSet(Object principal, Set<HostProjectRBAC> roles) throws ActionException;
}
