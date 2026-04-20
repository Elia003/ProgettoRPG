package it.unicam.cs.mpgc.rpg123022.Risorse;

import it.unicam.cs.mpgc.rpg123022.Enum.TipoRisorsa;

public class Stamina extends Risorsa {
   private int maxValore;

   public Stamina() {
       this.maxValore = 10;
       this.valore = maxValore;
   }

    @Override
    public TipoRisorsa getTipo() {
        return TipoRisorsa.STAMINA;
    }

    public void recupera(int quantita) {
        valore = Math.min(valore + quantita, maxValore);
    }
}
