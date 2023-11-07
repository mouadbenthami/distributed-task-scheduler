package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.Scanner;

public class task2 implements Serializable,Task
{
    int id;
    int[][] A;
    int[][] B;
    int size;
    int[][] result;
    boolean Add;
        
    public task2(int id,int[][] A,int[][] B,boolean Add,int size)
    {
        this.id=id;
        this.A=A;
        this.B=B;
        this.Add=Add;
        this.size=size;
        this.result=new int[size][size];
    }
     
    public static int[][] saisir(int size,Scanner st)
    {
       int[][] R = {{0,0},{0,0}};
       for(int i=0;i<size;i++ )
       {
          for(int j=0;j<size;j++)
          {
            System.out.println("["+i+"]"+"["+j+"] : "  );  
            R[i][j]=Integer.parseInt(st.nextLine());
          }
       }
   
        return R;
    }
     public static String afficher(int size,int[][] M)
      {
          String result="";
      for(int i=0;i<size;i++ )
       {
        result+="(";   
          for(int j=0;j<size;j++)
          {
            result+=" "+M[i][j]+" ";  
          }
        result+=")\n"; 
       }
      return result;
      }
    @Override
 public void execut() 
 {
      
    if(this.Add)
    {  
      for(int i=0;i<size;i++ )
       {
          for(int j=0;j<size;j++)
          {
            this.result[i][j]=this.A[i][j]+this.B[i][j];
          }
       }
    }
    else
    {
       for(int i=0;i<size;i++ )
       {
          for(int j=0;j<size;j++)
          {
            this.result[i][j]=this.A[i][j]-this.B[i][j];
          }
       }  
    }   
 }
 
 
 public static task2 resive(int i,DataInputStream din)
 {
    int[][] A1 =null;
    int[][] B1=null;
    int size1=0;
    boolean add1 = false;
        try {
            size1 =Integer.parseInt(din.readUTF());
            A1=new int[size1][size1];
            B1=new int[size1][size1];
            add1=din.readBoolean();
           for(int k=0;k<size1;k++ )
            {
             for(int j=0;j<size1;j++)
               {
                   A1[k][j]=Integer.parseInt(din.readUTF());
               }
            }  
           for(int k=0;k<size1;k++ )
            {
                for(int j=0;j<size1;j++)
                    {
                      B1[k][j]=Integer.parseInt(din.readUTF());
                    }
            }
          
        } catch (IOException ex) {
            ex.getMessage();
        }
  
     
   return new task2(i,A1,B1,add1,size1);  
 }
 
 public void send()
 {
     try {
            Socket socket= MianServer.clientSocketMap.get(this.id);
            DataOutputStream dout=new DataOutputStream(socket.getOutputStream());
            String result=task2.afficher(this.size,this.result);
            dout.writeUTF(result);
        } catch (IOException ex) { ex.getMessage(); }
 }
    
}

