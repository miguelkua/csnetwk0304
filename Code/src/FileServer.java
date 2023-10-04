import java.net.*;
import java.io.*;

public class FileServer
{
	public static void main(String[] args)
	{
		int nPort = Integer.parseInt(args[0]);
		System.out.println("Server: Listening on port " + args[0] + "...");
		ServerSocket serverSocket;
		Socket serverEndpoint;

		try 
		{
			serverSocket = new ServerSocket(nPort);
			serverEndpoint = serverSocket.accept();

			System.out.println("Server: New client connected: " + serverEndpoint.getRemoteSocketAddress());
			
			DataInputStream disReader = new DataInputStream(serverEndpoint.getInputStream());
			System.out.println(disReader.readUTF());

			//File
			String fileName = "Download.txt"; //File Path
			File file = new File(fileName); //Get the File from the File Path
			byte[] fileContentBytes = new byte[(int) file.length()];
			FileInputStream fileInputStream = new FileInputStream(fileName);
			fileInputStream.read(fileContentBytes); //Convert File Contents to Bytes

			//Sending File
			DataOutputStream dosWriter = new DataOutputStream(serverEndpoint.getOutputStream());
			System.out.println("Sending file " + fileName + " ["+fileContentBytes.length+"]");

			//Sending File Contents to the Client
			dosWriter.writeInt(fileContentBytes.length);
			dosWriter.write(fileContentBytes); //File Contents

			serverEndpoint.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			System.out.println("Server: Connection is terminated.");
		}
	}
}