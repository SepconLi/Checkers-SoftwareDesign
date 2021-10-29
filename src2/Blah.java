import javax.swing.*;
import java.util.Scanner;

class Blah {

    public static void main (String [] args){

        Scanner nameScanner = (new Scanner("Whats your name?"));
        String anotherName = (nameScanner.nextLine());


        System.out.println(product(3, 4));


    }

    public static int product (int a, int b){

        int product = a*b;
        return product;


    }

}