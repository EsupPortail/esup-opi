package org.esupportail.opi.web.utils.fj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: p-opidev
 * Date: 03/07/13
 * Time: 18:28
 * To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class PredicatesTest {

    @Test
    public void testPredicatesConstructorIsPrivate() throws Exception {
        //Given Predicates private constructor
        //When
        Constructor constructor = Predicates.class.getDeclaredConstructor();
        //Then
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
    }
}
