<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Сессия завершена</title>
<link rel="shortcut icon" href="image/logo.svg" type="image/svg">

<link rel="stylesheet" type="text/css" href="css/scanform.css">
</head>
<body>
	<div class="left">
	<form method="get" action="logout" style="align-content: flex-end;">
			<button type="submit">Авторизация</button>
		</form>
		<div style="align-content: flex-end;">
			<img alt="logo" src="image/logo.svg">
		</div>
	</div>
	<div class="right">
	<p style="font-family: 'Times New Roman', Times, serif; 
    font-style: italic;">
	<mark style="color: red; text-shadow: black;  font-weight: 600;">ВНИМАНИЕ!</mark></p>
		<p style="font-family: 'Times New Roman', Times, serif; 
    font-style: italic;">Создание заявки не удалось</p>
    <p style="font-family: 'Times New Roman', Times, serif; 
    font-style: italic;">Время сессии завершено. Авторизуйтесь повторно</p>
	</div>
</body>
</html>