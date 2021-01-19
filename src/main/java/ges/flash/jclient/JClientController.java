package ges.flash.jclient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JClientController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@RequestMapping(path = "/login", method = RequestMethod.GET)
	protected void doGET(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String returnedAtts[] = { "telephonenumber", "cn", "mail" };
		String searchFilter = "(&(objectClass=user)(sAMAccountName=" + request.getParameter("username") + "))";

		// Create the search controls
		SearchControls searchCtls = new SearchControls();
		searchCtls.setReturningAttributes(returnedAtts);

		// Specify the search scope
		searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://10.221.12.3:389");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, request.getParameter("username") + "@" + "flash.lan");
		env.put(Context.SECURITY_CREDENTIALS, request.getParameter("password"));

		LdapContext ctxGC = null;
		boolean ldapUser = false;

		try {
			ctxGC = new InitialLdapContext(env, null);
			// OU=Holding,DC=flash,DC=lan
			NamingEnumeration answer = ctxGC.search("OU=Holding,DC=flash,DC=lan", searchFilter, searchCtls);
			while (answer.hasMoreElements()) {
				SearchResult sr = (SearchResult) answer.next();
				Attributes attrs = sr.getAttributes();
				Map amap = null;
				if (attrs != null) {
					amap = new HashMap();
					NamingEnumeration ne = attrs.getAll();
					while (ne.hasMore()) {
						Attribute attr = (Attribute) ne.next();
						amap.put(attr.getID(), attr.get());
						ldapUser = true;
					}
					ne.close();
				}
				if (null!=amap.get("telephoneNumber")) 
					request.getSession().setAttribute("tId", amap.get("telephoneNumber"));
				
				else
					request.getSession().setAttribute("tId", "0000");
				//request.getSession().setAttribute("count", nu);
				request.getSession().setAttribute("user", amap.get("cn"));
				request.getSession().setAttribute("mail", amap.get("mail"));
				request.getSession().setAttribute("error", "");
				response.sendRedirect("/index.jsp");
			}

		} catch (Exception ex) {
			request.getSession().setAttribute("sucess", "");
			request.getSession().setAttribute("error", "Неверный логин/пароль");
			response.sendRedirect("/index.jsp");

		}

	}

	@RequestMapping(path = "/sentI", method = RequestMethod.POST)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String summary = request.getParameter("summary");
		String comment = request.getParameter("comment");
		comment += "\n" +"Пользователь: "+ request.getSession().getAttribute("user")+
				"\n" +"E-mail: "+ request.getSession().getAttribute("mail")+
				"\n" +"Внутренний номер: "+ request.getSession().getAttribute("tId");
		String type = request.getParameter("type");
		JiraClient myJiraClient = new JiraClient(
				  "helpdesk", 
				  "Desk+007Ha", 
				  "https://jira.flash.ru");
		try {
			if (myJiraClient.createIssueJira(summary, comment, type).equals("ok")) {
				response.sendRedirect("/index.jsp");
			} else {
				response.sendRedirect("/index.jsp");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(path = "/logout", method = RequestMethod.GET)
	protected void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().removeAttribute("user");
		response.sendRedirect("/index.jsp");
	}
}
