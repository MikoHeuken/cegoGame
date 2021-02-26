public class cego_card {
  
  private int nr;
  private int value;
  private String cardName;

  public cego_card(int nr){
    if(nr < 0 || nr > 37){
      System.out.println("ERROR in cego_card.java --Code 1--");
    }else{
      this.nr = nr;
      defineCard();
    }
  }

  public int getValue(){
    return value;
  }

  public String getCardName(){
    return cardName;
  }

  public int getNr(){
    return nr;
  }

  public void print(){
    System.out.println("Kartenname: " + cardName + ", Wert: " + value);
  }

  private void defineCard(){
    if(nr < 16){                              //Karten von Bube bis König definieren
      if(nr < 4){
        value = 0;
        cardName = "Bube";
      }else if(nr < 8){
        value = 1;
        cardName = "Reiter";
      }else if(nr < 12){
        value = 2;
        cardName = "Dame";
      }else{
        value = 3;
        cardName = "König";
      }
      if(nr % 4 == 0){
        cardName = "Kreuz " + cardName;
      }else if(nr % 4 == 1){
        cardName = "Karo " + cardName;
      }else if(nr % 4 == 2){
        cardName = "Pik " + cardName;
      }else{
        cardName = "Herz " + cardName;
      }
    }else if(nr < 37){                      //Karten von 1-21 definieren
      value = nr - 12;
      int zahl = value - 3;
      cardName = "" + zahl;
    }else{
      value = 25;
      cardName = "Gstieß";                  //Gstieß definieren
    }
  }

  public static int getValueFromNr(int nr){
    if(nr < 0 || nr > 37){
      System.out.println("ERROR in cego_card.java --Code 2--");
      return -1;
    }
    else{
      cego_card test = new cego_card(nr);
      return test.value;
    }
  }

  public static String getNameFromNr(int nr){
    if(nr < 0 || nr > 37){
      return "ERROR in cego_card.java --Code 3--";
    }
    else{
      cego_card test = new cego_card(nr);
      return test.cardName;
    }
  }

}