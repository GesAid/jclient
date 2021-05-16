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
		<div style="align-content: center;" >
			<h1>Данные для обратной связи*</h1>
			<ul style="text-align: left;">
				<li style="color: black;">User : <%=user%><br></li>
				<li style="color: black;">SIP-номер : <%=tel%><br></li>
				<li style="color: black;">E-mail : <%=mail%><br></li>
			</ul>
			<p style="font-family: 'Times New Roman', Times, serif; 
    font-style: italic;">*эти данные будут указаны в заявке</p>
		</div>
		<p>Инструкция:</p>
		<p style="font-family: 'Times New Roman', Times, serif; 
    font-style: italic;">
    1. Выбираем раздел обращения
    2. Указываем тему
    3. Описываем свою проблему
    3.1 При наличии Вложения убедитесь что размер менее 4Мб
    4. Нажимаем кнопку "отправить"
    </p>
		<form action="listissue.jsp">
    		<button type="submit">Список обращений</button>
		</form>
		<%
		} else {
		%>
		<div style="align-content: center;" class="d14">
			<h3>ИТ-поддержка</h3>
			<p><strong>Сервис для обращения в ИТ-поддержку</strong></p>
			<p><strong>Для перехода к заполнению формы авторизируйтесь</strong></p>
			<p>
			<p>Данные для авторизации доменные:</p>
			<p>Логин [ <mark style="color: red; background-color: yellow; text-shadow: black;  font-weight: 600;">i.ivanov</mark> ]@flash.ru</p>
			<p>Пароль: совпадает с паролем почты 
			и паролем для входа в систему
			</p>
		</div>
		<%
		}
		%>
		<div style="align-content: flex-end;">
			<img alt="logo" src="image/logo.svg">
		</div>
		<form action="instruction.jsp">

			<button type="submit">РУКОВОДСТВО ПОЛЬЗОВАТЕЛЯ</button>
		</form>
	</div>

	<div class="right">
		<%
		if (!user.isEmpty()& !user.equals("Не заполненное поле")) {
		%>
		<form method="post" action="sentI" enctype="multipart/form-data">
			<div>
				<span>Раздел</span> <select name="type">
					<option value="ITSUP-57" selected="selected" >Обращения по техническим вопросам</option>
					<option value="ERP-253">Обращения по вопросам 1С</option>
				</select>
			</div>
			<div>
				<label for="summary">Тема обращения</label> <input
					autofocus="autofocus" type="text" name="summary" value=""
					required>
			</div>

			<div>
				<label for="comment">Описание проблемы</label> 
				<textarea style="width: 95%;" name="comment" type="text"  rows="8" value="Описание" required="required" ></textarea>
			</div>
			<input class="custom-file-input" type="file" name="file" />
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