package com.javaex.ex01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorSelect {

	public static void main(String[] args) {

		List<AuthorVo> authorList = new ArrayList<AuthorVo>();
		
		// 작가 데이터 가져오기

		// 0. import java.sql.*;
				Connection conn = null;			//connection conn(connection객체)를 전역변수로 선언.
				PreparedStatement pstmt = null;
				ResultSet rs = null;
						
				try {
					// 1. JDBC 드라이버 (Oracle) 로딩
					Class.forName("oracle.jdbc.driver.OracleDriver");		//데이터베이스와 연결할 드라이버 클래스를 찾아서 로드.

					
					// 2. Connection 얻어오기 : 전화선 연결하는 개념
					String url = "jdbc:oracle:thin:@localhost:1521:xe";
					conn = DriverManager.getConnection(url, "webdb", "webdb"); 		//(연결문자열, DB_ID, DB_PW). 선언한 conn에 생성된 connection 객체 대입
					System.out.println("접속성공");

					
					// 3. SQL문 준비 / 바인딩 / 실행 ★

					// 문자열 만들기
					String query = "";		// 쿼리문 만들기
					query += " select author_id id, ";		//만약 컬럼명이 따로 설정되어 있으면 그 컬럼명도 같이 써줘야 함. 
					query += " 		  author_name, ";		// query = query + "author_name"; 축약형
					query += " 		  author_desc ";
					query += " from author ";
					System.out.println(query);

					// 문자열을 쿼리문으로 만들기
					pstmt = conn.prepareStatement(query);

					// 바인딩 (데이터를 넣어주는 작업) -> 생략. ?표 없음.
					
					// 실행
					rs = pstmt.executeQuery();		//rs는 큰 덩어리라고 생각하기.
					
					// 4.결과처리 ★
					
					
					while(rs.next()) {				//표 끝에 도달해 rs.next가 false가 되면 끝남.
					
						/* 위치가 바뀌어도 상관 없음. (컬럼명을 따라가니까...)
						int authorId = rs.getInt("id");		//rs.getInt(author_id)에 id라는 컬럼명이 설정되어 있어 id로 표시함.
						String authorName = rs.getString("author_name");//author_name 컬럼에서 숫자값 꺼내 authorId에 담기
						String authorDesc = rs.getString("author_desc");
						 */
						
						//위치가 바뀌지 않아야 함.
						int authorId = rs.getInt(1);
						String authorName = rs.getString(2);
						String authorDesc = rs.getString(3);
					
						AuthorVo vo = new AuthorVo(authorId, authorName, authorDesc);
						authorList.add(vo);		//authorList에 vo 넣기.
						
						
						
						// System.out.println(authorId + "\t " + authorName + "\t " + authorDesc);
					}
					
					//출력
					for(int i=0; i<authorList.size(); i++) {
						AuthorVo authorVo = authorList.get(i);
						System.out.println(authorVo.getAuthorId() + ", " + authorVo.getAuthorName() + ", " + authorVo.getAuthorDesc());
					}
					
					// 첫번째 작가 이름만 다시 출력
					AuthorVo authorVo = authorList.get(0);
					System.out.println(authorVo.getAuthorName());
					
					

				} catch (ClassNotFoundException e) {			//위 Class.forName에서 에러가 있다면 드라이버 로드에 실패한 것.
					System.out.println("error: 드라이버 로딩 실패 - " + e);
				} catch (SQLException e) {			//위 conn=에서 에러가 발생하면 connection 객체 생성에 실패한 것. 
					System.out.println("error:" + e);
				} finally {				//드라이버 로드 및 객체생성이 정상적으로 완료되면 finally를 이용해 실행되게 함.

					
					// 5. 자원정리
					try {
						if (pstmt != null) {
							pstmt.close();
						}
						if (conn != null) {			//conn 객체가 정상적으로 생성되면 conn은 더이상 null 값을 갖지 않으므로 connection객체를 닫아줌.
							conn.close();
						}
					} catch (SQLException e) {		//어떤 예외로 인해 conn.close()실행에 문제가 있을 경우 throw하는 용도.
						System.out.println("error:" + e);
					}

				}

		
		
	}

}
