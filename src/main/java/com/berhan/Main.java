package com.berhan;

import com.berhan.controller.AracController;
import com.berhan.controller.KiralamaController;
import com.berhan.controller.KisiController;
import com.berhan.enums.EMarka;
import com.berhan.enums.EModel;
import com.berhan.repository.AracRepository;
import com.berhan.repository.KisiRepository;
import com.berhan.repository.entity.Arac;
import com.berhan.repository.entity.Kiralama;
import com.berhan.repository.entity.Kisi;
import com.berhan.utility.UtilityClass;


import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.girisEkrani();

    }

    KisiController kisiController = new KisiController();

    public static Long userId;
    Scanner scanner = new Scanner(System.in);

    private void login() {
        System.out.print("Kullanıcı Adınızı giriniz : ");
        String userName = new Scanner(System.in).nextLine();

        System.out.print("Kullanıcı Sifrenizi Giriniz : ");
        String sifre = new Scanner(System.in).nextLine();


            userId = new KisiRepository().findByColumnNameAndValue("username", userName).get(0).getId();
            Long passwordId = new KisiRepository().findByColumnNameAndValue("password", sifre).get(0).getId();

            if (userId.equals(passwordId)) {
                System.out.println("Sisteme Giriş Başarılı");
                cases();
            } else {
                System.out.println("Kullanıcı Adı veya Sifre Hatalı");
                login();
            }
        }


    private int aracKiralamaIntro() {
        System.out.println("*************************************");
        System.out.println("********* GİRİŞ  İSLEMLERİ  *********");
        System.out.println("*************************************");
        System.out.println();
        System.out.println("1- Üye Ol");
        System.out.println("2- Giriş Yap");
        System.out.println("0- Cikis Yap");
        int secim =  utilityClass.intDeger("Seçim yapınız");
        return secim;
    }

    public void girisEkrani(){
        boolean status=true;
        do {
            switch (aracKiralamaIntro()){
                case 1:kisiEkle();break;
                case 2:login();break;
                case 0:status=false;
                break;
            }
        }while (status);
        System.out.println("Bye");


    }
    //TODO = ÜYE OL METHODU YAZILACAK SWİTCH YAPILACAK ENUMARETEDLAR YAPILACAK

    public int afterLogin() {
        System.out.println("1-Raporlama Menusu");
        System.out.println("2-Musteriye Gore Kiraladıgı Araclar");
        System.out.println("3-Bosta Olan Araclar");
        System.out.println("4-Kirada Olan Araclar");
        System.out.println("5-Kiralama Yap");
        System.out.println("6-Arac Ara");
        System.out.println("7-Kisi Ekle");
        System.out.println("8-Arac Ekle");
        System.out.println("0-Cıkıs");
        System.out.println("Secim Yapınız :");
        int secim = new Scanner(System.in).nextInt();

        return secim;

    }


    public void cases() {
        boolean status = true;
        do {
            switch (afterLogin()) {
                case 1:
                    raporlama();break;
                case 2:
                    musteriyeGoreKiralananAraclar();break;
                case 3:
                    bostaOlanAraclar();break;
                case 4:
                    kiradOlanAraclar();break;
                case 5:
                    kiralamaYap0();break;
                case 6:
                    aracAra();break;
                case 7:
                    kisiEkle();break;
                case 8:
                    aracEkle();break;
                case 0:
                    status = false;
                    break;

            }
        } while (status);


    }

    private void raporlama() {
        new KiralamaController().findAll().forEach(System.out::println);
    }

    public void musteriyeGoreKiralananAraclar(){
        new KisiController().findAll().forEach(System.out::println);
        Long musteriId = utilityClass.longDeger("Gormek istediğiniz müşterinin id sini giriniz");
        new KiralamaController().findAll().stream().filter(kiralama ->
                kiralama.getKisi().equals(new KisiController().findById(musteriId)));
    }

    private void bostaOlanAraclar() {
        new AracController().findAll().stream().filter(x->x.getDurum()==true).collect(Collectors.toList())
                .forEach(System.out::println);
    }

    private void kiradOlanAraclar() {
        new AracController().findAll().stream().filter(x->x.getDurum()==false).collect(Collectors.toList())
                .forEach(System.out::println);
    }

    public void kiralamaYap0() {
        new AracController().findAll().stream().filter(x->x.getDurum()==true).collect(Collectors.toList())
                .forEach(System.out::println);

        Long id = utilityClass.longDeger("Kiralamak istediğiniz aracın id sini giriniz");
        new KisiController().findAll().forEach(System.out::println);
        Long musteriId= utilityClass.longDeger("Kiralayan müşterinin id sini giriniz");
       int baslangicGun = utilityClass.intDeger("Kiralamanın başlayacağı günü giriniz: ");
        int baslangicAy= utilityClass.intDeger("Kiralamanın başlayacağı ayı giriniz: ");
        int baslangicYil =  utilityClass.intDeger("Kiralamanın başlayacağı yılı giriniz: ");

        int bGun = utilityClass.intDeger("Kiralamanın biteceği günü giriniz: ");
        int bAy= utilityClass.intDeger("Kiralamanın biteceği ayı giriniz: ");
        int bYil =  utilityClass.intDeger("Kiralamanın biteceği yılı giriniz: ");

                Kiralama kiralama = Kiralama.builder()
                        .baslangicTarihi(LocalDate.of(baslangicYil,baslangicAy,baslangicGun))
                        .kisi(new KisiController().findById(musteriId).get())
                        .arac(new AracController().findById(id).get())
                        .bitisTarihi(LocalDate.of(bYil,bAy,bGun))
                        .build();


        Arac arac = new AracController().findById(id).get();
       arac.setDurum(false);
        new AracController().update(arac);
        if(kiralama.getArac().getDurum()==false ){
            System.out.println("Arac Doludur Geçerli Arac Seciniz");
        }else{
            new KiralamaController().kiralamaYap(kiralama);
        }
    }

    private Optional<Arac> aracAra() {
        String aramakIstenilenAracPlakasi = utilityClass.stringDeger("Aramak Istenilen Arac Plakasini Giriniz");
        Long aracdId = new AracRepository().findByColumnNameAndValue("plaka",aramakIstenilenAracPlakasi).get(0).getId();
       return new AracController().findById(aracdId);
    }

    UtilityClass utilityClass = new UtilityClass();

    private Arac aracEkle() {

        String marka=utilityClass.stringDeger("Arac Marka :").toUpperCase();
        String model=utilityClass.stringDeger("Arac Model :").toUpperCase();

        try {
            String plaka=utilityClass.stringDeger("Plaka Giriniz :");
            Arac arac= Arac.builder()
                    .marka(EMarka.valueOf(marka))
                    .model(EModel.valueOf(model))
                    .plaka(plaka)
                    .durum(true)
                    .build();
            new AracController().save(arac);
            return arac;
        }catch (IllegalArgumentException e){
            System.out.println("İstediğiniz özellikte araba mevcut değildir.Gecerli Araba Giriniz");
        }
        return aracEkle();
    }

    private void kisiEkle() {
        System.out.println("İsminizi giriniz.");
        String name=new Scanner(System.in).nextLine();
        System.out.println("Kullanici adinizi giriniz.");
        String username=new Scanner(System.in).nextLine();
        System.out.println("Şifrenizi giriniz.");
        String password=new Scanner(System.in).nextLine();
        Kisi kisi= Kisi.builder()
                .name(name)
                .username(username)
                .password(password)
                .build();
        kisiController.save(kisi);
    }

}