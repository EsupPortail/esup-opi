/**
 * 
 */
package org.esupportail.opi.utils.exceptions;

import org.esupportail.commons.exceptions.EsupException;

public class ExportException extends EsupException {

	public ExportException() {
		super();
	}

	public ExportException(final String message) {
		super(message);
	}

	public ExportException(final Throwable cause) {
		super(cause);
	}

	public ExportException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
