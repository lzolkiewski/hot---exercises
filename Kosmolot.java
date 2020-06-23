package com.company;

public class Kosmolot {
    private Integer spaceship_size;
    private Boolean has_shield = false;

    public static void main(String[] args) {
        Kosmolot kosmolot = new Kosmolot();

        kosmolot.check_args(args);
    }
//    Method to check and set arguments that were passed to program
    public void check_args(String[]args){
        if(args.length==2){
            try{
//                Checking whether Y or N is passed as an argument
                if(args[1].compareTo("N")!=0 && args[1].compareTo("Y")!=0){
                    System.out.println("klops");
                    System.exit(0);
                }
//                Checking whether passed size belongs between the boundaries
                if((spaceship_size = Integer.parseInt(args[0]))<1 || spaceship_size >75){
                    System.out.println("klops");
                    System.exit(0);
                }
//               has_shield is gonna be true if Y is passed or false if not
                has_shield = (args[1].compareTo("Y") == 0);
//                begin displaying the spaceship in the console
                display();
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
//    Method to display the spaceship
    public void display(){
        String spaceship = "";
        int subtraction = spaceship_size-2;

        for(int i = 0 ; i < spaceship_size*2-1; i++){
            if(i <= spaceship_size-1){
                for(int position = 0; position < spaceship_size*spaceship_size; position++){
                    if(position%spaceship_size<=i){
                        if((position==0 || (position==spaceship_size*spaceship_size-1)) && has_shield){
                            spaceship += ">"; // setting the ion engines and sharp peak
                        }else {
                            if(has_shield && (position%spaceship_size==i) && (i != spaceship_size-1)){
                                spaceship+="\\";// setting shield to fend of the meteorites
                            }else {
                                spaceship += "*";
                            }
                        }
                    }else{
                        spaceship += " ";
                    }
                }
                spaceship += "\n";
//                    up till the middle part
            } else {
                for(int position = 0; position < spaceship_size*spaceship_size; position++){
                    if(position%spaceship_size<=subtraction){
                        if(position==0 && has_shield){
                            spaceship += ">"; // ion engines
                        }else{
                            if(has_shield && (position%spaceship_size==subtraction)){
                                spaceship+="/";// setting shield to fend of meteorites
                            }else{
                                spaceship += "*";
                            }
                        }
                    }else {
                        spaceship += " ";
                    }
                }
                spaceship += "\n";
                subtraction--;
            }
        }
        System.out.println(spaceship);
    }

}
