package Esercizio1;

public class Pizzaiolo extends Thread {
	
	public Tavolo bancone;
	public int id;
	
	public Pizzaiolo (Tavolo t, int ID) {
		this.bancone = t;
		this.id = ID;
	}
	
	public void run () {
		while (true) {
			System.out.println("Il pizzaiolo " +this.id + " si avvicina al tavolo");
			//inizia il deposito di ingredienti, gestita da Tavolo
			bancone.aggiungi();
			//dorme 200 ms dopo aver compiuto l'azione di deposito per preparare il nuovo ingrediente, prima di rientrare nel ciclo
			try {
				sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
