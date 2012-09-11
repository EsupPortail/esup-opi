package org.esupportail.apogee.readens;

import geographiemetier_06062007_impl.servicesmetiers.commun.apogee.education.gouv.GeographieMetierServiceInterface;
import geographiemetier_06062007_impl.servicesmetiers.commun.apogee.education.gouv.GeographieMetierServiceInterfaceService;
import geographiemetier_06062007_impl.servicesmetiers.commun.apogee.education.gouv.WebBaseException;
import gouv.education.apogee.commun.transverse.dto.geographie.paysdto.PaysDTO;

import java.util.List;

import org.esupportail.wssi.services.remote.AnneeUniDTO;
import org.esupportail.wssi.services.remote.ReadEnseignement;
import org.esupportail.wssi.services.remote.ReadEnseignementImplService;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;

public class TestReadEnseignement {

    public static void main(String[] args) {
        ReadEnseignement serv = new ReadEnseignementImplService().getReadEnseignementImplPort();
        try {
        AnneeUniDTO an = serv.anneeUniDTO1("2012");
        System.out.println(an.getLibAnu());
        
        GeographieMetierServiceInterface geoServ = new GeographieMetierServiceInterfaceService().getGeographieMetier();
        List<PaysDTO> pays = geoServ.recupererPays("", "");
        for (PaysDTO p : pays) System.out.println(p.getLibPay());
        
        List<VersionEtapeDTO> lve = serv.getVersionEtapes2(
            serv.getDiplomes("0014", null), "2012");
        for (VersionEtapeDTO ve : lve) System.out.println(ve.getLibWebVet());

        } catch (javax.xml.ws.soap.SOAPFaultException e) {
            e.printStackTrace();
        } catch (WebBaseException e) {
            e.printStackTrace();
        }
    }
    
}
