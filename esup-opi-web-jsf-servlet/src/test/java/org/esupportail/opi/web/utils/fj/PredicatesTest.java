package org.esupportail.opi.web.utils.fj;

import fj.data.Stream;
import org.esupportail.opi.domain.beans.parameters.TypeTraitement;
import org.esupportail.opi.domain.beans.user.candidature.Avis;
import org.esupportail.opi.web.beans.pojo.IndVoeuPojo;
import org.esupportail.opi.web.beans.pojo.IndividuPojo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static fj.data.Stream.iterableStream;
import static org.esupportail.opi.web.utils.fj.Predicates.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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

    @Test
    public void testIsTraitementNotEquals() throws Exception {
        //Given
        TypeTraitement transfert = buildTransfertTraitement();
        TypeTraitement nonTransfert = buildNonTransfertTraitement();
        Stream<IndVoeuPojo> stream = iterableStream(buildListIndVoeuPojo(transfert, nonTransfert));
        assertEquals("Should've built items of 3", 3, stream.length());
        assertEquals("First item is not transfert treatment", nonTransfert, stream.index(0).getTypeTraitement());
        assertEquals("Third item is not transfert treatment", nonTransfert, stream.index(2).getTypeTraitement());
        //and that this one should be filtered
        assertEquals("Second item is the culprit transfert treatment", transfert, stream.index(1).getTypeTraitement());
        //When
        Stream<IndVoeuPojo> result = stream.filter(isTraitementNotEquals(transfert));
        //Then
        assertEquals("Should have filtered the second item", stream.length() - 1, result.length());
        assertEquals(nonTransfert, result.index(0).getTypeTraitement());
        assertEquals(nonTransfert, result.index(1).getTypeTraitement());
    }

    @Test
    public void testIsOnlyValidate() throws Exception {
        //Given
        Stream<IndVoeuPojo> stream = iterableStream(buildListIndVoeuPojoWithAvis());
        assertEquals("Should've built items of 3", 3, stream.length());
        assertEquals("First item is valid avis", true, stream.index(0).getAvisEnService().getValidation());
        //and that these two should be filtered
        assertEquals("Second  item is invalid avis", false, stream.index(1).getAvisEnService().getValidation());
        assertEquals("Third  item doesnt even have an avis", null, stream.index(2).getAvisEnService());
        //When
        Stream<IndVoeuPojo> result = stream.filter(keepOnlyAvisWithValidationEquals(true));
        //Then
        assertEquals("Should've filtered the two invalid avis", stream.length() - 2, result.length());
        assertEquals("Should be valid avis left", true, result.index(0).getAvisEnService().getValidation());
    }

    @Test
    public void testIsOnlyNotValidate() throws Exception {
        //Given
        Stream<IndVoeuPojo> stream = iterableStream(buildListIndVoeuPojoWithAvis());
        assertEquals("Should've built items of 3", 3, stream.length());
        assertEquals("Second  item is invalid avis", false, stream.index(1).getAvisEnService().getValidation());
        assertEquals("Third  item doesnt even have an avis", null, stream.index(2).getAvisEnService());
        //and that this one should be filtered
        assertEquals("First item is valid avis", true, stream.index(0).getAvisEnService().getValidation());
        //When
        Stream<IndVoeuPojo> result = stream.filter(keepOnlyAvisWithValidationEquals(false));
        //Then
        assertEquals("Should've filtered the invalid avis and the null avis", stream.length() - 2, result.length());
        assertEquals("Should be invalid avis left", false, result.index(0).getAvisEnService().getValidation());
    }

    @Test
    public void testIsIndWithoutVoeux() throws Exception {
        //Given
        Stream<IndividuPojo> stream = iterableStream(buildListIndividuPojo());
        assertEquals("Should've built items of 3", 3, stream.length());
        assertFalse("Second item should have non empty voeux", stream.index(1).getIndVoeuxPojo().isEmpty());
        assertFalse("Third  item should have non empty voeux", stream.index(2).getIndVoeuxPojo().isEmpty());
        //that this one should be filtered
        assertTrue("First item has empty voeux", stream.index(0).getIndVoeuxPojo().isEmpty());
        //When
        Stream<IndividuPojo> result = stream.filter(isIndWithoutVoeux());
        //Then
        assertEquals("Should've removed the one with invalid voeux", stream.length() - 1, result.length());
        assertEquals("ipj2", result.index(0).getEtatIndBac());
        assertEquals("ipj3", result.index(1).getEtatIndBac());
    }

    private Iterable<IndividuPojo> buildListIndividuPojo() {
        List<IndividuPojo> result = new ArrayList<>();
        IndividuPojo ipj1 = mock(IndividuPojo.class);
        IndividuPojo ipj2 = mock(IndividuPojo.class);
        IndividuPojo ipj3 = mock(IndividuPojo.class);
        Set<IndVoeuPojo> list = new HashSet<>();
        Set<IndVoeuPojo> nonEmpty = spy(list);
        Set<IndVoeuPojo> empty = spy(list);
        when(empty.isEmpty()).thenReturn(true);
        when(nonEmpty.isEmpty()).thenReturn(false);
        when(ipj1.getEtatIndBac()).thenReturn("ipj1");
        when(ipj1.getIndVoeuxPojo()).thenReturn(empty);
        when(ipj2.getEtatIndBac()).thenReturn("ipj2");
        when(ipj2.getIndVoeuxPojo()).thenReturn(nonEmpty);
        when(ipj3.getEtatIndBac()).thenReturn("ipj3");
        when(ipj3.getIndVoeuxPojo()).thenReturn(nonEmpty);
        result.add(ipj1);
        result.add(ipj2);
        result.add(ipj3);
        return result;
    }

    /**
     * Build list of 3 IndVoeuPojo where only 1 avis is valid, the third doesnt even contain avis
     *
     * @return
     */
    private List<IndVoeuPojo> buildListIndVoeuPojoWithAvis() {
        List<IndVoeuPojo> spyListIndVoeu = new ArrayList<>();
        Avis validAvis = mock(Avis.class);
        Avis invalidAvis = mock(Avis.class);
        when(validAvis.getValidation()).thenReturn(true);
        when(invalidAvis.getValidation()).thenReturn(false);
        IndVoeuPojo ivp1 = mock(IndVoeuPojo.class);
        IndVoeuPojo ivp2 = mock(IndVoeuPojo.class);
        IndVoeuPojo ivp3 = mock(IndVoeuPojo.class);
        when(ivp1.getAvisEnService()).thenReturn(validAvis);
        when(ivp2.getAvisEnService()).thenReturn(invalidAvis);
        spyListIndVoeu.add(ivp1);
        spyListIndVoeu.add(ivp2);
        spyListIndVoeu.add(ivp3);
        return spyListIndVoeu;
    }

    private List<IndVoeuPojo> buildListIndVoeuPojo(TypeTraitement transfert, TypeTraitement notTransfert) {
        List<IndVoeuPojo> spyListIndVoeu = new ArrayList<>();
        IndVoeuPojo ivp1 = mock(IndVoeuPojo.class);
        IndVoeuPojo ivp2 = mock(IndVoeuPojo.class);
        IndVoeuPojo ivp3 = mock(IndVoeuPojo.class);
        when(ivp1.getTypeTraitement()).thenReturn(notTransfert);
        when(ivp2.getTypeTraitement()).thenReturn(transfert);
        when(ivp3.getTypeTraitement()).thenReturn(notTransfert);
        spyListIndVoeu.add(ivp1);
        spyListIndVoeu.add(ivp2);
        spyListIndVoeu.add(ivp3);
        assertEquals(3, spyListIndVoeu.size());
        assertEquals(notTransfert, ivp1.getTypeTraitement());
        assertEquals(transfert, ivp2.getTypeTraitement());
        assertEquals(notTransfert, ivp3.getTypeTraitement());
        return spyListIndVoeu;
    }

    private TypeTraitement buildTransfertTraitement() {
        TypeTraitement transfert = mock(TypeTraitement.class);
        when(transfert.getCode()).thenReturn("TR");
        when(transfert.getLabel()).thenReturn("Transfert");
        return transfert;
    }

    private TypeTraitement buildNonTransfertTraitement() {
        TypeTraitement notTransfert = mock(TypeTraitement.class);
        when(notTransfert.getCode()).thenReturn("NTR");
        when(notTransfert.getLabel()).thenReturn("NonTransfert");
        return notTransfert;
    }
}
