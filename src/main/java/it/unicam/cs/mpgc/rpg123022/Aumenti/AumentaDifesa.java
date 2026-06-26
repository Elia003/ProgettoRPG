package it.unicam.cs.mpgc.rpg123022.Aumenti;

import it.unicam.cs.mpgc.rpg123022.Personaggi.Personaggio;

public class AumentaDifesa implements Effetto {
    private final int valore;

    public AumentaDifesa(int valore) {
        this.valore = valore;

    }

    public int getValore() {
        return valore;
    }

    @Override
    public void applica(Personaggio p) {
        p.setDifesa(p.getDifesa() + valore);
    }

    @Override
    public void rimuovi(Personaggio p) {
        p.setDifesa(p.getDifesa() - valore);
    }

    @Override
    public String toString() {
        return "+ " + valore + " Difesa";
    }
}
