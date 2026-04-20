package it.unicam.cs.mpgc.rpg123022.Personaggi;

import it.unicam.cs.mpgc.rpg123022.Enum.Classe;
import it.unicam.cs.mpgc.rpg123022.Enum.Genere;
import it.unicam.cs.mpgc.rpg123022.Risorse.Mana;
import it.unicam.cs.mpgc.rpg123022.Risorse.Stamina;

public class Barbaro extends Personaggio {
    public Barbaro(int id, String nome, Genere genere, String coloreCapelli) {
        super(id, nome, Classe.BARBARO, genere, coloreCapelli);
        this.risorsa = new Stamina();
        this.attacco = 40;
        this.difesa = 25;
        this.hp = 130;
    }

}
