package Controller;

import javax.swing.*;
import V1.ClientGui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class getCodelGUI extends JFrame implements ActionListener {
	public static boolean answer;

	private static JTextArea jta = new JTextArea(40, 25);
	private JTextField jtf = new JTextField(25);
	private GUIcontroller controller = new GUIcontroller();
	JButton enter;

	JTextField offset, duration, gateEnable, aqmEnable, interval, targetResidence;

	public getCodelGUI() {
		setTitle("Codel Parameter Config");
		setSize(892, 400);
		setVisible(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // �������̽� X ������ ������ ������ ����

		// ��ü��������
		offset = new JTextField(10);
		duration = new JTextField(10);
		gateEnable = new JTextField(10);
		aqmEnable = new JTextField(10);
		interval = new JTextField(10);
		targetResidence = new JTextField(10);
		enter = new JButton("Send");
		enter.addActionListener(this);

		JPanel p = new JPanel();
		p.setLayout(new GridLayout(6, 2));
		p.add(new JLabel("Gate_OpenOffset(between 1 and 512 usec)"));
		p.add(offset);
		p.add(new JLabel("Gate_OpenDuration(between 1 and 512 usec)"));
		p.add(duration);
		p.add(new JLabel("Gate_Enable/Disable(between 0 and 1)"));
		p.add(gateEnable);
		p.add(new JLabel("CoDel AQM Enable/Disable(between 0 and 1)"));
		p.add(aqmEnable);
		p.add(new JLabel("CoDel_Interval(msec)(between 1 and 10)"));
		p.add(interval);
		p.add(new JLabel("CoDel_TargetResidenceTime(msec)(between 1 and 10)"));
		p.add(targetResidence);
		add(p);
		add(enter, BorderLayout.SOUTH);

		controller.setCodelGui(this);

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

	public void open() {
		setVisible(true);
	}

	public static void main(String[] args) {
		new getCodelGUI();
	}

	@Override
	// ��ġ�� ������ �κ�
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == enter) {
			/*
			byte[] off = offset.getText().getBytes();
			byte[] dur = duration.getText().getBytes();
			byte[] gaE = gateEnable.getText().getBytes();
			byte[] aqmE = aqmEnable.getText().getBytes();
			byte[] inte = interval.getText().getBytes();
			byte[] targ = targetResidence.getText().getBytes();
			*/
			String off = offset.getText();
			String dur = duration.getText();
			String gaE = gateEnable.getText();
			String aqmE = aqmEnable.getText();
			String inte = interval.getText();
			String targ = targetResidence.getText();
			offset.setText("");
			duration.setText("");
			gateEnable.setText("");
			aqmEnable.setText("");
			interval.setText("");
			targetResidence.setText("");
			answer = false;
			controller.setCoDelParameter(off, dur, gaE, aqmE, inte, targ);
			controller.sendMessage("setCodelConfig");
			setVisible(false);
		}
	}
}
