public class test {
  public static  void main(String[] args) {

    int k = 0; 
    cego_game game = new cego_game(4, 0);
    game.austeilen();
    /*for(int i = 0; i < 38; i++){
      if(game.getDeck().deck[i] != null){
        System.out.println(game.getDeck().deck[i].getNr() + " " + game.getDeck().deck[i].getCardName());
        k++;
      }
    }
    System.out.println(k + " Karten - erwartet 22");*/

    game.changeCards(1, true);
    k = 0;

    /*for(int i = 0; i < 38; i++){
      if(game.getDeck().deck[i] != null){
        System.out.println(game.getDeck().deck[i].getNr() + " " + game.getDeck().deck[i].getCardName());
        k++;
      }
    }
    System.out.println(k + " Karten - erwartet 14");*/

    System.out.println();
    System.out.println();

    for(int i = 0; i < game.getPlayer(); i++){
      game.getSpieler()[i].printCards();
    }

  }
}
