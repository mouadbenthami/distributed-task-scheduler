package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class task1 implements Serializable,Task
{
    int id;
    int a;
    int b;
    int result;
    public task1(int id,int a,int b)
    {
        this.id=id;
        this.a=a;
        this.b=b;

    }

    @Override
 public void execut() 
    {
       this.result=a+b;  
    }
 
 public static task1 recive(int i,DataInputStream din) throws IOException
 {
     String n1 =din.readUTF();
     String n2 =din.readUTF();
              
     return  new task1(i,Integer.parseInt(n1),Integer.parseInt(n2));
      
 }
 
 public void send()
 {
        try {
            Socket socket= MianServer.clientSocketMap.get(this.id);
            DataOutputStream dout=new DataOutputStream(socket.getOutputStream());
            dout.writeUTF(""+this.result);
        } catch (IOException ex) {
            Logger.getLogger(task1.class.getName()).log(Level.SEVERE, null, ex);
        }
 }
    
}
