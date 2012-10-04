package org.esupportail.opi.web.beans.paginator;

import org.esupportail.commons.services.paginator.ListPaginator;
import org.esupportail.commons.utils.ContextUtils;

public abstract class OpiListPaginator<E> extends ListPaginator<E> {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -33645137071692163L;

	/*
	 ******************* PROPERTIES ******************* */

	private boolean loadRunning = false;
	
	/*
	 ******************* INIT ******************* */
	
	public OpiListPaginator() {
		super();
	}

	
	/**
	 * @see org.esupportail.commons.web.controllers.Resettable#reset()
	 */
	public void reset() {
		super.reset();
		loadRunning = false;
	}

	/**
	 * @return the loaded marker name.
	 */
	protected String getRequestLoadedMarker() {
		return getClass().getName() + "." + hashCode() + ".loaded";
	}

	/**
	 * @return true if the paginator has already been loaded for this request.
	 */
	protected boolean isLoadedForThisRequest() {
		Boolean marker = (Boolean) ContextUtils.getRequestAttribute(getRequestLoadedMarker()); 
		return marker != null && marker;
	}

	/**
	 * Set the paginator as loaded for this request.
	 */
	protected void setLoadedForThisRequest() {
		ContextUtils.setRequestAttribute(getRequestLoadedMarker(), Boolean.TRUE);
	}

	/**
	 * @see org.esupportail.commons.web.beans.Paginator#forceReload()
	 */
	public void forceReload() {
		ContextUtils.setRequestAttribute(getRequestLoadedMarker(), Boolean.FALSE);
	}

	/**
	 * Load the items.
	 */
	protected void loadItems() {
		if (!loadRunning) {
			loadRunning = true;
			if (!isLoadedForThisRequest()) {
				loadItemsInternal();
				setLoadedForThisRequest();
			}
			loadRunning = false;
		}
	}
	
	/*
	 ******************* ACCESSORS ******************* */
	
}
