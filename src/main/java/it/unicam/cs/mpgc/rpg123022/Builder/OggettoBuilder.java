package it.unicam.cs.mpgc.rpg123022.Builder;

import it.unicam.cs.mpgc.rpg123022.Aumenti.Effetto;
import it.unicam.cs.mpgc.rpg123022.Enum.Classe;
import it.unicam.cs.mpgc.rpg123022.Enum.PesoOggetto;
import it.unicam.cs.mpgc.rpg123022.Enum.TipoOggetto;
import it.unicam.cs.mpgc.rpg123022.Oggetto;

import java.util.*;

public class OggettoBuilder {

    private String nome;
    private TipoOggetto tipo;
    private PesoOggetto peso;
    private Set<Classe> classiCompatibili = new HashSet<>();
    private List<Effetto> effetti = new ArrayList<>();
    private int consumo;

    public OggettoBuilder setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public OggettoBuilder setTipo(TipoOggetto tipo) {
        this.tipo = tipo;
        return this;
    }

    public OggettoBuilder setPeso(PesoOggetto peso) {
        this.peso = peso;
        return this;
    }


    public OggettoBuilder addClassiCompatibili(Classe... classi) {
        this.classiCompatibili.addAll(Arrays.asList(classi));
        return this;
    }

    public OggettoBuilder addEffetto(Effetto... e) {
        this.effetti.addAll(Arrays.asList(e));
        return this;
    }

    public OggettoBuilder setConsumo(int consumo) {
        this.consumo = consumo;
        return this;
    }

    public Oggetto build() {
        return new Oggetto(nome, tipo, peso, classiCompatibili, effetti,consumo);
    }
}
