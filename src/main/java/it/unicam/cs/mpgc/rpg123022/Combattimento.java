package it.unicam.cs.mpgc.rpg123022;

import it.unicam.cs.mpgc.rpg123022.Enum.TipoOggetto;
import it.unicam.cs.mpgc.rpg123022.Personaggi.Personaggio;

import java.util.concurrent.ThreadLocalRandom;

public class Combattimento {
    private final Personaggio giocatore1;
    private final Personaggio giocatore2;
    private Personaggio giocatoreCorrente;
    private int round;

    public Combattimento(Personaggio giocatore1, Personaggio giocatore2) {
        this.giocatore1 = giocatore1;
        this.giocatore2 = giocatore2;
        this.round = 0;
        this.giocatoreCorrente = ThreadLocalRandom.current().nextBoolean() ? giocatore1 : giocatore2;
    }

    public void inizia() {
        StatoPersonaggio statoGiocatore1 = new StatoPersonaggio(giocatore1);
        StatoPersonaggio statoGiocatore2 = new StatoPersonaggio(giocatore2);

        System.out.println("Inizio combattimento tra " + giocatore1.getNome() + " e " + giocatore2.getNome());
        System.out.println("Il primo ad attaccare e' " + giocatoreCorrente.getNome());

        while (giocatore1.getHp() > 0 && giocatore2.getHp() > 0) {
            eseguiTurno();
        }

        Personaggio vincitore = giocatore1.getHp() > 0 ? giocatore1 : giocatore2;
        Personaggio sconfitto = vincitore == giocatore1 ? giocatore2 : giocatore1;

        System.out.println("\nCombattimento terminato");
        System.out.println("Vincitore: " + vincitore.getNome() + " il " + vincitore.getClasse());
        System.out.println("Sconfitto: " + sconfitto.getNome() + " il " + sconfitto.getClasse());

        statoGiocatore1.ripristina();
        statoGiocatore2.ripristina();

        round = 0;
        giocatoreCorrente = ThreadLocalRandom.current().nextBoolean() ? giocatore1 : giocatore2;

        System.out.println("\nStatistiche ripristinate dopo il combattimento");
        System.out.println(giocatore1.getNome() + ": HP " + giocatore1.getHp() + ", Difesa " + giocatore1.getDifesa() + ", Attacco " + giocatore1.getAttacco() + ", Risorsa " + giocatore1.getRisorsa().getValore());
        System.out.println(giocatore2.getNome() + ": HP " + giocatore2.getHp() + ", Difesa " + giocatore2.getDifesa() + ", Attacco " + giocatore2.getAttacco() + ", Risorsa " + giocatore2.getRisorsa().getValore());
    }

    private void eseguiTurno() {
        round++;

        Personaggio attaccante = giocatoreCorrente;
        Personaggio difensore = giocatoreCorrente == giocatore1 ? giocatore2 : giocatore1;
        int risorsaPrima = attaccante.getRisorsa().getValore();
        int costoAttacco = calcolaCostoAttacco(attaccante);

        attaccante.getRisorsa().recupera(1);

        System.out.println("\nRound " + round);
        System.out.println("Attaccante: " + attaccante.getNome() + " il " + attaccante.getClasse());
        System.out.println("Difensore: " + difensore.getNome() + " il " + difensore.getClasse());
        System.out.println("Risorsa " + attaccante.getNome() + ": " + risorsaPrima + " -> " + attaccante.getRisorsa().getValore() + " dopo il recupero");
        System.out.println("Costo attacco armi equipaggiate: " + costoAttacco);

        if (!attaccante.getRisorsa().consuma(costoAttacco)) {
            System.out.println(attaccante.getNome() + " non ha abbastanza " + attaccante.getRisorsa().getTipo().name().toLowerCase() + " per attaccare.");
            System.out.println("Il round viene saltato.");
            prossimoTurno();
            return;
        }

        int dannoBase = attaccante.getAttacco();
        int dannoRimanente = dannoBase;
        int difesaPrima = difensore.getDifesa();
        int hpPrima = difensore.getHp();

        System.out.println(attaccante.getNome() + " consuma " + costoAttacco + " " + attaccante.getRisorsa().getTipo().name().toLowerCase() + " e attacca per " + dannoBase);

        if (difensore.getDifesa() > 0) {
            if (dannoRimanente <= difensore.getDifesa()) {
                difensore.setDifesa(difensore.getDifesa() - dannoRimanente);
                dannoRimanente = 0;
            } else {
                dannoRimanente -= difensore.getDifesa();
                difensore.setDifesa(0);
            }
        }

        if (dannoRimanente > 0) {
            difensore.setHp(Math.max(0, difensore.getHp() - dannoRimanente));
        }

        System.out.println("Difesa " + difensore.getNome() + ": " + difesaPrima + " -> " + difensore.getDifesa());
        System.out.println("HP " + difensore.getNome() + ": " + hpPrima + " -> " + difensore.getHp());
        System.out.println("Danno agli HP: " + dannoRimanente);
        System.out.println("Risorsa rimanente " + attaccante.getNome() + ": " + attaccante.getRisorsa().getValore());

        if (difensore.getHp() > 0) {
            prossimoTurno();
        }
    }

    private int calcolaCostoAttacco(Personaggio personaggio) {
        int consumoArmi = personaggio.getInventario().getOggetti().stream()
                .filter(oggetto -> oggetto.getTipo() == TipoOggetto.ARMA)
                .mapToInt(Oggetto::getConsumo)
                .sum();

        return consumoArmi > 0 ? consumoArmi : 2;
    }

    private void prossimoTurno() {
        if (giocatoreCorrente == giocatore1) {
            giocatoreCorrente = giocatore2;
        } else {
            giocatoreCorrente = giocatore1;
        }
    }

    @Override
    public String toString() {
        return "Combattimento"
                + "\nRound: " + round
                + "\nTurno corrente: " + giocatoreCorrente.getNome()
                + "\n\nGiocatore 1:\n" + giocatore1
                + "\n\nGiocatore 2:\n" + giocatore2;
    }

    private static class StatoPersonaggio {
        private final Personaggio personaggio;
        private final int hp;
        private final int difesa;
        private final int attacco;
        private final int risorsa;

        private StatoPersonaggio(Personaggio personaggio) {
            this.personaggio = personaggio;
            this.hp = personaggio.getHp();
            this.difesa = personaggio.getDifesa();
            this.attacco = personaggio.getAttacco();
            this.risorsa = personaggio.getRisorsa().getValore();
        }

        private void ripristina() {
            personaggio.setHp(hp);
            personaggio.setDifesa(difesa);
            personaggio.setAttacco(attacco);
            personaggio.getRisorsa().setValore(risorsa);
        }
    }
}
