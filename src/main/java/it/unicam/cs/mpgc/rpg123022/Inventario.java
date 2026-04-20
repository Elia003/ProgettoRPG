package it.unicam.cs.mpgc.rpg123022;

import java.util.ArrayList;
import java.util.List;

public class Inventario {
    private List<Oggetto> oggetti;
    private final int capacitaMax;

    public Inventario(int capacitaMax) {
        this.capacitaMax = capacitaMax;
        this.oggetti = new ArrayList<>();
    }


    public void rimuoviOggetto(Oggetto oggetto) {
        this.oggetti.remove(oggetto);
    }

    public int getPesoTotale() {
        return oggetti.stream()
                .mapToInt(Oggetto::getPeso)
                .sum();
    }

    public List<Oggetto> getOggetti() {
        return oggetti;
    }

    public boolean aggiungiOggetto(Oggetto o) {
        if (getPesoTotale() + o.getPeso() > capacitaMax) {
            System.out.println("Impossibile aggiungere l'oggetto, slot rimanenti: " + slotLiberi());
            return false;
        }
        oggetti.add(o);
        System.out.println("Oggetto : " + o + " equipaggiato con successo");
        return true;
    }

    public int getCapacitaMax() {
        return capacitaMax;
    }

    public int slotLiberi(){
        return capacitaMax - getPesoTotale();
    }

    public String toString(){
        return oggetti.toString()
                + "\nSpazio rimanente: " + slotLiberi();
    }
}
