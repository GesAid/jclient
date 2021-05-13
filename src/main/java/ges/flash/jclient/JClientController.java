package ges.flash.jclient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.ibm.icu.text.Transliterator;
import com.mashape.unirest.http.exceptions.UnirestException;

import ges.flash.jclient.obj.ExchangeClient;
import ges.flash.jclient.obj.WebHook;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;

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
		env.put(Context.PROVIDER_URL, "ldap://ip:389");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, request.getParameter("username") + "@" + "domain.lan");
		env.put(Context.SECURITY_CREDENTIALS, request.getParameter("password"));

		LdapContext ctxGC = null;
		boolean ldapUser = false;

		try {
			ctxGC = new InitialLdapContext(env, null);
			// OU=Holding,DC=flash,DC=lan
			NamingEnumeration answer = ctxGC.search("OU=user,DC=domain,DC=lan", searchFilter, searchCtls);
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
				if (null != amap.get("telephoneNumber"))
					request.getSession().setAttribute("tId", amap.get("telephoneNumber"));

				else
					request.getSession().setAttribute("tId", "0000");
				// request.getSession().setAttribute("count", nu);
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

	String name = "user";
	String pass = "pass";
	String url = "https://jira.domain.ru";

	@RequestMapping(path = "/error", method = RequestMethod.POST)
	protected void doError(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendRedirect("/error.jsp");
	}

	@RequestMapping(path = "/sentI", method = RequestMethod.POST)
	protected void doPost(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,
			@RequestParam("summary") String summary, @RequestParam("comment") String comment,
			@RequestParam("type") String type, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if (request.getSession().getAttribute("user") == null) {
			response.sendRedirect("/timout.jsp");
		} else {
			String email = request.getSession().getAttribute("mail") + "";
			String sip = request.getSession().getAttribute("tId") + "";
			String userD = request.getSession().getAttribute("user") + "";
			JiraClient myJiraClient = new JiraClient(name, pass, url);
			if (!file.isEmpty()) {
				addAttachmentToIssue(myJiraClient.createIssueJira(summary, comment, type, sip, userD, email), file);
				response.sendRedirect("/listissue.jsp");
			} else {
				try {
					myJiraClient.createIssueJira(summary, comment, type, sip, userD, email);
					
					response.sendRedirect("/listissue.jsp");

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	String auth = new String(org.apache.commons.codec.binary.Base64.encodeBase64((name + ":" + pass).getBytes()));

	public boolean addAttachmentToIssue(String issueKey, MultipartFile fullfilename) throws IOException {

		CloseableHttpClient httpclient = HttpClients.createDefault();

		HttpPost httppost = new HttpPost(url + "/rest/api/2/issue/" + issueKey + "/attachments");
		httppost.setHeader("X-Atlassian-Token", "nocheck");
		httppost.setHeader("Authorization", "Basic " + auth);
		InputStream in = fullfilename.getInputStream();
		String CYRILLIC_TO_LATIN = "Russian-Latin/BGN";
		Transliterator toLatinTrans = Transliterator.getInstance(CYRILLIC_TO_LATIN);
		String fname = toLatinTrans.transliterate(fullfilename.getOriginalFilename());
		fname = FilenameUtils.getBaseName(fname) + "." + FilenameUtils.getExtension(fname);
		File fileToUpload = new File("/opt/files/" + fname);
		FileUtils.copyInputStreamToFile(in, fileToUpload);
		FileBody fileBody = new FileBody(fileToUpload);

		MultipartEntityBuilder mbuilder = MultipartEntityBuilder.create();
		mbuilder.addPart("file", fileBody);

		HttpEntity entity = mbuilder.setCharset(StandardCharsets.UTF_8).build();

		httppost.setEntity(entity);

		CloseableHttpResponse response;

		try {
			response = httpclient.execute(httppost);
		} finally {
			httpclient.close();
		}

		if (response.getStatusLine().getStatusCode() == 200)
			return true;
		else
			return false;

	}

	@RequestMapping(path = "/logout", method = RequestMethod.GET)
	protected void logout(HttpServletRequest request, HttpServletResponse response)
			throws IOException, UnirestException {

		request.getSession().removeAttribute("user");
		response.sendRedirect("/index.jsp");
	}

	@RequestMapping(value = "/webhook", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void greetingJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
		final String json = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
		Gson g = new Gson();
		WebHook p = g.fromJson(json, WebHook.class);
		if (p.getIssue() != null)
			if (p.getIssue().getKey() != null)
				if (p.getComment() != null) {
					String rez = "";
					if (p.getIssue().getFields().getResolution()!=null)
						rez = p.getIssue().getFields().getStatus().getName() +".  " + p.getIssue().getFields().getResolution().getDescription()+".  ";
					else rez = p.getIssue().getFields().getStatus().getName() +".  " + p.getIssue().getFields().getStatus().getDescription();
					if (p.getIssue().getFields().getAssignee()!=null)
						rez +=  "\r\n"+"<br> <strong>Ответственный: </strong> " + p.getIssue().getFields().getAssignee().getDisplayName();
					String bodyMessage = "<p><strong>Обращение в HelpDesk </strong></p>" + "\r\n"
							+ "<br>______________________________________________ <br>" + "\r\n"
							+ "<p style=\"font-family: 'Times New Roman', Times, serif; \r\n" + "    font-style: italic;\">"
							+ p.getIssue().getFields().getDescription() + "</p>" + "\r\n" + "<br>______________________________________________" + "\r\n"+
							"<br> <strong>Статус: </strong> "+ rez + "\r\n"
							+  "<br> <strong>Комментарий: </strong> "+ p.getComment().getBody() + "\r\n"
							+
							"<br>" + "\r\n" +
							"<br>" + "\r\n"
							+ "<br>Посмотреть ваши заявки можно на странице \"Список Обращений\" : <a href=\"http://help.domain.ru\">ссылка</a>";
					ExchangeClient client = new ExchangeClient.ExchangeClientBuilder().hostname("mail.domain.ru")
							.exchangeVersion(ExchangeVersion.Exchange2010_SP2)
							.domain("domain.ru")
							.username("user")
							.password("pass")
							.recipientTo(p.getIssue().getFields().getCustomfield_10301() + "")
							.subject(p.getIssue().getKey() + " - HelpDesk - " + p.getIssue().getFields().getSummary() + " - новое событие")
							.message(bodyMessage).build();
					client.sendExchange();
				
				}
				else {
					String rez = "";
					if (p.getIssue().getFields().getResolution()!=null)
						rez = p.getIssue().getFields().getStatus().getName() +".  " +p.getIssue().getFields().getResolution().getDescription()+".  ";
					else rez = p.getIssue().getFields().getStatus().getName()+".  " + p.getIssue().getFields().getStatus().getDescription();
					if (p.getIssue().getFields().getAssignee()!=null)
						rez +=  "\r\n"+"<br><strong> Ответственный: </strong>" + p.getIssue().getFields().getAssignee().getDisplayName();
					
					String bodyMessage = "<p><strong>Обращение в HelpDesk </strong></p>" + "\r\n"
							+ "<br>______________________________________________ <br>" + "\r\n"
							+ "<p style=\"font-family: 'Times New Roman', Times, serif; \r\n" + "    font-style: italic;\">"
							+ p.getIssue().getFields().getDescription() + "</p>" + "\r\n" + "<br>______________________________________________" + "\r\n"+
							"<br> <strong>Статус: </strong>"+ rez + "\r\n" +
							"<br>" + "\r\n" +
							"<br>" + "\r\n"
							+ "<br>Посмотреть ваши заявки можно на странице \"Список Обращений\" : <a href=\"http://help.domain.ru\">ссылка</a>";
					ExchangeClient client = new ExchangeClient.ExchangeClientBuilder().hostname("mail.domain.ru")
							.exchangeVersion(ExchangeVersion.Exchange2010_SP2)
							.domain("domain.ru")
							.username("user")
							.password("pass")
							.recipientTo(p.getIssue().getFields().getCustomfield_10301() + "")
							.subject(p.getIssue().getKey() + " - HelpDesk - " + p.getIssue().getFields().getSummary() + " - новое событие")
							.message(bodyMessage)
							.build();
					client.sendExchange();
				}
	}

}
