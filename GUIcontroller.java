package Controller;

import java.io.*;
import java.net.*;
import java.nio.*;
import java.util.*;

public class GUIcontroller {
	private static InputStream input; // InputStream
	private static OutputStream output; // OutputStream
	private static GUI gui; // �ش� �������� �� GUI
	private static getCodelGUI codelgui; // �ش� �������� �� GUI
	private static Socket socket;
	/*
	 * private static byte[] Gate_OpenOffset = {}; private static byte[]
	 * Gate_OpenDuration = {}; private static byte[] Gate_Enable = {}; private
	 * static byte[] CoDelAQM_Enable = {}; private static byte[] CoDel_Interval =
	 * {}; private static byte[] CoDel_TargetResidenceTime = {};
	 */
	private static String Gate_OpenOffset;
	private static String Gate_OpenDuration;
	private static String Gate_Enable;
	private static String CoDelAQM_Enable;
	private static String CoDel_Interval;
	private static String CoDel_TargetResidenceTime;

	public static void main(String[] args) {
		GUIcontroller control = new GUIcontroller();
		control.connect();
	}

	public final void setGui(GUI gui) {
		this.gui = gui;
	}

	public final void setCodelGui(getCodelGUI gui) {
		this.codelgui = gui;
	}

	public static void setCoDelParameter(String offset, String duration, String gate_enable, String aqm_enable,
			String interval, String target) {
		Gate_OpenOffset = offset;
		Gate_OpenDuration = duration;
		Gate_Enable = gate_enable;
		CoDelAQM_Enable = aqm_enable;
		CoDel_Interval = interval;
		CoDel_TargetResidenceTime = target;
	}

