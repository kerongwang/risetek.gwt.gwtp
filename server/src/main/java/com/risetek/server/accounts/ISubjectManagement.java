package com.risetek.server.accounts;

import com.gwtplatform.dispatch.shared.ActionException;
import com.risetek.share.accounts.AccountEntity;

public interface ISubjectManagement {
	/**
	 * @return Current subject associated user entity.
	 * @throws Exception
	 */
	AccountEntity getSubjectEntity() throws ActionException;
	void setSubjectPassword(String password) throws ActionException;
}
