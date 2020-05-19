package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BbsDAO {
	private Connection conn; // db에 접근하게 해주는 객체
	private ResultSet rs; // 정보를 담을 수 있는 객체
	
	// 생성자 
	public BbsDAO() {
		// try - catch문 예외 처리
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
	
	// BbsDAO 클래스는 여러개의 함수들이 사용되기 때문에
	// 각각의 함수들이 데이터베이스 접근에 있어서 마찰이 일어나지 않도록
	// 	private PreparedStatement pstmt; 를 각각 만들어서 진행!
	
	// 날짜 생성
	public String getDate() {
		String SQL = "SELECT NOW()";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL); //sql문을 실행준비단계로
			rs = pstmt.executeQuery(); // 실행시 나온 결과 rs에 담기
			if(rs.next()) {
				return rs.getString(1); // 현재의 날짜 그대로 반환
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ""; // 데이터베이스 오류 (빈 문자열 반환)
	}
	
	// 게시글 번호
	public int getNext() {
		String SQL = "SELECT bbsID FROM BBS ORDER BY bbsID DESC";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL); //sql문을 실행준비단계로
			rs = pstmt.executeQuery(); // 실행시 나온 결과 rs에 담기
			if(rs.next()) {
				return rs.getInt(1) + 1; // 게시글 번호
			}
			return 1; // 현재가 첫번째 게시물인 경우
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;// 데이터베이스 오류
	}
	
	// 글 작성
	public int write(String bbsTitle, String userID, String bbsContent) {
		String SQL = "INSERT INTO BBS VALUES (?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL); //sql문을 실행준비단계로
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
		return -1;// 데이터베이스 오류
	}
	
	// 글 읽어오기
	public ArrayList<Bbs> getList(int pageNumber){
		String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
		ArrayList<Bbs> list = new ArrayList<Bbs>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL); //sql문을 실행준비단계로
			pstmt.setInt(1, getNext() - (pageNumber - 1)*10);
			rs = pstmt.executeQuery(); // 실행시 나온 결과 rs에 담기
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
	
	public boolean nextPage(int pageNumber) { // 페이징 처리
		String SQL = "SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL); //sql문을 실행준비단계로
			pstmt.setInt(1, getNext() - (pageNumber - 1)*10);
			rs = pstmt.executeQuery(); // 실행시 나온 결과 rs에 담기
			if (rs.next()) { // 다음 값이 존재한다면
				return true; // 다음 페이지로 넘어갈 수 있다
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Bbs getBbs(int bbsID) {
		String SQL = "SELECT * FROM BBS WHERE bbsID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL); //sql문을 실행준비단계로
			pstmt.setInt(1, bbsID);
			rs = pstmt.executeQuery(); // 실행시 나온 결과 rs에 담기
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
			PreparedStatement pstmt = conn.prepareStatement(SQL); //sql문을 실행준비단계로
			pstmt.setString(1, bbsTitle);
			pstmt.setString(2, bbsContent);
			pstmt.setInt(3, bbsID);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;// 데이터베이스 오류
	}
}
