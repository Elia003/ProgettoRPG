package it.unicam.cs.mpgc.rpg123022.Personaggi;

import it.unicam.cs.mpgc.rpg123022.Enum.Classe;
import it.unicam.cs.mpgc.rpg123022.Enum.Genere;
import it.unicam.cs.mpgc.rpg123022.Risorse.Stamina;

public class Guerriero extends Personaggio {
    public Guerriero(int id, String nome, Genere genere, String coloreCapelli) {
        super(id, nome, Classe.GUERRIERO, genere, coloreCapelli);
        this.risorsa = new Stamina();
        this.attacco = 35;
        this.difesa = 50;
        this.hp = 150;
    }


}
