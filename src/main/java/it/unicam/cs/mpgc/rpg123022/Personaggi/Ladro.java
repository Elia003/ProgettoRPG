package it.unicam.cs.mpgc.rpg123022.Personaggi;

import it.unicam.cs.mpgc.rpg123022.Enum.Classe;
import it.unicam.cs.mpgc.rpg123022.Enum.Genere;
import it.unicam.cs.mpgc.rpg123022.Risorse.Mana;
import it.unicam.cs.mpgc.rpg123022.Risorse.Stamina;

public class Ladro extends Personaggio {
    public Ladro(int id, String nome,Genere genere, String coloreCapelli) {
        super(id, nome, Classe.LADRO, genere, coloreCapelli);
        this.risorsa = new Stamina();
        this.attacco = 50;
        this.difesa = 15;
        this.hp = 80;
    }


}
