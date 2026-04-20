package it.unicam.cs.mpgc.rpg123022.Builder.personaggi;

import it.unicam.cs.mpgc.rpg123022.Personaggi.Druido;

public class DruidoBuilder extends AbstractPersonaggioBuilder<DruidoBuilder> {
    @Override
    protected DruidoBuilder self() {
        return this;
    }

    @Override
    public Druido build() {
        return new Druido(id, nome, genere,coloreCapelli);
    }
}
