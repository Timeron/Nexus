<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container-fluid">
	<table class="table table-striped">
		<tr>
			<th>#</th>
			<th>Nazwa</th>
			<th>Klucz produktu</th>
		</tr>
		<c:forEach items="${form.results}" var="observedObject">
			<tr>
				<td>${observedObject.id}</td>
				<td><a
					href="/nexus/multiobserver/viewer/editObservedObject?id=${observedObject.id}">${observedObject.name}</a></td>
				<td>${observedObject.productKay}</td>
			</tr>
		</c:forEach>
	</table>
</div>

