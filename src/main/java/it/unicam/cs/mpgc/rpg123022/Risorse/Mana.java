package it.unicam.cs.mpgc.rpg123022.Risorse;

import it.unicam.cs.mpgc.rpg123022.Enum.TipoRisorsa;

public class Mana extends Risorsa {
    private int maxValore;
     public Mana() {
         this.maxValore = 10;
         this.valore = maxValore;
     }

    @Override
    public TipoRisorsa getTipo() {
        return TipoRisorsa.MANA;
    }

    public void recupera(int quantita) {
        valore = Math.min(valore + quantita, maxValore);
    }

    public String toString() {
         return "Mana: " + valore;
    }
}
