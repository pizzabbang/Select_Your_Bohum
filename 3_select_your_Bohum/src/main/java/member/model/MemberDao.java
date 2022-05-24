package member.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.HtmlEmail;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import utility.Paging;

@Component("MyMember")
public class MemberDao {
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	private String namespace = "member.model.Member";

	
	public int insertMember(MemberBean member) {
		int cnt=-1;
		cnt=sqlSessionTemplate.insert(namespace+".InsertMember",member);
		System.out.println("insertDao cnt:" + cnt);
		return cnt;
	}
	public int insertCMember(MemberBean member) {
		int cnt=-1;
		cnt=sqlSessionTemplate.insert(namespace+".InsertCMember",member);
		System.out.println("insertDao cnt:" + cnt);
		return cnt;
	}
	//�α��ν� ���Ե� id���� Ȯ�� 
	public MemberBean searchId(String id) {
		MemberBean sbean = sqlSessionTemplate.selectOne(namespace+".SearchId", id);
		return sbean;
	}
	
	//�̸�,�ֹι�ȣ�� idã��
	public String getIdByNameRrn(MemberBean member) {
		String id = sqlSessionTemplate.selectOne(namespace+".GetIdByName", member);
		System.out.println("ID:"+id);
		return id;
	}
	//pw ã��
	public String getPwByIdNameRrn(MemberBean member,HttpServletResponse response) throws IOException {
		String email1="", email2="", pw="", msg="";
		
		List<MemberBean> mem = sqlSessionTemplate.selectList(namespace+".GetPwById", member);
		for(MemberBean m : mem) {
			email1 = m.getEmail1();
			email2 = m.getEmail2();
		}
		member.setEmail1(email1);
		member.setEmail2(email2);
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		// ���Ե� ���̵� ������
		if(mem.size() == 0) {
			out.println("<script>alert('ȸ�� ������ ã�� �� ����.(�̵�� ȸ��)'); location.href='findpw.mem';</script>");
			out.close();
		}
		else { //���Ե� ȸ���̸� �ӽ� ����� �߱�
			for(int i = 0; i<10; i++) {
				pw += (char)((Math.random() * 48)+58);
			}
		member.setPassword(pw);
		updatePw(member);
		sendEmail(member, "findpw");

		out.print("<script>alert('���̹� ���Ϸ� �ӽ� ��й�ȣ�� �߼��Ͽ����ϴ�.'); location.href='loginForm.mem';</script>");
		out.flush();
		}
		return pw;
	}
	
	//��й�ȣ ã�� �̸��� �߼�
	public void sendEmail(MemberBean bean, String div) {
		String charSet = "utf-8";
		String hostSMTP = "smtp.naver.com"; //gmail �̿�� smtp.gmail.com
		String hostSMTPid = "ay98923@naver.com";
		String hostSMTPpwd = "Star1220^^";
		
		// ������ ��� EMail, ����, ����
		String fromEmail = "ay98923@naver.com";
		String fromName = "select your ���� ������";
		String subject = "", msg = "";
		
		if(div.equals("findpw")) {
			subject = "select your ���� �ӽ� ��й�ȣ �Դϴ�.";
			msg += "<div align='center' style='border:1px solid black; font-family:verdana'>";
			msg += "<h3 style='color: blue;'>";
			msg += bean.getName() + "���� �ӽ� ��й�ȣ �Դϴ�. ��й�ȣ�� �����Ͽ� ����ϼ���.</h3>";
			msg += "<p>�ӽ� ��й�ȣ : ";
			msg += bean.getPassword() + "</p></div>";
		}
		
		// �޴� ��� E-Mail �ּ�
		String mail = bean.getEmail1()+"@"+bean.getEmail2();
		System.out.println("mail"+mail);
		try {
			HtmlEmail email = new HtmlEmail();
			email.setDebug(true);
			email.setCharset(charSet);
			email.setSSL(true);
			email.setHostName(hostSMTP);
			email.setSmtpPort(587);

			email.setAuthentication(hostSMTPid, hostSMTPpwd);
			email.setTLS(true);
			email.addTo(mail, charSet);
			email.setFrom(fromEmail, fromName, charSet);
			email.setSubject(subject);
			email.setHtmlMsg(msg);
			email.send();
		} catch (Exception e) {
			System.out.println("���Ϲ߼� ���� : " + e);
		}
	}
	//db�� �ӽú������ ����
	public void updatePw(MemberBean member) {
		sqlSessionTemplate.update(namespace+".updatePw", member);
	}
	
	//ID �ߺ�üũ 
	public int idCheck(String userid) {
		int cnt = -1;
		System.out.println("idCheck:" + userid);
		cnt = sqlSessionTemplate.selectOne(namespace+".IdCheck", userid);
		return cnt;
	}
	
	public int totalCount(Map<String,String> map) {
		int cnt = sqlSessionTemplate.selectOne(namespace+".GetTotalCount",map);
		System.out.println("totalCount:" + cnt);
		return cnt;
	}
	
	public List<MemberBean> memberList(Paging pageInfo,Map<String,String> map) {
		List<MemberBean> list = new ArrayList<MemberBean>();
		RowBounds rowBounds = new RowBounds(pageInfo.getOffset(),pageInfo.getLimit());
		list = sqlSessionTemplate.selectList(namespace+".GetMemberList",map,rowBounds);
		return list;
	}

	public MemberBean getMember(String id) {
		MemberBean bean = sqlSessionTemplate.selectOne(namespace+".GetMember",id);
		
		return bean;
	}

	public int updateMember(MemberBean member) {
		int cnt = -1;
		cnt = sqlSessionTemplate.update(namespace+".UpdateMember", member);
		System.out.println("updateXml:" + cnt);
		return cnt;
	}
	
	public int deleteMember(String id) {
		int cnt = -1;
		cnt = sqlSessionTemplate.delete(namespace + ".DeleteMember", id);
		System.out.println("deletexml cnt:" + cnt);
		return cnt;
	}

	public MemberBean getMemberKakao(MemberBean memberBean) {
		MemberBean member = sqlSessionTemplate.selectOne(namespace+".GetMemberKakao",memberBean);
		return member;
	}
	
	public int getTotalCount(Map<String, String> map) {
		int cnt = 0;
		cnt = sqlSessionTemplate.selectOne(namespace+".GetTotalCount",map);
		return cnt;
	}
	  
	public List<MemberBean> getMemberList(Map<String, String> map, Paging pageInfo) {
		RowBounds rowBounds = new RowBounds(pageInfo.getOffset(), pageInfo.getLimit());
		List<MemberBean> memberList = sqlSessionTemplate.selectList(namespace+".GetMemberList",map,rowBounds);
		return memberList;
	}
	
	public int userStateUpdate(MemberBean memberBean) {
		int cnt = -1;
		cnt = sqlSessionTemplate.update(namespace+".UserStateUpdate",memberBean);
		return cnt;
	}
	
}
