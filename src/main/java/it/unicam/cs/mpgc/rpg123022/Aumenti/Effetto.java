package it.unicam.cs.mpgc.rpg123022.Aumenti;

import it.unicam.cs.mpgc.rpg123022.Personaggi.Personaggio;

public interface Effetto {
    void applica(Personaggio p);

    void rimuovi(Personaggio p);
}
