import java.util.Arrays;
import java.util.*; 

public class Vehicle{

    private int x; //Hareket alanındaki verilen ilk x konumu.
    private int y; //Hareket alanındaki verilen ilk y konumu.
    private char orientation;//Aracın yön bilgisini tutar.
    int rangeOfMoveX; //Hareket alanı X mevcut inputta 5.
    int rangeOfMoveY; //Hareket alanı Y mevcut inputta 5.
    
    char[] directions  = {'N','W','S','E'}; //North,West,South and East. Arabanın dönebileceği yönleri tutan yön array'i.
    
    //                      N       W       S      E
    int movements[][] = { {0,1}, {-1,0}, {0,-1}, {1,0}}; //directions arrayinin index karşılığına göre araç hangi tarafa doğru pozisyonunu değiştirmesi gerektiğini anlayacak.
    //Eğer N yönünde bir hareket olacaksa, N nin karşılık geldiği koordinat hareketlerine göre {0,1} y pozisyonu 1 artmalı, x değeri sabit kalmalı.
    //Eğer W yönünde bir hareket olacaksa, W nin karşılık geldiği koordinat hareketlerine göre {-1,0} x pozisyonu 1 azalmalı, y değeri sabit kalmalı.
    
    //                          N - directions arrayinde index karşılığı 0 
    //                     /                                               \  
    //       W index karşılığı 1                                          E -index karşılığı 3
    //                     \                                              /
    //                                   S - index karşılığı 2

    public Vehicle(String rangeOfMove, String direction, String commands) { //İlk parametre:Aracın konum parametresi(1 2 N) ve İkinci parametre:Aracın hareket edeceği alanı nasıl gezeceği LMLMLMLMM
           
        String[] rangeOfMoves = rangeOfMove.split(" ",2);
        this.rangeOfMoveX = Integer.parseInt(rangeOfMoves[0]);// rangeOfMoves[0] bilgisinde yer alan, 5 değeri rangeOfMoveX e atanır.
        this.rangeOfMoveY = Integer.parseInt(rangeOfMoves[1]);// rangeOfMoves[1] bilgisinde yer alan, 5 değeri rangeOfMoveY e atanır.
        //Aracın son konum bilgisinde yer alan x ve y koordinatları rangeOfMoveX ve rangeOfMoveY ile kontrol edilecektir. 

        String[] parts = direction.split(" ",2); // commands parameter 1 2 N. Bu parametreyi parçalara ayırarak x,y ve yön bilgileri olarak araç değişkenlerine işlenir.
        String part1 = parts[0];// parts[0] bilgisinde yer alan, 1 değeri x e atanır.
        this.x = Integer.parseInt(part1);
           
        String parts2 [] = parts[1].split(" ",2); //parts[1] bilgisinde yer alan, 2 N değeri tekrar parçalara ayrılır. y ve orientation a atanır.
        String part2 = parts2[0]; //part2[0] is 2
        this.y = Integer.parseInt(part2);
           
        String part3 = parts2[1]; //part3 is N
        this.orientation = part3.charAt(0);
           
        char command; //LMLMLMLMM şeklinde bilgisi gelen yönlendirmenin her bir elemanı tek tek move methoduna işlem görmek üzere yollanır.

        for(int i = 0; i < commands.length(); i++){
            command = commands.charAt(i);
            move(command);
        } 
    }
 
    public void move(char command){
         
        int orientationIndex = new String(directions).indexOf(this.orientation);//Gelen yön değeri yön bilgilerinin tutulduğu directions arrayinde hangi indexte diye bakılır.
                                                                                //Ve karşılık gelen index değerine göre hareketi ve yönü belirlenir.
        int targetX; //Yeni hareketin ulaşması beklenen hedef X konumu
        int targetY; //Yeni hareketin ulaşması beklenen hedef Y konumu

        if (command == 'L'){ //Eğer gelen yeni yön bilgisi L ise 
            if(orientationIndex == 3 ){ //Mevcut konumum E ise yeni rotam N olmalıdır. Bu durumda directions[0] yeni yönüm olur. 
                this.orientation = directions[0];
            }
            else{
                this.orientation = directions[orientationIndex + 1]; //Mevcut konumum E den farklıysa(N,W,S gibi) directions arrayimde bir fazla olan yön artık yeni yönüm olur.
            }
        }
        else if(command == 'R'){ //Eğer gelen yeni yön bilgisi R ise 
            if(orientationIndex == 0 ){ //Mevcut konumum N ise yeni rotam E olmalıdır. Bu durumda yönüm directions[3] yeni yönüm olur.
                this.orientation = directions[3];
            }
            else{
                this.orientation = directions[orientationIndex - 1];//Mevcut konumum N den farklıysa directions arrayimde bir eksik olan yön artık yeni yönüm olur.
            }
        }
        else if(command == 'M'){//Eğer M komutu geldiyse karşılık geldiği index değerine göre yeni konum hesaplanır.
            targetX = this.x + movements[orientationIndex][0];
            targetY = this.y + movements[orientationIndex][1];
              
            if ( (targetX > rangeOfMoveX) || (targetY > rangeOfMoveY ) ){
                System.out.println("Aracın hareket alanına uymayan bir hareket gerçekleştirilemez.");
            }
            else{
                this.x = this.x + movements[orientationIndex][0];
                this.y = this.y + movements[orientationIndex][1];
            }
        }
    }
     
    // Getter X konum için
    public int getX() {
        return x;
    }
    
    // Setter X konum için
    public void setX(int x) {
        this.x = x;
    } 
      
    // Getter Y konum için
    public int getY() {
        return y;
    }
    
    // Setter Y konum için
    public void setY(int y) {
        this.y = y;
    } 
  
    public String getLocation(){
        // System.out.println("X: " + this.x + "Y: " + this.y + "O: " + this.orientation);
        return this.x + " " + this.y + " " + this.orientation;
    }
     
    public static void main(String []args){
         
        int numOfVehicles = 0;
        String[] array = new String[]{"5 5","1 2 N","LMLMLMLMM","3 3 E","MMRMMRMRRM"};

        numOfVehicles = (array.length - 1) / 2 ;
        //System.out.println("Araç sayısı " + numOfVehicles ); Mevcut inputta araç sayısı 2 dir.

        String[] locationArray = new String[numOfVehicles];

        List<String>locationArrayList = new ArrayList<String>();

        for(int i=0;i<numOfVehicles;i++){
            Vehicle v1 = new Vehicle(array[0], array[(2*i) + 1], array[(2*i) + 2]);
            locationArrayList.add(i,v1.getLocation());
        }
        //locationArrayList.forEach(System.out::println); //Araçların son konumu ve yön bilgisinin tutan listenin bilgilerini basar.
        locationArray = locationArrayList.toArray(locationArray); //Listedeki konum bilgilerini diziye döndürür.

        for(String s : locationArray)
            System.out.println(s);
    }
 }