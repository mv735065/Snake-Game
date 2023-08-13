import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel  implements ActionListener {

    int B_height=400;
    int B_width=400;

  int Max_dots=1600;
  int Dot_Size=10;
  int Dots=3;

  int x[]=new int[1600],y[]=new int[1600];

int apple_x,apple_y;

Image body,apple,head;
Timer timer;
int Delay=100;

boolean leftDir=true;
    boolean rightDir=false;
    boolean upDir=false;
    boolean downDir=false;

    boolean ingame=true;
    Board(){
        Tadapter tadapter=new Tadapter();
        addKeyListener(tadapter);
        setFocusable(true);
        setPreferredSize(new Dimension(B_width,B_height));
        setBackground(Color.black);
        intiatGame();
        imageloader();
    }

    public void intiatGame(){
        Dots=3;
        x[0]=150;
        y[0]=150;
        for(int i=1;i<Dots;i++){
            x[i]=x[i-1]+Dot_Size;
            y[i]=y[i-1];
        }
        apple_x=150;
        apple_y=150;
        locateApple();
        timer=new Timer(Delay,this);
        timer.start();


    }

    public void imageloader(){
        ImageIcon bodyicon=new ImageIcon("src/resources/dot.png");
        body=bodyicon.getImage();
        ImageIcon headicon=new ImageIcon("src/resources/head.png");
        head=headicon.getImage();
        ImageIcon  appleicon=new ImageIcon("src/resources/apple.png");
        apple= appleicon.getImage();

    }
  @Override
    public  void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }
    public void   locateApple(){
        apple_x=((int)(Math.random()*39))*Dot_Size;
        apple_y=((int)(Math.random()*39))*Dot_Size;

    }
    public void checkgame(){

        for(int i=1;i<Dots;i++){
            if(i>4 && x[i]==x[0] && y[i]==y[0]){
                ingame=false;
                break;
            }
        }

        if(x[0]<0) {
            ingame=false;

        }
        if(x[0]>=B_height) {
            ingame=false;

        }
        if(y[0]<0) {
            ingame=false;

        }
        if(y[0]>=B_width) {
            ingame=false;

        }
      //  if(!ingame) System.exit(0);

    }

    public void doDrawing(Graphics g) {
        if(ingame){
            g.drawImage(apple,apple_x,apple_y,this);

            for(int i=0;i<Dots;i++){
                if(i==0){
                    g.drawImage(head,x[0],y[0],this);
                }
                else  g.drawImage(body,x[i],y[i],this);
            }
        }
        else {
            game(g);
            timer.stop();
        }

    }
    @Override
    public void actionPerformed(ActionEvent actionEvent){
        if(ingame) {
            check();
            checkgame();
            move();
        }
        repaint();
    }
    public void move(){
        for(int i=Dots;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if(leftDir){
            x[0]-=Dot_Size;
        }
        if(rightDir){
            x[0]+=Dot_Size;
        }
        if(upDir){
            y[0]-=Dot_Size;
        }
        if(downDir){
            y[0]+=Dot_Size;
        }

    }

    public void check(){
        if(apple_x==x[0] && apple_y==y[0]){
            Dots++;
            locateApple();
        }
    }

    public void game( Graphics g){
        String msg="Game_Over";
        int score=(Dots-3)*10;
        String scoremsg="Score:"+Integer.toString(score);
        Font small = new Font( "Helvetica", Font. BOLD,  14);

        FontMetrics fontMetrics = getFontMetrics (small);

        g.setColor(Color.WHITE);

        g.setFont(small);

        g.drawString(msg,(B_width-fontMetrics.stringWidth (msg))/2,B_height/4);

        g.drawString (scoremsg, (B_width-fontMetrics.stringWidth (scoremsg))/2, 3*(B_height/4));
    }
    private class Tadapter extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent keyEvent){
            int key=keyEvent.getKeyCode();
            if(key==KeyEvent.VK_LEFT && !rightDir){
                leftDir=true;
                upDir=false;
                downDir=false;
            }
            if(key==KeyEvent.VK_RIGHT && !leftDir){
                rightDir=true;
                upDir=false;
                downDir=false;
            }
            if(key==KeyEvent.VK_UP && !downDir){
                upDir=true;
                leftDir=false;
                rightDir=false;
            }
            if(key==KeyEvent.VK_DOWN && !upDir){
                downDir=true;
                leftDir=false;
                rightDir=false;
            }
        }

    }


}
