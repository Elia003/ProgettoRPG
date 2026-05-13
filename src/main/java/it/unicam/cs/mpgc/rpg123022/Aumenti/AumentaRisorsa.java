package it.unicam.cs.mpgc.rpg123022.Aumenti;

import it.unicam.cs.mpgc.rpg123022.Enum.TipoRisorsa;
import it.unicam.cs.mpgc.rpg123022.Personaggi.Personaggio;

public class AumentaRisorsa implements Effetto {
    private final int valore;
    private final TipoRisorsa tipo;

    public AumentaRisorsa(int valore, TipoRisorsa tipo) {
        this.valore = valore;
        this.tipo = tipo;
    }

    @Override
    public void applica(Personaggio p) {
        if (p.getRisorsa().getTipo() == tipo) {
            p.getRisorsa().aumenta(valore);
        }
    }

    @Override
    public void rimuovi(Personaggio p) {
        p.getRisorsa().rimuoviBuff(valore);
    }

    @Override
    public String toString() {
        return "+" + valore + " " +tipo;
    }
}
