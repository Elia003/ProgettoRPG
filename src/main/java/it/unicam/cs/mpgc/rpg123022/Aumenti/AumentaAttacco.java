package it.unicam.cs.mpgc.rpg123022.Aumenti;

import it.unicam.cs.mpgc.rpg123022.Personaggi.Personaggio;

public class AumentaAttacco implements Effetto {
    private int valore;

    public AumentaAttacco(int valore){
        this.valore = valore;
    }
    @Override
    public void applica(Personaggio p) {
        p.setAttacco(p.getAttacco()+valore);
    }

    @Override
    public void rimuovi(Personaggio p) {
        p.setAttacco(p.getAttacco()-valore);
    }

}
