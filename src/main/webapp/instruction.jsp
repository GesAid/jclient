
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Инструкция</title>
<link rel="shortcut icon" href="image/logo.svg" type="image/svg">

<link rel="stylesheet" type="text/css" href="css/scanform.css">
</head>
<body>
	<div class="left">
		<p>
		<p></p>
		<p></p>
		<p></p>
		<p
			style="font-family: 'Times New Roman', Times, serif; font-style: italic;">Инструкция
			по использованию HelpDesk</p>
		<p
			style="font-family: 'Times New Roman', Times, serif; font-style: italic;">При
			создании заявки обращайте внимание на выбор раздела</p>
		<p
			style="font-family: 'Times New Roman', Times, serif; font-style: italic;">Заявки
			составленные некорректно обрабатываться не будут</p>
		<p></p>
		<p></p>
		<p></p>
		<p></p>
		<p></p>
		<form action="index.jsp">
			<button type="submit">Назад</button>
		</form>
		<div style="align-content: flex-end;">
			<img alt="logo" src="image/logo.svg">
		</div>
		
				
	</div>

	<div class="right"
		style="overflow-x: hidden; overflow-y: auto; text-align: justify; font-size: small;">
		
		<h3>
			<strong
				style="font-family: 'Times New Roman', Times, serif; font-style: oblique;">Экран
				авторизации</strong>
		</h3>
		<p>Для авторизвции в системе HelpDesk используются данные Вашей
			учетной записи:
		<p>Логин (например i.ivanov) - без указания @flash.ru
		<p>Пароль (например 1234asdf) - доменный пароль для входа в
			компьютер/почту
		<p>После ввода данных нажимаем кнопку "Вход"
		<h3>
			<strong
				style="font-family: 'Times New Roman', Times, serif; font-style: oblique;">Создание
				заявки</strong>
		</h3>

		<h4>Выбор раздела обращения</h4>
			<h5>Обращения по вопросам 1С</h5>
				<p>В этом разделе создаются заявки связанный с работой 1С
				<p>При создании заявки обязательно необходимо указать
				<p>- Наименование Базы 1С
				<p>- - При наличии указать наименование Объекта
			(документа/отчета/печатной формы/обработки и тп.)</p>
			<h5>Обращения по техническим вопросам</h5>
				<p>Раздел для обращений по вопросам 
				<p> - Программного обеспечения
				<p> - Оборудования (ПК/Принтеры/Телефоны/Расходные материалы/Перефирия  и тд.)
				<p> - Сетевого доступа
				<p> - Заведения пользователей
				<p> - Работоспособности сервисов
			<h4>Тема обращения</h4>
				<p> Краткое описание проблемы
			<h4>Описание</h4>
				<p> Полное и конкретное  описание задачи.
			<h4>Вложения</h4>
				<p> При необходимости прикрепить файлы нажимаем на "+" и выбираем файл
				<p> Размер файла не должен привышать 4Мб
		<h3>
			<strong
				style="font-family: 'Times New Roman', Times, serif; font-style: oblique;">Список обращений</strong>
		</h3>
			<p> Страница отображения информации о состоянии Ваших заявок
			<p> Статус, ответственный, комментарии
		</div>
		
		
</body>


</html>