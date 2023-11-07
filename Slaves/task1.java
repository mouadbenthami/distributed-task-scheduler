package main;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.Serializable;


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
        this.result=0;
        for(int i=a;i<=b;i++)
        {
            this.result+=i;
        } 
    }
 
 public static task1 recive(int i,DataInputStream din) throws IOException
 {
     String n1 =din.readUTF();
     String n2 =din.readUTF();     
     return  new task1(i,Integer.parseInt(n1),Integer.parseInt(n2));
      
 }
 
 public void send()
 {
      
 }
    
}
