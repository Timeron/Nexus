<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<table class="table table-striped">
	<tr>
		<th>#</th>
		<th>Nazwa</th>
		<th>URL</th>
		<th>Zatwierdzony</th>
	</tr>
		<tr>
			<td>${form.observedSite.id}</td>
			<td>${form.observedSite.articleName}</a></td>
			<td>${form.observedSite.url}</a></td>
			<td>${form.observedSite.approvedProductKay}</td>
		</tr>
</table>

