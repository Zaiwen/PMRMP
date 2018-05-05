package com.I18n.action;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class I18NFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub
		//System.out.println("In I18NFilter-------");
		String local=arg0.getParameter("local");
		String url = arg0.getParameter("url");
		//if(url == null) System.out.println("No Switch");
		//else System.out.println("Address ：" + url);
		if(local!=null){
		String loc[]=local.split("_");
		Locale locale=new Locale(loc[0],loc[1]);
		//System.out.println("In I18NFilter--------");
		((HttpServletRequest)arg0).getSession().setAttribute("WW_TRANS_I18N_LOCALE", locale);
		//((HttpServletRequest)arg0).getSession().setAttribute("url", url);
		}
		arg2.doFilter(arg0, arg1);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
