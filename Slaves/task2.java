package main;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.Serializable;
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
    }
     
    public static int[][] saisir(int size,Scanner st)
    {
       int[][] R = new int[size][size];
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
    int[][] A ={{0,0},{0,0}};
    int[][] B={{0,0},{0,0}};
    int size=0;
    boolean add = false;
        try {
            size =Integer.parseInt(din.readUTF());
            add=din.readBoolean();
           for(int k=0;k<size;k++ )
            {
             for(int j=0;j<size;j++)
               {
                   A[k][j]=Integer.parseInt(din.readUTF());
               }
            }  
           for(int k=0;k<size;k++ )
            {
                for(int j=0;j<size;j++)
                    {
                      B[k][j]=Integer.parseInt(din.readUTF());
                    }
            }
          
        } catch (IOException ex) {
            ex.getMessage();
        }
    
   return new task2(i,A,B,add,size);  
 }
    
 public void send(){}
}

