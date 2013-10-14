package org.esupportail.opi.domain.beans.etat;

final class States {
    private States() { throw new UnsupportedOperationException(); }

    static final String STATE_COMPLET = "STATE.COMPLET";
    static final String STATE_INCOMPLET = "STATE.INCOMPLET";
    static final String STATE_NON_RENSEIGNE = "STATE.NON_RENSEIGNE";
    static final String STATE_ARRIVE = "STATE.ARRIVE";
    static final String STATE_ARRIVE_COMPLET = "STATE.ARRIVE_COMPLET";
    static final String STATE_ARRIVE_INCOMPLET = "STATE.ARRIVE_INCOMPLET";
    static final String STATE_NON_ARRIVE = "STATE.NON_ARRIVE";
    static final String STATE_CONFIRM = "STATE.CONFIRM";
    static final String STATE_DESIST = "STATE.DESIST";
    static final String STATE_NULL = "STATE.NULL";
}
