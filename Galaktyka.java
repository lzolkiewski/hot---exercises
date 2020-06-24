package com.company;

public class Galaktyka {
    private static final String PATH = " ";

    private Integer telescope_size;
    private String orientation;
    private int path=0;
    private final String path_character=" ";
    private final String[][] galaxy = new String[10004][10004];
    private String[][]display_galaxy;

    public static void main(String[]args){
        new Galaktyka(args);
    }
    public Galaktyka(String[]args){
        this.check_args(args);
        create_galaxy();
        count_path();
        display_in_orientation();
        System.out.println(path);
    }
//    check validity of the arguments given
    public void check_args(String[] args){
        if(args.length==2){
            try{
//                Checking whether Y or N is passed as an argument
                if(args[1].compareTo("N")!=0 && args[1].compareTo("S")!=0 &&
                        args[1].compareTo("E")!=0 && args[1].compareTo("W")!=0){
                    System.out.println("klops");
                    System.exit(0);
                }
//                Checking whether passed size belongs between the boundaries
                if((telescope_size = Integer.parseInt(args[0]))<1 || telescope_size >10000){
                    System.out.println("klops");
                    System.exit(0);
                }
//               assign orientation in case everything is fine
                orientation=args[1];

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
//    create spiral in the galaxy array
    public void create_galaxy() {
//        initialize the galaxy with *
        for(int i = 0; i<galaxy.length; i++)
            for (int j = 0; j<galaxy.length; j++)
                galaxy[i][j] = "*";
        galaxy[1][0]=" ";

        int startX = 0, startY=1;
        int lastX = galaxy.length-1, lastY = galaxy.length-1;
        int firstX = 0, firstY = 2;
//        circling creating the galaxy
        while (firstX<lastX || firstY<lastY){
            for(;startX<lastX; startX++){
                galaxy[startY][startX]=path_character;
            }startX--;
            for(;startY<lastY; startY++){
                galaxy[startY][startX]=path_character;
            }startY--;
            for(;startX>firstX; startX--){
                galaxy[startY][startX]=path_character;
            }startX++;
            for(;startY>firstY;startY--){
                galaxy[startY][startX]=path_character;
            }startY++;

            firstX+=2;
            lastX-=2;
            firstY+=2;
            lastY-=2;
        }
    }
//    count the path in galaxy seen in the telescope and get the visible galaxy
    public void count_path(){
        display_galaxy =new String[telescope_size+3][telescope_size+2];

        for (int i = 0; i<telescope_size+3; i++){
            for (int j = 0; j<telescope_size+2; j++) {
//                getting visible galaxy
                display_galaxy[i][j]=galaxy[(galaxy.length/2)-(telescope_size+3)/2+i][(galaxy.length/2)-(telescope_size+2)/2+j];
//                counting the path
                if(galaxy[(galaxy.length/2)-(telescope_size+3)/2+i][(galaxy.length/2)-(telescope_size+2)/2+j].compareTo(path_character)==0)
                    path++;
            }
        }
    }
//    choose orientation depending on the argument given
    public void display_in_orientation(){
        switch (orientation){
            case "N":
                orientationN();
                break;
            case "S":
                orientationS();
                break;
            case "E":
                orientationE();
                break;
            case "W":
                orientationW();
                break;
            default:
                System.out.println("klops");
                System.exit(0);
                break;
        }
    }
//    display the seen part of the galaxy in given orientation N / S / E / W
    public void orientationN(){
        for (int i = 0; i<telescope_size+3; i++) {
            for (int j = 0; j < telescope_size + 2; j++) {
                System.out.print(display_galaxy[i][j]);
            }
            System.out.println();
        }
    }
    public void orientationS(){
        for (int i = 0; i<telescope_size+3; i++) {
            for (int j = 0; j < telescope_size + 2; j++) {
                System.out.print(display_galaxy[telescope_size+2-i][telescope_size+1-j]);
            }
            System.out.println();
        }
    }
    public void orientationE(){
        for (int i = 0; i<telescope_size+2; i++) {
            for (int j = 0; j < telescope_size + 3; j++) {
                System.out.print(display_galaxy[j][telescope_size+1-i]);
            }
            System.out.println();
        }
    }
    public void orientationW(){
        for (int i = 0; i<telescope_size+2; i++) {
            for (int j = 0; j < telescope_size + 3; j++) {
                System.out.print(display_galaxy[telescope_size+2-j][i]);
            }
            System.out.println();
        }
    }
}
