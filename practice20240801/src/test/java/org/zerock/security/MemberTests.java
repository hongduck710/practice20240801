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
				
				pstmt.setString(2, pwencoder.encode("pw" + 1));
				
				if(i < 80) {
					pstmt.setString(1, "user" + i);
					pstmt.setString(3, "슈퍼컴나라 주민" + i);
				} else if (i < 90) {
					pstmt.setString(1, "manager" +  i);
					pstmt.setString(3, "매니저" + i);
				} else {
					pstmt.setString(1, "admin" + i);
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
	
	@Test
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
					pstmt.setString(2, "ROLE_MAMBER");
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
