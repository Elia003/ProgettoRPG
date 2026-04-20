package it.unicam.cs.mpgc.rpg123022.Builder.personaggi;


import it.unicam.cs.mpgc.rpg123022.Enum.Genere;
import it.unicam.cs.mpgc.rpg123022.Personaggi.Mago;

public class MagoBuilder extends AbstractPersonaggioBuilder<MagoBuilder> {

    @Override
    protected MagoBuilder self() {
        return this;
    }

    @Override
    public Mago build() {
        return new Mago(id, nome, genere,coloreCapelli);
    }
}
