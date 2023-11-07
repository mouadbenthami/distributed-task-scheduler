
package main;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author hp
 */
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
            Logger.getLogger(task3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static task3 recive(int i,DataInputStream din) 
    {
        return null;
    }
    
    public void send()
    {
      
    }
    
}
