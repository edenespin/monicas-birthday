// Happy Birthday, M!

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.image.BufferedImage;


public class BirthdayApp extends JFrame {
    private JLabel recipeLabel;
    private int recipeIndex = 0;
    private String[] recipes = {
        "C:\\Users\\edenc\\PersonalProjects\\Java\\slide1.jpg",
        "C:\\Users\\edenc\\PersonalProjects\\Java\\slide2.jpg",
        "C:\\Users\\edenc\\PersonalProjects\\Java\\slide3.jpg",
        "C:\\Users\\edenc\\PersonalProjects\\Java\\slide4.jpg",
        "C:\\Users\\edenc\\PersonalProjects\\Java\\slide5.jpg",
        "C:\\Users\\edenc\\PersonalProjects\\Java\\slide6.jpg",
        "C:\\Users\\edenc\\PersonalProjects\\Java\\slide7.jpg",
        "C:\\Users\\edenc\\PersonalProjects\\Java\\slide8.jpg",
        "C:\\Users\\edenc\\PersonalProjects\\Java\\slide9.jpg",
        "C:\\Users\\edenc\\PersonalProjects\\Java\\slide10.jpg",
        "C:\\Users\\edenc\\PersonalProjects\\Java\\slide11.jpg",
        "C:\\Users\\edenc\\PersonalProjects\\Java\\slide12.jpg",
        "C:\\Users\\edenc\\PersonalProjects\\Java\\slide13.jpg"
    };
    
    private JLabel countdownLabel;
    private LocalDate anniversaryDate = LocalDate.of(2025, 10, 16);
    
    public BirthdayApp() {
        // Set up the frame
        setTitle("Happy Birthday, M!");
        setSize(1800, 900);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Slideshow area
        recipeLabel = new JLabel();
        recipeLabel.setHorizontalAlignment(JLabel.CENTER); // Center the image
        recipeLabel.setVerticalAlignment(JLabel.CENTER);   // Center vertically
        updateRecipeImage();
        add(recipeLabel, BorderLayout.CENTER);

        // Buttons for navigating slideshow
        JPanel buttonPanel = new JPanel();
        JButton prevButton = new JButton("Previous Slide");
        JButton nextButton = new JButton("Next Slide");
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Countdown area
        countdownLabel = new JLabel("Days until Engagement Anniversary: " + getDaysUntilAnniversary());
        countdownLabel.setFont(new Font("Arial", Font.BOLD, 20));
        countdownLabel.setHorizontalAlignment(JLabel.CENTER);
        add(countdownLabel, BorderLayout.NORTH);

        // Music Button
        JButton playMusicButton = new JButton("Play Music");
        buttonPanel.add(playMusicButton);

        // Button actions
        prevButton.addActionListener(e -> {
            recipeIndex = (recipeIndex - 1 + recipes.length) % recipes.length;
            updateRecipeImage();
        });

        nextButton.addActionListener(e -> {
            recipeIndex = (recipeIndex + 1) % recipes.length;
            updateRecipeImage();
        });

        playMusicButton.addActionListener(e -> {
            new Thread(() -> playMusic("C:\\Users\\edenc\\PersonalProjects\\Java\\wfil2.wav")).start(); // Run music in a separate thread
        });

        // Start countdown timer
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countdownLabel.setText("Days until Engagement Anniversary: " + getDaysUntilAnniversary());
            }
        });
        timer.start();
    }

    // Method to update the recipe image in the slideshow with high-quality scaling
    private void updateRecipeImage() {
        try {
            // Read the original image
            BufferedImage originalImage = ImageIO.read(new File(recipes[recipeIndex]));

            // Define the target width and height (90% of window size, so 720x540)
            int targetWidth = 1500;
            int targetHeight = 700;

            // Get scaled dimensions while maintaining aspect ratio
            int width = originalImage.getWidth();
            int height = originalImage.getHeight();
            double aspectRatio = (double) width / height;

            if (width > height) {
                targetHeight = (int) (targetWidth / aspectRatio);
            } else {
                targetWidth = (int) (targetHeight * aspectRatio);
            }

            // Create a high-quality scaled image
            BufferedImage scaledImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = scaledImage.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
            g2d.dispose();

            // Set the scaled image to the label
            recipeLabel.setIcon(new ImageIcon(scaledImage));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to calculate days until the anniversary
    private long getDaysUntilAnniversary() {
        LocalDate today = LocalDate.now();
        return ChronoUnit.DAYS.between(today, anniversaryDate);
    }

    // Method to play music
    private void playMusic(String filePath) {
        try {
            File musicFile = new File(filePath);
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(musicFile));
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Main method to run the app
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BirthdayApp().setVisible(true);
        });
    }
}
