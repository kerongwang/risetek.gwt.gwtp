package com.risetek.share.exception;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.gwtplatform.dispatch.shared.ActionException;

public class ActionUnauthenticatedException extends ActionException implements IsSerializable {
	public ActionUnauthenticatedException() {
		super();
	}
	public ActionUnauthenticatedException(String string) {
		super(string);
	}

	private static final long serialVersionUID = -3981082966104228611L;

}
