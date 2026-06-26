package it.unicam.cs.mpgc.rpg123022.ArmadioPersonaggi;

import it.unicam.cs.mpgc.rpg123022.Personaggi.Personaggio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArmadioPersonaggi {
    public static final int MAX_PERSONAGGI = 3;

    private static final ArmadioPersonaggi INSTANCE = new ArmadioPersonaggi();

    private final List<Personaggio> personaggi;
    private Personaggio personaggioCorrente;

    private ArmadioPersonaggi() {
        this.personaggi = new ArrayList<>();
    }

    public static ArmadioPersonaggi getInstance() {
        return INSTANCE;
    }

    public boolean aggiungiPersonaggio(Personaggio personaggio) {
        if (personaggi.size() >= MAX_PERSONAGGI) {
            return false;
        }

        personaggi.add(personaggio);
        personaggioCorrente = personaggio;
        return true;
    }

    public List<Personaggio> getPersonaggi() {
        return Collections.unmodifiableList(personaggi);
    }

    public int getNumeroPersonaggi() {
        return personaggi.size();
    }

    public boolean puoCreareNuovoPersonaggio() {
        return personaggi.size() < MAX_PERSONAGGI;
    }

    public int getProssimoId() {
        return personaggi.stream()
                .mapToInt(Personaggio::getId)
                .max()
                .orElse(0) + 1;
    }

    public Personaggio getPersonaggioCorrente() {
        return personaggioCorrente;
    }

    public void setPersonaggioCorrente(Personaggio personaggioCorrente) {
        this.personaggioCorrente = personaggioCorrente;
    }

    public void rimuoviPersonaggio(Personaggio p) {
        personaggi.remove(p);
        if (personaggi.isEmpty()) {
            personaggioCorrente = null;
        } else if (personaggioCorrente == p) {
            personaggioCorrente = personaggi.get(0);
        }
    }

    public void caricaPersonaggi(List<Personaggio> personaggiCaricati) {
        personaggi.clear();
        personaggi.addAll(personaggiCaricati);

        if (personaggi.isEmpty()) {
            personaggioCorrente = null;
        } else {
            personaggioCorrente = personaggi.get(personaggi.size() - 1);
        }
    }
}
