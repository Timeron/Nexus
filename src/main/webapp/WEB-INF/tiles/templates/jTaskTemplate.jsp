<!DOCTYPE HTML>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- gwarant polskich znaków -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
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
	<div id="body">
		<div class="messagePopup">
			<tiles:insertAttribute name="messagePopup" />
		</div>
		<tiles:insertAttribute name="body" />
	</div>
	<div id="footer">
		<tiles:insertAttribute name="footer" />
	</div>
</body>
</html>