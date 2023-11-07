package main;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class Slaves {
   
   private static int port;
   private static BlockingQueue < Task > taskQueue =new LinkedBlockingQueue<>(); ;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException 
    {
         port =  Integer.parseInt(args[0]);
         ServerSocket serverSocket = new ServerSocket(port);
         Socket clientSocket = serverSocket.accept();
         System.out.println("Accepted connection from " + clientSocket.getRemoteSocketAddress());
          
        Thread addThread = new Thread(() -> 
        {
            
            while (true) 
            {
                InputStream inputStream ;
                try {
                    inputStream = clientSocket .getInputStream();
                    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                    Task t = (Task) objectInputStream.readObject();
                    taskQueue.put(t);
                     
                } catch (IOException | ClassNotFoundException | InterruptedException ex) {
                      ex.getMessage();
                } 
                
            }
        });
        addThread.start();

        Thread takeThread = new Thread(() -> {
            while (true) {
                try {
                    Task task = taskQueue.take();
                    if(task instanceof task1)
                    {
                        task=(task1)task;
                    }
                    else if(task instanceof task2)
                    {
                        task=(task2)task;
                        
                    }
                    else if(task instanceof task3)
                    {
                        task=(task3)task;
                    }
                    else if(task instanceof task4)
                    {
                         task=(task4)task;
                         
                    }
                    task.execut();
                    OutputStream outputStream = clientSocket.getOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                    objectOutputStream.writeObject(task);                     
    
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException ex) {
                     ex.getMessage();
                }
            }
        });
        takeThread.start();

    } 
}
