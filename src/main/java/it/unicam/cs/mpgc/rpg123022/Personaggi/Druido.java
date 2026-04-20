package it.unicam.cs.mpgc.rpg123022.Personaggi;

import it.unicam.cs.mpgc.rpg123022.Enum.Classe;
import it.unicam.cs.mpgc.rpg123022.Enum.Genere;
import it.unicam.cs.mpgc.rpg123022.Risorse.Mana;

public class Druido extends Personaggio {
    public Druido(int id, String nome, Genere genere, String coloreCapelli) {
        super(id, nome, Classe.DRUIDO, genere, coloreCapelli);
        this.risorsa = new Mana();
        this.attacco = 30;
        this.difesa = 25;
        this.hp = 110;
    }


    public void cura(int c) {
           if(this.hp == 110){
               System.out.println(" Punti vita già al massimo ");

        } else this.hp = Math.min(this.hp + c, 110);
    }
}
