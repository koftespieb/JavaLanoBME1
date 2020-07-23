import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import acm.program.Program;

public class Editor extends Program {
	private JTextArea display;
	private JTextField tfFileName;
	private JButton save;
	private JButton open;

	public void init() {
		setSize(300, 300);
// your code goes here...
		display = new JTextArea();
		display.setFont(new Font("Manaspace", Font.BOLD, 18));
		add(display, CENTER);
		tfFileName = new JTextField(10);
		add(tfFileName, SOUTH);
		open = new JButton("Open");
		add(open, SOUTH);
		save = new JButton("Save");
		add(save, SOUTH);
		addActionListeners();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
// your code goes here...
		String cmd = e.getActionCommand();
		if (cmd == "Save") {
			saveFile(tfFileName.getText());
		}
		if (cmd == "Open") {
			openFile(tfFileName.getText());
		}
	}

	private void saveFile(String fileName) {
		try {
			// open file
			FileWriter fw = new FileWriter(tfFileName.getText(), false);
// write to file
			String text = display.getText();
			StringTokenizer st = new StringTokenizer(text, "\n");
			while (st.hasMoreTokens()) {
				fw.write(st.nextToken() + "\n");
			}
// close file fw.close();
		} catch (Exception e) {
			println("An error occurred!");
		}
	}

	private void openFile(String fileName) {

		try {
			// open file
			FileReader fr = new FileReader(tfFileName.getText());
			BufferedReader rd = new BufferedReader(fr);
			// read from file, line by line
			String text = "";
			while (true) {
				String line = rd.readLine();
				if (line == null)
					break;
				text += line + "\n";
			}
			// close file rd.close(); 	fr.close();
			// show file content display.setText(text);
		} catch (Exception e) {
			println("File does not exist!");
		}
	}
}