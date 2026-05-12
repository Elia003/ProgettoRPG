package it.unicam.cs.mpgc.rpg123022;

import java.util.List;

public class ListaOggettiTotale {
    private List<Oggetto> listaOggetti;

    public List<Oggetto> getListaOggetti(){
        return listaOggetti;
    }

    public Boolean aggiungiOggetto(Oggetto oggetto){
        listaOggetti.add(oggetto);
        return true;
    }


}
