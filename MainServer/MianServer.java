
package main;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;



public class MianServer 
{
  static  int[] slaves;//slaves ports
  static  int PORT ;//clinet port
  static  int NUM_WORKER_THREADS;//max number of clinets
  static Socket[] sockets ;//slaves socket
  static Map<Integer, Socket> clientSocketMap = new HashMap<>();//get client socket by client id
  static Map<Integer, Integer> imagesMap = new HashMap<>();//useed in convolution to determin number of img parts recived
  static BlockingQueue < Task > taskQueue =new LinkedBlockingQueue<>(); //task
  
  public static void main(String[] args) throws IOException 
  {
    //gets slaves ip adressses and ports  
    String slvs[]=config.getConfig("slaves.txt");
    sockets=new Socket[slvs.length/2];//array to store the slaves sockets to send tasks to theme
    slaves=new int[slvs.length/2];//array to store slaves prots
    int j=0;int t=0;
    //gets port that clients use and max cliets number
    String clts[]=config.getConfig("clients.txt");//array strore max number of cliets and port
    NUM_WORKER_THREADS=Integer.parseInt(clts[0]);//max client number
    PORT=Integer.parseInt(clts[1]);//clinets port
    //thread for each sleve to recive result and send it back to the client using the hashmap      
    
    while(j<slvs.length)
    { 
     sockets[t] = new Socket(slvs[j],Integer.parseInt(slvs[++j]));
     slaves[t]=Integer.parseInt(slvs[j]); 

     TaskReciver R=new TaskReciver(t);
     new Thread(R).start();
     j++;t++;
    }

    ClientAcceptor c=new ClientAcceptor();
    TaskSender T=new TaskSender();
    new Thread(c).start();
    new Thread(T).start();

  }
}

    
