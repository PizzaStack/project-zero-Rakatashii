package utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class Bash {
	private String output = null;
	private boolean isWindows = System.getProperty("os.name")
			  .toLowerCase().startsWith("windows");
	private static class StreamGobbler implements Runnable {
	    private InputStream inputStream;
	    private Consumer<String> consumer;
	 
	    public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
	        this.inputStream = inputStream;
	        this.consumer = consumer;
	    }
	 
	    @Override
	    public void run() {
	        new BufferedReader(new InputStreamReader(inputStream)).lines()
	          .forEach(consumer);
	    }
	}
	
	public String getTerminalOutput(String command) throws IOException {
		
		String homeDirectory = System.getProperty("user.home") + "\\java\\project-zero-Rakatashii";
		Process process;
		ArrayList<String> commands = new ArrayList<String>();
		
		/*
		if (isWindows) {
		    process = Runtime.getRuntime()
		    		(String.format("cmd.exe /c dir %s", homeDirectory));
		 
		} else {
		    process = Runtime.getRuntime()
		      .exec(String.format("sh -c %s", homeDirectory));
		}
		*/
		
		commands.add("bash");
		commands.add("-c");
		commands.add(command);
		
		ProcessBuilder builder = new ProcessBuilder(commands);
		builder.redirectErrorStream(true);
		try {
			process = builder.start();
			InputStream is = process.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
				   //System.out.println(line);
					line += "\n";
					output += line; 
				}
				StreamGobbler streamGobbler = 
						new StreamGobbler(process.getInputStream(), System.out::println);
				Executors.newSingleThreadExecutor().submit(streamGobbler);
				int exitCode;
				try {
					exitCode = process.waitFor();
					if (exitCode == 0) return output;
					else output = "bad exit code";
				} catch (InterruptedException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				return output;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return output;
	}
}
