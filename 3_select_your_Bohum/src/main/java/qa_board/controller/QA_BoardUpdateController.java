package qa_board.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import company.model.CompanyBean;
import company.model.CompanyDao;
import qa_board.model.QA_BoardBean;
import qa_board.model.QA_BoardDao;
import utility.Responsing;

@Controller
public class QA_BoardUpdateController {
   private final String command = "/update.qa";
   private String getPage = "updateForm";

   @Autowired
   QA_BoardDao qA_BoardDao;

   @Autowired
   CompanyDao companyDao;

   @Autowired
   ServletContext servletContext;

   @RequestMapping(value=command,method = RequestMethod.GET)
   public String doAction(
         @RequestParam("no")String no,
         @RequestParam("pageNumber")String pageNumber,
         HttpServletRequest request) {

      QA_BoardBean qA_boardBean = qA_BoardDao.getArticle(no);
      qA_boardBean.setFilename(qA_boardBean.getImage());
      //System.out.println("qA_boardBean.getFilename():"+qA_boardBean.getFilename());

      List<CompanyBean> companyList = new ArrayList<CompanyBean>();
      companyList = companyDao.getCompanyList();
      //System.out.println("companyList.size():"+companyList.size());

      System.out.println("no : "+qA_boardBean.getNo());
      request.setAttribute("QA_BoardBean", qA_boardBean);
      request.setAttribute("companyList", companyList);
      request.setAttribute("pageNumber", pageNumber);

      return getPage;
   }


   @RequestMapping(value=command,method = RequestMethod.POST)
   public String doAction(
         @Valid QA_BoardBean qA_BoardBean,
         BindingResult result,
         @RequestParam("pageNumber") int pageNumber,
         HttpServletRequest request,
         HttpServletResponse response) {

      System.out.println("qA_BoardBean.getNo():"+qA_BoardBean.getNo());

      Responsing responsing = new Responsing(response);
      if(result.hasErrors()) {
         //System.out.println("result.getErrorCount():"+result.getErrorCount());
         List<CompanyBean> companyList = new ArrayList<CompanyBean>();
         companyList = companyDao.getCompanyList();
         request.setAttribute("pageNumber", pageNumber);
         request.setAttribute("companyList", companyList);
         return getPage;
      }

      QA_BoardBean article2 = qA_BoardDao.getArticle(String.valueOf(qA_BoardBean.getNo()));
      if(!article2.getPassword().equals(qA_BoardBean.getPassword())) { //��й�ȣ ����ġ
         responsing.useAlert("��й�ȣ�� ��ġ���� �ʽ��ϴ�");
         request.setAttribute("pageNumber", pageNumber);
         responsing.useHistory(-1);
      }
      else { //��й�ȣ ��ġ

         String uploadPath = servletContext.getRealPath("/resources/qa_board/QA_BoardFiles"); //���ε���
         //System.out.println("uploadPath"+uploadPath);
         String filename=request.getParameter("filename"); //���� ����
         if(qA_BoardBean.getImage().equals("") || qA_BoardBean.getImage()==null) {
            qA_BoardBean.setImage(filename); //������ ����÷�θ� �� ���� �� ���������� ����ϵ��� ����
         }

         int cnt = qA_BoardDao.updateBoard(qA_BoardBean);

         MultipartFile mf=qA_BoardBean.getUpload();
         if(cnt>0) {
            if(!qA_BoardBean.getImage().equals(filename)) {
               File file=new File(uploadPath+"\\"+qA_BoardBean.getImage());
               File file2=new File(uploadPath+"\\"+filename);
               file2.delete(); //���� �̹��� ����
               try { 
                  mf.transferTo(file);
               } catch (IllegalStateException e) {
                  e.printStackTrace();
               } catch (IOException e) {
                  e.printStackTrace();
               }
            }
            responsing.useAlert("�Խù��� �����Ͽ����ϴ�");
            request.setAttribute("pageNumber",pageNumber);
            responsing.useRedirect("list.qa");
         }//�ٱ� if
         else {
            responsing.useAlert("�Խù� ������ �����Ͽ����ϴ�");
            request.setAttribute("pageNumber",pageNumber);
            responsing.useHistory(-1);
         }
      }//�ٱ� else(��й�ȣ ��ġ)
      return null;
   }
}