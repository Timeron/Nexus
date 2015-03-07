<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="container-fluid">
	<div class="row">
		<h1>Dodaj Typ.</h1>
		<form:form commandName="form" action="updateTypeResult" id="formEditType">
			<form:hidden id="id" path="newWalletType.id" value="${form.walletType.id}" />
			<div class="form-group, col-md-6">
				<div class="form-group" id="divName">
					<label for="name">Nazwa</label>
					<form:input type="text" class="form-control" path="newWalletType.name"
						id="name" value="${form.walletType.name}" />
				</div>

				<div class="form-group" id="divColor">
					<label for="color">Kolor</label>
					<form:input type="text" class="form-control"
						path="newWalletType.color" id="color" value="${form.walletType.color}" />
				</div>

				<div class="form-group" id="divIcon">
					<label for="icon">Ikona</label>
					<form:input type="text" class="form-control" path="newWalletType.icon"
						id="icon" value="${form.walletType.icon}" />
				</div>

				<div class="form-group" id="divParent">
					<label for="parent">Sub Typ</label>
					<form:select path="newWalletTypeParentTypeId" class="form-control"
  						id="walletParentType">  
  						<form:option value="${form.walletType.parentType.id}" label="${form.walletType.parentType.name}" />  
  						<form:options items="${form.walletTypes}"   
  							itemValue="id" itemLabel="name" />  
  					</form:select>  
				</div>

				<div class="form-group" id="divIncome">
					
						<c:choose>
							<c:when test="${walletType.defaultValue} == 1">
								<p id="incomeButton" class="btn btn-success btn-lg" label="income">
									<span class="glyphicon glyphicon-plus" ></span> Dochód
								</p>
							</c:when>
							<c:otherwise>
								<p id="incomeButton" class="btn btn-warning btn-lg" label="income">
									<span class="glyphicon glyphicon-minus" ></span> Wydatek
								</p>
							</c:otherwise>
						</c:choose>
					
					<form:hidden id="income" path="newWalletType.defaultValue" value="${walletType.defaultValue}" />
				</div>
			</div>

			<div class="form-group, col-md-12">
				<a href="./" class="btn btn-default">Cofnij</a>
				<button type="reset" class="btn btn-default">Resetuj</button>
				<input type="submit" value="Zapisz" class="btn btn-default" />
			</div>
		</form:form>

	</div>
</div>

<script>
	$("#incomeButton")
			.click(
					function() {
						$(this).children("span").remove();
						$(this).text("");
						if ($("#incomeButton").attr("label") == "income") {
							$(this)
									.append(
											'<span class="glyphicon glyphicon-minus" aria-hidden="true"></span> ');
							$(this).attr("class", "btn btn-warning btn-lg");
							$(this).attr("label", "expense");
							$(this).append("Wydatki");
							$("#income").prop('value', 0);
						} else {
							$(this)
									.append(
											'<span class="glyphicon glyphicon-plus" aria-hidden="true"></span> ');
							$(this).attr("class", "btn btn-success btn-lg");
							$(this).attr("label", "income");
							$(this).append("Dochód");
							$("#income").prop('value', 1);
						}
					});
	
	$("#formAddType").submit(function(e) {
		var log = "name "+$("#name").val();
		console.log(log);
		var log = "color "+$("#color").val();
		console.log(log);
		var log = "icon "+$("#icon").val();
		console.log(log);
		var log = "income "+$("#income").val();
		console.log(log);
		var log = "walletParentType "+$("#walletParentType").val();
		console.log(log);
	    return true; 
	});
</script>