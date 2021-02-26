public class cego_main {

  static int einsatz;
  static int player;

  public static void main(String[] args) {
    System.out.println("Starte Spiel...");
    System.out.println("Wie viele Spieler sollen mitspielen?");
    player = In.readInt();
    System.out.println("Was soll der Einsatz sein? (in ct)");
    einsatz = In.readInt();
    cego_game game = new cego_game(player, einsatz);

    startNew(game);
  }

  public static void startNew(cego_game game){
    System.out.println("Teile Karten aus...");
    game.austeilen();
    System.out.println("Jeder Spieler zahlt " + einsatz + "ct in den Pot.");
    game.setPotAll(einsatz);
    game.printPot();
    //change cards
  }

}
