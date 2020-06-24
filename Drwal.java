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
    private int file_height;
    private int file_width;

    public static void main(String[]args) throws IOException {
        Drwal drwal = new Drwal(args);
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
        file_height=file.size();
        file_width=size;
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
        // TODO: 24.06.2020 add traversing the table and painting the cells
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
//    check celling / floor for holes
    public boolean check_celling(){
        for (int i=xStart; i<width; i++){
//            if move right possible check above
            if (move_right(i, yStart))
//                if move upwards possible return true as celling has hole
                if(move_up(i, yStart))
                    return true;
        }
//        if reached the celling has no holes
        return false;
    }
    public boolean check_floor(){
        for(int i = xStart; i < width; i++){
//            if possible to move right check underneath
            if(move_right(i, yStart))
//                if move down possible floor has holes
                if (move_down(i, yStart))
                    return true;
        }
//        if reached floor has no holes
        return false;
    }
//    check if move up / down / left / right is possible
    public boolean move_up(int x, int y){
        return loaded_array[y - 1][x].compareTo(" ") == 0 && y > 0;
    }
    public boolean move_down(int x, int y){
        return loaded_array[y + 1][x].compareTo(" ") == 0 && y < height;
    }
    public boolean move_left(int x, int y){
        return loaded_array[y][x+1].compareTo(" ")==0 && x < width;
    }
    public boolean move_right(int x, int y){
        return loaded_array[y][x-1].compareTo(" ")==0 && x > 0;
    }
}
