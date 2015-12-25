import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jim_Gao on 12/24/2015.
 */
public class TrackerGUI extends JFrame implements ActionListener{

    public JPanel userPanel;

    public JPanel leftUserPanel;
    public JLabel lblUsernameLeft;
    public JLabel lblUserInfoLeft;
    public JTextField txtUsernameLeft;
    public JButton btnUpdateLeft;

    public JPanel rightUserPanel;
    public JLabel lblUsernameRight;
    public JLabel lblUserInfoRight;
    public JTextField txtUsernameRight;
    public JButton btnUpdateRight;

    public DMOJUser userLeft;
    public DMOJUser userRight;

    public TrackerGUI(){
        setUIFont (new javax.swing.plaf.FontUIResource(new Font("Arial",Font.PLAIN, 16)));

        this.setSize(800, 500);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("DMOJ Problem Tracker");
        this.setLayout(new BorderLayout());

        userPanel = new JPanel();
        userPanel.setLayout(new GridLayout(1, 2));

        leftUserPanel = new JPanel();
        leftUserPanel.setLayout(new FlowLayout());

        lblUsernameLeft = new JLabel("Username: ");
        lblUserInfoLeft = new JLabel();
        txtUsernameLeft = new JTextField();
        txtUsernameLeft.setColumns(20);
        btnUpdateLeft = new JButton("Update");
        btnUpdateLeft.setActionCommand("update_left");

        leftUserPanel.add(lblUsernameLeft);
        leftUserPanel.add(txtUsernameLeft);
        leftUserPanel.add(btnUpdateLeft);
        leftUserPanel.add(lblUserInfoLeft);

        rightUserPanel = new JPanel();
        rightUserPanel.setLayout(new FlowLayout());

        lblUsernameRight = new JLabel("Username: ");
        lblUserInfoRight = new JLabel();
        txtUsernameRight = new JTextField();
        txtUsernameRight.setColumns(20);
        btnUpdateRight = new JButton("Update");
        btnUpdateRight.setActionCommand("update_right");

        rightUserPanel.add(lblUsernameRight);
        rightUserPanel.add(txtUsernameRight);
        rightUserPanel.add(btnUpdateRight);
        rightUserPanel.add(lblUserInfoRight);

        userPanel.add(leftUserPanel);
        userPanel.add(rightUserPanel);

        this.add(userPanel, BorderLayout.CENTER);

        this.repaint();
        this.revalidate();
    }

    private static void setUIFont(javax.swing.plaf.FontUIResource f)
    {
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements())
        {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource)
            {
                UIManager.put(key, f);
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("update_left")){
            userLeft = new DMOJUser(this.txtUsernameLeft.getText());
            userLeft.updateProblems();

            lblUserInfoLeft.setText("<html>Display Name: <b>" + userLeft.displayName +
                    "</b><br>Total Points: <b>" + userLeft.totalPoints + "</b></html>");
        } else if (e.getActionCommand().equals("update_right")){
            userRight = new DMOJUser(this.txtUsernameRight.getText());
            userRight.updateProblems();

            lblUserInfoRight.setText("<html>Display Name: <b>" + userRight.displayName +
                    "</b><br>Total Points: <b>" + userRight.totalPoints + "</b></html>");
        }
    }
}
