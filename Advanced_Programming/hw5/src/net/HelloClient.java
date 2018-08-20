package net;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class HelloClient {

	Socket clientSocket;

	public static final int COUNT = 10;

	/**
	 * Connect to a remote host using TCP/IP and set {@link #clientSocket} to be the
	 * resulting socket object.
	 * 
	 * @param host remote host to connect to.
	 * @param port remote port to connect to.
	 * @throws IOException
	 */
	public void connect(String host, int port) throws IOException {
		this.clientSocket = new Socket (host, port);
	}

	/**
	 * Connects to the remove server (host:port), then performs the following actions 
	 * {@link #COUNT} times in a row: 
	 * 1. Write the string in myname (followed by newline) to the server 
	 * 2. Read one line of response from the server, write it to sysout (without the trailing newline) 
	 * 
	 * Then do the following (only once): 
	 * 1. Send {@link HelloServer#BYE_MESSAGE} to the server (followed by newline). 
	 * 2. Read one line of response from the server, write it to sysout (without
	 * the trailing newline
	 * 3. Close the connection 
	 * 
	 * If there are any IO Errors during the execution, output {@link HelloServer#ERR_MESSAGE}
	 * (followed by newline) to sysout. If the error is inside the loop,
	 * continue to the next iteration of the loop. Otherwise exit the method.
	 * 
	 * @param sysout
	 * @param host
	 * @param port
	 * @param myname
	 */
	public void run(PrintStream sysout, String host, int port, String myname) {

		PrintStream pw;
		BufferedReader br;
		String line;

		// Connects to the remove server (host:port)
		// Creating the stream

		try {	
			connect(host, port);
			pw = new PrintStream(clientSocket.getOutputStream());

		} catch (IOException e) {
			sysout.print(HelloServer.ERR_MESSAGE + "\n");
			return;
		} 


		// Then performs the following actions {@link #COUNT} times in a row
		for (int i = 0; i < COUNT; i++) {
			try {
				// 1. Write the string in myname (followed by newline) to the server
				pw.println(myname);
				//@tutor: (-2) you need to flush streams and pay attention when to use print and when println
				// 2. Read one line of response from the server, write it to sysout (without the trailing newline) 
				br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				line = br.readLine();
				sysout.println(line);
			}
			//If the error is inside the loop, continue to the next iteration of the loop. 
			catch (IOException e) {
				continue;
			}
		}


		try {
			// Then do the following (only once)
			// 1. Send {@link HelloServer#BYE_MESSAGE} to the server (followed by newline). 
			pw.println(HelloServer.BYE_MESSAGE);

			// 2. Read one line of response from the server, write it to sysout (without the trailing newline)
			br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			sysout.println(br.readLine());

			// 3. Close the connection
			pw.close();
			br.close();
			clientSocket.close();
		}

		// If there are any IO Errors during the execution, output {@link HelloServer#ERR_MESSAGE}
		// (followed by newline) to sysout.
		catch (IOException e) {
			sysout.print(HelloServer.ERR_MESSAGE + "\n");
			return;
		} 
	}
}

