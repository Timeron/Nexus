<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
HAHA
<c:forEach items="${form.observedSites}" var="observedSite">
	${observedSite.articleName}<br/>
</c:forEach>
    
    