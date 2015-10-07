<!DOCTYPE HTML>
<!-- gwarant polskich znaków -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
	<tiles:insertAttribute name="meta" />
	</head>
	<body>
		<div id="body">
			<tiles:insertAttribute name="body" />
		</div>
	</body>
</html>