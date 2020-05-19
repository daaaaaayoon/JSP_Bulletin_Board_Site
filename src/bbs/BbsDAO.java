package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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
	
	// �� �о����
	public ArrayList<Bbs> getList(int pageNumber){
		String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
		ArrayList<Bbs> list = new ArrayList<Bbs>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL); //sql���� �����غ�ܰ��
			pstmt.setInt(1, getNext() - (pageNumber - 1)*10);
			rs = pstmt.executeQuery(); // ����� ���� ��� rs�� ���
			while(rs.next()) {
				Bbs bbs = new Bbs();
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				list.add(bbs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean nextPage(int pageNumber) { // ����¡ ó��
		String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL); //sql���� �����غ�ܰ��
			pstmt.setInt(1, getNext() - (pageNumber - 1)*10);
			rs = pstmt.executeQuery(); // ����� ���� ��� rs�� ���
			if (rs.next()) { // ���� ���� �����Ѵٸ�
				return true; // ���� �������� �Ѿ �� �ִ�
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Bbs getBbs(int bbsID) {
		String SQL = "SELECT * FROM BBS WHERE bbsID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL); //sql���� �����غ�ܰ��
			pstmt.setInt(1, bbsID);
			rs = pstmt.executeQuery(); // ����� ���� ��� rs�� ���
			if (rs.next()) {
				Bbs bbs = new Bbs();
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				return bbs;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int update(int bbsID, String bbsTitle, String bbsContent) {
		String SQL = "UPDATE BBS SET bbsTitle = ?, bbsContent = ? WHERE bbsID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL); //sql���� �����غ�ܰ��
			pstmt.setString(1, bbsTitle);
			pstmt.setString(2, bbsContent);
			pstmt.setInt(3, bbsID);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;// �����ͺ��̽� ����
	}
}
