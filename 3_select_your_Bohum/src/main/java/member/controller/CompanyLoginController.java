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
public class CompanyLoginController {

   private final String command="/companyLogin.mem";
   private final String getPage = "companyLoginForm";
   private String gotoPage="redirect:/insert.bh";
   private String gotoPage1 = "loginForm";
   @Autowired
   private MemberDao memberDao;

   @RequestMapping(value=command, method=RequestMethod.GET)
   public String doAction() {

      return getPage;

   }
   //companyLoginForm.jsp���� �α��� Ŭ�� 
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
//�Ϲ� ȸ������ ����� �������� �����ϱ�
	         if(sbean.getPassword().equals(bean.getPassword())) { //��� ��ġ
	        	 if(sbean.getUserState().equals("ȸ��")) {
	                 String cname = sbean.getCname();
	                 System.out.println("cname : "+cname);
	                 System.out.println("cname : "+cname);
	        	 
	            session.setAttribute("loginInfo", sbean); 
	            //sbean���� id�� �ش��ϴ� ����� ������ �ִ�. �װŸ� loginInfo�� ���Ǽ����ϰڴ�.
	            session.setAttribute("loginCompany", cname); 
	            
	            return gotoPage;
	        	 }else {
	                 try {
	                    pw = response.getWriter();
	                 } catch (IOException e) {
	                    e.printStackTrace();
	                 }
	                 pw.println("<script> alert('ȸ�� ���̵� �ƴմϴ�.')</script>");
	                 pw.println("<script> alert('����� �α��� �������� �̵��մϴ�.')</script>");
	                 pw.flush();
	                 return gotoPage1;
	              }
	         }
	         else {//��� ����ġ
	            try {
	               pw = response.getWriter();
	            } catch (IOException e) {
	               e.printStackTrace();
	            }
	            pw.println("<script> alert('����� �߸��Ǿ����ϴ�.')</script>");
	            pw.flush();
	         }
	         return getPage; // �н����� ����ġ�� loginForm
	      }
	
	   }
}