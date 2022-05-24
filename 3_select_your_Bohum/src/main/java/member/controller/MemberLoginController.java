package member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import member.model.MemberBean;
import member.model.MemberDao;

@Controller
public class MemberLoginController {
	private final String command = "/loginForm.mem";
	private String getPage = "loginForm";
	private String gotoPage1 = "redirect:/insuInfo.lm";
	private String gotoPage2 = "redirect:/main.ha";
	private String gotoPage3 = "companyLoginForm";
	
	@Autowired
	private MemberDao memberDao;
	
	@RequestMapping(value=command,method = RequestMethod.GET)
	public String doAction() {
		
		return getPage;
	}
	
	//loginForm.jsp���� �α��ν�
	@RequestMapping(value=command,method=RequestMethod.POST)
	public String doAction(MemberBean bean, HttpServletResponse response, HttpSession session) {

		response.setContentType("text/html; charset=UTF-8");

		//id�� pw�Ѿ���°� bean ���� ������
		System.out.println(bean.getId());
		System.out.println(bean.getPassword());

		MemberBean sbean = memberDao.searchId(bean.getId()); 
		System.out.println("sbean:" + sbean); //null or �ּ�
		PrintWriter pw = null;
		if(sbean == null) { //ã�� id ����
			//�ش� id�������� �ʽ��ϴ�.
			try {
				pw = response.getWriter();
			} catch (IOException e) {
				e.printStackTrace(); 
			}
			pw.println("<script> alert('�ش� ���̵� �������� �ʽ��ϴ�')</script>");
			pw.flush();
			
			return getPage;

		}//if
		else { //id�� �����ϸ� 
	         if(!sbean.getUserState().equals("ȸ��")) { //ȸ�簡 �ƴ϶�� 
	            if(sbean.getPassword().equals(bean.getPassword())) { //��� ��ġ
	               session.setAttribute("loginInfo", sbean); 
	               //sbean���� id�� �ش��ϴ� ����� ������ �ִ�. �װŸ� loginInfo�� ���Ǽ����ϰڴ�.
	            
	               //String destination = (String)session.getAttribute("destination"); 
	               if(sbean.getUserState().equals("�Ϲ�")) {
	                  return gotoPage1; //redirect:/main.mem
	            
	               }
	               else if(sbean.getUserState().equals("������")) { // ���� �ʿ�
	                  return gotoPage2; //redirect:/main.ha
	               }
	            }//if
	            else {//��� ����ġ
	               try {
	                  pw = response.getWriter();
	               } catch (IOException e) {
	                  e.printStackTrace();
	               }
	               pw.println("<script> alert('����� �߸��Ǿ����ϴ�.')</script>");
	               pw.flush();
	               return getPage; //loginForm
	            }
	         }else {
	            try {
	               pw = response.getWriter();
	            } catch (IOException e) {
	               e.printStackTrace();
	            }
	            pw.println("<script> alert('�ش� ���̵�� ����� �α��� ���������� �����մϴ�.')</script>");
	            pw.println("<script> alert('����� �α��� �������� �̵��մϴ�.')</script>");
	            pw.flush();
	            return gotoPage3;  //companyLoginForm ����� �α��� �������� �̵�
	         }
	      }
	      return getPage; //loginForm

	   }
}
