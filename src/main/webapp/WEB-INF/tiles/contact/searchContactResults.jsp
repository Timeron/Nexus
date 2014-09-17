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
			<th>Action</th>
		</tr>


	</thead>
	<tbody>
		<c:forEach items="${personList}" var="name">
			<tr>
				<td>${name.id}</td>
				<td>${name.firstName}</td>
				<td>${name.lastName}</td>
				<td>${name.pseudo}</td>
				<c:choose>
					<c:when test="${editMode}">
						<td><a class="btn btn-primary btn-xs" role="button"
							href="./editContactSearch?id=${name.id}">Edytuj</a></td>
					</c:when>
					<c:otherwise>
						<td><a class="btn btn-primary btn-xs" role="button"
							href="./searchContact?id=${name.id}">Otwórz</a></td>
					</c:otherwise>
				</c:choose>

			</tr>
		</c:forEach>
	</tbody>
</table>

