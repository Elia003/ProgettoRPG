package it.unicam.cs.mpgc.rpg123022.Personaggi;

import it.unicam.cs.mpgc.rpg123022.Aumenti.Effetto;
import it.unicam.cs.mpgc.rpg123022.Enum.Classe;
import it.unicam.cs.mpgc.rpg123022.Enum.Genere;
import it.unicam.cs.mpgc.rpg123022.Inventario;
import it.unicam.cs.mpgc.rpg123022.Oggetto;
import it.unicam.cs.mpgc.rpg123022.Risorse.Risorsa;

import java.util.List;


public abstract class Personaggio {
    protected int id;
    protected String nome;
    protected final Classe classe;
    protected Genere genere;
    protected String coloreCapelli;

    protected Inventario inventario;
    protected Risorsa risorsa;

    protected int attacco;
    protected int difesa;
    protected int hp;


    public Personaggio(int id, String nome, Classe classe, Genere genere, String coloreCapelli) {
        this.id = id;
        this.nome = nome;
        this.classe = classe;
        this.genere = genere;
        this.coloreCapelli = coloreCapelli;
        this.inventario = new Inventario(30);
    }

    //metodi

    public void equipaggia(Oggetto o) {
        if (o.getClasseCompatibili().contains(this.classe)) {
            if(inventario.slotLiberi()>o.getPeso()){
            inventario.aggiungiOggetto(o);
                for (Effetto effetto : o.getEffetto()) {
                    effetto.applica(this);
                }
            }else System.out.println("Spazio nell'inventario insufficente");
        }else System.out.println("Impossibile equipaggiare l'oggetto puo equipaggiarlo solo un "+ o.getClasseCompatibili());
    }

    public void rimuovi(Oggetto o) {
        if (inventario.getOggetti().contains(o)) {
            inventario.rimuoviOggetto(o);
            for (Effetto effetto : o.getEffetto()) {
                effetto.rimuovi(this);
            }
        } else System.out.println("Oggetto " + o.getNome() + " non presente nell'inventario di " + nome + " il " + getClasse());
    }
    //Getter e setter
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Classe getClasse() {
        return classe;
    }

    public Genere getGenere() {
        return genere;
    }

    public void setGenere(Genere genere) {
        this.genere = genere;
    }

    public String getColoreCapelli() {
        return coloreCapelli;
    }

    public void setColoreCapelli(String coloreCapelli) {
        this.coloreCapelli = coloreCapelli;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public Risorsa getRisorsa() {
        return risorsa;
    }

    public void setRisorsa(Risorsa risorsa) {
        this.risorsa = risorsa;
    }

    public int getAttacco() {
        return attacco;
    }

    public void setAttacco(int attacco) {
        this.attacco = attacco;
    }

    public int getDifesa() {
        return difesa;
    }

    public void setDifesa(int difesa) {
        this.difesa = difesa;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public String toString(){
        return  this.getClasse()
                + "\nid: " + id
                + "\nNome: " + nome
                + "\nGenere: " + genere
                + "\nColoreCapelli: " + coloreCapelli
                + "\nRisorsa " + risorsa
                + "\nAttacco: " + attacco
                + "\nDifesa: " + difesa
                + "\nPunti vita: " + hp
                + "\nOggetti nell'inventario: " + inventario;

    }
}
