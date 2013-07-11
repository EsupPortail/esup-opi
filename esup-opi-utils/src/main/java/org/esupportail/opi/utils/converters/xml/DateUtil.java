package org.esupportail.opi.utils.converters.xml;

/**
 * Created with IntelliJ IDEA.
 * User: p-opidev
 * Date: 25/06/13
 * Time: 10:51
 * To change this template use File | Settings | File Templates.
 */

import org.springframework.util.Assert;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;

/**
 * Stateless utility class to handle date manipulations.
 * @author hrambelo (p-opidev)
 */
public final class DateUtil {

    //TODO decide if should merge DateHandler.java here as it is not a static class
    private DateUtil() {
        super();
    }

    /**
     * Transform a XmlGregorianCalendar date into a java.util.Date format.
     * @param gregorianCalendar the date to be trasnformed
     * @return the date transformed
     */
    public static Date transformIntoDate(XMLGregorianCalendar gregorianCalendar){
        Assert.notNull(gregorianCalendar, "You should pass me some gregorianCalendar param to convert into java.util.Date");
        return gregorianCalendar.toGregorianCalendar().getTime();
    }
}
