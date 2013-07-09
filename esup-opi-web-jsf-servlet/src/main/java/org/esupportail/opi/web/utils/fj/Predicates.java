package org.esupportail.opi.web.utils.fj;

import fj.F;
import org.esupportail.opi.domain.beans.parameters.TypeTraitement;
import org.esupportail.opi.domain.beans.user.candidature.Avis;
import org.esupportail.opi.web.beans.pojo.IndVoeuPojo;
import org.esupportail.opi.web.beans.pojo.IndividuPojo;

/**
 * Created with IntelliJ IDEA.
 * User: p-opidev
 * Date: 02/07/13
 * Time: 16:14
 * To change this template use File | Settings | File Templates.
 */
public class Predicates {

    private Predicates() {
        super();
    }

    /**
     * Remove items from  a {@link fj.data.Stream} of {@link IndVoeuPojo} matching param
     *
     * @param typeTraitement the given param
     * @return the filtered {@link fj.data.Stream}
     */
    public static F<IndVoeuPojo, Boolean> isTraitementNotEquals(final TypeTraitement typeTraitement) {
        return new F<IndVoeuPojo, Boolean>() {
            public Boolean f(IndVoeuPojo indVoeuPojo) {
                return !indVoeuPojo.getTypeTraitement().equals(typeTraitement);
            }
        };
    }

    /**
     * si onlyValidate = true, on enlève les voeux non validés
     * et inversement
     *
     * @param onlyValidate
     * @return
     */
    public static F<IndVoeuPojo, Boolean> keepOnlyAvisWithValidationEquals(final Boolean onlyValidate) {
        return new F<IndVoeuPojo, Boolean>() {
            public Boolean f(IndVoeuPojo indVoeuPojo) {
                Avis avis = indVoeuPojo.getAvisEnService();
                return avis != null
                        && avis.getValidation().equals(onlyValidate);
            }
        };
    }

    /**
     * Remove items from  a {@link fj.data.Stream} of {@link IndividuPojo} where its {@link IndVoeuPojo} list is empty
     *
     * @return the filtered {@link fj.data.Stream}
     */
    public static F<IndividuPojo, Boolean> isIndWithoutVoeux() {
        return new F<IndividuPojo, Boolean>() {
            public Boolean f(IndividuPojo ip) {
                return !ip.getIndVoeuxPojo().isEmpty();
            }
        };
    }
}
