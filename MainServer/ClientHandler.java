package main;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    public  int i;
    DataInputStream din;
    DataOutputStream dout;
    
    public ClientHandler(Socket clientSocket,int i) throws IOException 
    {
      this.clientSocket = clientSocket;
      din=new DataInputStream(clientSocket.getInputStream());
      dout=new DataOutputStream(clientSocket.getOutputStream());
      this.i=i;
    }

    @Override
 public void run() {
        try { 
            dout.writeUTF("ready");
            while(true)
            {
                String task =din.readUTF();
                System.out.println("receving "+task+" from client "+i);
                if("task1".equals(task))
                {
                    MianServer.clientSocketMap.put(i, clientSocket);
                    MianServer.taskQueue.put(task1.recive(i, din));
                }
                else if("task2".equals(task))
                {
                   MianServer.clientSocketMap.put(i, clientSocket);
                   MianServer.taskQueue.put(task2.resive(i, din));
                }
                else if("task3".equals(task))
                {
                    MianServer.clientSocketMap.put(i, clientSocket);
                    MianServer.taskQueue.put(task3.recive(i, din));  
                }
                else if("task4".equals(task))
                {
                    task4.recive(i, din);
                    MianServer.clientSocketMap.put(i, clientSocket);
                    MianServer.imagesMap.put(this.i,0);       
                }
                else if("end".equals(task))
                {
                    System.out.println("clients"+i+"disconnected successfully");
                }
            }
        } 
        catch (IOException | InterruptedException ex) {ex.getMessage();}
    }

  }