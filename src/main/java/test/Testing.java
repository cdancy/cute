package test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class Testing implements Runnable{
	OutputStream out;
	InputStream in,err;
	Process process;

	byte [] buffer;
	public Testing() throws Exception{
		//test1();
		
		//test3();
		
		//test5();
		
		test6();
		
		//test7();
		
		//test8();
	}
	  private void execute()
	    {
	      Thread t = new Thread(this);
	      t.start();
	      //this.setVisible(true);
	    }
	  public class InStream implements Runnable{
		  //private OutputStream out;
	
		  public InStream(){
			  
		  }
		  public synchronized void run(){
			  
			  Scanner scan = new Scanner(System.in);
			  
		      //String s = inputTextField.getText();
			  System.out.println("wants input:");
			  while(true){
		
			  String s;
			    try
		        {
		
			    	s = scan.nextLine();
			    	System.out.println("in try loop");
		          out.write(s.getBytes());
		          
		          out.flush();
		        }
		      catch(IOException e)
		        {
		          //JOptionPane.showConfirmDialog(this,"Unable to write to stdout.","Error", JOptionPane.WARNING_MESSAGE);
		    	  System.out.println("error in writing with writeToStdin " + e);
		        }
			  try{
				  in.close();
			  }catch(Exception e){
				  System.out.println(e);
			  }
		      
			  }
			  
		  }
		  public synchronized void wakeup(){
			  try{
				  this.notify();
			  }catch(Exception e){
				  return;
			  }
		  }
	  }
	public void run(){
		try{
			
		process = Runtime.getRuntime ().exec ("/home/cdancy/Desktop/test.rb");
		in = process.getInputStream();
        err = process.getErrorStream();
        out = process.getOutputStream();
 
        Thread t1 = new Thread(new ReadStream(in));
        Thread t2 = new Thread(new ReadStream(err));
        Thread t3 = new Thread(new InStream());
        t1.start();
        t2.start();
        t3.start();
        process.waitFor();
        
		}catch(Exception e){
			
		}
	}
	  private class ReadStream implements Runnable
	  {
	    /**constructor
	      *@param i the input stream
	      *@param t the textArea
	      */
		private InputStream input;
		
	    public ReadStream(InputStream i)
	      {
	        this.input = i;
	       // textArea = t;
	      }
	    public ReadStream(){
	    	
	    }
	      
	    public void run()
	      {
	        try
	          {

	            	buffer = new byte[1024];
	            	int len = 0;
	            	while(len != -1){
	            		len = input.read(buffer,0,buffer.length);
	            		if(len != -1)
	            			System.out.print((char)len);
	            			System.out.println(new String(buffer));
	            	}
	   
	          }
	        catch(IOException e)
	          {
	            System.out.println("IOException");
	          }
	        try{
	        in.close();
	        }catch(Exception e){
	        	System.out.println(e);
	        }
	      }

	  }
	
	public void test7() throws Exception{
		execute();
		
		
	}
	public void test1() throws Exception{
		  String line;
		  try{
	      Process process = Runtime.getRuntime ().exec ("/home/cdancy/Desktop/test.rb");
	      OutputStream stdin = process.getOutputStream ();
	      InputStream stderr = process.getErrorStream ();
	      InputStream stdout = process.getInputStream ();
	   
	      BufferedReader reader = new BufferedReader (new InputStreamReader (stdout));
	      PrintWriter writer = null;
	      //If I dont close the writer it blocks for some reason.
	      System.out.println("im here");
	      Scanner scan = new Scanner(System.in);
	      while ((line = reader.readLine ()) != null) {
	    	  System.out.println("[Stdout] " + line);
	    	  if(!reader.ready()){
	    		  writer = new PrintWriter(new OutputStreamWriter(stdin));
	    		  String temp = scan.nextLine();
		  	      temp += "\n";
		  	      writer.write(temp);
		  	      writer.flush();
	    	  }
	      }
	      writer.close();
	      reader.close();
	      reader = new BufferedReader (new InputStreamReader (stderr));
	      while ((line = reader.readLine ()) != null) {
	        System.out.println ("[Stderr] " + line);
	      }
	      reader.close();
		  }catch(Exception e){
			  System.out.println("Exception is " + e);
		  }
	}
	public void test5(){
		try{
		String line;
	    ProcessBuilder build = new ProcessBuilder("/home/cdancy/Desktop/start.rb");
	 
	    Process process = build.start();
	    System.out.println("Number of processors is: " + Runtime.getRuntime().availableProcessors());
	    OutputStream stdin = process.getOutputStream ();
	    InputStream  stdout = process.getInputStream ();
	    
	    BufferedInputStream is = new BufferedInputStream(stdout);
	    BufferedOutputStream os = new BufferedOutputStream(stdin);
	    
	    BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
	    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));
	    int i = 0;
	    char [] c = {'\n'};
	    while(!reader.ready()){
	    	System.out.println("start-num: " + i);
	    	writer.flush();
	    	writer.write('\n');
	    	writer.flush();
	    	
	    	if(reader.read() != -1){
	    		System.out.println("-1");
	    	}
	    	System.out.println("value of c: " + new String(c));
	    	i++;
	    }
	    
	   
	    
	    String s = new String("\n");
	    byte [] b = s.getBytes();
	    os.flush();
	    os.write(b);
	   
	    int temp = is.available();
	    System.out.println("bytes available: " + temp);
	    
	  
	    
	    writer.write("45");
	    writer.newLine();
	    
	    temp = is.available();
	    System.out.println(temp);
	    
	    writer.flush();
	    writer.close();
	    
	    while((line = reader.readLine()) != null){
	    	System.out.println(line);
	    }

	  
	    int result = process.waitFor();
	    System.out.println("End Result Code: " + result);
	    if(result == 0){
	    	System.out.println("Completed Successfully..");
	    }else{
	    	System.out.println("Did not complete successfully...");
	    }
		}catch(IOException e){
			System.out.println("ioe: " + e);
		}catch(Exception e){
			System.out.println("exc: " + e);
		}
	}
	public void test6() throws IOException,InterruptedException{
		String name = System.getProperty("os.name", "");
		String version = System.getProperty("os.version", "");
		System.out.println(name);
		System.out.println(version);
		//Process p = Runtime.getRuntime().exec("gnome-terminal");
		String [] s = {"xterm","-T","Hello world","-hold","-e","/home/cdancy/Desktop/start.rb"};
		Process build = new ProcessBuilder(s).start();
		build.waitFor();
		System.out.println("all done");
		
	}
}
