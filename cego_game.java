public class cego_game {

  private cego_deck deck;
  private int einsatz;
  private int pot;
  public int player;
  private cego_player[] spieler;
  
  public cego_game(){
    einsatz = 10;
    player = 4;
    insertPlayer();
  }

  public cego_game(int player, int einsatz){
    this.einsatz = einsatz;
    this.player = player;
    insertPlayer();
  }

  public int getEinsatz(){
    return einsatz;
  }

  public cego_deck getDeck(){
    return deck;
  }

  public int getPot(){
    return pot;
  }

  public void printPot(){
    System.out.println("Im Pot befinden sich nun " + pot + "ct.");
  }

  public cego_player[] getSpieler(){
    return spieler;
  }

  /**
   * fügt Spieler ins Spiel dazu
   */
  private void insertPlayer(){                                        
    spieler = new cego_player[player];
    System.out.println("Mit wie viel Geld startet ein Spieler? (in ct)");
      int money = In.readInt();
    for(int i = 0; i < spieler.length; i++){
      System.out.println("Erstelle Spieler " + (i+1) + ":");
      System.out.println("Soll dieser Spieler ein echter Spieler sein? (true/false)");
      boolean isReal = In.readBoolean();
      spieler[i] = new cego_player(money, isReal, i);
    }
  }

  /**
   * teilt jedem Spieler 4 Karten aus
   */
  public void austeilen(){                                            
    deck = new cego_deck();
    int zufall;
    for(int i = 0; i < spieler.length; i++){
      for(int k = 0; k < 4; k++){
        do{
          zufall = (int) (Math.random()*38);
        }while(deck.deck[zufall] == null);
        spieler[i].setCard(k, deck.deck[zufall]);
        deck.deck[zufall] = null;
      }
    }
  }

  /**
   * fügt von jedem Spieler den Einsatz in den Pot
   * @param einsatz
   */
  public void setPotAll(double einsatz){
    for(int i = 0; i < player; i++){
      spieler[i].setMoney((int)(einsatz * -1));
    }
    pot = pot + (int) (einsatz * player);
  }

  /**
   * startet eine Runde mit allen (Runde Typ 1)
   * @param beginner der Spieler der Anfängt
   */
  public void startRoundAll(int beginner){
    cego_player[] playerCopy = new cego_player[spieler.length];                         //eine Kopie des Spieler-Arrays wird erstellt,
    int count = 0;                                                                      //die Spieler werden so angeordnet, dass der, der 
    for(int i = beginner; i < playerCopy.length; i++){                                  //anfängt, an der ersten Position im neuen Array ist
      playerCopy[count] = spieler[i];
      count++;
    }
    for(int i = 0; i < beginner; i++){
      playerCopy[count] = spieler[i];
      count++;
    }

    int[] cards = new int[player];                          //ein array wird erstellt, welches die Kartennummern der Karten auf dem Tisch enthält
    for(int i = 0; i < player; i++){
      cards[i] = -1;
    }
  }

  /**
   * es wird eine Runde gespielt mit einem Spieler
   * @param player der Spieler der an der Reihe ist
   * @param cards die Karten die sich gerade auf dem Tisch befinden
   * @return gibt alle karten die sich auf dem Tisch befinden (also jetzt eine mehr) zurück
   */
  private int[] turn(cego_player player, int[] cards){
    if(player.getIsReal()){                                 //falls Spieler reel ist...
      if(cards[0] == -1){                                   //...und noch keine Karte auf dem Tisch
        System.out.println("Spieler " + player + " darf die erste Karte legen.");
        System.out.println("Das sind deine Karten:");
        player.printCards();
        System.out.println("Welche Karte willt du legen?");
        int card = In.readInt();
        cards[0] = player.getCards()[card - 1].getNr();
        player.setCard(card - 1, null);
      }else{                                                //...und schon mind. eine Karte auf dem Tisch
        int karten = 0;
        System.out.println("Folgende Karten liegen auf dem Tisch:");
        for(int i = 0; i < cards.length; i++){
          if(cards[i] != -1){
            System.out.println("Karte " + (i + 1) + " ist " + cego_card.getNameFromNr(cards[i]));
            karten++;
          }
        }
        System.out.println("Spieler " + player + " ist an der Reihe. Welche Karte willst du legen?");
        int card = In.readInt();
        if(!isValidCard(cards[0], player, card - 1)){
          do{
            System.out.println("Diese Karte darf nicht gelegt werden. Wähle bitte eine neue.");
            card = In.readInt();
          }while(!isValidCard(cards[0], player, card - 1));
        }
        cards[karten] = player.getCards()[card - 1].getNr();
        player.setCard(card - 1, null);
      }
    }
    else{                                                   //falls Spieler nicht reel ist...
      if(cards[0] == -1){                                   //...und noch keine Karte auf dem Tisch
        System.out.println("Spieler " + player + " legt die erste Karte.");
        int k = 26;
        int m = -1;
        for(int i = 0; i < player.getCards().length; i++){
          if(player.getCards()[i].getValue() < k){
            k = player.getCards()[i].getValue();
            m = i;
          }
        }
        cards[0] = player.getCards()[m].getNr();
        player.setCard(m, null);
      }
      else{                                                 //...und schon mind. eine Karte auf dem Tisch
        //TODO
      }
    }
    return cards;
  }

  /**
   * testet ob eine Karte gelegt werden darf
   * @param firstCard die erste Karte (Nr) die auf den Tisch gelegt wurde
   * @param player der Spieler, dessen Karte überprüft wird
   * @param card die Karte die überprüft werden soll
   * @return true falls die Karte gelegt werden darf
   */
  private boolean isValidCard(int firstCard, cego_player player, int card){
    if(!anyValidCard(firstCard, player)){
      return true;
    }else if(player.getCards()[card].getNr() > 15 || player.getCards()[card].getNr() % 4 == firstCard % 4){
      return true;
    }
    return false;
  }

  /**
   * überprüft ob überhaupt eine Karte gelegt werden kann
   * @param firstCard die erste Karte die auf den Tisch gelegt wurde
   * @param player der Spieler dessen Karten überprüft werden
   * @return true falls der Spieler mind. eine Karte hat die gelegt werden darf
   */
  private boolean anyValidCard(int firstCard, cego_player player){
    for(int i = 0; i < player.getCards().length; i++){
      if(player.getCards()[i].getNr() > 15 || player.getCards()[i].getNr() % 4 == firstCard % 4){
        return true;
      }
    }
    return false;
  }

}