

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;



public class TaskResult 
{
    Scanner input;
    DataInputStream din;
    DataOutputStream dout;
    
    public TaskResult(Scanner input,DataInputStream din,DataOutputStream dout)
    {
        this.input=input;
        this.din=din;
        this.dout=dout;
    }
    
   public static String[] getConfig() throws IOException
   {
    
     String[] a=new String[2];
     a[0]="";
     a[1]="";

     File f=new File("config.txt");   
     if(!f.exists()){System.out.println("Error config file does not exists"); throw new FileNotFoundException();}
      
      FileReader fr=new FileReader(f);  
      BufferedReader br=new BufferedReader(fr);  
      int c=0;        
      int i=0;
   
      while((c = br.read())!=-1 )         
       {
            char character =  (char) c; 
            if(character==' '){i=1;}
            if(character!=' ')
            a[i]+=character;  
       }
      
    return a;
    }
    
   public  void task1()
   {
       try {
           dout.writeUTF("task1");
           System.out.print("enter a ");
           String a = input.nextLine();
           dout.writeUTF(a);
           System.out.print("enter b ");
           String b = input.nextLine();
           dout.writeUTF(b);
           System.out.println("\nResult : "+din.readUTF());
       } catch (IOException ex) {ex.getMessage();}
   }
   
   public  void task2()
   {
       try {
           dout.writeUTF("task2");
           System.out.print("enter size ");
           int size = Integer.parseInt(input.nextLine());
           dout.writeUTF(""+size);
           System.out.println("choose the operation : + or - ");
           String op=input.nextLine();
           
           boolean operation = false;
           
           if (op.equals("+")) {operation = true;} 
           else if (op.equals("-")) {operation = false;} 
           else {System.out.println("Invalid operator. Please try again.");}
           dout.writeBoolean(operation);
           int r;
           System.out.println("enter first matrix ");
           for(int i=0;i<size;i++ )
           {
               for(int j=0;j<size;j++)
               {
                   while (true) 
                   {
                     try {
                            System.out.println("["+i+"]"+"["+j+"] : ");
                            r = Integer.parseInt(input.nextLine());
                            break; // break out of the loop if the input is a valid integer
                        } catch (NumberFormatException e) {System.out.println("Invalid input. Please enter an integer value.");}
                    }
                dout.writeUTF(""+r);
               }
           }
           System.out.println("enter secend matrix ");
           for(int i=0;i<size;i++ )
           {
               for(int j=0;j<size;j++)
               {
                   while (true) 
                   {
                     try {
                            System.out.println("["+i+"]"+"["+j+"] : ");
                            r = Integer.parseInt(input.nextLine());
                            break; // break out of the loop if the input is a valid integer
                        } catch (NumberFormatException e) {System.out.println("Invalid input. Please enter an integer value.");}
                    }
                   dout.writeUTF(""+r);
               }
           }
           System.out.println("Result : \n\n"+din.readUTF());
       } catch (IOException ex) {ex.getMessage();}
   }
   
   public  void task3()
   {
       int nbrimg=0;
       try {
           dout.writeUTF("task3");
           String filename;
           File f;
           do{
                System.out.print("enter imge path ");
                filename= input.nextLine();
                f=new File(filename);
                if (!f.exists()) {System.out.println("File does not exist. Please enter a valid file path.");}
           }while(!f.exists());
           
           dout.writeUTF(filename);
           
           
           System.out.println("Sending Image ...");
           FileInputStream fin=new FileInputStream(f);
           int ch;
           do
           {
               ch=fin.read();
               dout.writeUTF(String.valueOf(ch));
           }while(ch!=-1);
           fin.close();
           System.out.println("Receiving image ...");
           File result=new File("filtred_Black_and_White_"+nbrimg+filename);
           nbrimg++;
           FileOutputStream fout=new FileOutputStream(result);
           int ch1;
           String temp;
           do{
               temp=din.readUTF();
               ch1=Integer.parseInt(temp);
               if(ch1!=-1){
                   fout.write(ch1);}
           }while(ch1!=-1);
           fout.close();
       } catch (IOException ex) {ex.getMessage();}
   }
   
   public  void task4()
   {
      int nbrimg=0; 
       try {
           dout.writeUTF("task4");
           System.out.print("enter image path ");
           String filename;
           File f;
           do{
                System.out.print("enter imge path ");
                filename= input.nextLine();
                f=new File(filename);
                if (!f.exists()) {System.out.println("File does not exist. Please enter a valid file path.");}
           }while(!f.exists());
           
           dout.writeUTF(filename);
           System.out.print("enter Kernel : \n");
           int r;
           for(int i=0;i<3;i++ )
           {
               for(int j=0;j<3;j++)
               {
                   while (true) 
                   {
                     try {
                            System.out.println("["+i+"]"+"["+j+"] : ");
                            r = Integer.parseInt(input.nextLine());
                            break; // break out of the loop if the input is a valid integer
                        } catch (NumberFormatException e) {System.out.println("Invalid input. Please enter an integer value.");}
                    }                   
                   dout.writeUTF(""+r);
               }
           }
           System.out.println("Sending Image ...");
           FileInputStream fin=new FileInputStream(f);
           int ch;
           do
           {
               ch=fin.read();
               dout.writeUTF(String.valueOf(ch));
           }while(ch!=-1);
           fin.close();
           System.out.println("Receiving image ...");
           File result=new File("filtred_Conv_"+nbrimg+filename);
           nbrimg++;
           FileOutputStream fout=new FileOutputStream(result);
           int ch1;
           String temp;
           do{
               temp=din.readUTF();
               ch1=Integer.parseInt(temp);
               if(ch1!=-1){
                   fout.write(ch1);}
           }while(ch1!=-1);
           fout.close();
       } catch (IOException ex) {ex.getMessage();}
  
   }
}
