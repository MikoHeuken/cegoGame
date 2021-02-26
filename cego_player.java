public class cego_player {

  private int money;
  private boolean isReal;
  private cego_card[] cards = new cego_card[4];
  private int nr;

  public cego_player(int nr){
    money = 5;
    isReal = false;
    this.nr = nr;
  }
  
  public cego_player(int money, boolean isReal, int nr){
    this.money = money;
    this.isReal = isReal;
    this.nr = nr;
  }

  public int getMoney(){
    return money;
  }

  public boolean getIsReal(){
    return isReal;
  }

  public int getNr(){
    return nr;
  }

  public cego_card[] getCards(){
    return cards;
  }

  public void setCard(int cardNr, cego_card card){
    cards[cardNr] = card;
  }

  public void setMoney(int change){
    if(money + change >= 0){
      money = money + change;
    }else{
      System.out.println();
      System.out.println();
      System.out.println();
      System.out.println("Spieler " + nr + " hat kein Geld mehr.");
      System.out.println();
      System.out.println();
      System.out.println();
    }
  }

  public void printCards(){
    for(int i = 0; i < cards.length; i++){
      if(cards[i] != null){
        System.out.println("Karte " + (i+1) + ": " + cards[i].getCardName());
      }
    }
  }

}
