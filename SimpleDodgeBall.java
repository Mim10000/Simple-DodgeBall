import java.awt.*;
import java.applet.*;
import java.util.Random;
import java.awt.event.*;

public class SimpleDodgeBall extends Applet implements KeyListener,Runnable 
{
        Thread runner;
        private Image Buffer;
        private Graphics gBuffer;
        int y=0;
        Random n=new Random();
        int x=n.nextInt(450);
        public int x_pos = 400;
        int a=0; 
        
        boolean hit=false;
        

        public void keyTyped(KeyEvent evt) {
            char key = evt.getKeyChar();
            if (key == 'a')
            x_pos -= 7;
            else if (key == 'd')
            x_pos += 7;
            repaint();
}

    public void keyPressed(KeyEvent evt) {
        char key = evt.getKeyChar();
        if (key == 'a')
        x_pos -= 7;
        else if (key == 'd')
        x_pos += 7;
        repaint();
    }

    public void keyReleased(KeyEvent evt) {
        char key = evt.getKeyChar();
        if (key == 'a')
        x_pos -= 7;
        else if (key == 'd')
        x_pos += 7;
        repaint();
    }   
        
        
        //Init is called first, do any initialisation here
        public void init()
        {
                setVisible(true);
                addKeyListener(this);
                //create graphics buffer, the size of the applet
                Buffer=createImage(size().width,size().height);
                gBuffer=Buffer.getGraphics();
        }

        public void start()
        {
                if (runner == null)
                {
                    runner = new Thread (this);
                    runner.start();
                }
        }
        public void stop()
        {                
               if (runner != null)
               {
                    runner.stop();
                    runner = null;
               }
        }
        public void run()
        {
                while(hit==false)
                {
                        //Thread sleeps for 15 milliseconds here
                        try {runner.sleep(15);}
                        catch (Exception e) { }

                        //paint background blue
                        gBuffer.setColor(Color.blue);
                        gBuffer.fillRect(0,0,500,500);

                        //move red circle down until it
                        //leaves applet then let it reappear at the top in another random location
                        y+=7;
                        if(y>500)
                        {
                                y=0;
                            }
                        if (y<2)
                        {
                        x=n.nextInt(500);
                        a++;
                        }
                        if ((x<=x_pos+35)&&(x>=x_pos-35)&&(y<=400+35)&&(y>=400-35))  //Determines when hit
                        {
                        hit=true;
                    }
                        //the red circle
                        gBuffer.setColor(Color.red);
                        gBuffer.fillOval(x,y,50,50);
                        
                        //Player
                        gBuffer.setColor(Color.black);
                        gBuffer.fillRect(x_pos,400, 50, 50);
                       
                        gBuffer.drawString("Click the screen and use the A and D buttons to move",10,30);
                        gBuffer.drawString("SCORE: " + a*100 ,10,50);
                        gBuffer.drawString("X axis: "+ x_pos ,10,70);
                        
                        if (hit)
                        {
                            gBuffer.drawString("You were hit",10,90);
                        }       
                        if (x_pos<0) //Left Player Buffer
                        {
                            x_pos=10;
                        }
                         if (x_pos>450) //Right Player Buffer
                        {
                            x_pos=450;
                        }
                        
                        repaint();     
                }
        }

        //is needed to avoid erasing the background by Java
        public void update(Graphics g)
        {             
                paint(g);
        }

        public void paint(Graphics g)
        {
                g.drawImage (Buffer,0,0, this);
        }
}