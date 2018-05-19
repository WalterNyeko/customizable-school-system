package school.ui.main;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class KeyBoard extends JFrame {

    private JFrame frame = new JFrame("On Screen KeyBoard");
    private JPanel parent = new JPanel(new GridLayout(0, 1));
    private JPanel[] panel;
    private JButton[][] button;
    
    private static final String[][] key = {
        {"Esc", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9",
            "F10", "F11", "F12", "PrtSc", "Insert", "Delete", "Home",
            "End", "PgUp", "PgDn"}, {"3\n2", "&", "�", "\"", "'", "(",
            "�", "�", "!", "�", "�", ")", "-", "BkSpc", "NumLock", "/",
            "*", "-"}, {"Tab", "A", "Z", "E", "R", "T", "Y", "U", "I",
            "O", "P", "^", "$", "Enter", "7", "8", "9", "+"}, {"ShiftLock",
            "Q", "S", "D", "F", "G", "H", "J", "K", "L", "M", "�", "�",
            "4", "5", "6"}, {"Shift", "<", "W", "X", "C", "V", "B",
            "N", ",", ";", ":", "=", "Shift", "Up", "1", "2", "3", "Enter"},
        {"Ctrl", "Fn", "Win", "Alt", "Space", "AltGr", "Context",
            "Ctrl", "Left", "Down", "Right", "0", "."}};

    public KeyBoard() {
        super("On Screen KeyBoard");
        panel = new JPanel[6];
        
        for (int row = 0; row < key.length; row++) {
            panel[row] = new JPanel();
            button = new JButton[20][20];
            
            
            for (int column = 0; column < key[row].length; column++) {
                button[row][column] = new JButton(key[row][column]);
                button[row][column].putClientProperty("column", column);
                button[row][column].putClientProperty("row", row);
                button[row][column].putClientProperty("key", key[row][column]);
                button[row][column].addActionListener(new MyActionListener());
                button[row][column].setBackground(Color.black);
                //button[row][column].setForeground(Color.white);
                panel[row].add(button[row][column]);
            }
            parent.add(panel[row]);
        }
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(parent);
        setLocation(170,470);
        pack();
        setBackground(Color.decode("#16a085"));
        setVisible(true);
    }

    public class MyActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton) e.getSource();
            System.out.println("clicked column --> " + btn.getClientProperty("column")
                    + ", row --> " + btn.getClientProperty("row")
                    + ", Key Typed --> " + btn.getClientProperty("key"));
            String keytyped=(String) btn.getClientProperty("key");
            
        }
    }
    
    

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                KeyBoard guI = new KeyBoard();
                guI.addWindowListener(new WindowListener() {
					
					@Override
					public void windowOpened(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowIconified(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeiconified(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeactivated(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowClosing(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowClosed(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowActivated(WindowEvent arg0) {
						// TODO Auto-generated method stub
						
					}
				});
            }
        });
    }
}