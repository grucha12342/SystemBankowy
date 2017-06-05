import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;

public class Uzytkownik{
	int numerKlienta;
	String imie;
	String nazwisko;
	String pesel;
	String adres;
	double srodki;
	
	public Uzytkownik(){	
	}
	
	public Uzytkownik(int a, String b, String c, String d, String e, double f){
		numerKlienta = a;
		imie = b;
		nazwisko = c;
		pesel = d;
		adres = e;
		srodki = f;
	}
	
	public Uzytkownik dodajUzytkownika(){
		try{
			System.out.println("Dodajesz nowego użytkownika.");
			String imie,nazwisko,adres,pesel;
			int klienta;
			double hajs;
			Scanner odczyt = new Scanner(System.in);
			Scanner doadresu = new Scanner(System.in);
			System.out.println("Podaj imie klienta");
			imie = odczyt.next();
			System.out.println("Podaj nazwisko klienta");
			nazwisko = odczyt.next();
			do{
				System.out.println("Podaj pesel klienta");
				pesel = odczyt.next();
			}while(!sprawdzPesel(pesel));
			System.out.println("Podaj adres klienta (MIASTO ULICA NUMER)");
			adres = doadresu.nextLine();
			System.out.println("Podaj hajs klienta");
			hajs = odczyt.nextDouble();
			klienta = ListaUzytkownikow.dodajID();
			System.out.println("Wygenerowany numer klienta: "+klienta);
			Uzytkownik nowy = new Uzytkownik(klienta,imie,nazwisko,pesel,adres,hajs);
			System.out.println("Dodajesz użytkownika:");
			wyswietlDodawanego(nowy);
			if(ListaUzytkownikow.potwierdzenie()){
				Bank.zapis(nowy);
				return nowy;
			}
		}
		catch(InputMismatchException e) {
			System.out.println("Format pieniędzy to liczby oddzielone przecinkiem");
		}
		Uzytkownik nic = new Uzytkownik();
		return nic;
	}
	
		
	
	public int usunUzytkownika(List<Uzytkownik> lista, int numer){
		try{
			File plik = new File("bazadanych.txt");
			File pliktymcz = new File("bazadanychtemp.txt");
			BufferedReader buforcz = new BufferedReader(new FileReader(plik));
			BufferedWriter bufor = new BufferedWriter(new FileWriter(pliktymcz,true));	
			String linia = "";
			int wyjscie = 0;
			int liczbaLinii = 0;
			for(int i = 0; i < lista.size() ; i++) {
				linia = buforcz.readLine();
				if(numer == lista.get(i).numerKlienta){
					System.out.println("usuwasz/modyfikujesz użytkownika: "+linia);
					if(ListaUzytkownikow.potwierdzenie()){
						wyjscie = liczbaLinii;
						continue;
					}
				}
				bufor.write(linia);
				bufor.newLine();
				liczbaLinii++;
			}
			bufor.close();
			buforcz.close();
			boolean sukces = pliktymcz.renameTo(plik);
			return wyjscie;
		}
		catch(IOException e){
			System.out.println("Brak pliku");
			System.exit(0);
		}
		return 0; 
	}
	
	public static void modyfikujUzytkownika(Uzytkownik u, int numer){
		try{
			File plik = new File("bazadanych.txt");
			File pliktymcz = new File("bazadanychtemp.txt");
			BufferedReader buforcz = new BufferedReader(new FileReader(plik));
			BufferedWriter bufor = new BufferedWriter(new FileWriter(pliktymcz,true));
			Pattern pattern = null;
			String linia = "";
			String wzor = Integer.toString(numer);
			int wyjscie = 0;
			pattern = Pattern.compile(wzor);
			while((linia = buforcz.readLine()) != null){
				Matcher matcher = pattern.matcher(linia);
				
				if(matcher.find()){
					System.out.println("Modyfikujesz użytkownika: "+linia);
					if(ListaUzytkownikow.potwierdzenie()){
						bufor.write(u.numerKlienta+"||"+u.imie+"||"+u.nazwisko+"||"+u.pesel+"||"+u.adres+"||"+u.srodki);
						bufor.newLine();
						continue;
					}
				}
				bufor.write(linia);
				bufor.newLine();
			}
			bufor.close();
			buforcz.close();
			boolean sukces = pliktymcz.renameTo(plik);
		}
		catch(IOException e){
			System.out.println("Brak pliku");
		}
	}
	
	public int usunUzytkownika2(String wzor){
		try{
			File plik = new File("bazadanych.txt");
			File pliktymcz = new File("bazadanychtemp.txt");
			BufferedReader buforcz = new BufferedReader(new FileReader(plik));
			BufferedWriter bufor = new BufferedWriter(new FileWriter(pliktymcz,true));
			Pattern pattern = null;
			String linia = "";
			int wyjscie = 0;
			int liczbaLinii = 0;
			pattern = Pattern.compile(wzor);
			while((linia = buforcz.readLine()) != null){
				Matcher matcher = pattern.matcher(linia);
				
				if(matcher.find()){
					System.out.println("Usuwasz użytkownika: "+linia);
					if(ListaUzytkownikow.potwierdzenie()){
						wyjscie = liczbaLinii;
						continue;
					}
				}
				bufor.write(linia);
				bufor.newLine();
				liczbaLinii++;
			}
			bufor.close();
			buforcz.close();
			boolean sukces = pliktymcz.renameTo(plik);
			return wyjscie;
		}
		catch(IOException e){
			System.out.println("Brak pliku");
		}
		return 0;
	}
	
	public static void wyswietlDodawanego(Uzytkownik u){
		System.out.println(u.numerKlienta+" "+u.imie+" "+u.nazwisko+" "+u.pesel+" "+u.adres+" "+u.srodki);
	}
	
	public static boolean sprawdzPesel(String x){
		if(x.length() != 11){
			System.out.println("Niepoprawny pesel");
			return false;
		} else {
			for(int i = 0; i < x.length(); i++){
				if(!Character.isDigit(x.charAt(i))){
					System.out.println("Niepoprawny pesel");
					return false;
				}
			}
		}
		return true;
	}
}