	/*
	 * SDN Controller(����)�� Openflow Switch(Ŭ���̾�Ʈ)�� �����ϴ� �Լ�
	 */
	public void connect() {
		int port = 6653;
		Scanner command = new Scanner(System.in);

		try (ServerSocket serverSocket = new ServerSocket(port)) { // 3-HandShaking ���� TCP ����
			gui.appendMsg("Server is listening on port : " + port + "\n");
			System.out.println("Server is listening on port : " + port); // ��Ʈ���� Openflow Switch ������ ��ٸ�
			socket = serverSocket.accept(); // accpet ��ٸ�

			gui.appendMsg("[" + socket.getInetAddress() + "] client connected.\n");
			gui.appendMsg("Enter the Command.\n");

			System.out.println("[" + socket.getInetAddress() + "] client connected."); // ������ �Ǹ� ������ �Ǿ����� ���
			System.out.println("Enter the Command.");

			String commandIn = "";
			input = socket.getInputStream();
			output = socket.getOutputStream();

			while (input != null) { // ������ �޽����� �ƹ��͵� ���� ���� �ƴ϶��
				int length = input.available(); // Openflow Switch�κ��� ���� ������ ��Ŷ�� ũ�⸦ �޾ƿ�
				byte[] bytes = new byte[length]; // �޾ƿ� ��Ŷ�� ũ�⸦ ����Ʈ�迭�� �������
				int readByteCount = input.read(bytes); // �����Ʈ �Դ��� Ȯ��
				readMessage(); // �޽����� ����
				sendMessage(commandIn);
			}
		} catch (IOException ex) {
			System.out.println("Server exception : " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public static void sendMessage(String command) {
		// OutputStream output = output;
		ByteBuffer sendMessage = ByteBuffer.allocate(8);

		if (command.equals("sendHello")) {
			/*
			 * 0x01 = hello 0x02 = feature_request 0x03 = feature_reply
			 */
			byte Version = 1; // version, 8bit
			byte Type = 0; // Type, 8Bit
			byte Length = 8; // packet length, 16Bit
			byte xid = 0; // ��Ŷ�� �ĺ��ϴ� Ʈ����� ID, 32Bbit
			byte[] send = { Version, Type, 0, Length, 0, 0, 0, xid };
			sendMessage.order(ByteOrder.LITTLE_ENDIAN);
			sendMessage.put(send);
			gui.appendMsg("----controller to switch----\n");
			gui.appendMsg("OFP_Version : 1.3\n");
			gui.appendMsg("OFP_Type : OFP_HELLO(" + Type + ")\n");
			gui.appendMsg("Length : " + Length + "\n");
			gui.appendMsg("Xid : " + xid + "\n");
			try {
				output.write(sendMessage.array());
				output.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (command.equals("sendFeatureRequest")) {
			/*
			 * 0x01 = hello 0x02 = feature_request 0x03 = feature_reply
			 */
			byte Version = 1; // version, 8byte
			byte Type = 5; // Type, 8Byte
			byte Length = 8; // packet length, 16Byte
			byte xid = 0; // ��Ŷ�� �ĺ��ϴ� Ʈ����� ID, 32Byte
			byte[] send = { Version, Type, 0, Length, 0, 0, 0, xid };
			sendMessage.order(ByteOrder.LITTLE_ENDIAN);
			sendMessage.put(send);

			gui.appendMsg("----controller to switch----\n");
			gui.appendMsg("OFP_Version : 1.3\n");
			gui.appendMsg("OFP_Type : OFP_Feature_Request(" + Type + ")\n");
			gui.appendMsg("Length : " + Length + "\n");
			gui.appendMsg("Xid : " + xid + "\n");
			try {
				output.write(sendMessage.array());
				output.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (command.equals("getCodelConfig")) { // 21
			/*
			 * 0x01 = hello 0x02 = feature_request 0x03 = feature_reply
			 */
			byte Version = 1; // version, 8byte
			byte Type = 22; // Type, 8Byte
			byte Length = 8; // packet length, 16Byte
			byte xid = 0; // ��Ŷ�� �ĺ��ϴ� Ʈ����� ID, 32Byte
			byte[] send = { Version, Type, 0, Length, 0, 0, 0, xid };
			sendMessage.order(ByteOrder.LITTLE_ENDIAN);
			sendMessage.put(send);

			gui.appendMsg("----controller to switch----\n");
			gui.appendMsg("OFP_Version : 1.3\n");
			gui.appendMsg("OFP_Type : OFP_Get_CoDel_Config(" + Type + ")\n");
			gui.appendMsg("Length : " + Length + "\n");
			gui.appendMsg("Xid : " + xid + "\n");
			try {
				output.write(sendMessage.array());
				output.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (command.equals("setCodel")) { // 21
			codelgui.open();
		} else if (command.equals("setCodelConfig")) { // 24
			gui.appendMsg("----controller to switch----\n");
			ByteBuffer setMessage = ByteBuffer.allocate(32);
			byte Version = 1; // version, 8byte
			byte Type = 24; // Type, 8Byte
			byte Length = 8; // packet length, 16Byte
			byte xid = 0; // ��Ŷ�� �ĺ��ϴ� Ʈ����� ID, 32Byte

			byte[] send = { Version, Type, 0, Length, 0, 0, 0, xid };
			setMessage.order(ByteOrder.LITTLE_ENDIAN);
			setMessage.put(send);
			byte[] extra = { 0, 0, 0 };
			if(Integer.parseInt(Gate_OpenOffset) > 256) {
				int next = Integer.parseInt(Gate_OpenOffset)/256;
				byte[] extra2 = {0,0, (byte) next};
				setMessage.put(extra2);
				setMessage.put((byte) Integer.parseInt(Gate_OpenOffset));
			}
			else {
				setMessage.put(extra);
				setMessage.put((byte) Integer.parseInt(Gate_OpenOffset));
			}
			if(Integer.parseInt(Gate_OpenDuration) > 256) {
				int next = Integer.parseInt(Gate_OpenDuration)/256;
				byte[] extra2 = {0,0, (byte) next};
				setMessage.put(extra2);
				setMessage.put((byte) Integer.parseInt(Gate_OpenDuration));
			}
			else {
				setMessage.put(extra);
				setMessage.put((byte) Integer.parseInt(Gate_OpenDuration));
			}			
			setMessage.put(extra);
			setMessage.put(changeByte(Gate_Enable.getBytes()));
			setMessage.put(extra);
			setMessage.put(changeByte(CoDelAQM_Enable.getBytes()));
			setMessage.put(extra);
			setMessage.put(changeByte(CoDel_Interval.getBytes()));
			setMessage.put(extra);
			setMessage.put(changeByte(CoDel_TargetResidenceTime.getBytes()));
			System.out.println("Gate_OpenOffset" + byteArrayToHexaString((changeByte(Gate_OpenOffset.getBytes()))));
			System.out.println("Gate_OpenDuration" + byteArrayToHexaString(changeByte(Gate_OpenDuration.getBytes())));
			System.out.println("Gate_Enable" + byteArrayToHexaString(changeByte(Gate_Enable.getBytes())));
			System.out.println("CoDelAQM_Enable" + byteArrayToHexaString(changeByte(CoDelAQM_Enable.getBytes())));
			System.out.println("CoDel_Interval" + byteArrayToHexaString(changeByte(CoDel_Interval.getBytes())));
			System.out.println("CoDel_TargetResidenceTime"
					+ byteArrayToHexaString(changeByte(CoDel_TargetResidenceTime.getBytes())));

			gui.appendMsg("OFP_Version : 1.3\n");
			gui.appendMsg("OFP_Type : OFP_Set_CoDel_Config(" + Type + ")\n");
			gui.appendMsg("Length : " + Length + "\n");
			gui.appendMsg("Xid : " + xid + "\n");
			System.out.println();
			try {
				output.write(setMessage.array());
				output.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void readMessage() throws IOException {
		int length = input.available();
		byte[] bytes = new byte[length];

		// �޼����� ���ŵ� ���
		int readByteCount = input.read(bytes);
		if (readByteCount > 0) {
			System.out.println("Ŭ���̾�Ʈ�� ���� ������ ����");
			System.out.println("bytes�� ��� : " + new String(byteArrayToHexaString(bytes)));
			gui.appendMsg("----switch to controller----\n");

			if (bytes[1] == 0) {
				gui.appendMsg("Ŭ���̾�Ʈ�� ���� OFTP_Hello_Reply ����\n");
				gui.appendMsg("----controller to switch----\n");
				gui.appendMsg("OFP_Version : 1.3\n");
				gui.appendMsg("OFP_Type : OFTP_HELLO(" + bytes[1] + ")\n");
				gui.appendMsg("Length : " + bytes[2] + "" + bytes[3] + "\n");
				gui.appendMsg("Xid : " + bytes[4] + "" + bytes[5] + "" + bytes[6] + "" + bytes[7] + "" + "\n");
			} else if (bytes[1] == 06) {
				gui.appendMsg("Ŭ���̾�Ʈ�� ���� OFTP_Feature_Reply ����\n");
				gui.appendMsg("----controller to switch----\n");
				gui.appendMsg("OFP_Version : 1.3\n");
				gui.appendMsg("OFP_Type : OFTP_Feature_Reply(" + bytes[1] + ")\n");
				gui.appendMsg("Length : " + bytes[2] + "" + bytes[3] + "\n");
				gui.appendMsg("Xid : " + bytes[4] + "" + bytes[5] + "" + bytes[6] + "" + bytes[7] + "" + "\n");
			} else if (bytes[1] == 10) {
				gui.appendMsg("Ŭ���̾�Ʈ�� ���� OFTP_Packet_In ����\n");
				gui.appendMsg("----controller to switch----\n");
				gui.appendMsg("OFP_Version : 1.3\n");
				gui.appendMsg("OFP_Type : OFTP_Feature_Reply(" + bytes[1] + ")\n");
				gui.appendMsg("Length : " + bytes[2] + "" + bytes[3] + "\n");
				gui.appendMsg("Xid : " + bytes[4] + "" + bytes[5] + "" + bytes[6] + "" + bytes[7] + "" + "\n");
			} else if (bytes[1] == 23) {
				/*
				 * oftp_header
				 */
				gui.appendMsg("Ŭ���̾�Ʈ�� ���� OFTP_Get_CoDel_Config_Reply ����\n");
				gui.appendMsg("----controller to switch----\n");
				gui.appendMsg("OFP_Version : 1.3\n");
				gui.appendMsg("OFP_Type : OFTP_Get_CoDel_Config_Reply(" + bytes[1] + ")\n");
				gui.appendMsg("Length : " + bytes[2] + "" + bytes[3] + "\n");
				gui.appendMsg("Xid : " + bytes[4] + "" + bytes[5] + "" + bytes[6] + "" + bytes[7] + "" + "\n");

				/*
				 * 
				 */
				byte[] offset = { bytes[8], bytes[9], bytes[10], bytes[11] };
				byte[] openduration = { bytes[12], bytes[13], bytes[14], bytes[15] };
				byte[] gate_enable = { bytes[16], bytes[17], bytes[18], bytes[19] };
				byte[] codel_enable = { bytes[20], bytes[21], bytes[22], bytes[23] };
				byte[] interval = { bytes[24], bytes[25], bytes[26], bytes[27] };
				byte[] targetResidencetime = { bytes[28], bytes[29], bytes[30], bytes[31] };
				gui.appendMsg("Ŭ���̾�Ʈ�� ���� OFTP_STATS_Reply ����\n");
				gui.appendMsg("Gate_Offset(usec) : " + ByteBuffer.wrap(offset).getInt() + "\n");
				gui.appendMsg("Gate_OpenDuration(usec) : " + ByteBuffer.wrap(openduration).getInt() + "\n");
				gui.appendMsg("Gate_Enable/Disable : " + ByteBuffer.wrap(gate_enable).getInt() + "\n");
				gui.appendMsg("CoDelAQM Enable : " + ByteBuffer.wrap(codel_enable).getInt() + "\n");
				gui.appendMsg("CoDel_Interval(msec) : " + ByteBuffer.wrap(interval).getInt() + "\n");
				gui.appendMsg(
						"CoDel_TargetResidenceTime(msec) : " + ByteBuffer.wrap(targetResidencetime).getInt() + "\n");
				// System.out.println("Gate_OpenOffset" +
				// byteArrayToHexaString((changeByte(Gate_OpenOffset.getBytes()))));
				// System.out.println("Gate_OpenDuration" +
				// byteArrayToHexaString(changeByte(Gate_OpenDuration.getBytes())));
				// System.out.println("Gate_Enable" +
				// byteArrayToHexaString(changeByte(Gate_Enable.getBytes())));
				// System.out.println("CoDelAQM_Enable" +
				// byteArrayToHexaString(changeByte(CoDelAQM_Enable.getBytes())));
				// System.out.println("CoDel_Interval" +
				// byteArrayToHexaString(changeByte(CoDel_Interval.getBytes())));
				// System.out.println("CoDel_TargetResidenceTime" +
				// byteArrayToHexaString(changeByte(CoDel_TargetResidenceTime.getBytes())));

			}

			/*
			 * ByteBuffer sendByteBuffer = ByteBuffer.allocate(length + 1);
			 * sendByteBuffer.put(bytes); sendByteBuffer.flip(); // flip�� �������� 0���� �����ϰ� ����Ʈ��
			 * ���� ������ ������ ��ġ�� �����Ŵ
			 * 
			 * String data = new String(byteArrayToHexaString(bytes)); //
			 * System.out.println(data);
			 * 
			 */
		} else {
			readByteCount = 0;
		}
	}

	public static byte[] changeByteOrder(byte[] value, int Order) {
		int idx = value.length;
		byte[] Temp = new byte[idx];

		if (Order == 1) {
			Temp = value;
		} else if (Order == 2) {
			for (int i = 0; i < idx; i++) {
				Temp[i] = value[idx - (i + 1)];
			}
		}

		return Temp;
	}

	public static String byteArrayToHexaString(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		for (byte data : bytes) {
			builder.append(String.format("%02X ", data));
		}
		return builder.toString();
	}

	public static byte[] changeByte(byte[] bytes) {
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] -= 48;
		}
		return bytes;
	}

	public static String byteArrayToHexaString(byte bytes) {
		StringBuilder builder = new StringBuilder();

		builder.append(String.format("%02X ", bytes));

		return builder.toString();
	}
}
