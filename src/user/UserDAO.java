package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

	private Connection conn; // db�� �����ϰ� ���ִ� ��ü
	private PreparedStatement pstmt;
	private ResultSet rs; // ������ ���� �� �ִ� ��ü
	
	// ������ 
	public UserDAO() {
		// ���� ó��
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
	
	public int login(String userID, String userPassword) {
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?"; // ��� ����� ��ɾ�
		try {
			pstmt = conn.prepareStatement(SQL); // sql������ �����ͺ��̽��� �����ϴ� ������ �ν��Ͻ�
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery(); // ������ ��� rs��ü�� �־���
			if(rs.next()) { // ����� �����Ѵٸ�
				if(rs.getString(1).equals(userPassword)) {
					return 1; // �α��� ����
				}
				else 
					return 0; // �α��� ����
			}
			return -1; // ���̵� ����
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -2; // �����ͺ��̽� ����
	}
}
