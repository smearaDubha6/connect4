package connectFour;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

class TestClient {

	@Test
	void sendNoParams() throws IOException {
			
		String output = "";
		String [] args = new String[0];
		
		try (ByteArrayOutputStream bOutput = new ByteArrayOutputStream()) {
	        System.setOut(new PrintStream(bOutput));
	        ConnectClient.main(args);
	        bOutput.flush();
	        output = bOutput.toString();
		
	        assertTrue(output.contains("You need to pass the hostname and port to connect to")); 
		}
	}
	
	@Test
	void sendNoPort() throws IOException {
		
		String [] args = new String[1];
		args[0] = "localhost";
		
		String output = "";
		
		try (ByteArrayOutputStream bOutput = new ByteArrayOutputStream()) {
	        System.setOut(new PrintStream(bOutput));
	        ConnectClient.main(args);
	        bOutput.flush();
	        output = bOutput.toString();
		
	        assertTrue(output.contains("You need to pass the hostname and port to connect to"));
		}
	}
	
	@Test
	void sendInvalidPort() throws IOException {
		
		String [] args = new String[2];
		args[0] = "localhost";
		args[1] = "bad Port";
		
		String output = "";
		
		try (ByteArrayOutputStream bOutput = new ByteArrayOutputStream()) {
	        System.setOut(new PrintStream(bOutput));
	        ConnectClient.main(args);
	        bOutput.flush();
	        output = bOutput.toString();
		
	        assertTrue(output.contains("You didn't specify a valid port"));
		}
	}
	
	@Test
	void unknownHost() throws IOException {
		
		String [] args = new String[2];
		args[0] = "bad Host";
		args[1] = "1234";
		
		String output = "";
		
		try (ByteArrayOutputStream bOutput = new ByteArrayOutputStream()) {
	        System.setOut(new PrintStream(bOutput));
	        ConnectClient.main(args);
	        bOutput.flush();
	        output = bOutput.toString();
		
	        assertTrue(output.contains("Server not found"));
		}
	}
	
	@Test
	void testServerDown() throws IOException {
		
		String [] args = new String[2];
		args[0] = "localhost";
		args[1] = "1234";
		
		String output = "";
		
		try (ByteArrayOutputStream bOutput = new ByteArrayOutputStream()) {
	        System.setOut(new PrintStream(bOutput));
	        ConnectClient.main(args);
	        bOutput.flush();
	        output = bOutput.toString();
		
	        assertTrue(output.contains("I/O error"));
		}
	}
}
