<!DOCTYPE HTML>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!-- gwarant polskich znaków -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<html>
<head>
<tiles:insertAttribute name="meta" />
</head>
<body>
	<div class="navbar-inverse nexus">
		<div class="title">
			<tiles:insertAttribute name="title" />
		</div>
		<a href="/nexus/">
			<div>NEXUS</div>
		</a>
	</div>
	<div id="body">
		<tiles:insertAttribute name="body" />
	</div>
</body>
</html>