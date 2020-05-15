package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

	private Connection conn; // db에 접근하게 해주는 객체
	private PreparedStatement pstmt;
	private ResultSet rs; // 정보를 담을 수 있는 객체
	
	// 생성자 
	public UserDAO() {
		// 예외 처리
		try { // mysql에 접속을 하게 해주는 부분
			String dbURL = "jdbc:mysql://localhost:3306/BBS";
			String dbID = "root";
			String dbPassword = "root";
			Class.forName("com.mysql.jdbc.Driver"); // Driver : mysql에 접속할 수 있도록 하는 하나의 매개체 역할
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword); // 접속된 정보가 담김
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int login(String userID, String userPassword) {
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?"; // 디비에 명령할 명령어
		try {
			pstmt = conn.prepareStatement(SQL); // sql문장을 데이터베이스에 삽입하는 형태의 인스턴스
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery(); // 실행한 결과 rs객체에 넣어줌
			if(rs.next()) { // 결과가 존재한다면
				if(rs.getString(1).equals(userPassword)) {
					return 1; // 로그인 성공
				}
				else 
					return 0; // 로그인 실패
			}
			return -1; // 아이디가 없음
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -2; // 데이터베이스 오류
	}
}
