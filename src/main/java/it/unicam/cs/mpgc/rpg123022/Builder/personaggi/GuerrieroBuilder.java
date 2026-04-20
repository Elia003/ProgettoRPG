package it.unicam.cs.mpgc.rpg123022.Builder.personaggi;

import it.unicam.cs.mpgc.rpg123022.Personaggi.Guerriero;

public class GuerrieroBuilder extends AbstractPersonaggioBuilder<GuerrieroBuilder> {
    @Override
    protected GuerrieroBuilder self() {
        return this;
    }

    @Override
    public Guerriero build() {
        return new Guerriero(id,nome,genere,coloreCapelli);
    }
}
