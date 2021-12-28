package com.javaex.ex01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuthorInsert {

	public static void main(String[] args) {

		// insert문

		// 0. import java.sql.*;
		Connection conn = null;		
		PreparedStatement pstmt = null;

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");

			
			// 2. Connection 얻어오기 : 전화선 연결하는 개념
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb"); // (연결문자열, DB_ID, DB_PW).
			System.out.println("접속성공");

			
			// 3. SQL문 준비 / 바인딩 / 실행 ★

			// 문자열 만들기
			String query = ""; // 쿼리문 만들기
			// query = query + "문자열"
			query += " insert into author "; // query = query + "insert into author"; 축약형
			query += " values(seq_author_id.nextval, ?, ? ) "; // 값이 들어갈 자리는 ?로 표시
			System.out.println(query);

			// 문자열을 쿼리문으로 만들기
			pstmt = conn.prepareStatement(query);

			// 바인딩 (데이터를 넣어주는 작업)
			pstmt.setString(1, "김영하"); // 첫번째 물음표에 이문열 입력
			pstmt.setString(2, "알쓸신잡"); // 두번째 물음표에 경북 영양 입력			//자바에서 실행하면 바로 commit됨.

			// 실행
			int count = pstmt.executeUpdate(); // 쿼리문 실행(jdbc를 통해 날리기)

			
			// 4.결과처리 ★
			System.out.println(count + "건이 저장되었습니다."); // 1이면 성공 0이면 실패

		} catch (ClassNotFoundException e) {		
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {		 
			System.out.println("error:" + e);
		} finally {		

			
			// 5. 자원정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {		
					conn.close();
				}
			} catch (SQLException e) {		
				System.out.println("error:" + e);
			}

		}
	}
}
