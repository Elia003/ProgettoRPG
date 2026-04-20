package it.unicam.cs.mpgc.rpg123022;

import it.unicam.cs.mpgc.rpg123022.Aumenti.Effetto;
import it.unicam.cs.mpgc.rpg123022.Enum.Classe;
import it.unicam.cs.mpgc.rpg123022.Enum.PesoOggetto;
import it.unicam.cs.mpgc.rpg123022.Enum.TipoOggetto;
import it.unicam.cs.mpgc.rpg123022.Personaggi.Personaggio;

import java.util.List;
import java.util.Set;

public class Oggetto {
    private final String nome;
    private final TipoOggetto tipo;
    private final PesoOggetto peso;
    private  final Set<Classe> classiCompatibili;
    private  final List<Effetto> effetti;
    private final int consumo;

    public Oggetto(String nome,TipoOggetto tipo,PesoOggetto peso, Set<Classe> classiCompatibili, List<Effetto> effetti, int consumo) {
        this.nome = nome;
        this.tipo = tipo;
        this.peso = peso;
        this.consumo = consumo;
        this.effetti = effetti;
        this.classiCompatibili = classiCompatibili;
    }



    public int getPeso() {
        return peso.getValore();
    }

    public String getNome() {
        return nome;
    }

    public TipoOggetto getTipo() {
        return tipo;
    }

    public int getConsumo() {
        return consumo;
    }

    public Set<Classe> getClasseCompatibili() {
        return classiCompatibili;
    }

    public String toString(){
        return nome;
    }


    public List<Effetto> getEffetto() {
        return effetti;
    }


}
