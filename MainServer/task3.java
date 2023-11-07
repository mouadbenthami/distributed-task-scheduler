
package main;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.Socket;
import javax.imageio.ImageIO;


public class task3 implements Task,Serializable
{
    int id;
    String filename;
    byte[] image;
    byte[] result;
   
    
    public task3(int id, byte[] image,String filename)
    {
        this.id=id;
        this.image=image;
        this.filename=filename;
    }
    
    @Override
    public void execut() 
    {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(image);
            BufferedImage image = ImageIO.read(bais);
            bais.close();
            
            int width = image.getWidth();
            int height = image.getHeight();
            int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);
            
            for (int i = 0; i < pixels.length; i++) {
                int rgb = pixels[i];
                int red = (rgb >> 16) & 0xff;
                int green = (rgb >> 8) & 0xff;
                int blue = rgb & 0xff;
                int avg = (red + green + blue) / 3;
                pixels[i] = (avg << 16) | (avg << 8) | avg;
            }
            
            image.setRGB(0, 0, width, height, pixels, 0, width);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            result = baos.toByteArray();
            
           
            
            
        } catch (IOException ex) {
            ex.getMessage();
        }
    }
    
    public static task3 recive(int i,DataInputStream din) 
    {
        task3 t = null;
        try {
            String filename=din.readUTF();
            File f=new File(filename);
            String extension = "";

           int n = filename.lastIndexOf('.');
           if (n > 0) { extension = filename.substring(n+1);}
            
           FileOutputStream fout=new FileOutputStream(f);
            int ch;
            String temp;
            do{
                temp=din.readUTF();
                ch=Integer.parseInt(temp);
                if(ch!=-1){
                    fout.write(ch);}
            }while(ch!=-1);
            fout.close();

            BufferedImage image = ImageIO.read(f);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, extension, baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            f.delete();
            t=new task3(i,imageInByte,filename);
            
        } catch (IOException ex) {
                 ex.getMessage();       
        }
        return t;  
    }
    
    public void send()
    {
        try {
            String extension = "";
            int n = filename.lastIndexOf('.');
            if (n > 0) { extension = filename.substring(n+1);}
            
            InputStream in = new ByteArrayInputStream(this.result);
            BufferedImage image = ImageIO.read(in);
            File file = new File("task3_"+this.id+filename);
            ImageIO.write(image, extension, file);
            Socket socket = MianServer.clientSocketMap.get(this.id);
            DataOutputStream dout=new DataOutputStream(socket.getOutputStream());
            FileInputStream fin=new FileInputStream(file);
            int ch;
            do
            {
                ch=fin.read();
                dout.writeUTF(String.valueOf(ch));
            }while(ch!=-1);
            fin.close();
            file.delete();
        } catch (IOException ex) {
              ex.getMessage();
        }
    }
    
}
