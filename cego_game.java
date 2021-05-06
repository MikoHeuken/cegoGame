import java.util.ArrayList;
import java.util.List;

public class cego_game {

  private cego_deck deck;
  private int einsatz;
  private int pot;
  private int player;
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

  public int getPlayer(){
    return player;
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
    System.out.println();
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
      spieler[i] = new cego_player(money, isReal, (i+1));
    }
  }

  /**
   * teilt jedem Spieler 4 Karten aus
   * @param player die Spieler, denen Karten ausgeteilt wird
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
   * fügt dem Pot einen bestimmten Betrag hinzu
   * @param betrag der Betrag der hinzugefügt wird
   */
  public void setPot(int betrag){
    pot = pot + betrag;
  }

  /**
   * startet eine Runde
   * @param beginner der Spieler der Anfängt
   * @param gamer die Spieler die mitspielen, null falls RUnde Typ 1
   * @return der Spieler der die Runde gewonnen hat
   */
  public cego_player startRoundAll(int beginner, cego_player[] gamer){
    cego_player[] playerCopy;
    if(gamer == null){
      playerCopy = new cego_player[spieler.length];                         //eine Kopie des Spieler-Arrays wird erstellt,
      int count = 0;                                                                      //die Spieler werden so angeordnet, dass der, der 
      for(int i = beginner; i < playerCopy.length; i++){                                  //anfängt, an der ersten Position im neuen Array ist
        playerCopy[count] = spieler[i];
        count++;
      }
      for(int i = 0; i < beginner; i++){
        playerCopy[count] = spieler[i];
        count++;
      }
    }
    else{
      playerCopy = new cego_player[gamer.length];                         //eine Kopie des Gamer-Arrays wird erstellt,
      int count = 0;                                                                      //die Spieler werden so angeordnet, dass der, der 
      for(int i = beginner; i < playerCopy.length; i++){                                  //anfängt, an der ersten Position im neuen Array ist
        playerCopy[count] = gamer[i];
        count++;
      }
      for(int i = 0; i < beginner; i++){
        playerCopy[count] = gamer[i];
        count++;
      }
    }

    int[] cards = new int[player];                          //ein Array wird erstellt, welches die Kartennummern der Karten auf dem Tisch enthält
    for(int i = 0; i < player; i++){
      cards[i] = -1;
    }

    for(int i = 0; i < playerCopy.length; i++){                        //jeder Spieler darf eine Karte ablegen
      cards = turn(playerCopy[i], cards);
    }

    return playerCopy[highestCard(cards)];
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
        System.out.println("Spieler " + player.getNr() + " darf die erste Karte legen.");
        System.out.println("Das sind deine Karten:");
        player.printCards();
        System.out.println("Welche Karte willt du legen?");
        int card = In.readInt();
        cards[0] = player.getCards()[card - 1].getNr();
        player.setCard(card - 1, null);

      }else{                                                //...und schon mind. eine Karte auf dem Tisch
        int karten = 0;
        System.out.println();
        System.out.println("Folgende Karten liegen auf dem Tisch:");
        for(int i = 0; i < cards.length; i++){
          if(cards[i] != -1){
            System.out.println("Karte " + (i + 1) + " ist " + cego_card.getNameFromNr(cards[i]));
            karten++;
          }
        }
        System.out.println("Spieler " + player.getNr() + " ist an der Reihe. Welche Karte willst du legen?");
        System.out.println();
        System.out.println("Dies sind deine Karten:");
        player.printCards();
        int card = In.readInt();
        if(card > 0 && card < 5 && !isValidCard(cards[0], player, card - 1)){
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
        System.out.println("Spieler " + player.getNr() + " legt die erste Karte.");
        int k = 26;
        int m = -1;
        for(int i = 0; i < player.getCards().length; i++){
          if(player.getCards()[i] != null){
            if(player.getCards()[i].getValue() < k){
              k = player.getCards()[i].getValue();
              m = i;
            }
          }
        }
        cards[0] = player.getCards()[m].getNr();
        player.setCard(m, null);
      }

      else{                                                       //...und schon mind. eine Karte auf dem Tisch
        System.out.println("Spieler " + player.getNr() + " legt eine Karte.");
        int karten = 0;
        for(int i = 0; i < cards.length; i++){
          if(cards[i] != -1){
            karten++;                                             //schauen wie viel Karten schon auf "dem Tisch" liegt
          }
        }

        if(karten == cards.length - 1){                           //falls der Spieler die letzte Karte legen darf
          int card = leastValueForStitch(cards, player);
          cards[karten] = player.getCards()[card].getNr();
          player.setCard(card, null);

        }else{                                                    //falls nicht
          int card = leastValuePossible(player, cards[0]);
          cards[karten] = player.getCards()[card].getNr();
          player.setCard(card, null);
        }
      }

    }
    return cards;
  }

  /**
   * testet ob eine Karte gelegt werden darf
   * @param firstCard die erste Karte (Nr) die auf den Tisch gelegt wurde
   * @param player der Spieler, dessen Karte überprüft wird
   * @param card die Karte die überprüft werden soll (nummer auf der hand des Spielers)
   * @return true falls die Karte gelegt werden darf
   */
  private boolean isValidCard(int firstCard, cego_player player, int card){
    if(player.getCards()[card] == null){
      return false;
    }
    if(!anyValidCard(firstCard, player)){
      return true;
    }else if(player.getCards()[card].getNr() > 15 || player.getCards()[card].getNr() % 4 == firstCard % 4 && firstCard < 16){
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
      if(player.getCards()[i] != null){
        if(player.getCards()[i].getNr() > 15 || player.getCards()[i].getNr() % 4 == firstCard % 4){
          return true;
        }
      }
    }
    return false;
  }

  /**
   * gibt die niedrigste Karte zurück die gelegt werden kann
   * @param cards die Karten auf der Hand
   * @param firstCard die erste Karte die auf dem Tisch liegt
   * @return die Stelle der niedrigsten Karte
   */
  private int leastValuePossible(cego_player player, int firstCard){
    int k = 0;
    if(!anyValidCard(firstCard, player)){
      while(player.getCards()[k] == null){                              //wenn keine Karte passend gespielt werden kann,
        k++;                                                            //wird die erste Karte genommen
      }
    } else {
      while((player.getCards()[k] == null || !isValidCard(firstCard, player, k)) && k < 3){          
        k++;
      }
      for(int i = 0; i < player.getCards().length; i++){
        if(player.getCards()[i] != null && player.getCards()[k] != null){
          if(player.getCards()[i].getValue() < player.getCards()[k].getValue() && isValidCard(firstCard, player, i)){
            k = i;
          }
        }
      }
    }
    return k;
  }

  /**
   * gibt die niedrigste Karte zurück mit der ein Stich gemacht werden kann,
   * kann keiner gemacht werden gibt es die niedrigste erlaubte Karte zurück
   * @param cardsOnTable  alle Karten die sich auf dem Tisch befinden
   * @param player  der Spieler der an der Reihe ist
   * @return die Stelle der gesuchten Karte
   */
  private int leastValueForStitch(int[] cardsOnTable, cego_player player){
    int firstCard = cardsOnTable[0];
    int highestCard = -1;
    cego_card[] playerCards = player.getCards();
    int position = -1;

    for(int i = 0; i < cardsOnTable.length; i++){                      //die höchste Karte auf dem Tisch bestimmen
      if(cardsOnTable[i] > highestCard){
        highestCard = cardsOnTable[i];
      }
    }

    for(int i = 0; i < playerCards.length; i++){                      //alle Karten des Spielers durchgehen und schauen ob eine höher ist als diehöchste auf dem Tisch
      if(playerCards[i] != null){
        if(playerCards[i].getNr() > highestCard){
          position = i;
        }
      }
    }

    if(position == -1){
      return leastValuePossible(player, firstCard);
    }else{
      return position;
    }
  }

  /**
   * schaut was die höchste Karte von mehreren ist
   * falls mehrere Karten gleich groß ist, wird die erste Karte mit dem höchsten Wert im Staprl zurückgegeben
   * @param cards der Stapel Karten, der durchgeschaut wird
   * @return  die Stelle an der sich die höchste Karte im Stapel befindet
   */
  private int highestCard(int[] cards){
    int k = 0;
    for(int i = 1; i <cards.length; i++){
      if(cego_card.getValueFromNr(cards[i]) > cego_card.getValueFromNr(cards[k])){
        k = i;
      }
    }
    return k;
  }

  /**
   * lässt jeden Spieler seine Karten tauschen bevor eine Runde losgeht
   * @param beginner der Spieler der als erstes tauschen darf
   * @param oldRound bestätigt ob es sich um eine alte Runde handelt
   * @return gibt eine Liste der Spieler mit, die mitspielen
   */
  public cego_player[] changeCards(int beginner, boolean oldRound){
    int aussetzer = player - 3;                                                         //zählt wie viele noch aussetzen dürfen
    List<cego_player> out = new ArrayList<>();
    List<cego_player> in = new ArrayList<>();

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

    for(int i = 0; i < playerCopy.length; i++){
      boolean raus = false;

      if(playerCopy[i].getIsReal()){                                                    //falls Spieler echt...

        System.out.println("Spieler " + playerCopy[i].getNr() + " das sind deine Karten: ");
        playerCopy[i].printCards();

        if(aussetzer > 0 && oldRound){                                                  //wenn man noch aussetzen könnte...
          System.out.println("Willst du aussetzen? (true/false)");   
          raus = In.readBoolean();                                                      
        }
        if(!raus){
          in.add(playerCopy[i]);
          System.out.println("Wie viele Karten willst du tauschen?");
          int howMany = In.readInt();
          int[] stellen = new int[howMany];
          for(int k = 0; k < howMany; k++){                                                //die Plätze der zu tauschenden Karten werden sich gemerkt
            System.out.println("Welche Karte willst du tauschen?");
            stellen[k] = In.readInt()-1;
          }
          for(int k = 0; k < howMany; k++){
            int zufall;
            do{                                                                            //eine zufällige Karte aus dem Stapel (die noch nicht verteilt wurde)
              zufall = (int) (Math.random()*38);                                           //wird dem Spieler gegeben
            }while(deck.deck[zufall] == null);
            playerCopy[i].setCard(stellen[k], deck.deck[zufall]);
            deck.deck[zufall] = null;
          }
        }
        else{
          aussetzer++;
          out.add(playerCopy[i]);
        }
      }

      else{
        in.add(playerCopy[i]);
        for(int k = 0; k < playerCopy[i].getCards().length; k++){                       //falls Spieler nicht echt...
          if(playerCopy[i].getCards()[k].getValue() < 8){                               //alle Karten kleiner gleich 5 werden getauscht
            int zufall;
          do{                                                                            
            zufall = (int) (Math.random()*38);                                          
          }while(deck.deck[zufall] == null);
          playerCopy[i].setCard(k, deck.deck[zufall]);
          }
        }
      }
    }
    if(oldRound){
      for(int i = 0; i < out.size(); i++){
        System.out.print("Spieler " + out.get(i).getNr() + ", ");
      }
      System.out.println("setzen diese Runde aus.");

      cego_player[] mitmacher = in.toArray(new cego_player[in.size()]);
      return mitmacher;
    }
    else{
      return null;
    }
  }
}