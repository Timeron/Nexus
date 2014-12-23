<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="container-fluid">
	<div class="row">
		<h1>Dodaj Typ.</h1>
		<form:form commandName="form" action="addTypeResult" id="formAddType">
			
			<div class="form-group, col-md-6">
				<div class="form-group" id="divName">
					<label for="name">Nazwa</label>
					<form:input type="text" class="form-control" path="walletType.name"
						id="name" placeholder="Nazwa" />
				</div>

				<div class="form-group" id="divColor">
					<label for="color">Kolor</label>
					<form:input type="text" class="form-control"
						path="walletType.color" id="color" placeholder="Kolor" />
				</div>

				<div class="form-group" id="divIcon">
					<label for="icon">Ikona</label>
					<form:input type="text" class="form-control" path="walletType.icon"
						id="icon" placeholder="Ikona" />
				</div>

				<div class="form-group" id="divParent">
					<label for="parent">Sub Typ</label>
					<form:select path="walletTypeId" class="form-control"
 						id="walletParentType"> 
 						<form:option value="" label="Wybierz Typ" /> 
 						<form:options items="${form.walletTypes}"  
 							itemValue="id" itemLabel="name" /> 
 					</form:select> 
				</div>

				<div class="form-group" id="divIncome">
					<p id="incomeButton" class="btn btn-success btn-lg" label="income">
						<span class="glyphicon glyphicon-plus" ></span>Dochód
					</p>
					<form:hidden id="income" path="walletType.defaultValue" value="1" />
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
		alert();
	    return true; 
	});
</script>