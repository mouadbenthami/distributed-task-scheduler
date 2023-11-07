
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


public class Clients 
{

public static void main(String[] args) throws IOException 
  {
    String[] config= TaskResult.getConfig();
    
    Socket socket = new Socket(config[0],Integer.parseInt(config[1]));
    DataInputStream din=new DataInputStream(socket.getInputStream());
    DataOutputStream dout=new DataOutputStream(socket.getOutputStream());     
    Scanner input = new Scanner(System.in);
    
    System.out.println("Connected to server: " + socket.getRemoteSocketAddress());
    System.out.println("Server is"+din.readUTF()+"to recive tasks");
    
    TaskResult T=new TaskResult(input, din, dout);
    
    String msg ="";
      while(true)
      {
           System.out.print("\nchose a task "
            + ":\n task1 : calculate the sum from a to b"
            + "\n task2 : calculate the sum or difference of two matrices"
            + "\n task3 : apply a black and white filter to an image"
            + "\n task4 : apply a convolution product to an image using a kernel"
            + "\n To exit type end\n");
           msg = input.nextLine();
        switch (msg) {
            case "task1":
                T.task1();
                break;
            case "task2":
                T.task2();
                break;
            case "task3":
                T.task3();
                break;
            case "task4":
                T.task4();
                break;
            case "end":
                dout.writeUTF("end");
                socket.close();
                System.exit(0);
            default:
                System.out.println("please chose a valid task or type end to quit");
                break;
        }           
      } 
  }
}

    

