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
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // �������̽� X ������ ������ ������ ����
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
		 * GridLayout grid = new GridLayout(4,2); // 4x2 ũ��� ����â�� ǥ��, 4�� 2��
		 * grid.setVgap(5); // ������Ʈ ���̿� �� ������ �־� �����
		 * 
		 * Container c = getContentPane(); c.setLayout(grid); c.add(new
		 * JLabel("CoDel Sojourn Delay")); c.add(new JTextField("")); c.add(new
		 * JLabel("CoDel Interval")); c.add(new JTextField("")); c.add(new
		 * JLabel("CoDel Target Delay")); c.add(new JTextField(""));
		 * 
		 * setSize(300,200); // ������ �ʱ� ������ ũ�⸦ ���� setVisible(true); // ȭ�鿡 ǥ�ð� �ǰԲ� ����
		 */

	}

	public static void main(String[] args) {
		new GUI();
		new getCodelGUI();
	}

	@Override
	// ��ġ�� ������ �κ�
	public void actionPerformed(ActionEvent e) {
		String msg = jtf.getText();

		controller.sendMessage(msg);

		jtf.setText("");
	}

	public void appendMsg(String msg) {
		jta.append(msg);
	}
}
