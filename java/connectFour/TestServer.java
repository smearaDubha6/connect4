package connectFour;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

class TestServer {

	@Test
	void sendNoParams() throws IOException {
			
		String output = "";
		String [] args = new String[0];
		
		try (ByteArrayOutputStream bOutput = new ByteArrayOutputStream()) {
	        System.setOut(new PrintStream(bOutput));
	        ConnectServer.main(args);
	        bOutput.flush();
	        output = bOutput.toString();
		
	        assertTrue(output.contains("You need to pass port to connect to"));    
		}
	}
	
	@Test
	void sendInvalidPort() throws IOException {
		
		String [] args = new String[1];
		args[0] = "bad Port";
		
		String output = "";
		
		try (ByteArrayOutputStream bOutput = new ByteArrayOutputStream()) {
	        System.setOut(new PrintStream(bOutput));
	        ConnectServer.main(args);
	        bOutput.flush();
	        output = bOutput.toString();
		
	        assertTrue(output.contains("You didn't specify a valid port"));
		}
	}
}
