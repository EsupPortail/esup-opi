package org.esupportail.opi.web.utils.exceptions;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class ExHandlerFactory extends ExceptionHandlerFactory {

    private final Logger log = new LoggerImpl(getClass());

    private ExceptionHandlerFactory parent;

    public ExHandlerFactory() {
        super();
    }

    public ExHandlerFactory(final ExceptionHandlerFactory parent) {
        super();
        this.parent = parent;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        if (log.isDebugEnabled()) {
            log.debug("entering ExceptionHandlerFactory::getExceptionHandler");
        }
        if (parent != null) {
            ExceptionHandler result = parent.getExceptionHandler();
            result = new ExHandler(result);

            return result;
        }
        return null;
    }

    @Override
    public ExceptionHandlerFactory getWrapped() {
        return parent;
    }
}
