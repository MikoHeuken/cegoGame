public class cego_deck {

  public cego_card[] deck;
  
  public cego_deck(){
    deck = new cego_card[38];
    for(int i = 0; i < deck.length; i++){
      deck[i] = new cego_card(i);
    }
  }

}