package fr.eni.sortir.utils;

public enum State {
	CREATED("Créée"),
    OPENED("Ouverte"),
    FENCED("Clôturée"),
    ACTIVITY_IN_PROGRESS("Activité en cours"),
    PASSED("Passée"),
    CANCELED("Annulée");

    private final String text;

    State(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
