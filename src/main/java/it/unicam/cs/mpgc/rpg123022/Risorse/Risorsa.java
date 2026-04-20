package it.unicam.cs.mpgc.rpg123022.Risorse;

import it.unicam.cs.mpgc.rpg123022.Enum.TipoRisorsa;

public abstract class Risorsa {
    protected int valore;

    public boolean consuma(int quantita){
        if(valore>=quantita){
            valore-=quantita;
            return true;
        }
        return false;
    }
    public abstract TipoRisorsa getTipo();

    public abstract void recupera(int quantita);

    public void aumenta(int quantita) {
        valore += quantita;
    }

    public void rimuoviBuff(int quantita) {
        valore-=quantita;
    }

    public int getValore() {
        return valore;
    }

    public void setValore(int valore) {
        this.valore = valore;
    }

    public String toString(){
        return this.getTipo().name() + " " + this.getValore();
    }
}
