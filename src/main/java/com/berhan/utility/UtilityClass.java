package com.berhan.utility;

import java.util.Scanner;

public class UtilityClass {


        public  String stringDeger(String aciklama){
            System.out.print(aciklama);
            return new Scanner(System.in).nextLine();
        }

        public  int intDeger(String aciklama){
            System.out.print(aciklama);
            String deger =new Scanner(System.in).nextLine();
            return Integer.parseInt(deger);
        }

        public  Long longDeger(String aciklama){
            System.out.print(aciklama);
            String deger =new Scanner(System.in).nextLine();
            return Long.parseLong(deger);
        }

        public  Double doubleDeger(String aciklama){
            System.out.print(aciklama);
            String deger =new Scanner(System.in).nextLine();
            return Double.parseDouble(deger);
        }
    }

