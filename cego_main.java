public class cego_main {

  static int einsatz;
  static int player;
  static int beginner;
  static int starter;

  public static void main(String[] args) {
    System.out.println("Starte Spiel...");
    System.out.println("Wie viele Spieler sollen mitspielen?");
    player = In.readInt();
    System.out.println("Was soll der Einsatz sein? (in ct)");
    einsatz = In.readInt();
    cego_game game = new cego_game(player, einsatz);

    startNew(game);
  }

  private static void startNew(cego_game game){
    System.out.println();
    System.out.println("Teile Karten aus...");
    System.out.println();
    game.austeilen(game.getSpieler());
    System.out.println("Jeder Spieler zahlt " + einsatz + "ct in den Pot.");
    game.setPotAll(einsatz);
    game.printPot();
    game.changeCards(starter);
    beginner = starter;
    setStarter();
    for(int i = 0; i < 4; i++){                                                                                       //4 Runden werden gespielt
      cego_player winner = game.startRoundAll(beginner);
      beginner = winner.getNr() - 1;
      System.out.println();
      System.out.println("Spieler " + winner.getNr() + ", hat die " + (i+1) + ". Runde gewonnen.");
      System.out.println("Er erhält: " + game.getPot()/(4-i) + "ct.");
      System.out.println();
      winner.setMoney(game.getPot()/(4-i));
      game.setPot((int) game.getPot()/(4-i)*-1);
    }
    for(int i = 0; i < game.getPlayer(); i++){                                                                        //zeigt nach dem Rundenende den Spielstand jedes Spielers
      System.out.println("Spieler " + (i+1) + " hat nun " + game.getSpieler()[i].getMoney() + "ct.");
      System.out.println();
    } 
  }

  private static void startOld(cego_game game, cego_player[] payer, int toPay, cego_player[] player){
    System.out.println("Starte neue Runde...");
    for(int i = 0; i < payer.length; i++){
      System.out.print("Spieler " + payer[i].getNr() + ", ");
      payer[i].setMoney(-(toPay));
    }
    System.out.println("zahlen je " + toPay + "ct in den Pot ein.");
    game.setPot(payer.length * toPay);
    game.printPot();
    
    System.out.println("Teile Karten aus...");
    System.out.println();
    game.austeilen(player);

    game.changeCards(starter);
    beginner = starter;
    setStarter();

    for(int i = 0; i < 4; i++){
      cego_player winner = game.startRoundAll(beginner);
      beginner = winner.getNr() - 1;
      System.out.println();
      System.out.println("Spieler " + winner.getNr() + ", hat die " + (i+1) + ". Runde gewonnen.");
      System.out.println("Er erhält: " + game.getPot()/(4-i) + "ct.");
      System.out.println();
      winner.setMoney(game.getPot()/(4-i));
      game.setPot((int) game.getPot()/(4-i)*-1);
    }
    for(int i = 0; i < game.getPlayer(); i++){                                                                        //zeigt nach dem Rundenende den Spielstand jedes Spielers
      System.out.println("Spieler " + (i+1) + " hat nun " + game.getSpieler()[i].getMoney() + "ct.");
      System.out.println();
    }
  }

  /**
   * ändert den Starter nach jeder runde
   */
  private static void setStarter(){
    if(starter == 0 || starter == player){
      starter = 1;
    }else{
      starter++;
    }
  }

}
