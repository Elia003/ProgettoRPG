package it.unicam.cs.mpgc.rpg123022.Builder.personaggi;

import it.unicam.cs.mpgc.rpg123022.Personaggi.Barbaro;

public class BarbaroBuilder extends AbstractPersonaggioBuilder<BarbaroBuilder> {
    @Override
    protected BarbaroBuilder self() {
        return this;
    }

    @Override
    public Barbaro build() {
        return new Barbaro(id, nome, genere, coloreCapelli);
    }
}
