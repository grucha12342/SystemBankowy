import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bank{
	public void wplata(int numer, double kwota){
		List<Uzytkownik> lista = ListaUzytkownikow.przekazListe();
		for(int i = 0; i < lista.size() ; i++){
			if(numer == lista.get(i).numerKlienta){
				double c = lista.get(i).srodki;
				c+=kwota;
				lista.get(i).srodki = c;
				Uzytkownik.modyfikujUzytkownika(lista.get(i),numer);
			}
		}
		System.out.println("Akcja wpłaty zakończona");
	}
	
	
	public void wyplata(int numer, double kwota){
		List<Uzytkownik> lista = ListaUzytkownikow.przekazListe();
		for(int i = 0; i < lista.size() ; i++) {
			if(numer == lista.get(i).numerKlienta && zdolnoscKredytowa(i,kwota)){
				double c = lista.get(i).srodki;
				c-=kwota;
				lista.get(i).srodki = c;
				Uzytkownik.modyfikujUzytkownika(lista.get(i),numer);
			}
		}
		System.out.println("Akcja wypłaty zakończona");
		
	}
	
	public void transfer(int numerod, int numerdo, double kwota){
		wyplata(numerod, kwota);
		wplata(numerdo, kwota);
	}
	
	public void wyswietlPoPliku(String wzor){
		String linia = "";
		Pattern pattern=null;
		int liczbaLinii = 0;
		try{
			BufferedReader buforcz = new BufferedReader(new FileReader("bazadanych.txt"));
			pattern = Pattern.compile(wzor);
			while((linia = buforcz.readLine()) != null){
				liczbaLinii++;
				Matcher matcher = pattern.matcher(linia);
				if(matcher.find()){
					System.out.println(liczbaLinii+".   "+linia);
				}
			}
			System.out.println("Koniec wyszukiwania");
			buforcz.close();	
		}
		catch(IOException e){
			System.out.println("Brak pliku");
		}
		
	}
	
	public static void zapis(Uzytkownik u){
		try{
			BufferedWriter bufor = new BufferedWriter(new FileWriter("bazadanych.txt",true));
			bufor.write(u.numerKlienta+"||"+u.imie+"||"+u.nazwisko+"||"+u.pesel+"||"+u.adres+"||"+u.srodki);
			bufor.newLine();
			bufor.close();
		}
		catch(IOException e){
			e.printStackTrace();
			System.out.println("Brak pliku");
		}

	}
	
	public int odczyt(){
		String linia = "";
		int liczbaLinii = 0;
		try{
			BufferedReader buforcz = new BufferedReader(new FileReader("bazadanych.txt"));
			while((linia = buforcz.readLine()) != null){
				liczbaLinii++;
				System.out.println(linia);
			}
			buforcz.close();	
			System.out.println("Baza posiada aktualnie "+liczbaLinii+" użytkowników");
			return liczbaLinii;
			
		}
		catch(IOException e){
			System.out.println("Brak pliku");
			System.exit(0);
		}
		return 0;
	}
	

	
	public static boolean zdolnoscKredytowa(int i, double kwota){
		List<Uzytkownik> lista = ListaUzytkownikow.przekazListe();
		if((lista.get(i).srodki-kwota)<-1000){
			System.out.println("Brak zdolności kredytowej (min. stan konta -1000)");
			return false;
		}
		return true;
	}
	
	
}

