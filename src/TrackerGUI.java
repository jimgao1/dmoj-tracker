import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

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

    public JTabbedPane problemsPanel;

    public JPanel pnlBothSolved;
    public JList lstBothSolved;
    public DefaultListModel<String> lstModelBothSolved;

    public JPanel pnlPersonA;
    public JList lstPersonA;
    public DefaultListModel<String> lstModelPersonA;

    public JPanel pnlPersonB;
    public JList lstPersonB;
    public DefaultListModel<String> lstModelPersonB;

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
        btnUpdateLeft.setPreferredSize(new Dimension(250, 30));
        btnUpdateLeft.addActionListener(this);

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
        btnUpdateRight.setPreferredSize(new Dimension(250, 30));
        btnUpdateRight.addActionListener(this);

        rightUserPanel.add(lblUsernameRight);
        rightUserPanel.add(txtUsernameRight);
        rightUserPanel.add(btnUpdateRight);
        rightUserPanel.add(lblUserInfoRight);

        userPanel.add(leftUserPanel);
        userPanel.add(rightUserPanel);

        userPanel.setPreferredSize(new Dimension(this.getWidth(), this.getHeight() / 5));
        this.add(userPanel, BorderLayout.NORTH);

        problemsPanel = new JTabbedPane();

        lstModelBothSolved = new DefaultListModel<>();
        lstModelPersonA = new DefaultListModel<>();
        lstModelPersonB = new DefaultListModel<>();

        pnlBothSolved = new JPanel();
        pnlBothSolved.setLayout(new GridLayout(1, 1));
        lstBothSolved = new JList(lstModelBothSolved);
        pnlBothSolved.add(lstBothSolved);

        problemsPanel.addTab("Solved by Both User", pnlBothSolved);

        this.add(problemsPanel, BorderLayout.CENTER);

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

            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            userLeft.updateProblems();
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

            if (userLeft.displayName != null){
                lblUserInfoLeft.setText("<html>Display Name: <b>" + userLeft.displayName +
                        "</b><br>Total Points: <b>" + userLeft.totalPoints + "</b></html>");
            } else {
                JOptionPane.showMessageDialog(null, "User Not Found");
                userLeft = null;
                this.txtUsernameLeft.setText("");
                this.lblUserInfoLeft.setText("");
            }
        } else if (e.getActionCommand().equals("update_right")){
            userRight = new DMOJUser(this.txtUsernameRight.getText());

            this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            userRight.updateProblems();
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

            if (userRight.displayName != null) {
                lblUserInfoRight.setText("<html>Display Name: <b>" + userRight.displayName +
                        "</b><br>Total Points: <b>" + userRight.totalPoints + "</b></html>");
            } else {
                JOptionPane.showMessageDialog(null, "User Not Found");
                userRight = null;
                this.txtUsernameRight.setText("");
                this.lblUserInfoRight.setText("");
            }
        }

        if (userRight.displayName != null && userLeft.displayName != null){
            Collections.sort(userLeft.solvedProblems);
            Collections.sort(userRight.solvedProblems);

            for (DMOJProblem prob : userLeft.solvedProblems){
                if (userRight.solvedProblems.contains(prob)){
                    lstModelBothSolved.addElement(prob.toString());
                } else {
                    lstModelPersonA.addElement(prob.toString());
                }
            }

            for (DMOJProblem prob : userRight.solvedProblems){
                if (!userLeft.solvedProblems.contains(prob)){
                    lstModelPersonB.addElement(prob.toString());
                }
            }
        }
    }
}
