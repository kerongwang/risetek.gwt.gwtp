package com.risetek.server;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;

import com.gwtplatform.dispatch.shared.ActionException;
import com.risetek.share.exception.ActionAuthenticationException;
import com.risetek.share.exception.ActionAuthorizationException;
import com.risetek.share.exception.ActionUnauthenticatedException;
import com.risetek.share.exception.ActionUnauthorizedException;

/**
 * Map Server side Shiro Exception to common Exception, so GWT Client may handler exception.
 * @author wangyc@risetek.com
 *
 */
public class ActionExceptionMapper {
	public static void handler(Throwable t) throws ActionException {
		if(t instanceof ActionException) {
			// forward
			throw (ActionException)t;
		} else if(t instanceof AuthenticationException) {
			throw new ActionAuthenticationException(t.getMessage());
		} else if(t instanceof UnauthenticatedException) {
			throw new ActionUnauthenticatedException("UnauthenticatedException");
		} else if(t instanceof AuthorizationException) {
			System.out.println("\r\n\r\n\r\n !!!!! ActionAuthorizationException\r\n" + t.getMessage() + "\r\n\r\n!!!!\r\n");
			if(t.getCause() != null) {
				System.out.println(t.getCause());
				System.out.println(t.getCause().getMessage());
			}
			throw new ActionAuthorizationException();
		} else if(t instanceof UnauthorizedException) {
			System.out.println("\r\n\r\n\r\n !!!!! ActionUnauthorizedException\r\n" + t.getMessage() + "\r\n\r\n!!!!\r\n");
			if(t.getCause() != null) {
				System.out.println(t.getCause());
				System.out.println(t.getCause().getMessage());
			}
			throw new ActionUnauthorizedException(t.getMessage());
		} else {
			throw new ActionException("TODO: UnMapped exception:\r\n  class: " + t + "\r\n" + t.getMessage());
		}
	}
}
