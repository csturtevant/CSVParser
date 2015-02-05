package edu.csturtevant.csvparser;

/**
 * Created by chrissturtevant0 on 2/4/15.
 */

        import java.awt.BorderLayout;
        import java.awt.GridLayout;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import javax.swing.JButton;
        import javax.swing.JFileChooser;
        import javax.swing.JFrame;
        import javax.swing.JPanel;
        import javax.swing.JTextField;
        import javax.swing.SwingUtilities;

public class FileChooserDemo extends JFrame {

    private JTextField display = new JTextField();
    private JButton open = new JButton("Open");
    private JButton save = new JButton("Save");

    public FileChooserDemo() {
        JPanel panel = new JPanel();
        open.addActionListener(new OpenClass());
        panel.add(open);
        save.addActionListener(new SaveClass());
        panel.add(save);
        add(panel, BorderLayout.SOUTH);
        display.setEditable(false);
        panel = new JPanel();
        panel.setLayout(new GridLayout(2,1));
        panel.add(display);
        add(panel, BorderLayout.NORTH);
    }

    class OpenClass implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();

            int option = chooser.showOpenDialog(FileChooserDemo.this);
            if(option == JFileChooser.APPROVE_OPTION) {
                display.setText("You chose " +
                        ((chooser.getSelectedFile()!=null)?
                                chooser.getSelectedFile().getName():
                                "nothing"));
            }

            if(option == JFileChooser.CANCEL_OPTION) {
                display.setText("You canceled.");
            }
        }
    }

    class SaveClass implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();

            int option = chooser.showSaveDialog(FileChooserDemo.this);
            if(option == JFileChooser.APPROVE_OPTION) {
                display.setText("You chose " +
                        ((chooser.getSelectedFile()!=null)?
                                chooser.getSelectedFile().getName():
                                "nothing"));
            }

            if(option == JFileChooser.CANCEL_OPTION) {
                display.setText("You canceled.");
            }
        }
    }

    public static void main(String[] args) {
        setFrame(new FileChooserDemo(), 200, 100);
    }

    public static void
    setFrame(final JFrame frame, final int width, final int height) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                frame.setTitle(frame.getClass().getSimpleName());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(width, height);
                frame.setVisible(true);
            }
        });
    }

}

