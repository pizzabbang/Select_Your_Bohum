package board.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import board.model.BoardBean;
import board.model.BoardDao;
import utility.Responsing;

@Controller
public class BoardUpdateController {

	private final String command = "/update.bd";
	private String getPage = "/b_updateForm";


	@Autowired
	BoardDao boardDao;

	@Autowired
	ServletContext servletContext; 

	@RequestMapping(value=command, method=RequestMethod.GET)
	public String doAction(
			@RequestParam("bnum") int bnum,
			@RequestParam("pageNumber") int pageNumber,
			Model model) {
		BoardBean boardBean=boardDao.getArticle(bnum);
		boardBean.setFilename(boardBean.getBimage());
		model.addAttribute("boardBean",boardBean);
		model.addAttribute("pageNumber",pageNumber);
		return getPage;
	}

	@RequestMapping(value=command, method=RequestMethod.POST)
	public String doAction(
							@Valid BoardBean boardBean, 
							BindingResult result,
							@RequestParam("pageNumber") int pageNumber,
							HttpServletRequest request,
							HttpServletResponse response) {

		Responsing responsing=new Responsing(response);

		if(result.hasErrors()) {
			request.setAttribute("pageNumber", pageNumber);
			return getPage;
		}//
		
		String uploadPath=servletContext.getRealPath("/resources/board"); // ���ε����� ���
		String filename=request.getParameter("filename"); //���� ���� �̹����̸�
		System.out.println("filename:"+filename);
		if(boardBean.getBimage().equals("") || boardBean.getBimage()==null) { //������ ���� ������ �� ���� ���
			boardBean.setBimage(filename); //�������� �״�� ÷�εǵ���
		}
		System.out.println("boardBean.getBimage():"+boardBean.getBimage());
		System.out.println("filename:"+filename);
		int cnt=boardDao.updateArticle(boardBean);
		
		MultipartFile mf=boardBean.getUpload();

		if(cnt>0) {
			if(!boardBean.getBimage().equals(filename)) {
				File file=new File(uploadPath+"\\"+boardBean.getBimage());
				File file2=new File(uploadPath+"\\"+filename);
				file2.delete();
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
			responsing.useRedirect("list.bd");
		}
		else {
			responsing.useAlert("�Խù� ������ �����Ͽ����ϴ�");
			request.setAttribute("pageNumber",pageNumber);
			responsing.useHistory(-1);
		}
		
		return null;
	}
}
