 import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class MusicGui
{
  static JFrame frame=new JFrame("Music video ;) ");
  static MyDrawPanel m;
  public static void main(String args[])
   {
     MusicGui music=new MusicGui();
     music.go();
   }
  public void go()
   {
     gui();
     try{
	Sequencer sequencer=MidiSystem.getSequencer();
        sequencer.open();
	sequencer.addControllerEventListener(m,new int[] {127});
	Sequence seq=new Sequence(Sequence.PPQ,4);
        Track track=seq.createTrack();
	int r=0;
	for(int i=0;i<100;i+=4)
	  {
	     r=(int)((Math.random()*50)+1);
	     track.add(makeEvent(144,1,r,100,i));
             track.add(makeEvent(176,1,127,0,i));
             track.add(makeEvent(128,1,r,100,i+2));
          }
         sequencer.setSequence(seq);
         sequencer.start();
         sequencer.setTempoInBPM(120);
	}
      catch(Exception e){e.printStackTrace();}
   }
   public void gui()
   {
    m=new MyDrawPanel();
    frame.setContentPane(m);
    frame.setBounds(30,30,600,630);
    frame.setVisible(true);
   }
   public MidiEvent makeEvent(int comd,int chan,int one,int two,int tick)
   {
     MidiEvent event=null;
     try{
	ShortMessage a=new ShortMessage();
	a.setMessage(comd,chan,one,two);
	event=new MidiEvent(a,tick);
        }
     catch(Exception e){}
     return event;
   }
   class MyDrawPanel extends JPanel implements ControllerEventListener
    {
      boolean msg=false;
      public void controlChange(ShortMessage event)
       {
         msg=true;
         repaint();
       }
       public void paintComponent(Graphics g)
	{
	  if (msg)
           {
	     Graphics2D g2=(Graphics2D)g;
	     int r=(int)(Math.random()*250);
	     int gr=(int)(Math.random()*250);
	     int b=(int)(Math.random()*250);
	     g.setColor(new Color(r,gr,b));
             int ht=(int)((Math.random()*500)+10);
             int wd=(int)((Math.random()*500)+10);
	     int x=(int)((Math.random()*100)+10);
	     int y=(int)((Math.random()*100)+10);
             g.fillRect(x,y,ht,wd);
             msg=false;
	   }
	}
    }
}