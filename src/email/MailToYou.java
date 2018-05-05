package email;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import changecode.Utf8code;

public class MailToYou extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String checkCode=null;
	private String sendAddress = null;
	private String sendSubject = null;
	private String sendContent = null;

	public MailToYou() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response){
		
		try{
		checkCode=request.getParameter("check");
		if(!checkCode.equalsIgnoreCase((String)request.getSession().getAttribute("randomCode")))
		  {
			request.getSession().setAttribute("senderror", "error!");
			response.sendRedirect("help/help.jsp");
			return;
		  }else{
		sendAddress=Utf8code.changeCode(request.getParameter("useraddress"));
		sendSubject=Utf8code.changeCode(request.getParameter("sendsubject"));
		sendContent=Utf8code.changeCode(request.getParameter("sendcontent"));
		if(sendAddress==null){
			request.getSession().setAttribute("senderror", "error!");
			response.sendRedirect("help/help.jsp");
			return;
		}else if(sendSubject==null){
			request.getSession().setAttribute("senderror", "error!");
			response.sendRedirect("help/help.jsp");
			return;
		}else if(sendContent==null){
			request.getSession().setAttribute("senderror", "error!");
			response.sendRedirect("help/help.jsp");
			return;
		}else{
		sendContent="来自:"+sendAddress+"(请管理员回复该邮箱!)    此用户向您咨询:"+sendContent;
		// 这个类主要是设置邮件
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.qq.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("2814941757@qq.com");
		mailInfo.setPassword("15527518572");// 您的邮箱密码
		mailInfo.setFromAddress("2814941757@qq.com");
		mailInfo.setToAddress("1694844516@qq.com");
		mailInfo.setSubject(sendSubject);
		mailInfo.setContent(sendContent);
		// 这个类主要来发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		//sms.sendTextMail(mailInfo);// 发送文体格式
		sms.sendHtmlMail(mailInfo);// 发送html格式
		request.getSession().setAttribute("sendsuccess", "success!");
		response.sendRedirect("help/help.jsp");
		return;
		}
		  }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			request.getSession().setAttribute("senderror", "error!");
			try {
				response.sendRedirect("help/help.jsp");
				return;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				return;
			}
		}
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {

		doGet(request, response);
	}
	
	public void init(){
		// Put your code here
	}
}