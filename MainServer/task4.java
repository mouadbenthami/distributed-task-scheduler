/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
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


/**
 *
 * @author hp
 */
public class task4 implements Task,Serializable
{
    int id;
    int r;
    String filename;
    byte[] image;
    byte[] result;
    float[] kernel;
    
    public task4(int id,int r, byte[] image,float[] kernel,String filename)
    {
        this.id=id;
        this.r=r;
        this.image=image;
        this.kernel=kernel;
        this.filename=filename;
    }
    
    @Override
    public void execut() 
    {
        InputStream in = new ByteArrayInputStream(image);
        try {
            BufferedImage img = ImageIO.read(in);
            int width = 3;//img.getWidth();
            int height = 3;//img.getHeight();
            Kernel k = new Kernel(3,3,kernel);
            ConvolveOp convolution = new ConvolveOp(k,ConvolveOp.EDGE_NO_OP,null);
            BufferedImage r=convolution.filter(img, null);
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(r, "png", baos);
            result = baos.toByteArray();
            
        } catch (IOException ex) {
             ex.getMessage();
        }
    }
    public static task4 recive(int i,DataInputStream din) {
        try {
            String filename=din.readUTF();
            float[] kernel = new float[9];;
            File f=new File(filename);
           for(int k=0;k<9;k++ )
            {
              kernel[k]=Integer.parseInt(din.readUTF()); 
            }
            FileOutputStream fout=new FileOutputStream(f);
            
            String extension = "";
            int n = filename.lastIndexOf('.');
            if (n > 0) { extension = filename.substring(n+1);}
            
            int ch;
            String temp;
            do{
                temp=din.readUTF();
                ch=Integer.parseInt(temp);
                if(ch!=-1){
                    fout.write(ch);}
            }while(ch!=-1);
            fout.close();
            //float[] kernel={0,-1,0,-1,5,-1,0,-1,0};
            
            int rows;
            int columns;
            
            if (MianServer.slaves.length%2==0)
            {
                rows =MianServer.slaves.length/2;
                columns =MianServer.slaves.length/2;
            }
            else
            {
                rows=1;
                columns=MianServer.slaves.length;
            }
            BufferedImage image = ImageIO.read(f);
            BufferedImage imgs[] = new BufferedImage[MianServer.slaves.length];
            // Equally dividing original image into subimages
            int subimage_Width = image.getWidth() / columns;
            int subimage_Height = image.getHeight() / rows;
            
            int current_img = 0;
            
            // iterating over rows and columns for each sub-image
            for (int t = 0; t < rows; t++)
            {
                for (int j = 0; j < columns; j++)
                {
                    // Creating sub image
                    imgs[current_img] = new BufferedImage(subimage_Width, subimage_Height, image.getType());
                    Graphics2D img_creator = imgs[current_img].createGraphics();
                    
                    // coordinates of source image
                    int src_first_x = subimage_Width * j;
                    int src_first_y = subimage_Height * t;
                    
                    // coordinates of sub-image
                    int dst_corner_x = subimage_Width * j + subimage_Width;
                    int dst_corner_y = subimage_Height * t + subimage_Height;
                    
                    img_creator.drawImage(image, 0, 0, subimage_Width, subimage_Height, src_first_x, src_first_y, dst_corner_x, dst_corner_y, null);
                    current_img++;
                }
                for (int k = 0; k< MianServer.slaves.length ; k++)
                {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(imgs[k], extension, baos);
                    baos.flush();
                    byte[] imageInByte = baos.toByteArray();
                    baos.close();            
                    MianServer.taskQueue.put(new task4(i,k,imageInByte,kernel,filename));
                }            
            }
        } catch (IOException ex) {
              ex.getMessage();
        } catch (InterruptedException ex) {
             ex.getMessage();
        }
        return null;
}
    public void send() throws IOException
    {
      int rows, columns;
        
      if (MianServer.slaves.length%2==0)
       {
         rows =MianServer.slaves.length/2;
         columns =MianServer.slaves.length/2;
       }
       else
       {
           rows=1;
           columns=MianServer.slaves.length;
       }
        int chunkWidth, chunkHeight; 
        int type; 

        //creating a bufferd image array from image files 
        BufferedImage[] buffImages = new BufferedImage[MianServer.slaves.length]; 
        for (int i = 0; i < MianServer.slaves.length; i++) {
          buffImages[i] = ImageIO.read(new File("filterd_"+i+"_"+this.id+filename)); 
         } 
          type = buffImages[0].getType(); 
        chunkWidth = buffImages[0].getWidth(); 
        chunkHeight = buffImages[0].getHeight();  

        //Initializing the final image 
         BufferedImage finalImg = new BufferedImage(chunkWidth*columns, chunkHeight*rows, type);                                    
          int num = 0; 
        for (int i = 0; i < rows; i++) { 
        for (int j = 0; j < columns; j++) { 
        finalImg.createGraphics().drawImage(buffImages[num], chunkWidth * j, chunkHeight * i, null); 
        num++; 
        } 
        } 
        String extension = "";
        int n = this.filename.lastIndexOf('.');
       if (n > 0) { extension = this.filename.substring(n+1);}
        File file1 = new File("imgFinale_"+this.id+filename);
        ImageIO.write(finalImg, extension, file1);
        Socket socket = MianServer.clientSocketMap.get(this.id);
        DataOutputStream dout=new DataOutputStream(socket.getOutputStream());

        FileInputStream fin=new FileInputStream(file1);
        int ch;
        do
        {
            ch=fin.read();
            dout.writeUTF(String.valueOf(ch));
        }while(ch!=-1);
        fin.close();        
    }
    
    public void reciveImgPart()
    {
        try {
            InputStream in = new ByteArrayInputStream(this.result);
            BufferedImage image = ImageIO.read(in);
            File file = new File("filterd_"+this.r+"_"+this.id+this.filename);
            String extension = "";
            int n = this.filename.lastIndexOf('.');
            if (n > 0) { extension = this.filename.substring(n+1);}
            ImageIO.write(image, extension, file);
        } catch (IOException ex) {
              ex.getMessage();
        }
    }
}