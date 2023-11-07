
package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            int width = 3;//kernel.getWidth();
            int height = 3;//kernel.getHeight();
            Kernel k = new Kernel(3,3,kernel);
            ConvolveOp convolution = new ConvolveOp(k,ConvolveOp.EDGE_NO_OP,null);
            BufferedImage r=convolution.filter(img, null);
            String extension = "";
            int n = filename.lastIndexOf('.');
            if (n > 0) { extension = filename.substring(n+1);}
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(r, extension, baos);
            result = baos.toByteArray();
            
        } catch (IOException ex) {
            Logger.getLogger(task4.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static task4 recive(int i,DataInputStream din) {return null;}
    public void send() throws IOException{}
    public void reciveImgPart(){}
}