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
    private int file_height;
    private int file_width;

    public static void main(String[]args) throws IOException {
        Drwal drwal = new Drwal(args);
    }

    public Drwal(String[]args) throws IOException {
        check_args(args);
        load_file();

    }

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
        String[][]loaded_array = new String[file.size()][size];
        for (int i = 0; i < file_height; i++) {
            for (int j = 0; j < file_width; j++) {
                loaded_array[i][j] = String.valueOf(file.get(i).charAt(j));
            }
        }
        paint(loaded_array);
    }

    public void paint(String[][]loaded_file){
        String[][]array = new String[height][width];
        for (int i = 0; i < height; i++) {
            if (width >= 0) System.arraycopy(loaded_file[i], 0, array[i], 0, width);
        }
        find_corner(array);
    }

    public void find_corner(String[][]file_array){
        if(file_array[yStart][xStart].compareTo(" ")!=0){
            System.out.println("klops");
            System.exit(0);
        }

        while (true) {
            if (!moveUp(file_array))
                if (!moveLeft(file_array))
                    if(!check_celling(file_array))
                        break;
        }
        System.out.println(xStart+" "+yStart);
    }

    public boolean check_celling(String[][]file_array){
        for (int i = xStart; i<width-1; i++){
            if(file_array[yStart-1][i].compareTo(" ")==0 && file_array[yStart][i+1].compareTo(" ")==0){
                xStart=i;
                return moveUp(file_array);
            }
        }
        return false;
    }

    public boolean moveUp(String[][]file_array){
        if(yStart!=0){
            if(file_array[yStart-1][xStart].compareTo(" ")==0){
                yStart--;
                return true;
            }
        }
        return false;
    }
    public boolean moveDown(String[][]file_array){
        if(yStart!=file_height){
            if(file_array[yStart+1][xStart].compareTo(" ")==0){
                yStart++;
                return true;
            }
        }
        return false;
    }
    public boolean moveRight(String[][]file_array){
        if(xStart!=file_width){
            if(file_array[yStart][xStart+1].compareTo(" ")==0){
                xStart++;
                return true;
            }
        }
        return false;
    }
    public boolean moveLeft(String[][]file_array){
        if(xStart!=0){
            if(file_array[yStart][xStart-1].compareTo(" ")==0){
                xStart--;
                return true;
            }
        }
        return false;
    }

}
