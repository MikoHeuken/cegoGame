public class test {
  public static  void main(String[] args) {

    int k = 0; 
    cego_game game = new cego_game(3, 0);
    game.austeilen();
    for(int i = 0; i < 38; i++){
      if(game.getDeck().deck[i] != null){
        System.out.println(game.getDeck().deck[i].getNr() + " " + game.getDeck().deck[i].getCardName());
        k++;
      }
    }
    System.out.println(k + " Karten");

    game.changeCards(1, false);

    for(int i = 0; i < 38; i++){
      if(game.getDeck().deck[i] != null){
        System.out.println(game.getDeck().deck[i].getNr() + " " + game.getDeck().deck[i].getCardName());
        k++;
      }
    }
    System.out.println(k + " Karten");

  }
}
