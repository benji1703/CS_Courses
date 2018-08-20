package net;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class HelloServer {

	public static final String ERR_MESSAGE = "IO Error!";
	public static final String LISTEN_MESSAGE = "Listening on port: ";
	public static final String HELLO_MESSAGE = "hello ";
	public static final String BYE_MESSAGE = "bye"; 

	private ServerSocket serverSocket;

	public ServerSocket getServerSocket() {

		return serverSocket;

	}

	/**
	 * Listen on the first available port in a given list.
	 * 
	 * <p>Note: Should not throw exceptions due to ports being unavailable</p> 
	 *  
	 * @return The port number chosen, or -1 if none of the ports were available.
	 *   
	 */
	public int listen(List<Integer> portList) {

		int returnPort = -1; 

		for (int i = 0; i < portList.size(); i++) {

			// Using try to catch the Exception
			try {
				// Try the Port
				this.serverSocket = new ServerSocket(portList.get(i));
				return serverSocket.getLocalPort();
			}
			catch (IOException e) {
				// Port not good :(
				// Maybe next?	
				continue;
			}

		}
		return returnPort;
	}


	/**
	 * Listen on an available port. 
	 * Any available port may be chosen.
	 * @return The port number chosen.
	 */
	public int listen() throws IOException {

		//https://stackoverflow.com/questions/2675362/how-to-find-an-available-port

		this.serverSocket = new ServerSocket(0);

		return this.serverSocket.getLocalPort();
	}


	/**
	 * 1. Start listening on an open port. Write {@link #LISTEN_MESSAGE} followed by the port number (and a newline) to sysout.
	 * 	  If there's an IOException at this stage, exit the method.
	 * 
	 * 2. Run in an infinite loop; 
	 * in each iteration of the loop, wait for a client to connect,
	 * then read a line of text from the client. If the text is {@link #BYE_MESSAGE}, 
	 * send {@link #BYE_MESSAGE}, close the connection and wait for the next client to connect. 
	 * Otherwise, send {@link #HELLO_MESSAGE} to the client, followed by the string sent by the client (and a newline).
	 * 
	 * If there's an IOException while in the loop, or if the client closes the connection before sending a line of text,
	 * send the text {@link #ERR_MESSAGE} to sysout, but continue to the next iteration of the loop.
	 * 
	 * *: in any case, before exiting the method you must close the server socket. 
	 *  
	 * @param sysout a {@link PrintStream} to which the console messages are sent.
	 * 
	 * 
	 */
	public void run(PrintStream sysout) {

		Socket client = null;
		PrintStream pw = null;
		BufferedReader br = null;
		boolean first = true;
		String line = "";

		// 1. Start listening on an open port.
		try {
			listen();
			sysout.println(LISTEN_MESSAGE + listen());
		}

		// If there's an IOException at this stage, exit the method.
		catch (IOException e) {
			sysout.println(ERR_MESSAGE);
			return;
		}


		// 2. Run in an infinite loop;
		for (;;) { // This is running an infinite loop.
			try {

				// in each iteration of the loop, wait for a client to connect
				if (first) {
					client = this.getServerSocket().accept();
					pw = new PrintStream(client.getOutputStream());
					br = new BufferedReader(new InputStreamReader(client.getInputStream()));
					first = false;
				}

				// then read a line of text from the client
				line = br.readLine();

				// If the text is {@link #BYE_MESSAGE}, send {@link #BYE_MESSAGE}, close the connection
				// and wait for the next client to connect.
				if (line.equals(BYE_MESSAGE)) {
					pw.print(BYE_MESSAGE);
					first = true;
					pw.close();
					br.close();
					client.close();
				}

				//Otherwise, send {@link #HELLO_MESSAGE} to the client, followed by the string sent by the client
				else {
					pw = new PrintStream(client.getOutputStream());
					pw.print(HELLO_MESSAGE + line + '\n');
				}

			}

			//If there's an IOException while in the loop, or if the client closes the connection before sending a line of text,
			//send the text {@link #ERR_MESSAGE} to sysout, but continue to the next iteration of the loop.
			catch (IOException e) {
				sysout.println(ERR_MESSAGE);
				first = true; // This will be a new client
				try {
					pw.close();
					br.close();
					client.close();
				} catch (IOException | NullPointerException e1) {
					sysout.println(ERR_MESSAGE);
				}
			}
		}

	}

	public static void main(String args[]) {

		HelloServer server = new HelloServer();

		server.run(System.err);

	}

}
