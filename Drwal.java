package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Drwal {
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
        while (scanner.hasNext()){
            file.add(scanner.nextLine());
        }
//       find the biggest row
        int size=0;
        for (String element: file) {
            if(element.length()>size)
                size=element.length();
        }
        int file_height = file.size();
        int file_width = size;
//        initialize file array
        loaded_array = new String[file.size()][size];
        for (int i = 0; i < file_height; i++) {
            for (int j = 0; j < file_width; j++) {
                loaded_array[i][j] = String.valueOf(file.get(i).charAt(j));
            }
        }
        get_array();
    }
//    convert file into what we need
    public void get_array(){
        String[][]array = new String[height][width];
        try {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    array[i][j] = loaded_array[i][j];
                }
            }
            loaded_array=array;
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("klops");
            System.exit(0);
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
//        going to the highest point (at least trying)
        get_top_corner();
//        if floor has holes going down and painting else just paint last line
        while (true){
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
            if (move_right(i, yStart))
//                if move down possible return true
                if (move_down(i, yStart))
                    return true;
        }
        return false;
    }
//    check if move up / down / left / right is possible
    public boolean move_up(int x, int y){
        if (y>0)
            return loaded_array[y - 1][x].compareTo(" ") == 0 && y > 0;
        else
            return false;
    }
    public boolean move_down(int x, int y){
        if(y+1<height)
            return loaded_array[y + 1][x].compareTo(" ") == 0 && y < height;
        else
            return false;
    }
    public boolean move_left(int x, int y){
        if(x>0)
            return loaded_array[y][x-1].compareTo(" ")==0 && x > 0;
        else
            return false;
    }
    public boolean move_right(int x, int y){
        if(x+1<width)
            return loaded_array[y][x+1].compareTo(" ")==0 && x < width;
        else
            return false;
    }
//    get corners
    public void move_to_left_corner(){
        while (move_up(xStart, yStart) || move_left(xStart - 1, yStart)) {
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
        while (check_celling()){
            move_to_left_corner();
            move_to_right_corner();
        }
        move_to_left_corner();
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
