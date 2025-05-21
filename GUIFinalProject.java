//Malia Moreno | 4 April 2025//
package guifinalproject;

//import files//
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class GUIFinalProject {

    private static int jumNum;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createForm();
        });
        
        System.out.println("I work!");
    }
    
    private static void createForm(){
        //set size of frame//
        JFrame frame = new JFrame("Math Player");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 900);
        
        //make a panel//
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);     //make items on panel//
        
        //set frame to middle of screen//
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    //define number of jumps available//
    int jumpNum = 0;
    
    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);
        
        //Make title//
        JLabel title1 = new JLabel("Solve Math Equations to Play");
        title1.setBounds(250, 50, 500, 50);
        title1.setFont(new Font("Serif", Font.PLAIN, 25));
        panel.add(title1);
        
        //mute and play sounds//
        JButton backMusic = new JButton("BackgroundMusic");
        backMusic.setBounds(600, 25, 150, 25);
        panel.add(backMusic);
        backMusic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("sounds/BackgroundMusic.wav"); // Update with actual file path
            }
        });
        
        //Make randomized equation//
        Random ran = new Random();
        int x = ran.nextInt(100);
        int y = ran.nextInt(100);
        //make first equation//
        JLabel equation1 = new JLabel(x + " + " + y + " = ");
        equation1.setBounds(240, 125, 100, 25);
        equation1.setFont(new Font("Serif", Font.PLAIN, 20));
        panel.add(equation1);
        JTextField equation1Ansewr = new JTextField(100);
        equation1Ansewr.setBounds(325, 125, 165, 25);
        panel.add(equation1Ansewr);
        //make first check button//
        JButton check1Button = new JButton("Check");
        check1Button.setBounds(500, 125, 75, 25);
        panel.add(check1Button);
        
        int helperInt1 = jumpNum;
        
        //add action to first button//
        check1Button.addActionListener(new ActionListener() {
            //set intermediate variable//
            int helperInt2 = jumNum;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                /*check if anser is right or not: 
                    - If answer is correct, add one to movable step
                    - Else if anser is incorrect, minus one to movable step
                */  
                String inAns1 = equation1Ansewr.getText();
                if(inAns1.equals(Integer.toString(x+y))){
                    jumpNum++;
                }
            }
        });
        
        //make second equation//
        JLabel equation2 = new JLabel("1 + 1 =");
        equation2.setBounds(250, 200, 80, 25);
        equation2.setFont(new Font("Serif", Font.PLAIN, 20));
        panel.add(equation2);
        JTextField equation2Ansewr = new JTextField(100);
        equation2Ansewr.setBounds(325, 200, 165, 25);
        panel.add(equation2Ansewr);
        //make secoind check button//
        JButton check2Button = new JButton("Check");
        check2Button.setBounds(500, 200, 75, 25);
        panel.add(check2Button);
        
        
    }
    
    private static void playSound(String filePath) {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.setFramePosition(0);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            JOptionPane.showMessageDialog(null, "Error playing sound: " + e.getMessage());
        }
    }
}
