package Project.CRUD;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Array;
import java.time.Year;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class TambahData{
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
                tambahData();
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
    private static void tambahData() throws IOException{
    

        FileWriter fileOutput = new FileWriter("database.txt",true);
            BufferedWriter bufferOutput = new BufferedWriter(fileOutput);
        
            //mengambil input dari user
            Scanner terminalInput = new Scanner (System.in);
            String penulis, judul, penerbit, tahun;
        
            System.out.println("Masukkan nama penulis: ");
            penulis = terminalInput.nextLine();
            System.out.print("Masukkan judul buku: ");
            judul = terminalInput.nextLine();
            System.out.print("Masukkan nama penerbit: ");
            penerbit = terminalInput.nextLine();
            System.out.print("Masukkan tahun terbit, format=(YYYY): ");
            tahun = ambilTahun();
        
            //cek buku di database
                
            String[] keywords = {tahun+","+penulis+","+penerbit+","+judul};
            System.out.println(Arrays.toString(keywords));

            boolean isExist = cekBukuDiDatabase(keywords, false);

            //menulis buku di database
            if (!isExist){
//          fiersabesari_2012_1,2012,fiersa besari,media kita,jejak langkah
            System.out.println(ambilEntryPerTahun(penulis, tahun));
            long nomorEntry = ambilEntryPerTahun(penulis, tahun) + 1;

            String penulisTanpaSpasi = penulis.replaceAll("\\s+", "");
            String primaryKey = punulisTanpaSpasi+"_"+tahun+"_"+nomorEntry;
            System.out.println("\nData yang akan anda masukan adalah");
            System.out.println("----------------------------------------");
            System.out.println("primary key  : " + primaryKey);
            System.out.println("tahun terbit : " + tahun);
            System.out.println("penulis      : " + penulis);
            System.out.println("judul        : " + judul);
            System.out.println("penerbit     : " + penerbit);

            boolean isTambah = getYesorNo("Apakah akan ingin menambah data tersebut? ");
            if(isTambah){
                bufferOutput.write(primaryKey + "," + tahun + "," + penulis +","+penerbit+","+judul);
                bufferOutput.newLine();
                bufferOutput.flush();
            }
    
        }else{
            System.out.println("buku yang anda akan masukkan sudah tersedia di database dengan data berikut: ");
            cekBukuDiDatabase(keywords, true);
        }
                 
        bufferOutput.close(); //char stream harus diclose, kalau read otomatis
    }

    private static long ambilEntryPerTahun(String penulis, String tahun) throws IOException
         
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

   
       
    private static String ambilTahun() throws IOException{            
        boolean tahunValid = false;
        Scanner terminalInput = new Scanner (System.in);
        String tahunInput = terminalInput.nextLine();
    
        while(!tahunValid){
            try{
                Year.parse(tahunInput);
                tahunValid = true;
            } catch (Exception e){
                System.err.println("format tahun yang anda masukkan salah");
                System.err.println("masukkan tahun terbit lagi");
                tahunValid = false;
                tahunInput = terminalInput.nextLine();
            }
        }

        return tahunInput;
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
