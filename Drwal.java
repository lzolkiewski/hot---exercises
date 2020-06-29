import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Drwal {
    boolean first_move = true;
    private int xStart;
    private int yStart;
    private String color;
    private int width;
    private int height;
    String[][]loaded_array;

    public static void main(String[]args) throws IOException {
        new Drwal(args);
    }

    public Drwal(String[]args) throws IOException {
        check_args(args);
        load_file();
        check_position();
    }
//    check if arguments given are correct
    public void check_args(String[]args){
        if(args.length==5){
            try{
//                Checking whether Y or N is passed as an argument
                if(args[2].compareTo("")==0){
                    System.out.println("klops");
                    System.exit(0);
                }
//                Checking whether passed size belongs between the boundaries
                xStart=Integer.parseInt(args[0]);
                yStart=Integer.parseInt(args[1]);
                width=Integer.parseInt(args[3]);
                height=Integer.parseInt(args[4]);

                if( (xStart<1 || xStart > 50) && (yStart<1 || yStart>50) &&(height<1 || height > 50) && (width<1 || width>50)){
                    System.out.println("klops");
                    System.exit(0);
                }
                if(xStart>width || yStart>height){
                    System.out.println("klops");
                    System.exit(0);
                }

                color = args[2];
                xStart-=1;
                yStart-=1;
//                Checking whether the value is an Integer
            }catch (NumberFormatException e){
                System.out.println("klops");
            }
//            In case no arguments are passed
        }else{
            System.out.println("klops");
            System.exit(0);
        }
    }
//    get input file int an array
    public void load_file(){
//        store input file into array list
        Scanner scanner = new Scanner(System.in);
        ArrayList<String>file = new ArrayList<>();
        int i = 0;
        for (; i<height && scanner.hasNext(); i++){
            file.add(scanner.nextLine().substring(0, width+1));
        }
        scanner.close();
        height = i;
//        initialize file array
        loaded_array = new String[height][width];
        for (i = 0; i < height; i++) {
                String splt_str = String.valueOf(file.get(i));
                loaded_array[i] = splt_str.split("");
        }
        
    }
//    check if starting point is valid
    public void check_position(){
        if(loaded_array[yStart][xStart].compareTo(" ")!=0){
            System.out.println("klops");
            System.exit(0);
        }
        paint();
    }
//    paint the place
    public void paint(){
//        if floor has holes going down and painting else just paint last line
        while (true){
        get_top_corner();
            if(check_floor()){
                int x = xStart, y = yStart;
                go_down();
                fill(x, y);
            }else {
                fill(xStart, yStart);
                break;
            }
        }
//        displaying the painting
        display();
    }

    //    display painted part
    public void display(){
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(loaded_array[i][j]);
            }
            System.out.println();
        }
    }
//    check celling for holes
    public boolean check_celling(){
        int i=xStart;
//        moving to the left to check whole celling
        while (move_left(i, yStart)) i--;
//        checking celling
        for (; i<width; i++){
//            if move right possible check above
            if (move_right(i, yStart))
//                if move upwards possible return true as celling has hole
                if(move_up(i, yStart))
                    return true;
        }
//        if reached the celling has no holes
        return false;
    }
//    check floor for holes
    public boolean check_floor(){
        int i = xStart;
//        move to the left
        while (move_left(i, yStart))i--;
//        check floor
        for (;i<width; i++){
//            if move right possible
            if (move_right(i, yStart)){
//                if move down possible return true
                if (move_down(i, yStart))
                    return true;
            }else if(move_down(xStart, yStart)) return true;
        }
        return false;
    }
//    check if move up / down / left / right is possible
    public boolean move_up(int x, int y){
        if (y>0&&y<height  && x>=0&& x<width)
            return loaded_array[y - 1][x].compareTo(" ") == 0;
        else
            return false;
    }
    public boolean move_down(int x, int y){
        if(y+1<height)
            return loaded_array[y + 1][x].compareTo(" ") == 0;
        else
            return false;
    }
    public boolean move_left(int x, int y){
        if(x>0)
            return loaded_array[y][x-1].compareTo(" ")==0;
        else
            return false;
    }
    public boolean move_right(int x, int y){
        if(x+1<width)
            return loaded_array[y][x+1].compareTo(" ")==0;
        else
            return false;
    }
//    get corners
    public void move_to_left_corner(){
        while (move_up(xStart, yStart) || move_left(xStart-1, yStart)) {
            if (move_up(xStart, yStart)) yStart--;
            else if (move_left(xStart, yStart)){
                xStart--;
            }
        }
//        needed to correct the place
        xStart--;
    }
    public void move_to_right_corner(){
        while (move_up(xStart, yStart)||move_right(xStart+1, yStart)){
            if(move_up(xStart, yStart))yStart--;
            else if(move_right(xStart, yStart)){
                xStart++;
            }
        }
        xStart++;
    }
//    supposedly going to top left corner in the marked place
    public void get_top_corner(){
        if(first_move){
            // making sure it goes to the left
            move_to_left_corner();
            first_move=!first_move;
        }
        // mainly usefull to peek all holes in the celling
        while (check_celling()){
            move_to_left_corner();
            move_to_right_corner();
            move_to_left_corner();
        }
    }

//    going down if floor has holes
    public void go_down(){
        if(check_floor()){
//            if not a hole move right if possible
            while (!move_down(xStart, yStart)&&move_right(xStart, yStart)) xStart++;
//            move down
            if(move_down(xStart, yStart)) yStart++;
//            move left border if move left possible
            while (move_left(xStart, yStart)) xStart--;
        }
    }
//    filling the place given horizontally with color
    public void fill(int x, int y){
        for (int i = x; i<width && loaded_array[y][i].compareTo(" ")==0; i++)
            loaded_array[y][i] = color;
        }
}
