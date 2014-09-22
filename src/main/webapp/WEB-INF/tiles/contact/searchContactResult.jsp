<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<div>
	<label for="imie">${peronFound.firstName} ${peronFound.lastName}</label>
	<label for="imie">${peronFound.pseudo}</label>
</div>

<c:choose>
<c:when test="${empty peronFound.phone1 && empty peronFound.phone2 && empty peronFound.phone3}">Nie podano numeru telefon</c:when>
<c:otherwise>
<table class="table table-hover">
	<thead>
		<tr>
			<th>Telefon</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>${peronFound.phone1}</td>
		</tr>
		<tr>
			<td>${peronFound.phone2}</td>
		</tr>
		<tr>
			<td>${peronFound.phone3}</td>
		</tr>
	</tbody>
</table>
</c:otherwise>
</c:choose>

<br>

<c:choose>
<c:when test="${empty peronFound.email}">Nie podano adresu Mailowego</c:when>
<c:otherwise>

</c:otherwise>
</c:choose>
