package com.kafka;

import java.util.Scanner;

public class App 
{
    public static void main( String[] args )
    {
        Scanner sc = new Scanner(System.in);  
        System.out.println("Run Producer or Consumer? P/C");
        while(true){
            String choice = sc.nextLine();
            if( choice.equals("C")){
                sc.close();
                System.out.println("Consumer is running...");
                new Consumer();
                break;
            } else if (choice.equals("P")) {
                System.out.println("Producer is running...");
                sc.close();
                new Producer();
                break;
            } else {
                System.out.println("Not a supported option, pick either P or C.");
            }
        }
        System.out.println( "Kafka Hello World!" );
    }
}
