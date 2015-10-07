<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
	<c:when test="${not empty form.walletTypes}">
		<table class="table table-striped">
			<tr>
				<th>#</th>
				<th></th>
				<th>name</th>
				<th>value</th>
				<th>date</th>
				<th>icon</th>
				<th>color</th>
				<th></th>
			</tr>
			<c:forEach items="${form.walletTypes}" var="record">
				<tr>
					<td>${record.id}</td>
					<td></td>
					<td>${record.name}</td>
					<td>${record.defaultValue}</td>
					<td>${record.timestamp}</td>
					<td>${record.icon}</td>
					<td>${record.color}</td>
					<td>
						<a id="transferButton" class="btn btn-primary" href="/nexus/wallet/walletEditType?id=${record.id}">
							<span class="glyphicon glyphicon-edit" aria-hidden="true">
							</span>
						</a>
						<a id="transferButton" class="btn btn-danger" href="/nexus/wallet/walletRemoveType?id=${record.id}">
							<span class="glyphicon glyphicon-trash" aria-hidden="true">
							</span>
						</a>
					</td>
				</tr>
				<c:choose>
					<c:when test="${not empty record.childrenTypes}">
						<c:forEach items="${record.childrenTypes}" var="children">
							<tr>
								<td></td>
								<td>${children.id}</td>
								<td>${children.name}</td>
								<td>${children.defaultValue}</td>
								<td>${children.timestamp}</td>
								<td>${children.icon}</td>
								<td>${children.color}</td>
								<td>
						<a id="transferButton" class="btn btn-primary" href="/nexus/wallet/walletEditType?id=${children.id}">
							<span class="glyphicon glyphicon-edit" aria-hidden="true">
							</span>
						</a>
						<a id="transferButton" class="btn btn-danger" href="/nexus/wallet/walletRemoveType?id=${children.id}">
							<span class="glyphicon glyphicon-trash" aria-hidden="true">
							</span>
						</a>
					</td>
							</tr>
						</c:forEach>
					</c:when>
				</c:choose>
			</c:forEach>
		</table>
	</c:when>
	<c:otherwise>
	empty
</c:otherwise>
</c:choose>