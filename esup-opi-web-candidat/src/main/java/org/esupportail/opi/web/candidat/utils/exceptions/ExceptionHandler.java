package org.esupportail.opi.web.candidat.utils.exceptions;

import org.primefaces.extensions.component.ajaxerrorhandler.AjaxExceptionHandler;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import java.util.Iterator;

import static org.esupportail.opi.web.candidat.utils.exceptions.ExceptionUtils.exceptionUtils;


public class ExceptionHandler extends AjaxExceptionHandler {

	public ExceptionHandler(javax.faces.context.ExceptionHandler wrapped) {
		super(wrapped);
	}

	@Override
	public void handle() throws FacesException {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getResponseComplete()) {
            return;
        }

		Iterable<ExceptionQueuedEvent> exceptionQueuedEvents = getUnhandledExceptionQueuedEvents();
		if (exceptionQueuedEvents != null
				&& exceptionQueuedEvents.iterator() != null) {
			Iterator<ExceptionQueuedEvent> unhandledExceptionQueuedEvents = exceptionQueuedEvents.iterator();

			if (unhandledExceptionQueuedEvents.hasNext()) {
                final ExceptionQueuedEventContext eqec = unhandledExceptionQueuedEvents.next().getContext();
                final Throwable exception = eqec.getException();

                if (exception instanceof ViewExpiredException) {
                    unhandledExceptionQueuedEvents.remove();

                    final String outcome = "/stylesheets/welcome.xhtml?faces-redirect=true&expired=expired";

                    final NavigationHandler navHandler = context.getApplication().getNavigationHandler();
                    navHandler.handleNavigation(context, null, outcome);
                    context.responseComplete();

                } else {
//                    exception.printStackTrace();
                    exceptionUtils.send(exception);
                }
			}
		}

		getWrapped().handle();
	}
}
