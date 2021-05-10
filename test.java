public class test {
  public static  void main(String[] args) {

    cego_player[] sort = {new cego_player(1), new cego_player(3), new cego_player(4), new cego_player(2)};
    sort = sortPlayer(sort);
    for(int i = 0; i < sort.length; i++){
      System.out.println(sort[i].getNr());
    }


  }


  private static cego_player[] sortPlayer(cego_player[] toSort){
    cego_player[] sorted = new cego_player[toSort.length];
    int lowest = 0;
    for(int i = 0; i < sorted.length; i++){
      for(int k = 0; k < toSort.length; k++){
        while(toSort[lowest] == null && lowest < 3){
          lowest++;
        }
        if(toSort[k] != null && toSort[k].getNr() < toSort[lowest].getNr()){
          lowest = k;
        }
      }
      sorted[i] = toSort[lowest];
      toSort[lowest] = null;
      lowest = 0;
    }
    return sorted;
  }
}
