<!DOCTYPE HTML>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- gwarant polskich znaków -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html lang="pl">

<head>
<tiles:getAsString name="title" />
<tiles:insertAttribute name="meta" />
</head>
<body>
<!-- 	<div id="topHeader"> -->
<%-- 		<tiles:insertAttribute name="logInHeader" /> --%>
<!-- 	</div> -->
	<div id="body">
		<div id="menu">
			<tiles:insertAttribute name="menu" />
		</div>
		<tiles:insertAttribute name="body" />
	</div>
	<div id="footer">
		<tiles:insertAttribute name="footer" />
	</div>
	<script src="<c:url value="/resources/js/validation.js" />"></script>
</body>
</html>