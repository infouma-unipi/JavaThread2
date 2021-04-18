package Esercizio1;

public class Tavolo {	//classe buffer
	
	//MAX per la capacità massima del tavolo
	private int pizzeMAX;
	private int contenitoriMAX;
	//MIN per la quantità necessaria all'ordine
	private int pizzeMIN;
	private int contenitoriMIN;
	//TOT per quelli attualmente sul tavolo
	//il tavolo si assume inizialmente vuoto
	private int pizzeTOT = 0;
	private int contenitoriTOT = 0;
	
	private boolean tavoloOccupato = false;
		
	
	public Tavolo (int pMAX, int cMAX, int pMIN, int cMIN) {
		this.pizzeMAX = pMAX;
		this.contenitoriMAX = cMAX;
		this.pizzeMIN = pMIN;
		this.contenitoriMIN = cMIN;
	}

	//aggiunge degli ingredienti: ciascun pizzaiolo aggiunge sempre un unità dello stesso ingrediente.
	public synchronized void aggiungi () {
		if (Thread.currentThread() instanceof Pizzaiolo) { //questo controllo non è necessario perchè il metodo è chiamato solo da thread di tipo pizzaiolo
			Pizzaiolo p = (Pizzaiolo) Thread.currentThread();
			while (tavoloOccupato) {
				//... il thread Pizzaiolo va in wait
				System.out.println("Il pizzaiolo " + p.id +" aspetta di poter aggiungere il suo ingrediente: Tavolo occupato");
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Il pizzaiolo " + p.id +" si riscuote");
			}
			if (p.id==1) {
				while (!controlloPizzaiolo1()) {
					//... il thread Pizzaiolo va in wait
					System.out.println("Il pizzaiolo " + p.id +" aspetta di poter aggiungere la pizza: ce n'è troppa.");
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("Il pizzaiolo " + p.id +" si riscuote");
				}
			} else { //se è pizzaiolo2
					while (!controlloPizzaiolo2()) {
						//... il thread Pizzaiolo va in wait
						System.out.println("Il pizzaiolo " + p.id +" aspetta di poter aggiungere cartoni della pizza: sono troppi.");
						try {
							wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println("Il pizzaiolo " + p.id +" si riscuote");
					}
			}
			//quando il pizzaiolo si risveglia:
			//blocca l'accesso al tavolo
			tavoloOccupato = true;
			System.out.println("Il pizzaiolo " + p.id +" va a depositare i suoi ingredienti");
			//in base al pizzaiolo, viene incrementato di 1 un ingrediente di tipo diverso;
			if (p.id == 1) {
				pizzeTOT++;
				System.out.println("Il pizzaiolo " + p.id +" va a depositare una pizza. Ora ci sono " + pizzeTOT + " pizze.");
			} else {
				contenitoriTOT++;
				System.out.println("Il pizzaiolo " + p.id +" va a depositare un cartone. Ora ci sono " + contenitoriTOT + " cartoni.");
			}
			//libera il tavolo e risveglia gli altri thread
			tavoloOccupato = false;
			System.out.println("Il pizzaiolo " + p.id + " torna in cucina");
			notifyAll();
		}
	}
	
	
	public synchronized boolean controlloPizzaiolo1 () {
		if (pizzeMAX <= pizzeTOT) {
			return false;
		} else return true;
	}
	
	public synchronized boolean controlloPizzaiolo2 () {
		if (contenitoriMAX <= contenitoriTOT) {
			return false;
		} else return true;
	}
	
	//controlla che ci siano gli ingredienti necessari per la consegna
	public synchronized boolean controlloFattorino () {
		if ((pizzeMIN > pizzeTOT) || (contenitoriMIN > contenitoriTOT)) {
			return false;
		} else return true;
	}
	
	public synchronized void preleva () {
		//se il tavolo è occupato o se non ci sono sufficienti ingredienti...
		while (!(controlloFattorino()) || tavoloOccupato) {
			//... il thread Fattorino va in wait
			System.out.println("Il fattorino aspetta di poter prendere la consegna.");
			if (tavoloOccupato) System.out.println("Tavolo occupato");
			if (!(controlloFattorino())) System.out.println("Pizze: "+pizzeTOT + "; Cartoni: "+contenitoriTOT);
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Il fattorino si riscuote");
		}
		//quando il fattorino si risveglia:
		//blocca l'accesso al tavolo
		tavoloOccupato=true;
		System.out.println("Il fattorino sta prelevando gli ingredienti");
		//preleva pizze e contenitori in 10 ms
		pizzeTOT -= pizzeMIN;
		contenitoriTOT -= contenitoriMIN;
		//libera il tavolo e risveglia gli altri thread
		tavoloOccupato = false;
		System.out.println("Il fattorino parte per la consegna");
		notifyAll();
	}
	
}
