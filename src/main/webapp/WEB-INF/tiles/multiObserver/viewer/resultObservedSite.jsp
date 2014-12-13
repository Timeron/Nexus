<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container-fluid">
	<table class="table table-striped">
		<tr>
			<th>#</th>
			<th>Nazwa</th>
			<th>URL</th>
			<th>Zatwierdzony</th>
		</tr>
		<c:forEach items="${form.results}" var="observedSites">
			<tr>
				<td>${observedSites.id}</td>
				<td><a
					href="/timeron-nexus/multiobserver/viewer/editObservedSite?id=${observedSites.id}">${observedSites.articleName}</a></td>
				<td>${observedSites.url}</td>
				<td>${observedSites.approvedProductKay}</td>
			</tr>
		</c:forEach>
	</table>
</div>

