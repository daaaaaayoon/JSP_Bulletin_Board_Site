package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BbsDAO {
	private Connection conn; // db�� �����ϰ� ���ִ� ��ü
	private ResultSet rs; // ������ ���� �� �ִ� ��ü
	
	// ������ 
	public BbsDAO() {
		// try - catch�� ���� ó��
		try { // mysql�� ������ �ϰ� ���ִ� �κ�
			String dbURL = "jdbc:mysql://localhost:3306/BBS";
			String dbID = "root";
			String dbPassword = "root";
			Class.forName("com.mysql.jdbc.Driver"); // Driver : mysql�� ������ �� �ֵ��� �ϴ� �ϳ��� �Ű�ü ����
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword); // ���ӵ� ������ ���
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// BbsDAO Ŭ������ �������� �Լ����� ���Ǳ� ������
	// ������ �Լ����� �����ͺ��̽� ���ٿ� �־ ������ �Ͼ�� �ʵ���
	// 	private PreparedStatement pstmt; �� ���� ���� ����!
	
	// ��¥ ����
	public String getDate() {
		String SQL = "SELECT NOW()";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL); //sql���� �����غ�ܰ��
			rs = pstmt.executeQuery(); // ����� ���� ��� rs�� ���
			if(rs.next()) {
				return rs.getString(1); // ������ ��¥ �״�� ��ȯ
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ""; // �����ͺ��̽� ���� (�� ���ڿ� ��ȯ)
	}
	
	// �Խñ� ��ȣ
	public int getNext() {
		String SQL = "SELECT bbsID FROM BBS ORDER BY bbsID DESC";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL); //sql���� �����غ�ܰ��
			rs = pstmt.executeQuery(); // ����� ���� ��� rs�� ���
			if(rs.next()) {
				return rs.getInt(1) + 1; // �Խñ� ��ȣ
			}
			return 1; // ���簡 ù��° �Խù��� ���
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;// �����ͺ��̽� ����
	}
	
	// �� �ۼ�
	public int write(String bbsTitle, String userID, String bbsContent) {
		String SQL = "INSERT INTO BBS VALUES (?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL); //sql���� �����غ�ܰ��
			pstmt.setInt(1, getNext());
			pstmt.setString(2, bbsTitle);
			pstmt.setString(3, userID);
			pstmt.setString(4, getDate());
			pstmt.setString(5, bbsContent);
			pstmt.setInt(6, 1);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;// �����ͺ��̽� ����
	}
}
