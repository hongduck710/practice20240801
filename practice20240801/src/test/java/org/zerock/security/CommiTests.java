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
public class CommiTests {
	@Setter(onMethod_ = @Autowired)
	private PasswordEncoder pwencoder;
	
	@Setter(onMethod_ = @Autowired)
	private DataSource ds;
	
	@Test
	public void testInsertMember() {
		String sql = "insert into tbl_member(userid, userpw, username) values(?, ?, ?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, "commi");
			pstmt.setString(2, pwencoder.encode("commi"));
			pstmt.setString(3, "요정컴미");
			
			pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) { try { pstmt.close(); }catch(Exception e) {} }
			if(con != null) { try {pstmt.close();}catch(Exception e) {} }
		} // try catch finally 닫음
	} // testInsertMember 닫음
	
	@Test
	public void testInsertAuth() { /* 20240923 - 첫 번째 실행해서는 tbl_member에서만 데이터가 들어가고 tbl_member_auth에서는 아무 것도 들어가지 않는 것을 확인. 두 번째 실행해서 tbl_member_auth에도 데이터가 들어가는 것을 확인  */
		String sql = "insert into tbl_member_auth (userid, auth) values(?, ?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, "commi");
			pstmt.setString(2, "ROLE_ADMIN");
			
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt != null) { try {  pstmt.close(); }catch(Exception e) {} }
			if(con != null) { try{ con.close(); }catch(Exception e) {} }
		}
	} // testInsertAuth 닫음
	
}
