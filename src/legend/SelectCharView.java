package legend;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controls.MyButton;
import utility.file.XmlParser;

public class SelectCharView extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 6L;
	private JScrollPane sp;
	private JPanel panel = new JPanel();
	private JButton createChar = new JButton("Create New Char");
	private MyButton button[] = new MyButton[99];
	private Box box0 = Box.createVerticalBox();
	private Box box1 = Box.createVerticalBox();
	private Box boxH = Box.createVerticalBox();
	private File folder = new File(System.getProperty("user.dir") + "\\src\\runSuite\\save\\");
	private File file[] = folder.listFiles();
	private File activeFile = new File(System.getProperty("user.dir") + "\\src\\runSuite\\LegendHero.xml");
	private File targetFile;
	private XmlParser xmlParser;
	
	public SelectCharView(String s) {
		super(s);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setResizable(false);
		sp = new JScrollPane(box0);
		sp.setPreferredSize(new Dimension(600, 300));
		int count = file.length;
		int remainder = count % 3;
		int line = remainder == 0 ? count / 3 : count / 3 + 1;
		for (int i = 0; i < line; i++) {
			JPanel jp = new JPanel();
			button[i*3] = new MyButton(file[i*3].getName().split("\\.")[0]);
			jp.add(button[i*3]);
			button[i*3].addActionListener(this);
			button[i*3+1] = new MyButton("");
			jp.add(button[i*3+1]);
			if (remainder != 1 || line - i > 1) {
				button[i*3+1].setText(file[i*3+1].getName().split("\\.")[0]);
				button[i*3+1].addActionListener(this);
			}
			button[i*3+2] = new MyButton("");
			jp.add(button[i*3+2]);
			if (remainder == 0 || line - i > 1) {
				button[i*3+2].setText(file[i*3+2].getName().split("\\.")[0]);
				button[i*3+2].addActionListener(this);
			}
			box0.add(jp, BorderLayout.WEST);
		}
		createChar.addActionListener(this);
		panel.add(createChar);
		box1.add(panel, BorderLayout.CENTER);
		boxH.add(box0);
		boxH.add(box1);
		add(boxH);
		pack();
		setLocation(screenSize.width/2 - this.getSize().width/2, screenSize.height/2 - this.getSize().height/2);
		setTitle("Select Char");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		File root = new File(System.getProperty("user.dir") + "\\src\\runSuite\\save\\");
		File rootFiles[] = root.listFiles();
		boolean validRecord = false;
		for (int i = 0; i < rootFiles.length; i++) {
			if ("xml".equals(rootFiles[i].getName().split("\\.")[1])) {
				validRecord = true;
				break;
			}
		}
		if (validRecord) {
			new SelectCharView("");
		} else {
			new RegisterView("");
		}
	}

	public void actionPerformed(ActionEvent actionevent) {
		if (actionevent.getSource() == createChar) {
			setVisible(false);
			new RegisterView("");
			return;
		}
		for (int i = 0; i < 99; i++) {
			if (actionevent.getSource() == button[i]) {
				targetFile = new File(System.getProperty("user.dir") + "\\src\\runSuite\\save\\" + file[i].getName());
				xmlParser = new XmlParser("runSuite\\save\\" + file[i].getName());
				if (activeFile.exists()) {
					activeFile.delete();
					file[i].renameTo(activeFile);
				}
				xmlParser.save();
				changeName();
				setVisible(false);
				new LegendView("");
				return;
			}
		}
	}
	
	private void changeName() {
		File newFiles[] = folder.listFiles();
		for (int i = 0; i < newFiles.length; i++) {
			if ("%".equals(newFiles[i].getName().substring(0, 1))) {
				newFiles[i].renameTo(targetFile);
				return;
			}
		}
	}

}
