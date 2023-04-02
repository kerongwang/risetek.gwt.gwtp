package com.risetek.server;

import java.util.List;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.shiro.guice.aop.ShiroAopModule;
import org.apache.shiro.guice.web.GuiceShiroFilter;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.gwtplatform.dispatch.rpc.server.guice.DispatchServiceImpl;
import com.risetek.server.devops.ServicesManagement;
import com.risetek.server.shiro.MyShiroWebModule;
import com.risetek.server.shiro.OAuthAuthzServlet;
import com.risetek.server.shiro.OAuthLoginUrlBuilderServlet;
import com.risetek.server.shiro.OAuthTokenServlet;

/**
 * Maven-processer-plugin need this class to override for auto load submodules.
 * 
 * @author wangyc@risetek.com
 */

public abstract class AppServletContextListener extends GuiceServletContextListener {
	private ServletContext servletContext;

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		this.servletContext = servletContextEvent.getServletContext();
		super.contextInitialized(servletContextEvent);
	}	

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		Injector injector = (Injector) servletContext.getAttribute(Injector.class.getName());
		if(null != injector) {
			// TODO: callback? or inject?
		}
		// Call registered clean functions.
		// TODO: Destroyable
		ServicesManagement.cleanServices();
		
		super.contextDestroyed(servletContextEvent);
	}
	
	private final List<Module> modulesList = new Vector<>();
	
	public abstract void appendModules(List<Module> list);
	
	@Override
	protected Injector getInjector() {
		final Context ctx;
		try {
			InitialContext ic = new InitialContext();
			ctx = (Context) ic.lookup("java:comp/env");
		} catch (NamingException ne) {
			throw new RuntimeException(ne);
		}

		modulesList.add(new ServletModule() {
			@Override
			protected void configureServlets() {
				// Demo Login for devops
				serve("/demo/oauth/login").with(OAuthLoginUrlBuilderServlet.class);
				serve("/demo/oauth/authz").with(OAuthAuthzServlet.class);
				serve("/demo/oauth/token").with(OAuthTokenServlet.class);
				
				serve("/dispatch/*").with(DispatchServiceImpl.class);
				//shiro filter
		        filter("/oauth/token").through(GuiceShiroFilter.class);
		        filter("/oauth/logout").through(GuiceShiroFilter.class); // SecurityManagement inject

		        // TODO:!!
		        // filter("/oauth/token").through(OAuthTokenServlet.class);
		        

//		        filter("/demo/oauth/token").through(GuiceShiroFilter.class);
		        filter("/dispatch/*").through(GuiceShiroFilter.class);
		        filter("*").through(HttpHeaderFilter.class);
//				bind(ExceptionHandler.class).to(DefaultExceptionHandler.class);

		        // For microprofile config without CDI
		        bind(Config.class).toInstance(ConfigProviderResolver.instance().getConfig());
				bind(Context.class).toInstance(ctx);
			}
		});
		// Install this module to enable Shiro AOP functionality in Guice.
		modulesList.add(new ShiroAopModule());
		
		// @AutoLoadModule loading.
		appendModules(modulesList);
		modulesList.add(new MyShiroWebModule(servletContext));
		return Guice.createInjector(modulesList);
	}
}

