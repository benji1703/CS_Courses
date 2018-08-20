package net;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ThreadedHelloServer extends HelloServer{

	public static final String ERR_MESSAGE = "IO Error!";
	public static final String LISTEN_MESSAGE = "Listening on port: ";
	public static final String HELLO_MESSAGE = "hello ";
	public static final String BYE_MESSAGE = "bye"; 


	/**
	 * 1. Start listening on an open port. Write {@link #LISTEN_MESSAGE} followed by the port number (and a newline) to sysout.
	 * 	  If there's an IOException at this stage, exit the method.
	 * 
	 * 2. Run in an infinite loop; 
	 * in each iteration of the loop, wait for a client to connect, and execute a Thread to handle the connection.
	 * 
	 * *: in any case, before exiting the method you must close the server socket. 
	 *  
	 * @param sysout a {@link PrintStream} to which the console messages are sent.
	 */

	@Override
	public void run(PrintStream sysout) {


		// 1. Start listening on an open port. Write {@link #LISTEN_MESSAGE} followed by the port number (and a newline) to sysout.
		// If there's an IOException at this stage, exit the method.

		try {
			listen();
			sysout.println(LISTEN_MESSAGE + listen());
		} 

		catch (IOException e) {
			sysout.println(ERR_MESSAGE);
			return;
		} 

		// 2. Run in an infinite loop;
		for (;;) {
			try {
				// in each iteration of the loop, wait for a client to connect, and execute a Thread to handle the connection.
				new ConnectionHandler(getServerSocket().accept(), sysout).start();
			}
			catch (IOException e) {
				sysout.print(ERR_MESSAGE + "\n");
				break;
			} 
		}

		// In any case, before exiting the method you must close the server socket. 

		try {
			getServerSocket().close();
		} catch (IOException e) {
			sysout.print(ERR_MESSAGE + "\n");
		}

	}


	public static void main(String args[]) {

		ThreadedHelloServer server = new ThreadedHelloServer();
		server.run(System.err);

	}


	/**
	 *  The ConnectionHandler wraps a single connection to a client.
	 */
	private static class ConnectionHandler extends Thread{
		private Socket s; 				// The connection to the client
		private PrintStream sysout;		// a PrintStream to which the console messages are sent.

		public ConnectionHandler(Socket s, PrintStream sysout){
			this.s = s;
			this.sysout = sysout;
		}

		/**
		 * Run in a loop; 
		 * in each iteration of the loop read a line of text from the client. 
		 * If the text is {@link #BYE_MESSAGE}, send {@link #BYE_MESSAGE}, close the connection and quit the function. 
		 * Otherwise, send {@link #HELLO_MESSAGE} to the client, followed by the string sent by the client (and a newline).
		 * 
		 * If there are any IO Errors during the execution, output {@link HelloServer#ERR_MESSAGE}
		 * (followed by newline) to sysout. If the error is inside the loop,
		 * continue to the next iteration of the loop. Otherwise exit the method.
		 */
		@Override
		public void run() {

			PrintStream pw = null;
			BufferedReader br = null;
			String line = "";
			//@tutor: you could unite all these try catch clauses (-1)
			try {
				pw = new PrintStream(s.getOutputStream());
				br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			} catch (IOException e) {
				sysout.println(ERR_MESSAGE);
				return;
			}
			
			// Run in a loop; 
			for (;;) {
				// In each iteration of the loop read a line of text from the client.
				try {
					line = br.readLine();
				} catch (IOException e) {
					sysout.println(ERR_MESSAGE);
				}
				// If the text is {@link #BYE_MESSAGE}, send {@link #BYE_MESSAGE}, close the connection and quit the function.
				if (line.equals(BYE_MESSAGE)) {
					pw.print(BYE_MESSAGE);
					try {
						pw.close();
						br.close();
						this.s.close();
					} catch (IOException e) {
						sysout.println(ERR_MESSAGE);
					}
					return; // quit the function
				}
				// Otherwise, send {@link #HELLO_MESSAGE} to the client, followed by the string sent by the client (and a newline).
				else {
					pw.print(HELLO_MESSAGE + line + "\n");
				}
			}
		}
	}
}

