
package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ClientAcceptor implements Runnable
{
ExecutorService executorService = Executors.newFixedThreadPool(MianServer.NUM_WORKER_THREADS);//therad for each client
    @Override
    public void run() 
    {
       int i=0;//client id
       ServerSocket serverSocket = null;
          try {
              serverSocket = new ServerSocket(MianServer.PORT);
          } catch (IOException ex) {ex.getMessage();}
          System.out.println("Server listening on port " + MianServer.PORT);
      while (true) {
          try {
               
              Socket clientSocket = serverSocket.accept();
              System.out.println("Client "+i+"connected" + clientSocket.getRemoteSocketAddress());
              executorService.submit(new ClientHandler(clientSocket,i));i++;
          } catch (IOException ex) {ex.getMessage();}
    }
    }
    
}
