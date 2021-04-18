package Esercizio1;

public class PizzaDelivery {

	public static void main(String[] args) {
		//crea un oggetto tavolo con capacità max 5 per le pizze e 10 per i cartoni; con due pizze e due cartoni parte l'ordine del fattorino
		Tavolo table = new Tavolo (5, 10, 2, 2);
		//crea due tread della classe Pizzaiolo, inizializzati per lavorare sull'oggetto tavolo; ciascuno ha il suo id
		Pizzaiolo p1 = new Pizzaiolo (table, 1);
		Pizzaiolo p2 = new Pizzaiolo (table, 2);
		//crea un thread della classe Fattorino con identificativo e lo inizializza a lavorare sull'oggetto tavolo
		Fattorino f1 = new Fattorino (table, 1);
		//fa partire tutti i thread
		p1.start();
		p2.start();
		f1.start();

	}

}
