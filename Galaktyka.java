package com.company;

public class Galaktyka {
    private static final String PATH = " ";

    private Integer telescope_size;
    private String orientation;
    private Integer path=0;
    private final String path_character=" ";
    private final String[][] galaxy = new String[10004][10004];

    public static void main(String[]args){
        Galaktyka galaxy = new Galaktyka(args);

    }
    
    public Galaktyka(String[]args){
        this.check_args(args);
        create_galaxy();
        create_path();
    }

    public void check_args(String[] args){
        if(args.length==2){
            try{
//                Checking whether Y or N is passed as an argument
                if(args[1].compareTo("N")!=0 && args[1].compareTo("S")!=0 &&
                        args[1].compareTo("E")!=0 && args[1].compareTo("W")!=0){
                    System.out.println("Pass correct orientation (N/S/E/W)");
                    System.exit(0);
                }
//                Checking whether passed size belongs between the boundaries
                if((telescope_size = Integer.parseInt(args[0]))<1 || telescope_size >10000){
                    System.out.println("Pass the value between <1,10000>");
                    System.exit(0);
                }
//               assign orientation in case everything is fine
                orientation=args[1];

//                Checking whether the value is an Integer
            }catch (NumberFormatException e){
                System.out.println(args[0]+ " is not an integer");
            }
//            In case no arguments are passed
        }else{
            System.out.println("Err disappointed, pass (some value <1,10000>) (orientation (N/S/E/W))");
            System.exit(0);
        }
    }

    public void create_galaxy() {
//        initialize
        for(int i = 0; i<galaxy.length; i++)
            for (int j = 0; j<galaxy.length; j++)
                galaxy[i][j] = "*";
        galaxy[1][0]=" ";

        int startX = 0, startY=1;
        int lastX = galaxy.length-1, lastY = galaxy.length-1;
        int firstX = 0, firstY = 2;

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

    public void create_path(){
        String[][]display_galaxy =new String[telescope_size+3][telescope_size+2];
        for (int i = 0; i<telescope_size+3; i++){
            for (int j = 0; j<telescope_size+2; j++) {
                display_galaxy[i][j]=galaxy[(galaxy.length/2)-(telescope_size+3)/2+i][(galaxy.length/2)-(telescope_size+2)/2+j];
                if(galaxy[(galaxy.length/2)-(telescope_size+3)/2+i][(galaxy.length/2)-(telescope_size+2)/2+j].compareTo(path_character)==0)
                    path++;
            }
        }
        display_in_orientation(display_galaxy);
        System.out.println(path);
    }
    public void display_in_orientation(String[][]display_galaxy){
        switch (orientation){
            case "N":
                orientationN(display_galaxy);
                break;
            case "S":
                orientationS(display_galaxy);
                break;
            case "E":
                orientationE(display_galaxy);
                break;
            case "W":
                orientationW(display_galaxy);
                break;
            default:
                System.out.println("Something went wrong with orientation");
                System.exit(0);
                break;
        }
    }

    public void orientationN(String[][]display_galaxy){
        for (int i = 0; i<telescope_size+3; i++) {
            for (int j = 0; j < telescope_size + 2; j++) {
                System.out.print(display_galaxy[i][j]);
            }
            System.out.println();
        }
    }
    public void orientationS(String[][]display_galaxy){
        for (int i = 0; i<telescope_size+3; i++) {
            for (int j = 0; j < telescope_size + 2; j++) {
                System.out.print(display_galaxy[telescope_size+2-i][telescope_size+1-j]);
            }
            System.out.println();
        }
    }
    public void orientationE(String[][]display_galaxy){
        for (int i = 0; i<telescope_size+2; i++) {
            for (int j = 0; j < telescope_size + 3; j++) {
                System.out.print(display_galaxy[j][telescope_size+1-i]);
            }
            System.out.println();
        }
    }
    public void orientationW(String[][]display_galaxy){
        for (int i = 0; i<telescope_size+2; i++) {
            for (int j = 0; j < telescope_size + 3; j++) {
                System.out.print(display_galaxy[telescope_size+2-j][i]);
            }
            System.out.println();
        }
    }

}
