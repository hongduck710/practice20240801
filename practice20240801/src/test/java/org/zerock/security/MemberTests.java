package org.zerock.security;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/security-context.xml"
})
@Log4j
public class MemberTests {
	@Setter(onMethod_ = @Autowired)
	private PasswordEncoder pwencoder;
	
	@Setter(onMethod_ = @Autowired)
	private DataSource ds;
	
	@Test
	public void testInsertMember() {
		String sql = "insert into tbl_member(userid, userpw, username) values (?, ?, ?)";
		for(int i = 0; i < 100; i++) {
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(sql);
				
				// pstmt.setString(2, pwencoder.encode("pw" + i));
				
				if(i < 80) {
					pstmt.setString(1, "user" + i);
					pstmt.setString(2, pwencoder.encode("user" + i)); /* 20240923 아이디 패스워드 통일시키기 위해 임의로 코드 작성 */ 
					pstmt.setString(3, "슈퍼컴나라 주민" + i);
				} else if (i < 90) {
					pstmt.setString(1, "manager" +  i);
					pstmt.setString(2, pwencoder.encode("manager" + i)); /* 20240923 아이디 패스워드 통일시키기 위해 임의로 코드 작성 */
					pstmt.setString(3, "매니저" + i);
				} else {
					pstmt.setString(1, "admin" + i);
					pstmt.setString(2, pwencoder.encode("admin" + i)); /* 20240923 아이디 패스워드 통일시키기 위해 임의로 코드 작성 */
					pstmt.setString(3, "관리자" + i);
				}
				pstmt.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
			} finally {
				if(pstmt != null) { try{pstmt.close();} catch(Exception e) {} }
				if(con != null) { try{con.close();} catch(Exception e) {} }
			}//try catch finally 닫음
		} // for구문 닫음
	} // testInsertMember 닫음
	
	@Test /* 테스트 실행시 첫 번째 실행에서는tbl_member 테이블에는 데이터가 들어가고 tbl_member_auth 테이블에는 데이터가 들어가지 않는 현상 발생.  testInsertMember 메소드 주석처리 하고 2 번째 실행시 tbl_member_auth 테이블에 데이터가 들어가는 것을 확인 */
	public void testInsertAuth() { 
		String sql = "insert into tbl_member_auth (userid, auth) values (?, ?)";
		
		for(int i = 0; i < 100; i++) {
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(sql);
				
				if(i < 80) {
					pstmt.setString(1, "user" + i);
					pstmt.setString(2, "ROLE_USER");
				} else if(i < 90) {
					pstmt.setString(1, "manager" + i);
					pstmt.setString(2, "ROLE_MEMBER");
				} else {
					pstmt.setString(1, "admin" + i);
					pstmt.setString(2, "ROLE_ADMIN");
				} // if else 닫음
				pstmt.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
			} finally {
				if(pstmt != null) { try { pstmt.close(); } catch(Exception e) {} }
				if(con != null) { try { con.close(); } catch(Exception e) {} }
			} // try catch finally 닫음		
		}// for구문 닫음
	} // testInsertAuth 닫음
	
}
