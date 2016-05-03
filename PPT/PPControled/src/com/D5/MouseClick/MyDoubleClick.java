package com.D5.MouseClick;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.D5.MyBean.MyChooser;
import com.D5.OfJFrams.importPPT;


public class MyDoubleClick extends MouseAdapter {
	private MouseEvent me = null;

	public void mouseClicked(MouseEvent e) {
		this.me = e;
		String outStr = "";
		if (me.getButton() == MouseEvent.BUTTON1) {
			outStr = "×ó¼ü";
			Incident();
		} else if (me.getButton() == MouseEvent.BUTTON3) {
			outStr = "ÓÒ¼ü";
		} else {
			outStr = "ÖÐ¼ü";
		}
		if (me.getClickCount() == 2) {
			outStr = outStr + "Ë«»÷";
			Incident();
		} else {
			outStr = outStr + "µã»÷";
		}
		//System.out.println(outStr);
	}

	public void Incident() {
		if (MyChooser.getImportppt() == null) {
			importPPT importppt = new importPPT();
			MyChooser.setImportppt(importppt);
			MyChooser.getImportppt().showChooseFrame();
		} else {
			if (MyChooser.getImportppt().getIsExit()) {
				/*System.out.println(MyChooser.getImportppt().getIsExit() + "  "
						+ MyChooser.getImportppt().getTextArea().getText());*/
			} else {
				MyChooser.setImportppt(null);
				importPPT importppt = new importPPT();
				MyChooser.setImportppt(importppt);
				MyChooser.getImportppt().showChooseFrame();
			}
		}
	}
}
