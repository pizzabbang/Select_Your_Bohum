package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import member.model.MemberBean;
import member.model.MemberDao;
import qa_board.model.QA_BoardBean;
import utility.Responsing;

@Controller
public class MemberDeleteController {
	private final String command = "/memberDeleteForm.mem";
	private String getPage = "/memberDeleteForm";
	private String gotoPage = "/loginForm.mem";
	
	@Autowired
	private MemberDao memberDao;
	
	@RequestMapping(value=command,method = RequestMethod.GET)
	public String doAction(
			HttpSession session,
			HttpServletRequest request) {
		
		MemberBean loginInfo = (MemberBean)session.getAttribute("loginInfo");
		
		if(loginInfo==null) {
			session.setAttribute("destination", "redirect:/myPage.mem");
			return gotoPage; 
		}
		
		MemberBean bean = memberDao.getMember(loginInfo.getId());
		System.out.println("bean3"+bean);
		request.setAttribute("MemberBean", bean);
		
//		int cnt = memberDao.deleteMember(id);
//		System.out.println("delete cnt:" + cnt);
//		System.out.println("command:" + command);
		
		return getPage;
	}
	

	@RequestMapping(value=command,method=RequestMethod.POST)
	public String doAction(
			MemberBean bean, 
			HttpSession session,
			BindingResult result,
			HttpServletResponse response) {
		
		System.out.println("delete1");
		System.out.println("pw1:"+bean.getPassword());
		
		if(result.hasErrors()) {
			System.out.println("delete2");
			return getPage;
		}
		MemberBean mbean=memberDao.getMember(bean.getId());
		System.out.println("pw2:"+bean.getPassword());
		System.out.println("mpw2:"+mbean.getPassword());
		
		
		Responsing responsing = new Responsing(response);
		
		
		if(mbean.getPassword().equals(bean.getPassword())) { 
			System.out.println("id2:"+bean.getId());
			int cnt = memberDao.deleteMember(bean.getId());
			if(cnt>0) {
				responsing.useAlert("Ż�� ����");
				responsing.useRedirect("logout.jsp");
			}else {
				responsing.useAlert("Ż�� ����");
				responsing.useHistory(-1);
			}
		}
		else { 
			responsing.useAlert("��й�ȣ�� ��ġ���� �ʽ��ϴ�");
			responsing.useHistory(-1);
		}
		
		return gotoPage;
		
	}
}


