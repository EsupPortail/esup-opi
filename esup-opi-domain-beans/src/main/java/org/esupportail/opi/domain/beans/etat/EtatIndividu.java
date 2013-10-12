/**
 * 
 */
package org.esupportail.opi.domain.beans.etat;



/**
 * @author cleprous
 *
 */
public enum  EtatIndividu implements Etat {
    EtatComplet(States.STATE_COMPLET),
    EtatIncomplet(States.STATE_INCOMPLET);

    private final String codeLabel;
    private EtatIndividu(String codeLabel) { this.codeLabel = codeLabel; }

    /**
	 * if incomplet can not make a wish.
	 * @return Boolean true if this is EtatComplet
	 */
	public Boolean getCanDoChoiceEtape() { return this == EtatComplet; }

    @Override
    public String getCodeLabel() { return codeLabel; }

    public static EtatIndividu fromString(String str) {
        switch (str) {
            case States.STATE_COMPLET: return EtatComplet;
            case States.STATE_INCOMPLET: return EtatIncomplet;
            default: throw new IllegalArgumentException("No EtatIndividu enum constant for " + str);
        }
    }

}
