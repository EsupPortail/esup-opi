package org.esupportail.opi.web.utils.fj;

import fj.F;
import fj.data.Stream;
import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.beans.parameters.Transfert;
import org.esupportail.opi.domain.beans.parameters.TypeTraitement;
import org.esupportail.opi.domain.beans.user.candidature.Avis;
import org.esupportail.opi.web.beans.parameters.RegimeInscription;
import org.esupportail.opi.web.beans.pojo.IndVoeuPojo;
import org.esupportail.opi.web.beans.pojo.IndividuPojo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.*;

import static fj.data.Stream.iterableStream;
import static org.esupportail.opi.web.utils.fj.Conversions.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: p-opidev
 * Date: 03/07/13
 * Time: 18:27
 * To change this template use File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConversionsTest {

    @Mock
    private DomainApoService mockApoServ;

    @Mock
    private I18nService mockI18n;

    private Transfert transfert;
    private Transfert nonTransfert;


    @Test
    public void testConversionesConstructorIsPrivate() throws Exception {
        //Given Predicates private constructor
        //When
        Constructor constructor = Conversions.class.getDeclaredConstructor();
        //Then
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
    }
}
