import javafx.scene.text.Font;
public class FontCheck {
  public static void main(String[] args) throws Exception {
    var stream = FontCheck.class.getResourceAsStream("/fonts/MedievalSharp-Bold.ttf");
    System.out.println(stream == null ? "stream=null" : "stream=ok");
    Font f = Font.loadFont(stream, 24);
    System.out.println(f == null ? "font=null" : ("name=" + f.getName() + " family=" + f.getFamily() + " style=" + f.getStyle()));
  }
}
