package Controller;

import javax.swing.*;
import V1.ClientGui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener {

	private static JTextArea jta = new JTextArea(40, 25);
	private JTextField jtf = new JTextField(25);
	private GUIcontroller controller = new GUIcontroller();
	private getCodelGUI codelG = new getCodelGUI();
	public static JScrollPane scrollPane = new JScrollPane(jta,
			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

	public GUI() {
		setTitle("Java based SDN Controller");
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 인터페이스 X 누르면 윈도우 닫으며 종료
		scrollPane.setBounds(800, 100, 400, 600);
		add(scrollPane);
		scrollPane.setVisible(true);
		//add(jta, BorderLayout.CENTER);
		add(jtf, BorderLayout.SOUTH);
		jtf.addActionListener(this);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setBounds(800, 100, 400, 600);

		controller.setGui(this);
		controller.connect();

		/*
		 * GridLayout grid = new GridLayout(4,2); // 4x2 크기로 설정창을 표시, 4열 2행
		 * grid.setVgap(5); // 컴포넌트 사이에 빈 공간을 넣어 띄어줌
		 * 
		 * Container c = getContentPane(); c.setLayout(grid); c.add(new
		 * JLabel("CoDel Sojourn Delay")); c.add(new JTextField("")); c.add(new
		 * JLabel("CoDel Interval")); c.add(new JTextField("")); c.add(new
		 * JLabel("CoDel Target Delay")); c.add(new JTextField(""));
		 * 
		 * setSize(300,200); // 윈도우 초기 사이즈 크기를 설정 setVisible(true); // 화면에 표시가 되게끔 설정
		 */

	}

	public static void main(String[] args) {
		new GUI();
		new getCodelGUI();
	}

	@Override
	// 말치면 보내는 부분
	public void actionPerformed(ActionEvent e) {
		String msg = jtf.getText();

		controller.sendMessage(msg);

		jtf.setText("");
	}

	public void appendMsg(String msg) {
		jta.append(msg);
	}
}
