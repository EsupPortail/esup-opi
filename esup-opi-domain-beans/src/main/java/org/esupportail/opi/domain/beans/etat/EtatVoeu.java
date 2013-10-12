/**
 * 
 */
package org.esupportail.opi.domain.beans.etat;

public enum EtatVoeu implements Etat {
    EtatArrive(States.STATE_ARRIVE),
    EtatArriveComplet(States.STATE_ARRIVE_COMPLET),
    EtatArriveIncomplet(States.STATE_ARRIVE_INCOMPLET),
    EtatNonArrive(States.STATE_NON_ARRIVE),
    EtatConfirme(States.STATE_CONFIRM),
    EtatDesiste(States.STATE_DESIST),
    EtatNull(States.STATE_NULL);

    private final String codeLabel;
    private EtatVoeu(String codeLabel) { this.codeLabel = codeLabel; }

    /**
	 * Display the missing piece if etat is EtatArriveIncomplet.
	 * @return Boolean
	 */
	public Boolean getDisplayPJ() { return this == EtatArriveIncomplet; }
	
	public Boolean getDisplayForms() { return true; }

    @Override
    public String getCodeLabel() { return codeLabel; }

    public static EtatVoeu fromString(String str) {
        switch (str) {
            case States.STATE_ARRIVE: return EtatArrive;
            case States.STATE_ARRIVE_COMPLET: return EtatArriveComplet;
            case States.STATE_ARRIVE_INCOMPLET: return EtatArriveIncomplet;
            case States.STATE_NON_ARRIVE: return EtatNonArrive;
            case States.STATE_CONFIRM: return EtatConfirme;
            case States.STATE_DESIST: return EtatDesiste;
            case States.STATE_NULL: return EtatNull;
            default: throw new IllegalArgumentException("No EtatVoeu enum constant for " + str);
        }
    }
}
