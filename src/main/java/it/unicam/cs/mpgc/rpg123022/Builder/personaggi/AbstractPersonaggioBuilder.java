package it.unicam.cs.mpgc.rpg123022.Builder.personaggi;

import it.unicam.cs.mpgc.rpg123022.Enum.Genere;
import it.unicam.cs.mpgc.rpg123022.Personaggi.Personaggio;

public abstract class AbstractPersonaggioBuilder<T extends AbstractPersonaggioBuilder<T>> {

    protected int id;
    protected String nome;
    protected Genere genere;
    protected String coloreCapelli;

    public T setId(int id) {
        this.id = id;
        return self();
    }

    public T setNome(String nome) {
        this.nome = nome;
        return self();
    }

    public T setGenere(Genere genere) {
        this.genere = genere;
        return self();
    }

    public T setColoreCapelli(String colore) {
        this.coloreCapelli = colore;
        return self();
    }

    protected abstract T self();

    public abstract Personaggio build();
}
