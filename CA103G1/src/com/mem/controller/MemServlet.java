package com.mem.controller;

import java.io.*; 
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.mem.model.MemJDBCDAO;
import com.mem.model.MemService;
import com.mem.model.MemVO;

public class MemServlet extends HttpServlet {

	public void doGet (HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		doPost(req,res);
	}
	
	public void doPost (HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException { 

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("mem_signUp".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("psw");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入密碼");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front_end/mem/Mem_Login_Signup.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
//				Integer empno = null;
//				try {
//					empno = new Integer(str);
//				} catch (Exception e) {
//					errorMsgs.add("員工編號格式不正確");
//				}
				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/emp/select_page.jsp");
//					failureView.forward(req, res);
//					return;//程式中斷
//				}

				/***************************2.開始註冊資料*****************************************/
				
				MemVO memVO = new MemVO();
				
				String email = req.getParameter("email");
				String memName = req.getParameter("memName");
				String psw = req.getParameter("psw");
				memVO.setMem_account(email);
				memVO.setMem_name(memName);
				memVO.setMem_password(psw);
				memVO.setMem_email(email);
				memVO.setMem_status("MS0");
				
				MemService memSvc = new MemService();
				memVO = memSvc.addMem(email, memName, psw);
				
				String url = "/front_end/mem/Mem_Login_Signup.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);
				
			}catch(Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front_end/mem/Mem_Login_Signup.jsp");
				failureView.forward(req, res);
			}
			
		}
		
	}
}