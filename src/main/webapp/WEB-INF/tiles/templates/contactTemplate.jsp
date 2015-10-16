<!DOCTYPE HTML>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!-- gwarant polskich znaków -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html lang="pl">
<head>
<tiles:insertAttribute name="meta" />
<div class="navbar-inverse nexus">
	<div class="title">
		<tiles:insertAttribute name="title" />
	</div>
	<a href="/nexus/">
		<div>NEXUS</div>
	</a>
</div>
</head>
<body>
	<div id="header">
		<tiles:insertAttribute name="header" />
	</div>
	<div id="menu">
		<tiles:insertAttribute name="menu" />
	</div>
	<div id="body">
		<tiles:insertAttribute name="body" />
	</div>
	<div id="footer">
		<tiles:insertAttribute name="footer" />
	</div>
</body>
</html>