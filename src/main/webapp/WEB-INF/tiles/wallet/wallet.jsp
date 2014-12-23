<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container-fluid">
	<div class="bs-example">
		<c:choose>
			<c:when test="${not empty form.accounts}">
				<c:forEach items="${form.accounts}" var="account">
					<div class="btn-group">
						<a class="btn btn-primary btn-ms" role="button"
							href="/timeron-nexus/wallet/walletAccout?id=${account.id}">${account.name}</a>
					</div>
				</c:forEach>
			</c:when>
			<c:otherwise>
		test
		</c:otherwise>
		</c:choose>
	</div>
	<h1>Wszystkie operacje</h1>
</div>
<c:choose>

	<c:when test="${not empty form.records}">
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
				<th>Edytuj</th>
				<th>Usuń</th>
			</tr>
			<c:forEach items="${form.records}" var="record">
				<tr>
					<td class="tdRecordId" >${record.id}</td>
					<td>${record.walletType.icon}</td>
					<td class="tdRecordValue" >${record.value}</td>
					<td>${record.description}</td>
					<td>${record.date}</td>
					<td>${record.transfer}</td>
					<td>${record.income}</td>
					<td>${record.destinationWalletAccount.name}</td>
					<td>
						<a id="transferButton" class="btn btn-primary" href="/timeron-nexus/wallet/walletEditRecord?id=${record.id}">
							<span class="glyphicon glyphicon-edit" aria-hidden="true">
							</span>
						</a>
					</td>
					<td>
						<a id="transferButton" class="btn btn-danger" href="/timeron-nexus/wallet/walletRemoveRecord?id=${record.id}">
							<span class="glyphicon glyphicon-trash" aria-hidden="true">
							</span>
						</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:when>
	<c:otherwise>
		empty
	</c:otherwise>
</c:choose>
</div>

</div>



