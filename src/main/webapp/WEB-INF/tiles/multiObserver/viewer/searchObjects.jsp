<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
HAHA
<c:forEach items="${form.observedObjects}" var="observedObject">
	${observedObject.name}<br/>
</c:forEach>

