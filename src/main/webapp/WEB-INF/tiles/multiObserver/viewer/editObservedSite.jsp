<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container-fluid">
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
	
	<table class="table table-striped">
		<tr>
			<th>#</th>
			<th>Nazwa</th>
			<th>Product Kay</th>
		</tr>
		<tr>
			<td>${form.observedObject.id}</td>
			<td>${form.observedObject.articleName}</a></td>
			<td>${form.observedObject.url}</a></td>
		</tr>
	</table>
</div>

