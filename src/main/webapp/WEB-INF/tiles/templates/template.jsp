<!DOCTYPE HTML>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<html>
<head>
<tiles:getAsString name="title" />
<tiles:insertAttribute name="meta" />
</head>
<body>
	<div id="topHeader">
		<tiles:insertAttribute name="logInHeader" />
	</div>
	<div id="header">
		<tiles:insertAttribute name="header" />
	</div>
	<div id="body">
		<tiles:insertAttribute name="body" />
	</div>
	<div id="footer">
		<tiles:insertAttribute name="footer" />
	</div>
</body>
</html>