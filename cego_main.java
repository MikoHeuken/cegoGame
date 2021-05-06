import java.util.ArrayList;
import java.util.List;

public class cego_main {

  static int einsatz;
  static int player;
  static int beginner;  //wechselt nach jedem Stich
  static int starter;   //wechselt erst nach dem austeilen

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
    List<cego_player> zahler = new ArrayList<>();                                                                       //alle Spieler werden gespeichert
    for(int i = 0; i < game.getSpieler().length; i++){                                                                 //...die, die gewinnen werden rausgestrichen
      zahler.add(game.getSpieler()[i]);                                                                                 //der Rest muss in der nächsten Runde bezahlen
    }

    System.out.println();                                                                                                 
    System.out.println("Starte Runde...");                                                                            
    System.out.println("Teile Karten aus...");
    System.out.println();
    game.austeilen();
    System.out.println("Jeder Spieler zahlt " + einsatz + "ct in den Pot.");
    game.setPotAll(einsatz);
    game.printPot();
    int toPay = game.getPot();
    game.changeCards(starter, false);
    beginner = starter;
    setStarter();
    for(int i = 0; i < 4; i++){                                                                                       //4 Runden werden gespielt
      cego_player winner = game.startRoundAll(beginner, null);
      beginner = winner.getNr() - 1;
      System.out.println();
      System.out.println("Spieler " + winner.getNr() + ", hat die " + (i+1) + ". Runde gewonnen.");
      System.out.println("Er erhält: " + game.getPot()/(4-i) + "ct.");
      System.out.println();
      winner.setMoney(game.getPot()/(4-i));
      game.setPot((int) game.getPot()/(4-i)*-1);
      zahler.remove(winner);
    }
    for(int i = 0; i < game.getPlayer(); i++){                                                                        //zeigt nach dem Rundenende den Spielstand jedes Spielers
      System.out.println("Spieler " + (i+1) + " hat nun " + game.getSpieler()[i].getMoney() + "ct.");
      System.out.println();
    } 

    System.out.println("Soll weiter gespielt werden?");
    boolean weiter = In.readBoolean();
    if(weiter){
      if(zahler.size() == 0){
        startNew(game);
      }else{
        cego_player[] payer = zahler.toArray(new cego_player[zahler.size()]);
        startOld(game, payer, toPay);
      }
    }
  }

  private static void startOld(cego_game game, cego_player[] payer, int toPay){
    List<cego_player> zahler = new ArrayList<>();                                                                       //alle Spieler werden gespeichert
    for(int i = 0; i < game.getSpieler().length; i++){                                                                 //...die, die gewinnen werden rausgestrichen
      zahler.add(game.getSpieler()[i]);                                                                                 //der Rest muss in der nächsten Runde bezahlen
    }

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
    game.austeilen();

    cego_player[] player = game.changeCards(starter, true);
    beginner = starter;
    setStarter();

    for(int i = 0; i < 4; i++){
      cego_player winner = game.startRoundAll(beginner, player);
      beginner = winner.getNr() - 1;
      System.out.println();
      System.out.println("Spieler " + winner.getNr() + ", hat die " + (i+1) + ". Runde gewonnen.");
      System.out.println("Er erhält: " + game.getPot()/(4-i) + "ct.");
      System.out.println();
      winner.setMoney(game.getPot()/(4-i));
      game.setPot((int) game.getPot()/(4-i)*-1);
      zahler.remove(winner);
    }
    for(int i = 0; i < game.getPlayer(); i++){                                                                        //zeigt nach dem Rundenende den Spielstand jedes Spielers
      System.out.println("Spieler " + (i+1) + " hat nun " + game.getSpieler()[i].getMoney() + "ct.");
      System.out.println();
    }

    System.out.println("Soll weiter gespielt werden?");
    boolean weiter = In.readBoolean();
    if(weiter){
      if(zahler.size() == 0){
        startNew(game);
      }else{
        cego_player[] geldVerlierer = zahler.toArray(new cego_player[zahler.size()]);
        startOld(game, geldVerlierer, toPay);
      }
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
