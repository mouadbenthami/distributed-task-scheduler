
package main;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;


public class TaskSender implements Runnable
{
    @Override
    public void run() 
    {
        int i=0;
        while(true)
        {
         OutputStream outputStream;
            try {
                  Task task =  MianServer.taskQueue.take();
                  outputStream = MianServer.sockets[i%MianServer.slaves.length].getOutputStream();
                  ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                  objectOutputStream.writeObject(task);
                  i++;  
               } catch (IOException | InterruptedException ex) { ex.getMessage();}
        }
    } 
}
