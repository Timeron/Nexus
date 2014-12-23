<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container-fluid">

	<h1>${form.walletAccount.name}</h1>
<div class="bs-example">
	<div class="btn-group">
		<h1><a class="btn btn-primary btn-ms" role="button" href="/timeron-nexus/wallet/addRecord?id=${form.walletAccount.id}">+</a></h1>
	</div>
</div>
	<c:choose>

		<c:when test="${not empty form.walletRecords}">
			<table class="table table-striped">
				<tr>
					<th>#</th>
					<th>type</th>
					<th>value</th>
					<th>description</th>
					<th>date</th>
					<th>transfer</th>
					<th>income</th>
					<th>Transfer do</th>
				</tr>
				<c:forEach items="${form.walletRecords}" var="record">
					<tr>
						<td>${record.id}</td>
						<td>${record.walletType.icon}</td>
						<td>${record.value}</td>
						<td>${record.description}</td>
						<td>${record.date}</td>
						<td>${record.transfer}</td>
						<td>${record.income}</td>
						<td>${record.destinationWalletAccount.name}</td>
					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
		empty
		</c:otherwise>
	</c:choose>
</div>
