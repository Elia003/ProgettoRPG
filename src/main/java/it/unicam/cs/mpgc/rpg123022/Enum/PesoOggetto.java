package it.unicam.cs.mpgc.rpg123022.Enum;

public enum PesoOggetto {
    //Armature
    LEGGERO(2),
    MEDIO(4),
    PESANTE(6),
    //Armi e scudo
    ARMA(3),
    SCUDO(4),
    ARMA_LEGGERA(2);

    private final int valore;

    PesoOggetto(int valore) {
        this.valore = valore;
    }

    public int getValore() {
        return valore;
    }
}
