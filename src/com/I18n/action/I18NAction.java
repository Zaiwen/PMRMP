package com.I18n.action;

 

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;  
 
  
public class I18NAction extends ActionSupport  {  
  private static final long serialVersionUID = 1L;  
 
  
  @Override  
  public String execute() throws Exception {  
	  Locale locale ;
	  ActionContext cxt = ActionContext.getContext();   
	  HttpServletRequest request = (HttpServletRequest)cxt.get(ServletActionContext.HTTP_REQUEST);

	  String lang =  request.getParameter("local"); //获取名为attrName的request属性的值
	  String result = request.getParameter("url");
	  System.out.println(result);
	  if(lang.equalsIgnoreCase("en_US")){
		  locale = new Locale("en","US");
	  }else{
		  locale = new Locale("zh","CN");
	  }
	 
	  System.out.println(" In Action");
	
	
	
	ServletActionContext.getRequest().getSession().setAttribute("WW_TRANS_I18N_LOCALE", locale);
    return result;  
  }  
  

    
  public String run() throws Exception {  
    System.out.println("Action.run()");  
   
    return INPUT;  
  }  
}  