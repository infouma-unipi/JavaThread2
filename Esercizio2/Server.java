package Esercizio2;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server {

	public static final int PORT = 8880;
	//array delle caselle del tris
	public static char [] caselle = {0, 0, 0, 0, 0, 0, 0, 0, 0};
	static PrintWriter out;
	
	public static void main(String[] args) {
		try {
			//crea una nuova socket e passa la porta come input
			ServerSocket s = new ServerSocket(PORT);
			System.out.println("Started " + s);
			Socket socket = s.accept(); 
			System.out.println("Connection accepted: " + socket);
			// apre l'output verso il socket
			out = new PrintWriter (socket.getOutputStream(), true);
			OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
			out = new PrintWriter(new BufferedWriter(osw), true);
			//apre l'input dal socket
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//stampa i primi messaggi
//			//prova per vedere se va in (fallita)
//			out.println("Come ti chiami?");
//			String name = in.readLine();
//			out.println("Ciao " + name);
			out.println("Nuova partita di tris! Sei i cerchi ( O )");
			out.println(" (1) | (2) | (3) \n"
							 + "-----------------\n" 
							 + " (4) | (5) | (6) \n" 
							 + "-----------------\n" 
							 + " (7) | (8) | (9) \n");
			while (true) {
				out.println("Digita il numero della casella che vuoi occupare");
				//legge l'input dal client
				String pos = in.readLine();
				out.println("Ricevuto " + pos + "!");
				int posizione = Integer.parseInt(pos);
				//int posizione = (int)(Math.random() * 9) + 1;// Integer.parseInt(in.readLine());
				out.println("Mossa " + posizione + " ricevuta!");
				boolean controllato = false;
				//il ciclo prosegue finchè non si è sicuri che l'input sia una casella, e che sia vuota
				while(!controllato) {
					try {
						if (posizione <1 || posizione > 8) throw new IllegalArgumentException();
						else if (caselle[posizione-1] != 0) {
							out.println("La casella è già occupata! Scegline una vuota.");
							posizione = (int)(Math.random() * 9) + 1;//Integer.parseInt(in.readLine());
						} else { controllato=true; }
					} catch (IllegalArgumentException e) {
						out.println("Il numero inserito non corrisponde ad una casella. Seleziona una casella libera");
						posizione = (int)(Math.random() * 9) + 1;//Integer.parseInt(in.readLine());
					}
						
				}
				out.println("Fin qui tutto bene");
				//assegna ai cerchi la casella richiesta
				caselle[posizione-1] = 'O'; 
				out.println("Fin qui tutto bene");
				//giocano le X
				turnoX();
				out.println("Fin qui tutto bene");
				//stampa lo stato della partita
				stampaPartita();
				out.println("Fin qui tutto bene");
				//valuta se qualcuno ha vinto...
				int vittoria = arbitro();
				out.println("Fin qui tutto bene");
				//...e fa le stampe del caso. In caso di vittoria si interrompe e manda il messaggio di fine al client
				if (vittoria == 0) { out.println("La partita continua..."); }
				else {
					if (vittoria == 1) { out.println("X ha vinto. Hai perso!"); }
					else out.println("HAI VINTO!");
					out.println("La partita è finita");
					break;
				} 
				
			}
			//chiusura di tutti i sistemi aperti
			//aggiungere in e out!
			System.out.println("Il server si sta chiudendo...");
			out.close();
			in.close();
			socket.close();
			s.close();
		} catch (IOException e) {
			System.out.println("Errore di I/O");
			e.printStackTrace();
		}

	}
	
	public static int arbitro() {
		//controlla le combinazioni possibili di tris
		if (	(caselle[0]==caselle[1] && caselle[0]==caselle[2])
				|| (caselle[0]==caselle[3] && caselle[0]==caselle[6])
				|| (caselle[0]==caselle[4] && caselle[0]==caselle[8])	){
			//se l'elemento comune è la casella vuota non ha vinto nessuno
			if (caselle[0] == 0) {
				return 0;
			} else if (caselle[0] == 'X') {
					return 1;
			} else return 2;
		} else if ( (caselle[3]==caselle[4] && caselle[3]==caselle[5])
				|| (caselle[1]==caselle[4] && caselle[1]==caselle[7])
				|| (caselle[2]==caselle[4] && caselle[4]==caselle[6])	){
			//se l'elemento comune è la casella vuota non ha vinto nessuno
			if (caselle[4] == 0) {
				return 0;
			} else if (caselle[4] == 'X') {
					return 1;
			} else return 2;
		} else if ( (caselle[6]==caselle[7] && caselle[6]==caselle[8]) || (caselle[2]==caselle[5] && caselle[2]==caselle[8]) ) {
			//se l'elemento comune è la casella vuota non ha vinto nessuno
			if (caselle[8] == 0) {
				return 0;
			} else if (caselle[8] == 'X') {
					return 1;
			} else return 2;
		} else return 0;
	}
	
	public static void turnoX () {
		boolean check = false;
		int mossa = (int)(Math.random() * 9) + 1;
		while(!check) {
			if (caselle[mossa] != 0) {
				mossa = (int)(Math.random() * 9) + 1;
			} else { 
				check=true;
				caselle[mossa] = 'X';
				}
		}
	}
	
	public static void stampaPartita () {
		String [] stampe = new String [9];
		for (int i = 1; i <= caselle.length; i++) {
			if (caselle[i -1] == 0) {
				stampe[i-1] = "(" + i + ")";
			} else stampe[i-1] = " " + caselle[i-1] + " ";
		}
		out.println(" " + stampe[0] + " | " + stampe[1] + " | " + stampe[2] + " \n"
				 + "-----------------\n" 
				 + " " + stampe[3] + " | " + stampe[4] + " | " + stampe[5] + " \n" 
				 + "-----------------\n" 
				 + " " + stampe[6] + " | " + stampe[7] + " | " + stampe[8] + " \n");
	}

}
