<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<% if (request.getParameter("titre")== null){
		%>
<title>Sortir.com</title>
<% 
	}else{
%>

<title><%= request.getParameter("titre") %> | Sortir.com</title>

<% } %>

<!-- Bootstrap core CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css"
	integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
	crossorigin="anonymous">

<!-- Custom styles for this template -->
<link href="<%=request.getContextPath()%>/assets/css/style.css" rel="stylesheet">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
</head>

<link href="<%=request.getContextPath()%>/assets/css/jquery.flexdatalist.min.css" rel="stylesheet">

<body>
	<div class="wrapper">
		<!-- allow responsive footer -->