<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>

	<c:when test="${not empty form.records}">
		<table class="table table-striped">
			<tr>
				<th>#</th>

				<th>Litry</th>
				<th>Kilometry</th>
				<th>Miasto</th>
				<th>Mieszany</th>
				<th>Data</th>

				<th>Edytuj/Usuń</th>
			</tr>
			<c:forEach items="${form.records}" var="record">
				<tr>
					<td>${record.id}</td>

					<td>${record.liters}</td>
					<td>${record.distance}</td>
					<td>${record.city}</td>
					<td>${record.mixed}</td>
					<td>${record.date}</td>

					<td><a id="transferButton" class="btn btn-primary"
						href="/timeron-nexus/car/carEditRecord?id=${record.id}"> <span
							class="glyphicon glyphicon-edit" aria-hidden="true"> </span>
					</a> <a id="transferButton" class="btn btn-danger"
						href="/timeron-nexus/car/carRemoveRecord?id=${record.id}"> <span
							class="glyphicon glyphicon-trash" aria-hidden="true"> </span>
					</a></td>
				</tr>
			</c:forEach>
		</table>
	</c:when>
	<c:otherwise>
		empty
		</c:otherwise>
</c:choose>
