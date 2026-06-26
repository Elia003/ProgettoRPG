package it.unicam.cs.mpgc.rpg123022.ArmadioPersonaggi;

import it.unicam.cs.mpgc.rpg123022.Enum.Classe;
import it.unicam.cs.mpgc.rpg123022.Enum.Genere;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

public class ClasseStyleResolver {
    private static final String BASE_CARD_STYLE =
            "-fx-background-color: rgba(198, 179, 170, 1);" +
                    "-fx-border-color: black;" +
                    "-fx-border-radius: 11;" +
                    "-fx-border-width: 3;" +
                    "-fx-background-radius: 14;" +
                    "-fx-padding: 18;";

    private static final String ACTIVE_CARD_STYLE =
            "-fx-background-color: rgba(198, 179, 170, 1);" +
                    "-fx-border-color: gold;" +
                    "-fx-border-width: 3;" +
                    "-fx-background-radius: 14;" +
                    "-fx-border-radius: 11;" +
                    "-fx-padding: 18;";

    private ClasseStyleResolver(){}

    public static String getImagePath(Classe classe, Genere genere){
        return switch (classe) {
            case MAGO -> switch(genere){
                case Uomo -> "/schedePersonaggi/wizard.jpg";
                case Donna -> "/schedePersonaggi/woman_wizard.png";
            };

            case GUERRIERO -> switch(genere){
                case Uomo -> "/schedePersonaggi/warrior.jpg";
                case Donna -> "/schedePersonaggi/woman_warrior.png";
            };

            case BARBARO -> switch(genere){
                case Uomo -> "/schedePersonaggi/barbarian.jpg";
                case Donna -> "/schedePersonaggi/woman_barbarian.png";
            };

            case LADRO -> switch(genere){
                case Uomo -> "/schedePersonaggi/rogue.jpg";
                case Donna -> "/schedePersonaggi/woman_rogue.png";
            };

            case DRUIDO -> switch(genere){
                case Uomo -> "/schedePersonaggi/druid.jpg";
                case Donna -> "/schedePersonaggi/druid.png";
            };
        };
    }

    public static String buildCardStyle(Classe classe,Genere genere) {
        return buildCardStyle(classe, genere, false);
    }

    public static void applyRoundedClip(Region region) {
        Rectangle clip = new Rectangle();
        clip.setArcWidth(28);
        clip.setArcHeight(28);
        clip.widthProperty().bind(region.widthProperty());
        clip.heightProperty().bind(region.heightProperty());
        region.setClip(clip);
    }

    public static String buildCardStyle(Classe classe,Genere genere, boolean attivo){
        String baseStyle = attivo ? ACTIVE_CARD_STYLE : BASE_CARD_STYLE;
        String imagePath = getImagePath(classe, genere);

        if (imagePath == null) {
            return baseStyle;
        }

        var imageResource = ClasseStyleResolver.class.getResource(imagePath);
        if (imageResource == null) {
            return baseStyle;
        }

        String imageUrl = imageResource.toExternalForm();
        return "-fx-background-color: rgba(198, 179, 170, 0.88);"
                + "-fx-background-image: url('" + imageUrl + "');"
                + "-fx-background-size: cover;"
                + "-fx-background-position: center center;"
                + "-fx-background-repeat: no-repeat;"
                + (attivo
                ? "-fx-border-color: gold;-fx-border-width: 3;-fx-border-radius: 11;"
                : "-fx-border-color: black;-fx-border-width: 3;-fx-border-radius: 11;")
                + "-fx-background-radius: 14;"
                + "-fx-padding: 18;";
    }
}
