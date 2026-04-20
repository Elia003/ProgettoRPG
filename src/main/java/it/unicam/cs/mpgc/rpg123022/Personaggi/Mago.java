package it.unicam.cs.mpgc.rpg123022.Personaggi;

import it.unicam.cs.mpgc.rpg123022.Enum.Classe;
import it.unicam.cs.mpgc.rpg123022.Enum.Genere;
import it.unicam.cs.mpgc.rpg123022.Risorse.Mana;

public class Mago extends Personaggio {
    public Mago(int id, String nome, Genere genere, String coloreCapelli) {
        super(id, nome, Classe.MAGO, genere, coloreCapelli);
        this.risorsa = new Mana();
        this.attacco = 25;
        this.difesa = 20;
        this.hp = 100;

    }




}
