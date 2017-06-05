import java.util.*;

public class Interfejs{
	Uzytkownik uzytkownik = new Uzytkownik();
	static Bank bank = new Bank();
	static ListaUzytkownikow lista = new ListaUzytkownikow();
	static String fraza;
	static int idklienta;
	static int idklienta2;
	static double kwota;
	static Scanner przycisk = new Scanner(System.in);
	
	public static void main(String[] args){
		lista.odczytDoListy();
		System.out.println("rozmiar wed?ug listy "+lista.rozmiar());
		do{
			wypiszMenu();
			przelacznik();
		}while(true);
	}
	
	public static void wypiszMenu(){
			System.out.println();
			System.out.println("System bankowy");
			System.out.println("d - dodaj uzytkownika");
			System.out.println("u - usuń uzytkownika");
			System.out.println("w - wyświetl wszystkich użytkowników");
			System.out.println("wyb - wyświetl wybranego użytkownika");
			System.out.println("r - rozmiar wed?ug listy");
			System.out.println("wplata - dokonuje wpłaty na konto danego użytkownika");
			System.out.println("wyplata - dokonuje wypłaty z konta danego użytkownika");
			System.out.println("transfer - przelewy między kontami");
			System.out.println("reset - wyczyść bazę danych użytkowników");
			System.out.println("x - wyjście z systemu");
			System.out.println();
	}
	
	public static void usun(){
		try{
			System.out.println("Podaj imie, nazwisko, lub adres uzytkownika do usunięcia");
			fraza = przycisk.next();
			lista.usunZListy(fraza);
		}
		catch(IndexOutOfBoundsException e){
			System.out.println("Przekroczono zakres w liście");
		}
	}
	
	public static void wybierz(){
		System.out.println("Podaj któreś z kryteriów do wyświetlenia użytkownika");
		fraza = przycisk.next();
		bank.wyswietlPoPliku(fraza);
	}
	
	public static void wplacanie(){
		try{
			System.out.println("Podaj ID użytkownika konta do dokonania wpłaty");
			idklienta = przycisk.nextInt();
			System.out.println("Podaj kwotę");
			kwota = przycisk.nextDouble();
			bank.wplata(idklienta, kwota);
		}
		catch(InputMismatchException e){
			System.out.println("ID liczba dziesiętna, kwota liczba zmiennoprzecinkowa");
		}
	}
	
	public static void wyplacanie(){
		try{
			System.out.println("Podaj ID użytkownika konta do dokonania wypłaty");
			idklienta = przycisk.nextInt();
			System.out.println("Podaj kwotę");
			kwota = przycisk.nextDouble();
			bank.wyplata(idklienta, kwota);
		}
		catch(InputMismatchException e){
			System.out.println("ID liczba dziesiętna, kwota liczba zmiennoprzecinkowa");
		}
	}
	
	public static void przelew(){
		try{
			System.out.println("Podaj ID użytkownika konta do dokonania wypłaty do przelewu");
			idklienta = przycisk.nextInt();
			System.out.println("Podaj ID użytkownika konta do dokonania wpłaty przelewu");
			idklienta2 = przycisk.nextInt();
			System.out.println("Podaj kwotę");
			kwota = przycisk.nextDouble();
			bank.transfer(idklienta, idklienta2, kwota);
		}
		catch(InputMismatchException e){
			System.out.println("ID liczba dziesiętna, kwota liczba zmiennoprzecinkowa");
		}
	}
	
	public static void przelacznik(){
		switch(przycisk.next()){
				case "d":
					lista.dodajDoListy();;
					break;
					
				case "u":
					usun();
					break;
					
				case "w":
					bank.odczyt();
					break;
					
				case "wyb":
					wybierz();
					break;
					
				case "r":
					System.out.println("Rozmiar wed?ug listy: "+lista.rozmiar());
					break;
					
				case "wplata":
					wplacanie();
					break;
					
				case "wyplata":
					wyplacanie();
					break;
					
				case "transfer":
					przelew();
					break;
					
				case "reset":
					lista.czyszczenie();
					break;
					
				case "x":
					System.exit(0);
					break;
					
				default:
					System.out.println("Nie ma takiej opcji");
					break;
			}
	}
	
}