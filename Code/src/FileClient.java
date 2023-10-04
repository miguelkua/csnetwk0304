import java.net.*;
import java.io.*;

public class FileClient
{
	public static void main(String[] args)
	{
		String sServerAddress = args[0];
		int nPort = Integer.parseInt(args[1]);
		
		try
		{
			Socket clientEndpoint = new Socket(sServerAddress, nPort);
			
			System.out.println("Client: Connected to server at" + clientEndpoint.getRemoteSocketAddress());
			
			DataOutputStream dosWriter = new DataOutputStream(clientEndpoint.getOutputStream());
			dosWriter.writeUTF("Client: Hello from client" + clientEndpoint.getLocalSocketAddress());
			
			DataInputStream disReader = new DataInputStream(clientEndpoint.getInputStream());

			//Read File Content
			int fileContentLength = disReader.readInt();
			byte[] fileContentBytes = new byte[fileContentLength];
			disReader.readFully(fileContentBytes,0,fileContentBytes.length);
			String contents = new String(fileContentBytes);
			//Write Content
			try{
				String filename = "Received.txt";
				FileWriter myWriter = new FileWriter(filename);
				myWriter.write(contents);
				myWriter.close();
				System.out.println("Downloaded file \""+filename+"\"");
			} catch(Exception e){
				System.out.println("Download Failed");
			}

			clientEndpoint.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			System.out.println("Client: Connection is terminated.");
		}
	}
}