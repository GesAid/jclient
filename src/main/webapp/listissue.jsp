<%@page import="ges.flash.jclient.utils.Operation"%>
<%@page import="com.mashape.unirest.http.exceptions.UnirestException"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.json.JSONArray"%>
<%@page import="com.mashape.unirest.http.JsonNode"%>
<%@page import="com.mashape.unirest.http.HttpResponse"%>
<%@page import="com.mashape.unirest.http.Unirest"%>
<%@page import="java.util.stream.StreamSupport"%>
<%@page import="java.util.stream.Collectors"%>
<%@page import="java.util.Spliterator"%>
<%@page import="ges.flash.jclient.obj.IssueInfo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.atlassian.jira.rest.client.api.JiraRestClient"%>
<%@page
	import="com.atlassian.jira.rest.client.api.JiraRestClientFactory"%>
<%@page import="com.atlassian.jira.rest.client.api.domain.BasicProject"%>
<%@page import="com.atlassian.jira.rest.client.api.domain.Issue"%>
<%@page import="com.atlassian.jira.rest.client.api.domain.SearchResult"%>
<%@page import="com.atlassian.jira.rest.client.api.domain.User"%>
<%@page
	import="com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory"%>
<%@page import="com.atlassian.util.concurrent.Promise"%>
<%@page import="java.net.URI"%>
<%@page import="com.atlassian.jira.rest.client.api.domain.Comment"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ИТ-поддержка</title>
<link rel="shortcut icon" href="image/logo.svg" type="image/svg">

<link rel="stylesheet" type="text/css" href="css/scanform.css">
</head>
<body>
	<div class="left">
		<%
		String user = "";
		try {
			user = request.getSession().getAttribute("user").toString();
		} catch (Exception e) {
			user = "";
		}
		%>
		<p>
		<p>
			<a href="http://10.221.12.151/phonebook/pb.php">Телефонный
				справочник</a>
		<p></p>
		<p></p>
		<p></p>
		<p
			style="font-family: 'Times New Roman', Times, serif; font-style: italic;">Статус
			"Открытый" - заявка создана, но не принята в работу</p>
		<p
			style="font-family: 'Times New Roman', Times, serif; font-style: italic;">Статус
			"В работе" - заявка выполняется</p>
		<p
			style="font-family: 'Times New Roman', Times, serif; font-style: italic;">Статус
			"Готово" - заявка выполнена</p>
		<p></p>
		<p></p>
		<p></p>
		<p></p>
		<p></p>
		<form action="index.jsp">

			<button type="submit">Назад</button>
		</form>
		</form>
		<form method="get" action="logout" style="align-content: flex-end;">
			<button type="submit">Выход</button>
		</form>
		<div style="align-content: flex-end;">
			<img alt="logo" src="image/logo.svg">
		</div>
	</div>

	<div class="right"
		style="overflow-x: hidden; overflow-y: auto; text-align: justify;">
		<%
		String name = "user";
		String pass = "pass";
		String url = "https://jira.flash.ru";
		if (!user.isEmpty() & !user.equals("Не заполненное поле")) {
			JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
			URI uri = new URI(url);
			JiraRestClient client = factory.createWithBasicHttpAuthentication(uri, "user", "pass");
			String start = "https://jira.flash.ru//rest/api/2/issue/";
			String finish = "/comment?orderBy=-created&maxResults=1";
			// Invoke the JRJC Client
			Promise<User> promise = client.getUserClient().getUser("helpdesk");
			User userj = promise.claim();
			Operation operation = new Operation();
			/**	for (BasicProject project : client.getProjectClient().getAllProjects().claim()) {
			System.out.println(project.getKey() + ": " + project.getName());
				}
			*/
			Promise<SearchResult> searchJqlPromise = client.getSearchClient().searchJql("project = ERP AND issuetype = 10065");
			List<IssueInfo> list = new ArrayList<IssueInfo>();
			for (Issue issue : searchJqlPromise.claim().getIssues()) {
				if (issue.getFieldByName("Пользователь").getValue() != null) {
					if (issue.getFieldByName("Пользователь").getValue().toString().contains(user)) {
						list.add(operation.getInfo(issue));
					}
						} else if (issue.getDescription() != null) {
					if (issue.getDescription().contains(user)) {
						list.add(operation.getInfo(issue));
					}
						}
			}
			searchJqlPromise = client.getSearchClient().searchJql("project = ITSUP AND issuetype = 10065");
			for (Issue issue : searchJqlPromise.claim().getIssues()) {
				if (issue.getFieldByName("Пользователь").getValue() != null) {
			if (issue.getFieldByName("Пользователь").getValue().toString().contains(user)) {
				list.add(operation.getInfo(issue));
			}
				} else if (issue.getDescription() != null) {
			if (issue.getDescription().contains(user)) {
				list.add(operation.getInfo(issue));
			}
				}
			}
		%>
		<table
			style="width: 90%; border-collapse: collapse; table-layout: fixed;"
			class="simple-little-table">
			<tr>
				<td>ID</td>
				<td>Тема</td>
				<td>Дата</td>
				<td>Ответственный</td>

			</tr>
			<%
			for (IssueInfo ii : list) {
			%>
			<tr style="padding: 5px; border: 4px double black;"
				title="<%=ii.getDescription()%>">
				<td><%=ii.getIdIssue()%></td>
				<td><%=ii.getSummary()%></td>

				<td><%=ii.getDateCreate()%></td>
				<td><%=ii.getAssigne()%></td>
			</tr>

			<tr>
				<%
				if (ii.getStatus().equals("Готово") | ii.getStatus().contains("Завершена")) {
				%>
				<td align="right" colspan="1">Статус</td>
				<td align="right" colspan="3" style="color: green;"><%=ii.getStatus()%></td>
				<%
				} else if (ii.getStatus().contains("Эта проблема не будет выполнена")) {
				%>
				<td align="right" colspan="1">Статус</td>
				<td align="right" colspan="3" style="color: red;"><%=ii.getStatus()%></td>
				<%
				} else {
				%>
				<td align="right" colspan="1">Статус</td>
				<td align="right" colspan="3"><%=ii.getStatus()%></td>
				<%
				}
				%>
			</tr>
			<%
			if (ii.getComments() != null) {
				for (String s : ii.getComments()) {
			%>
			<tr>
				<td align="right" colspan="1">Комментарий</td>
				<td align="right" colspan="3"><%=s + ""%></td>
			</tr>
			<%
			}
			}
			}
			} else {
			%>
			<tr>
				<td>Нет заявок</td>
			</tr>
			<%
			}
			%>
		</table>
	</div>
</body>


</html>
