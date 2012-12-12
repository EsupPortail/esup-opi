package org.esupportail.opi.web.beans.components;

import org.apache.myfaces.custom.schedule.model.DefaultScheduleEntry;

/**
 * 
 * @author root
 *
 */
public class ExtendedEntry extends DefaultScheduleEntry {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5878707803452195704L;
	
	
	/**
	 * Color of entry.
	 */
	private String color;  
	
	
	/**
	 * Empty constructor.
	 */
	public ExtendedEntry() {  
		super();  
	}  
	
	
    /**
     * @return color
     */
    public String getColor() {  
        return color;  
    }  
    
    
    /**
     * @param color
     */
    public void setColor(final String color) {  
         this.color = color;  
    }  
}
