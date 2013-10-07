package org.esupportail.opi.web.config.rewrite;

import static org.ocpsoft.logging.Logger.Level.*;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.esupportail.opi.web.beans.ManagedCalendar;
import org.esupportail.opi.web.beans.utils.NavigationRulesConst;
import org.ocpsoft.rewrite.config.Condition;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.config.Log;
import org.ocpsoft.rewrite.config.Operation;
import org.ocpsoft.rewrite.context.EvaluationContext;
import org.ocpsoft.rewrite.el.El;
import org.ocpsoft.rewrite.servlet.config.Forward;
import org.ocpsoft.rewrite.servlet.config.HttpCondition;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.HttpOperation;
import org.ocpsoft.rewrite.servlet.config.Redirect;
import org.ocpsoft.rewrite.servlet.config.RequestParameter;
import org.ocpsoft.rewrite.servlet.config.URL;
import org.ocpsoft.rewrite.servlet.http.event.HttpServletRewrite;

public class Navigation extends HttpConfigurationProvider {

	@Inject
	private ManagedCalendar managedCalendar;
	
	@Override
	public int priority() { return 10; }

	@Override
	public Configuration getConfiguration(final ServletContext context) {
		return ConfigurationBuilder.begin()
				
				.addRule()
				.when(URL.matches("http{*}://{*}/stylesheets/cas/accueil.xhtml?ent={ent}&isManager={isManager}").withRequestBinding()
						.and(RequestParameter.exists("isManager"))
						.and(RequestParameter.matches("isManager", "false"))
						.andNot(calIsOpen))
				.perform(Log.message(DEBUG, "ent={ent} isManager={isManager}")
						.and(Forward.to("/stylesheets/candidat/inscriptionClose.xhtml")))
				.where("ent").bindsTo(El.property("sessionController.isInEnt"))
				
				.addRule()
				.when(URL.matches("http{*}://{*}/stylesheets/cas/accueil.xhtml?ent={ent}&isManager={isManager}").withRequestBinding()
						.and(RequestParameter.exists("isManager"))
						.and(RequestParameter.matches("isManager", "false"))
						.and(calIsOpen))
				.perform(Log.message(DEBUG, "ent={ent} isManager={isManager}")
						.and(Forward.to("/stylesheets/cas/iframeWelcome.xhtml")))
				.where("ent").bindsTo(El.property("sessionController.isInEnt"))
				
				.addRule()
				.when(URL.matches("http{*}://{*}/stylesheets/cas/accueil.xhtml?ent={ent}&isManager={isManager}").withRequestBinding()
						.and(RequestParameter.exists("isManager"))
						.and(RequestParameter.matches("isManager", "true")))
				.perform(Log.message(DEBUG, "ent={ent} isManager={isManager}")
						.and(Forward.to("/stylesheets/cas/accueil.xhtml?faces-redirect=true")))
				.where("ent").bindsTo(El.property("sessionController.isInEnt"))
				
				.addRule()
				.when(URL.matches("http{*}://{*}/stylesheets/cas/accueil.xhtml?logout={logout}").withRequestBinding()
						.and(RequestParameter.exists("logout")))
				.perform(Log.message(DEBUG, "logout={logout}")
						.and(logoutInd)
						.and(Redirect.temporary(NavigationRulesConst.APPLI_RESTART)))
				
				;
	}
	

	private final Condition calIsOpen = new HttpCondition() {
		public boolean evaluateHttp(HttpServletRewrite event,
				EvaluationContext context) {
			return managedCalendar.getCalInsIsOpen();
		}
	};
	
	private final Operation logoutInd = new HttpOperation() {
		public void performHttp(HttpServletRewrite event, EvaluationContext context) {
	        HttpServletRequest request = event.getRequest();
	        request.getSession().invalidate();
	        request.getSession(true);
		}
	};
}
