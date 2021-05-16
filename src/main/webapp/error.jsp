<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ошибка</title>
<link rel="shortcut icon" href="image/logo.svg" type="image/svg">
<link rel="stylesheet" type="text/css" href="css/scanform.css">
</head>
<body>
	<div class="left">
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
	<div class="right">
	<p style="font-family: 'Times New Roman', Times, serif; 
    font-style: italic;">
	<mark style="color: red; text-shadow: black;  font-weight: 600;">ВНИМАНИЕ!</mark></p>
		<p style="font-family: 'Times New Roman', Times, serif; 
    font-style: italic;">Создание заявки не удалось</p>
    <p style="font-family: 'Times New Roman', Times, serif; 
    font-style: italic;">Убедитесь что данные для обратной  свзязи заполнены.
    					Если данные не заполнены нажмите "выход" и перезайдите</p>
    <p style="font-family: 'Times New Roman', Times, serif; 
    font-style: italic;">Если данные корректны убедитесь что прикрепленный файл менее 4Мб</p>
     <p style="font-family: 'Times New Roman', Times, serif; 
    font-style: italic;">Если проблема не в этом напишите на support@flash.ru</p>
	</div>
</body>
</html>