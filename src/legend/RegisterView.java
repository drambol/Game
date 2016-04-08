package legend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controls.MyButtonGroup;
import controls.MyRadioButton;
import utility.file.XmlParser;

public class RegisterView extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private JPanel headerPanel = new JPanel();
	private JPanel textPanel = new JPanel();
	private JPanel genderPanel = new JPanel();
	private JPanel careerPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	private JLabel label = new JLabel();
	private JTextField tText = new JTextField();
	private MyRadioButton genderButton[] = new MyRadioButton[2];
	private MyButtonGroup genderGroup = new MyButtonGroup();
	private MyRadioButton careerButton[] = new MyRadioButton[3];
	private MyButtonGroup careerGroup = new MyButtonGroup();
	private JButton button = new JButton("Submit");
	private Box box = Box.createVerticalBox();
	XmlParser xmlParser = new XmlParser("runSuite\\LegendHero.xml");
	
	public RegisterView(String s) {
		super(s);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		button.addActionListener(this);
		label.setPreferredSize(new Dimension(200, 24));
		label.setForeground(Color.RED);
		tText.setPreferredSize(new Dimension(120, 22));
		tText.setText("");
		initialRadioButtons();
		button.setPreferredSize(new Dimension(80, 22));
		headerPanel.add(label);
		box.add(headerPanel);
		textPanel.add(tText);
		box.add(textPanel);
		box.add(genderPanel);
		box.add(careerPanel);
		buttonPanel.add(button);
		box.add(buttonPanel);
		add(box);
		pack();
		setLocation(screenSize.width/2 - this.getSize().width/2, screenSize.height/2 - this.getSize().height/2);
		setResizable(false);
		setVisible(true);
		setTitle("Create New Hero");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent actionevent) {
		if (actionevent.getSource() == button) {
			if (tText.getText().length() >= 2) {
				xmlParser.getNodeByName("name").setTextContent(tText.getText());
				xmlParser.getNodeByName("career").setTextContent(careerGroup.getSelectedButtonText());
				initialCharData(genderGroup.getSelectedButtonText(), careerGroup.getSelectedButtonText());
				xmlParser.save();
				this.dispose();
				new LegendView("");
			} else {
				label.setText("Name cannot be less than 2 letters!");
			}
		}
	}
	
	private void initialRadioButtons() {
		genderButton[0] = new MyRadioButton(LegendConstant.Male);
		genderButton[1] = new MyRadioButton(LegendConstant.Female);
		for (int i = 0; i < 2; i++) {
			genderButton[i].setPreferredSize(new Dimension(50, 22));
			genderPanel.add(genderButton[i]);
			genderGroup.add(genderButton[i]);
		}
		genderButton[0].setSelected(true);
		
		careerButton[0] = new MyRadioButton(LegendConstant.Warrior);
		careerButton[1] = new MyRadioButton(LegendConstant.Taoist);
		careerButton[2] = new MyRadioButton(LegendConstant.Mage);
		for (int i = 0; i < 3; i++) {
			careerButton[i].setPreferredSize(new Dimension(50, 22));
			careerPanel.add(careerButton[i]);
			careerGroup.add(careerButton[i]);
		}
		careerButton[0].setSelected(true);
	}
	
	private void initialCharData(String gender, String career) {
		xmlParser.getNodeByName("gender").setTextContent(gender);
		xmlParser.getNodeByName("level").setTextContent("1");
		xmlParser.getNodeByName("exp").setTextContent("0");
		xmlParser.getNodeByName("dex").setTextContent("15");
		xmlParser.getNodeByName("weapon").setTextContent(LegendConstant.StartWeapon);
		xmlParser.getNodeByName("helmet").setTextContent("");
		xmlParser.getNodeByName("amulet").setTextContent("");
		xmlParser.getNodeByName("medal").setTextContent("");
		xmlParser.getNodeByName("leftBracelet").setTextContent("");
		xmlParser.getNodeByName("rightBracelet").setTextContent("");
		xmlParser.getNodeByName("leftRing").setTextContent("");
		xmlParser.getNodeByName("rightRing").setTextContent("");
		xmlParser.getNodeByName("belt").setTextContent("");
		xmlParser.getNodeByName("boots").setTextContent("");
		xmlParser.getNodeByName("gem").setTextContent("");
		xmlParser.getNodeByName("money").setTextContent("0");
		xmlParser.getNodeByName("ore1").setTextContent("0");
		xmlParser.getNodeByName("ore2").setTextContent("0");
		xmlParser.getNodeByName("charm1").setTextContent("0");
		xmlParser.getNodeByName("charm2").setTextContent("0");
		xmlParser.getNodeByName("charm3").setTextContent("0");
		for (int i = 0; i < 99; i++) {
			xmlParser.getNodeByName("item", i).setTextContent("");
		}
		switch (career) {
		case LegendConstant.Warrior:
			xmlParser.getNodeByName("dc").setTextContent("0-0");
			xmlParser.getNodeByName("sc").setTextContent("0-0");
			xmlParser.getNodeByName("mc").setTextContent("0-0");
			xmlParser.getNodeByName("ac").setTextContent("0-0");
			xmlParser.getNodeByName("mac").setTextContent("0-0");
			xmlParser.getNodeByName("hp").setTextContent("19");
			xmlParser.getNodeByName("mp").setTextContent("5");
			xmlParser.getNodeByName("hit").setTextContent("17");
			break;
		case LegendConstant.Taoist:
			xmlParser.getNodeByName("dc").setTextContent("0-0");
			xmlParser.getNodeByName("sc").setTextContent("0-0");
			xmlParser.getNodeByName("mc").setTextContent("0-0");
			xmlParser.getNodeByName("ac").setTextContent("0-0");
			xmlParser.getNodeByName("mac").setTextContent("0-0");
			xmlParser.getNodeByName("hp").setTextContent("18");
			xmlParser.getNodeByName("mp").setTextContent("20");
			xmlParser.getNodeByName("hit").setTextContent("15");
			break;
		case LegendConstant.Mage:
			xmlParser.getNodeByName("dc").setTextContent("0-0");
			xmlParser.getNodeByName("sc").setTextContent("0-0");
			xmlParser.getNodeByName("mc").setTextContent("0-0");
			xmlParser.getNodeByName("ac").setTextContent("0-0");
			xmlParser.getNodeByName("mac").setTextContent("0-0");
			xmlParser.getNodeByName("hp").setTextContent("16");
			xmlParser.getNodeByName("mp").setTextContent("77");
			xmlParser.getNodeByName("hit").setTextContent("11");
			break;
		}
		switch (gender) {
		case LegendConstant.Male:
			xmlParser.getNodeByName("armor").setTextContent(LegendConstant.StartMaleArmor);
			break;
		case LegendConstant.Female:
			xmlParser.getNodeByName("armor").setTextContent(LegendConstant.StartFemaleArmor);
			break;
		}
	}

}
