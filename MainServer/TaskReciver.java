
package main;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import static main.MianServer.sockets;

public class TaskReciver implements Runnable
{
    int index;

    public TaskReciver(int index) {this.index=index;}
    
    @Override
    public void run() 
    {
        while(true)
            {
                try {
                    InputStream inputStream = sockets[index].getInputStream();
                    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                    Task R = (Task) objectInputStream.readObject();
                    if(R instanceof task1)
                    {
                        task1 t = (task1) R;
                        t.send();
                    }
                    else if(R instanceof task2)
                    {
                        task2 t = (task2) R;
                        t.send();
                    }
                    else if(R instanceof task3)
                     {
                          task3 t=(task3)R;
                          t.send();
                     }
                    else if(R instanceof task4)
                     {
                         task4 t=(task4)R;
                         t.reciveImgPart();
                         if(MianServer.imagesMap.get(t.id)==MianServer.slaves.length-1)//recived all the images parts from slaves
                         {
                              t.send();

                         }else
                         {
                             MianServer.imagesMap.put(t.id,MianServer.imagesMap.get(t.id)+1);//increment the number of images recived
                         }
                     }
                } catch (IOException | ClassNotFoundException ex) { ex.getMessage();} 
            }
    }  
}
