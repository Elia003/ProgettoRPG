package it.unicam.cs.mpgc.rpg123022;

import java.util.ArrayList;
import java.util.List;

public class ListaOggettiTotale {
    private List<Oggetto> listaOggetti;

    private static ListaOggettiTotale INSTANCE = new ListaOggettiTotale();

    public static ListaOggettiTotale getInstance() {
        return INSTANCE;
    }


    public ListaOggettiTotale() {
        this.listaOggetti = new ArrayList<Oggetto>();
    }

    public List<Oggetto> getListaOggetti(){
        return listaOggetti;
    }

    public Boolean aggiungiOggetto(Oggetto oggetto){
        listaOggetti.add(oggetto);
        return true;
    }


}
