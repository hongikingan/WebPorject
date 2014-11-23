package mysns.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import mysns.sns.Message;
import mysns.sns.MessageSet;
import mysns.sns.Reply;
import mysns.util.DBManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemberDAO {
	
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	Logger logger = LoggerFactory.getLogger(MemberDAO.class);
	

	public boolean addMember(Member member) {
		conn = DBManager.getConnection();
		String sql = "insert into s_member(name, uid, passwd, email,date) values(?,?,?,?,now())";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getUid());
			pstmt.setString(3, member.getPasswd());
			pstmt.setString(4, member.getEmail());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("Error Code : {}",e.getErrorCode());
			return false;
		}
		finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	
	public boolean login(String uid, String passwd) {
		conn = DBManager.getConnection();
		String sql = "select uid, passwd from s_member where uid = ?";
		boolean result = false;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uid);
			rs = pstmt.executeQuery();
			rs.next();
			if(rs.getString("passwd").equals(passwd))
				result=true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public boolean newMsg(Message msg){
		conn = DBManager.getConnection();
		
		String sql = "insert into s_message(uid, msg, date) values(?,?,now())";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, msg.getUid());
			pstmt.setString(2, msg.getMsg());
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
			logger.info("Error code : {}", e.getErrorCode());
			return false;
		}finally{
			try{pstmt.close();}catch(SQLException e){}
			try{conn.close();}catch(SQLException e){}
		}
		return true;
	}
	
	public boolean delMsg(int mid){
		conn = DBManager.getConnection();
		
		String sql = "delete from s_message where mid = ?";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mid);
			pstmt.executeUpdate();			
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}finally{
			try{pstmt.close();}catch(SQLException e){}
			try{conn.close();}catch(SQLException e){}
		}
		return true;
	}
	
	public boolean newReply(Reply reply){
		conn = DBManager.getConnection();
		
		String sql = "insert into s_reply(mid,uid,rsmg,date) values(?,?,?,now())";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reply.getMid());
			pstmt.setString(2, reply.getUid());
			pstmt.setString(3, reply.getRmsg());
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}finally{
			try{pstmt.close();}catch(SQLException e){}
			try{conn.close();}catch(SQLException e){}
		}
		return true;
	}
	
	public boolean delReply(int mid){
		conn = DBManager.getConnection();
		
		String sql = "delete from s_reply where mid = ?";
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mid);
			pstmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}finally{
			try{pstmt.close();}catch(SQLException e){}
			try{conn.close();}catch(SQLException e){}
		}
		return true;
	}
	
	public ArrayList<MessageSet> getAll(int cnt, String suid){
		conn = DBManager.getConnection();
		String sql;
		
		ArrayList<MessageSet> datas = new ArrayList<MessageSet>();
		
		try{
			if((suid == null)||(suid.equals(""))){
				sql = "select * from s_message order by date desc limit 0,?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, suid);
				pstmt.setInt(1, cnt);
			}
			else{
				sql = "select * from s_message where uid=? order by date desc limit 0,?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, suid);
				pstmt.setInt(2, cnt);
			}
			
			rs = pstmt.executeQuery();
			while(rs.next()){
				MessageSet ms = new MessageSet();
				Message m = new Message();
				ArrayList<Reply> rlist = new ArrayList<Reply>();
				
				m.setMid(rs.getInt("mid"));
				m.setMsg(rs.getString("msg"));
				m.setDate(rs.getDate("Date")+" / "+rs.getTime("date"));
				m.setFavcount(rs.getInt("favcount"));
				m.setUid(rs.getString("uid"));
				m.setReplycount(rs.getInt("replycount"));
				
				String rsql = "select * from s_reply where mid=? order by date desc";
				pstmt = conn.prepareStatement(rsql);
				pstmt.setInt(1, rs.getInt("mid"));
				ResultSet rrs = pstmt.executeQuery();
				
				while(rrs.next()){
					Reply r = new Reply();
					r.setRid(rrs.getInt("rid"));
					r.setUid(rrs.getString("uid"));
					r.setRmsg(rrs.getString("rmsg"));
					r.setDate(rrs.getDate("date")+"/"+rrs.getTime("date"));
					rlist.add(r);
				}
				
				rrs.last();
				m.setReplycount(rrs.getRow());
				
				ms.setMessage(m);
				ms.setRlist(rlist);
				datas.add(ms);
				rrs.close();
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{rs.close();}catch(SQLException e){}
			try{pstmt.close();}catch(SQLException e){}
			try{conn.close();}catch(SQLException e){}
		}
		return datas;
	}
	
	public static Connection getConnection(){
		Connection conn;
		try{
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/mysql");
			conn = ds.getConnection();
		}catch(Exception e){
			System.out.println(e);
			e.printStackTrace();
			return null;
		}
		return conn;
	}
	
	public ArrayList<String> getNewMembers() {
		ArrayList<String> nmembers = new ArrayList<String>();
		conn = DBManager.getConnection();
		
		String sql = "select * from s_member order by date desc limit 0,7";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				nmembers.add(rs.getString("uid"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("Error Code : {}",e.getErrorCode());
		}
		finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return nmembers;
	}
	
	
}