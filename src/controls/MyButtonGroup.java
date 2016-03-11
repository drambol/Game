package controls;

import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

public class MyButtonGroup extends ButtonGroup {
	
	private static final long serialVersionUID = 1L;

	public MyButtonGroup() {
		super();
	}
	
	public String getSelectedButtonText() {
        for (Enumeration<AbstractButton> buttons = this.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }

}
