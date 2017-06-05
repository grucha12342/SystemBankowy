import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ListaUzytkownikow {
	public static List<Uzytkownik> lista = new LinkedList<Uzytkownik>();
	Uzytkownik uzytkownik = new Uzytkownik();
	
	public void dodajDoListy(){
		lista.add(uzytkownik.dodajUzytkownika());
	}
	
	public void usunZListy(String wzor) {
		lista.remove(uzytkownik.usunUzytkownika2(wzor));
	}
	
	public int rozmiar() {
		return lista.size();
	}
	
	public void odczytDoListy(){
		String imie, nazwisko, pesel, adres, linia;
		int id;
		double hajs;
		try{                                                     
			BufferedReader buforcz = new BufferedReader(new FileReader("bazadanych.txt"));
			while((linia = buforcz.readLine()) != null){
				StringTokenizer st = new StringTokenizer(linia, "||");
				System.out.println(linia);
				id = Integer.parseInt(st.nextToken());
				imie = st.nextToken();
				nazwisko = st.nextToken();
				pesel = st.nextToken();
				adres = st.nextToken();
				hajs = Double.parseDouble(st.nextToken());
				Uzytkownik nowy = new Uzytkownik(id,imie,nazwisko,pesel,adres,hajs);
				lista.add(nowy);
			}
			buforcz.close();
		}
		catch(IOException e){
			System.out.println("Błąd odczytu pliku");
			System.exit(0);
		}
		catch(NoSuchElementException e){
			System.out.println("Błąd zczytywania bazy");
			czyszczenie();
		}
	}
	
	public static boolean potwierdzenie(){
		String potw = "t";
		String akcept;
		System.out.println("Potwierdź wykonanie zadania wpisując 't'. Wpisanie innej litery będzie oznaczało rezygnację.");
		Scanner odczyta = new Scanner(System.in);
		akcept = odczyta.next();
		if(!potw.equals(akcept)){
			return false;
		}else{
			return true;
		}
	}
	
	public void czyszczenie(){
		try{
			System.out.println("Czyścisz całą bazę danych!");
			if(potwierdzenie()){
				File plik = new File("bazadanych.txt");
				File pliktymcz = new File("bazadanychtemp.txt");
				BufferedReader buforcz = new BufferedReader(new FileReader(plik));
				BufferedWriter bufor = new BufferedWriter(new FileWriter(pliktymcz,true));
				bufor.close();
				buforcz.close();
				boolean sukces = pliktymcz.renameTo(plik);
			}
		}
		catch(IOException e){
			System.out.println("Brak pliku");
			System.exit(0);
		}
	}
		
	
	public static List<Uzytkownik> przekazListe() {
		return lista;
	}
	
	
	
	
	public static int generatorID(){
		Random generator = new Random();
		int id = generator.nextInt(100000);
		return id;
	}
	
	public static boolean sprawdzID(int ID){
		for(int i = 0; i < lista.size() ; i++){
			if(ID == lista.get(i).numerKlienta){
				return false;
			}
		}
		return true;
	}
	
	public static int dodajID(){
		int ident;
		do{
			ident = generatorID();
		}while(!sprawdzID(ident));
		return ident;
	}

}
