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
		String error = "";
		try {
			error = request.getSession().getAttribute("error").toString();
		} catch (Exception e) {
			error = "";
		}
		String user = "";
		try {
			user = request.getSession().getAttribute("user").toString();
		} catch (Exception e) {
			user = "";

		}
		String tel = "";
		try {
			tel = request.getSession().getAttribute("tId").toString();
		} catch (Exception e) {
			tel = "";

		}
		String mail = "";
		try {
			mail = request.getSession().getAttribute("mail").toString();
		} catch (Exception e) {
			mail = "";

		}
		if (!error.isEmpty()) {
		%>
		<div>
			<h1 style="color: red;"><%=error%></h1>
		</div>
		<%
		} else if (!user.isEmpty()) {
		%>
		<div style="align-content: center;">
			<h1>Данные для обратной связи</h1>
			<ul style="text-align: left;">
				<li style="color: green;">Пользователь : <%=user%><br></li>
				<li style="color: green;">Внутренний номер : <%=tel%><br></li>
				<li style="color: green;">Почта : <%=mail%><br></li>
			</ul>
		</div>
		<%
		} else {
		%>
		<div style="align-content: center;">
			<h1>ИТ-поддержка</h1>
			<ul style="text-align: left;">
				<li>Авторизируйтесь для составления заявки<br></li>
				<li>Логин/пароль как на вход в систему<br></li>
				<li>Например: <br>
				 i.ivanov<br>
				               P@$$word<br></li>
			</ul>
		</div>
		<%
		}
		%>
		<div style="align-content: flex-end;">
			<img alt="logo" src="image/logo.svg">
		</div>
	</div>

	<div class="right">
		<%
		if (!user.isEmpty()& !user.equals("Не заполненное поле")) {
		%>
		<form method="post" action="sentI">
			<div>
				<span>Раздел</span> <select name="type">
					<option value="ITSUP-58">Сеть</option>
					<option selected="selected" value="ITSUP-57">Программное обеспечение</option>
					<option value="ITSUP-56">1С</option>
					<option value="ITSUP-55">Оборудование</option>
				</select>
			</div>
			<div>
				<label for="summary">Тема обращения</label> <input
					autofocus="autofocus" type="text" name="summary" value="Проблема"
					required>
			</div>

			<div>
				<label for="comment">Описание проблемы</label> 
				<textarea style="width: 95%;" name="comment" type="text"  rows="8" value="Описание" required="required" ></textarea>
			</div>
		<%-- 	<div > <label for="photo">Вложение</label>
			<input  class="custom-file-input" type="file" name="photo" multiple>
			</div> 
		--%>
			<button type="submit">Отправить</button>
		</form>
				<form method="get" action="logout" style="align-content: flex-end;">
		    <button type="submit">Выход</button>
		</form>
		<%
		} else {
		%>
		<form method="get" action="login">
			<div>
				<label for="username">Логин</label> <input autofocus="autofocus"
					type="text" name="username" required>
			</div>
			<div>
				<label for="password">Пароль</label> <input type="password"
					name="password" required>
			</div>
			<button type="submit">Вход</button>
		</form>

		<%
		}
		%>
	</div>
</body>
</html>