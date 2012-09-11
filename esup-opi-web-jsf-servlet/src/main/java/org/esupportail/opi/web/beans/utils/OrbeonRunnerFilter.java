package org.esupportail.opi.web.beans.utils;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.web.filter.GenericFilterBean;

/**
 * @author root
 *
 */
public class OrbeonRunnerFilter extends GenericFilterBean {

	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * 
	 */
	private FilterConfig filterConfig;

	/*
	 ******************* INIT ************************* */

	/*
	 ******************* METHODS ********************** */

	/** 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, 
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(final ServletRequest request, final ServletResponse response,
			final FilterChain chain) throws IOException, ServletException {
		if (filterConfig == null) {
			return;
		}



	}

	/*
	 ******************* ACCESSORS ******************** */

}
