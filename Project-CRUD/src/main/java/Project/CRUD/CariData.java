package Project.CRUD;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class CariData{
    public static void main(String[] args) throws IOException{
        
        Scanner terminalInput = new Scanner(System.in);
        String pilihanUser;
        boolean isLanjutkan = true;

        while(isLanjutkan){

        clearScreen();

        System.out.println("Database Perpustakaan");
        System.out.println("1. \tLihat seluruh buku");
        System.out.println("2. \tCari data buku");
        System.out.println("3. \tTambah data buku");
        System.out.println("4. \tUbah data buku");
        System.out.println("5. \tHapus data buku");

        System.out.println("\n Pilihan anda : ");
        pilihanUser = terminalInput.next();

        switch (pilihanUser){
            case "1":
                System.out.println("\n=================");
                System.out.println("LIST SELURUH BUKU");
                System.out.println("=================");
                tampilkanData();                 
                //Tampilkan data
                break;
            case "2":
                System.out.println("\n=========");
                System.out.println("CARI BUKU");
                System.out.println("=========");
                cariData();
                //cari data
                break;
            case "3":
                System.out.println("\n================");
                System.out.println("TAMBAH DATA BUKU");
                System.out.println("================");
                //tambah data
                break;
            case "4":            
                System.out.println("\n==============");
                System.out.println("UBAH DATA BUKU");
                System.out.println("==============");
                //ubah data
                break;
            case "5":
                System.out.println("\n===============");
                System.out.println("HAPUS DATA BUKU");
                System.out.println("===============");
                //hapus data
                break;
            default:
                System.err.println("\nPilihan anda tidak ditemukan\nSilakan isi (1-5)");
            }
            
            isLanjutkan = getYesorNo("Apakah anda ingin melanjutkan");
            
        }
    }

    private static void cariData() throws IOException{

        // membaca database ada atau tidak
        try {
            File file = new File("database.txt");
        } catch (Exception e){
             System.err.println("Database tidak ditemukan");
             System.err.println("Silakan tambah data terlebih dahulu");
             return;
        }

        // kita ambil keyword dari user

        Scanner terminalInput = new Scanner(System.in);
        System.out.print("Masukkan kata kunci untuk mencari buku: ");
        String cariString = terminalInput.nextLine(); //nextLine() --> mencari satu baris
        System.out.println(cariString);

        String[] keywords = cariString.split("\\s+");

        cekBukuDiDatabase(keywords);

    }
    
            // kita cek keyword di database
 
    private static void cekBukuDiDatabase(String[] keywords) throws IOException{

        FileReader fileInput = new FileReader("database.txt");
        BufferedReader bufferInput = new BufferedReader(fileInput);

        String data = bufferInput.readLine();
        boolean isExist;
        int nomorData = 0;

        System.out.println("| No |\t Tahun \t| Penulis \t| Penerbit \t| Judul Buku \t|");
        System.out.println("=================================================================");

        while(data!=null){

            //cek keywords dalam baris
            isExist = true;
            
            for(String keyword: keywords){
                isExist = isExist && data.toLowerCase().contains(keyword.toLowerCase());
            }

            // jika keywordsnya cocok maka tampilkan

            if(isExist){
            nomorData++;
            StringTokenizer stringToken = new StringTokenizer(data, ",");
            
            stringToken.nextToken();
            System.out.printf("| %2d ", nomorData);
            System.out.printf("|   %4s \t",stringToken.nextToken());
            System.out.printf("| %s \t",stringToken.nextToken());
            System.out.printf("| %s \t",stringToken.nextToken());
            System.out.printf("| %s \t",stringToken.nextToken());
            System.out.println("");
            
        }

        data = bufferInput.readLine();
        }

    }

    private static void tampilkanData() throws IOException{
        FileReader fileInput;
        BufferedReader bufferInput;

        try {
            fileInput = new FileReader("database.txt");
            bufferInput = new BufferedReader(fileInput);
        } catch (Exception e){
             System.err.println("Database tidak ditemukan");
             System.err.println("Silakan tambah data terlebih dahulu");
             return;
        }
        
        
        System.out.println("| No |\t Tahun \t| Penulis \t| Penerbit \t| Judul Buku \t|");
        System.out.println("================================================================");
        
        String data = bufferInput.readLine();
        int noData = 0;

        while(data!=null){
            
            noData++;
            StringTokenizer stringToken = new StringTokenizer(data, ",");
            
            stringToken.nextToken();
        
            System.out.printf("| %2d ", noData);
            System.out.printf("|   %4s \t",stringToken.nextToken());
            System.out.printf("| %s \t",stringToken.nextToken());
            System.out.printf("| %s \t",stringToken.nextToken());
            System.out.printf("| %s \t",stringToken.nextToken());
            System.out.println("");
        
            data = bufferInput.readLine();

        }

        System.out.println("Akhir dari database");
    }

    private static boolean getYesorNo(String message){
        
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("\n"+message+"(y/n)? ");
        
        String pilihanUser = terminalInput.next();

        while(!pilihanUser.equalsIgnoreCase("y") && !pilihanUser.equalsIgnoreCase("n")){
            System.err.println("Pilihan anda bukan y atau n");
            System.out.print("\n"+message+"(y/n)? ");
            pilihanUser = terminalInput.next();
            
        }
        return pilihanUser.equalsIgnoreCase("y");
    }

    private static void clearScreen(){
    try{
        if(System.getProperty("os.name").contains("windows")){
                new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            } else {
                System.out.println("\033\143");
            }
        }catch (Exception ex){
                System.err.println("Tidak bisa clearscreen");
            }
        }
    }
