package CoinEat;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;


public class CoinEat extends JFrame {
    private Image bufferImage;
    private Graphics screenGraphic;

    private Image backgroundImage = new ImageIcon("src/Image/mainScreen.png").getImage();
    private Image player = new ImageIcon("src/Image/player.png").getImage();
    private Image coin = new ImageIcon("src/Image/coin.png").getImage();

    private int playerX, playerY;   // 플레이어 위치
    private int playerWidth = player.getWidth(null);    // 플레이어 가로 크기
    private int playerHeight = player.getHeight(null);  //플레이어 세로 크기
    private int coinX, coinY;   //코인 위치
    private int coinWidth = coin.getWidth(null);    // 코인 가로 크기
    private int coinHeight = coin.getHeight(null);  // 코인 세로 크기

    private int score;  //점수

    private boolean up, down, left, right;  // 키 누름


    private CoinEat() {

        setTitle("동전 먹기 게임");
        setVisible(true);
        setSize(500,500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                switch(e.getKeyCode()){
                    case KeyEvent.VK_W:
                        up=true;
                        break;
                    case KeyEvent.VK_A:
                        left=true;
                        break;
                    case KeyEvent.VK_S:
                        down=true;
                        break;
                    case KeyEvent.VK_D:
                        right=true;
                        break;
                }
            }

            public void keyReleased(KeyEvent e){
                switch(e.getKeyCode()){
                    case KeyEvent.VK_W:
                        up=false;
                        break;
                    case KeyEvent.VK_A:
                        left=false;
                        break;
                    case KeyEvent.VK_S:
                        down=false;
                        break;
                    case KeyEvent.VK_D:
                        right=false;
                        break;
                }
            }
        }); // 키보드 움직임을 위한 키리스터너 부착
        Init(); // 게임 초기화
        while (true) {
            try{
                Thread.sleep(20);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
            keyProcess();
            crashCheck();
        }
    }

    private void Init() {

        score = 0;

        playerX = (500-playerWidth)/2;
        playerY = (500-playerHeight)/2;

        coinX = (int)(Math.random()*(501-playerWidth));
        coinY = (int)(Math.random()*(501-playerHeight - 30))+30;


    }   //형변환을 활용한 초기화(점수, 코인, 플레이어 위치)

    public void keyProcess() {
        if (up && playerY - 3 > 30) playerY-=3;
        if (down && playerY + playerHeight +3 < 500) playerY+=3;
        if (left && playerX - 3 > 0) playerX-=3;
        if (right && playerX + playerWidth + 3 < 500) playerX+=3;
    }

    public void crashCheck(){
        if (playerX+playerWidth > coinX && coinX + coinWidth > playerX && playerY + playerHeight > coinY && coinY + coinHeight > playerY){
            score+=100;
            coinX = (int)(Math.random()*(501-playerWidth));
            coinY = (int)(Math.random()*(501-playerHeight - 30))+30;
        }
    }

    public void paint(Graphics g) {
        bufferImage = createImage(500,500);
        screenGraphic = bufferImage.getGraphics();
        screenDraw(screenGraphic);
        g.drawImage(bufferImage, 0, 0, null);

    }

    public void screenDraw(Graphics g){
        g.drawImage(backgroundImage, 0, 0, null);
        g.drawImage(coin, coinX, coinY, null);
        g.drawImage(player, playerX, playerY, null);
        // g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD,40));
        g.drawString("SCORE : "+score, 30, 80);
        this.repaint();
    }

    public static void main(String[] args){

        new CoinEat();
    }
}
