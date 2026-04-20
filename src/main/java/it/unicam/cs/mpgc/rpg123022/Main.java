package it.unicam.cs.mpgc.rpg123022;

import it.unicam.cs.mpgc.rpg123022.Aumenti.AumentaAttacco;
import it.unicam.cs.mpgc.rpg123022.Aumenti.AumentaDifesa;
import it.unicam.cs.mpgc.rpg123022.Aumenti.AumentaRisorsa;
import it.unicam.cs.mpgc.rpg123022.Builder.OggettoBuilder;
import it.unicam.cs.mpgc.rpg123022.Builder.personaggi.MagoBuilder;
import it.unicam.cs.mpgc.rpg123022.Enum.*;
import it.unicam.cs.mpgc.rpg123022.Personaggi.Mago;

public class Main {
    /*public static void main(String[] args) {
        Mago mago = new MagoBuilder()
                .setId(1)
                .setNome("elia")
                .setGenere(Genere.Uomo)
                .setColoreCapelli("Nero")
                .build();

        Mago magoNero = new MagoBuilder()
                .setId(2)
                .setNome("Mago nero")
                .setGenere(Genere.Uomo)
                .setColoreCapelli("Nero")
                .build();

        Oggetto spada = new OggettoBuilder()
                .addClassiCompatibili(Classe.GUERRIERO)
                .setNome("Spada")
                .setTipo(TipoOggetto.ARMA)
                .addEffetto(new AumentaAttacco(10))
                .setPeso(PesoOggetto.ARMA)
                .setConsumo(2)
                .build();

        Oggetto bastone = new OggettoBuilder()
                .addClassiCompatibili(Classe.MAGO)
                .setNome("Bastone dello Stregone")
                .setTipo(TipoOggetto.ARMA)
                .addEffetto(new AumentaAttacco(10))
                .setPeso(PesoOggetto.ARMA)
                .setConsumo(6)
                .build();

        Oggetto mantello = new OggettoBuilder()
                .addClassiCompatibili(Classe.MAGO)
                .setNome("Mantello dell'arcano")
                .setTipo(TipoOggetto.ARMATURA)
                .addEffetto(new AumentaAttacco(10),new AumentaRisorsa(10, TipoRisorsa.MANA))
                .setPeso(PesoOggetto.PESANTE)
                .setConsumo(0)
                .build();

        Oggetto elmo = new OggettoBuilder()
                .addClassiCompatibili(Classe.values())
                .setNome("Elmo di Ferro")
                .setTipo(TipoOggetto.ARMATURA)
                .addEffetto(new AumentaDifesa(20))
                .setPeso(PesoOggetto.MEDIO)
                .setConsumo(0)
                .build();

        Oggetto corazza = new OggettoBuilder()
                .addClassiCompatibili(Classe.values())
                .setNome("Corazza di Ferro")
                .setTipo(TipoOggetto.ARMATURA)
                .addEffetto(new AumentaDifesa(30))
                .setPeso(PesoOggetto.PESANTE)
                .setConsumo(0)
                .build();



        mago.equipaggia(spada);
        mago.equipaggia(bastone);
        mago.equipaggia(mantello);

        magoNero.equipaggia(elmo);
        magoNero.equipaggia(mantello);
        magoNero.equipaggia(corazza);

        System.out.println(mago);
        System.out.println("\n---------------------------------------");
        System.out.println(magoNero);
        System.out.println("\n---------------------------------------\n");

        Combattimento vs = new Combattimento(mago,magoNero);
        vs.inizia();




    }*/
}
