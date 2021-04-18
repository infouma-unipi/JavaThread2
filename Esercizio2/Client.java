package Esercizio2;

import java.io.*;
import java.net.*;
import java.util.Scanner;


public class Client {

	public static void main(String[] args) {
		try {
			InetAddress addr = InetAddress.getByName(null);
			//crea un socket specificando indirizzo e porta del server
			Socket socket = new Socket (addr, 8880);
			//apro un canale input verso il server
			Scanner input = new Scanner(System.in);
			BufferedReader in = new BufferedReader (new InputStreamReader (socket.getInputStream()));
			PrintWriter out = new PrintWriter (new OutputStreamWriter (socket.getOutputStream()));
//			//prova per vedere se va in da server (fallita)
//			String leggi = in.readLine();
//			System.out.println(leggi);
//			String nome = input.nextLine();
//			out.println(nome);
//			System.out.println("inviato " + nome);
			//boolean vittoria=true;
			while (true) {
				String serverOutput = in.readLine();
				System.out.println(serverOutput);
				if(serverOutput.equals("La partita è finita")) break;	
				else if (serverOutput.equals("Digita il numero della casella che vuoi occupare") || serverOutput.equals("La casella è già occupata! Scegline una vuota.") || serverOutput.equals("Il numero inserito non corrisponde ad una casella. Seleziona una casella libera")){
					int scelta = input.nextInt();
					out.println(scelta);
					System.out.println("Mossa inviata!");
					input.nextLine();
				}
				
			}
			input.close();
			in.close();
			out.close();
			System.out.println("Il client si sta chiudendo...");
			socket.close();
		} catch (UnknownHostException e) {
			System.out.println("Local host non trovato...");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Errore di I/O");
			e.printStackTrace();
		}

	}

}
