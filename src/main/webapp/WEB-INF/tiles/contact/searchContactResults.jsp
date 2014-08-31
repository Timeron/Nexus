<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>



<table class="table table-hover">
	<thead>
		<tr>
			<th>#</th>
			<th>Imię</th>
			<th>Nazwisko</th>
			<th>Pseudo</th>
		</tr>


	</thead>
	<tbody>
		<c:forEach items="${personList}" var="name">
			<tr>
				<td>${name.id}</td>
				<td>${name.firstName}</td>
				<td>${name.lastName}</td>
				<td>${name.pseudo}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

