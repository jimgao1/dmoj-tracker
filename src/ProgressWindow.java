import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by Jim_Gao on 12/25/2015.
 */
public class ProgressWindow extends JFrame {

    public String loadingText;
    public JLabel lblLoadingText;
    public JProgressBar progress;
    public double totalPoints = 0;
    public double currentPoints = 0;

    public ProgressWindow(String text, double tpoints){
        this.loadingText = text;
        this.totalPoints = tpoints;
        this.progress = new JProgressBar();
        this.progress.setMinimum(0);
        this.progress.setMaximum(1000);
        this.setSize(250, 100);
        this.setLayout(new BorderLayout());

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);

        this.lblLoadingText = new JLabel(this.loadingText);
        this.lblLoadingText.setFont(new Font("Courier New", Font.BOLD, 16));
        this.add(lblLoadingText, BorderLayout.CENTER);

        this.add(progress, BorderLayout.SOUTH);

        this.repaint();
        this.revalidate();
    }

    public void addProgress(double point){
        currentPoints += point;
        this.progress.setValue((int)(1000 * currentPoints / totalPoints));

        this.repaint();
    }

}
