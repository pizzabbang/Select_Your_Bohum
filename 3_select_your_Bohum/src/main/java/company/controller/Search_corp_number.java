package company.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import company.model.CompanyDao;

@Controller
public class Search_corp_number {
   private final String command="/search_corp_num.cp"; //ȸ�����Խ� ����ڵ�Ϲ�ȣ ��ȸ Ŭ��
   private String getPage="companyRegisterForm";
   private String gotoPage= "redirect:/companyRegister.mem"; //���߿� �����ϱ�
   
   @Autowired
   private CompanyDao companyDao;

   @RequestMapping(value=command)
   public void doAction(@RequestParam (value="cregi_num") int cregi_num, 
         HttpServletResponse response,HttpSession session) {
      
      String cname = companyDao.getCompanyList(cregi_num);
      System.out.println("cregi_num:" + cregi_num);
      System.out.println("cname:" + cname);
      
      try {
         response.setContentType("text/html; charset=UTF-8");
         PrintWriter out = response.getWriter();
         out = response.getWriter();
         out.println(cname);
         out.flush();
      } catch (IOException e) {
         e.printStackTrace();
         System.out.println("ȸ��� �������� ����");
      }
      
   }
   
   
}