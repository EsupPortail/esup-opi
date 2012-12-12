package org.esupportail.opi.web.beans.components;

import org.apache.myfaces.custom.schedule.DefaultScheduleEntryRenderer;
import org.apache.myfaces.custom.schedule.HtmlSchedule;
import org.apache.myfaces.custom.schedule.model.ScheduleDay;
import org.apache.myfaces.custom.schedule.model.ScheduleEntry;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

/**
 * 
 * @author root
 *
 */
public class ExtendedEntryRenderer extends DefaultScheduleEntryRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1129608277106819769L;
	
	
	/**
	 * Empty Contrustor.
	 */
	public ExtendedEntryRenderer() {
		super();
	}
	
	
	/**
	 * @see org.apache.myfaces.custom.schedule.renderer.DefaultScheduleEntryRenderer#getColor(
	 * 		javax.faces.context.FacesContext, 
	 *		org.apache.myfaces.custom.schedule.HtmlSchedule,
	 * 		org.apache.myfaces.custom.schedule.model.ScheduleEntry, boolean)
	 */
	@Override
	public String getColor(final FacesContext context, 
			final HtmlSchedule schedule,  
				final ScheduleEntry entry,
				final boolean selected) {  
	
		ExtendedEntry ent = (ExtendedEntry) entry;  
	    return ent.getColor();
	}	
	
	
	/**
	 * @see org.apache.myfaces.custom.schedule.renderer.DefaultScheduleEntryRenderer#renderCompactContent(
	 * javax.faces.context.FacesContext, javax.faces.context.ResponseWriter, 
	 * org.apache.myfaces.custom.schedule.HtmlSchedule, 
	 * org.apache.myfaces.custom.schedule.model.ScheduleDay, 
	 * org.apache.myfaces.custom.schedule.model.ScheduleEntry, boolean)
	 */
	@Override
	protected void renderCompactContent(final FacesContext context, final ResponseWriter writer, 
			final HtmlSchedule schedule, final ScheduleDay day, 
			final ScheduleEntry entry, final boolean selected) throws IOException {  
	  
      StringBuffer text = new StringBuffer();  
        Date startTime = entry.getStartTime();  

        if (day.getDayStart().after(entry.getStartTime())) {
        	startTime = day.getDayStart();  
        }  
        Date endTime = entry.getEndTime();  
	
        if (day.getDayEnd().before(entry.getEndTime())) {
        	endTime = day.getDayEnd();  
        }  
        
	    if (!entry.isAllDay()) {  

	    	DateFormat format = DateFormat.getTimeInstance(DateFormat.SHORT);  
	    	text.append(format.format(startTime));  
	    	
	    	if (!startTime.equals(endTime)) {  
	        
	    		text.append("-");  
	    		text.append(format.format(endTime));  
	    	}  
	        text.append(": ");  
	    }  
	    text.append(entry.getTitle());  
	    
	    StringBuffer entryStyle = new StringBuffer();  
	    entryStyle.append("height: 100%; width: 100%;");  
	    String entryColor = getColor(context, schedule, entry, selected);  
	    if (entryColor != null) {
	    	entryStyle.append("border-color: ");  
	        entryStyle.append(entryColor);  
	        entryStyle.append(";");  
	        entryStyle.append("background-color: ");  
	        entryStyle.append(entryColor);  
	        entryStyle.append(";");  
	    }  
	    writer.startElement(HTML.DIV_ELEM, null);  
	    writer.writeAttribute(HTML.STYLE_ATTR, entryStyle.toString(), null);  
	    writer.writeText(text.toString(), null);  
	    writer.endElement(HTML.DIV_ELEM);
	}
	
	
	/** 
	 * @see org.apache.myfaces.custom.schedule.renderer.DefaultScheduleEntryRenderer#renderDetailedContentText(
	 * javax.faces.context.FacesContext, javax.faces.context.ResponseWriter, 
	 * org.apache.myfaces.custom.schedule.HtmlSchedule, 
	 * org.apache.myfaces.custom.schedule.model.ScheduleDay, 
	 * org.apache.myfaces.custom.schedule.model.ScheduleEntry, 
	 * boolean)
	 */
	@Override
	protected void renderDetailedContentText(final FacesContext context, final ResponseWriter writer,
            final HtmlSchedule schedule, final ScheduleDay day, 
            final ScheduleEntry entry, final boolean selected) throws IOException {
		
		StringBuffer entryStyle = new StringBuffer();  
	    String entryColor = getColor(context, schedule, entry, selected);  
	    if (entryColor != null) {
	    	entryStyle.append("margin: 2px; ");
	    	entryStyle.append("background-color: " + entryColor + ";");  
	    }
	        
        // write the title of the entry
        if (entry.getTitle() != null) {
        	
        	writer.startElement(HTML.DIV_ELEM, null);
        	writer.writeAttribute(HTML.STYLE_ATTR, entryStyle.toString(), null);  
     	    writer.writeText(entry.getTitle(), null);  
     	    writer.endElement(HTML.DIV_ELEM);
        }
        if (entry.getSubtitle() != null) {
        	
            writer.startElement("br", schedule);
            writer.endElement("br");
            writer.startElement(HTML.SPAN_ELEM, schedule);
            writer.writeAttribute(HTML.CLASS_ATTR, getStyleClass(
                    schedule, "subtitle"), null);
            writer.writeText(entry.getSubtitle(), null);
            writer.endElement(HTML.SPAN_ELEM);
        }    	
    }
} 


