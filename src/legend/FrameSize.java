package legend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FrameSize {

    private JFrame frm = new JFrame();
    private JPanel pnl1 = new JPanel();
    private JPanel pnl2 = new JPanel();
    private JButton btn = new JButton("Get ScreenSize for JComponents");

    public FrameSize() {
        btn.setPreferredSize(new Dimension(400, 40));
        btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("JButton Size - " + btn.getSize());
                System.out.println("JPanel Size - " + pnl1.getSize());
                System.out.println("JPanel Size - " + pnl2.getSize());
                System.out.println("JFrame Size - " + frm.getSize());
            }
        });
        pnl1.setPreferredSize(new Dimension(540, 50));
        pnl1.add(btn, BorderLayout.NORTH);
        pnl1.setBackground(Color.blue);
        frm.add(pnl1, BorderLayout.NORTH);
        pnl2.setPreferredSize(new Dimension(540, 50));
        pnl2.add(btn, BorderLayout.SOUTH);
        pnl2.setBackground(Color.red);
        frm.add(pnl2, BorderLayout.SOUTH);
        frm.setLocation(150, 100);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // EDIT
        frm.setResizable(false);
        frm.pack();
        frm.setVisible(true);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                FrameSize fS = new FrameSize();
            }
        });
    }
}