package org.esupportail.opi.domain.beans.etat;

public enum  EtatNonRenseigne implements Etat {
    EtatNonRenseigne(States.STATE_NON_RENSEIGNE);

    private final String codeLabel;
    private EtatNonRenseigne(String codeLabel) { this.codeLabel = codeLabel; }

    @Override
	public String getCodeLabel() { return codeLabel; }

    public static EtatNonRenseigne fromString(String str) {
        switch (str) {
            case States.STATE_NON_RENSEIGNE : return EtatNonRenseigne;
            default: throw new IllegalArgumentException("No EtatNonRenseigne enum constant for " + str);
        }
    }

}
