package it.unicam.cs.mpgc.rpg123022.Builder.personaggi;

import it.unicam.cs.mpgc.rpg123022.Personaggi.Ladro;

public class LadroBuilder extends AbstractPersonaggioBuilder<LadroBuilder> {
    @Override
    protected LadroBuilder self() {
        return this;
    }

    @Override
    public Ladro build() {
        return new Ladro(id,nome,genere,coloreCapelli);
    }
}
