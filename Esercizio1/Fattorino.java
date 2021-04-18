package Esercizio1;

public class Fattorino extends Thread {

	public Tavolo table;
	public int id;
	
	public Fattorino (Tavolo t, int ID) {
		this.table = t;
		this.id = ID;
	}
	
	public void run () {
		//ciclo infinito
		while (true) {
			System.out.println("Il fattorino si avvicina al tavolo");
			//inizia la raccolta di materiali di consegnare, gestita da Tavolo
			table.preleva();
			//dorme 200 ms dopo aver compiuto l'azione per la consegna, prima di rientrare nel ciclo
			try {
				sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}
