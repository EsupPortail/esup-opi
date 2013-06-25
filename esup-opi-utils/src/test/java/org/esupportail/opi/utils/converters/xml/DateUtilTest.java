package org.esupportail.opi.utils.converters.xml;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: p-opidev
 * Date: 25/06/13
 * Time: 11:21
 * To change this template use File | Settings | File Templates.
 */
public class DateUtilTest {

    @Test
    public void testDateUtilConstructorIsPrivate() throws Exception {
        //Given DAtaUtil private constructor
        //When
        Constructor constructor = DateUtil.class.getDeclaredConstructor();
        //Then
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
    }

    @Test
    public void testTransformIntoDate() throws Exception {
        //Given
        XMLGregorianCalendar date = new XMLGregorianCalendarImpl();
        date.setDay(1);
        date.setMonth(12);
        date.setYear(2009);
        //When
        Date result = DateUtil.transformIntoDate(date);
        //Then
        assertEquals(result.getTime(), date.toGregorianCalendar().getTimeInMillis());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransformIntoDateFailonNullParam() throws Exception {
        //Given a null param
        //When
        DateUtil.transformIntoDate(null);
        //Then should throw exception
    }
}
