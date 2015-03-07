<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="container-fluid">
	<!-- Example row of columns -->
	<div class="row">
		<h1>Dodaj rekord.</h1>
		<form:form commandName="form" action="walletAddRecordResult"
			id="formEditRecord">
			<div class="form-group, col-md-6">
			
				<form:hidden id="id" path="walletRecord.id" value="${form.walletRecord.id}" />
				<form:hidden id="accountId" path="walletAccountId" value="${form.walletRecord.walletAccount.id}" />
				<form:hidden id="income" path="walletRecord.income" value="${form.walletRecord.income}" />
				<form:hidden id="transfer" path="walletRecord.transfer" value="${form.walletRecord.transfer}" />
					
<!-- 				<p id="transferButton" class="btn btn-default btn-lg"> -->
<!-- 					<span class="glyphicon glyphicon-random" aria-hidden="true"> -->
<!-- 					</span> Transfer -->
<!-- 				</p> -->
				
<!-- 				<p id="incomeButton" class="btn btn-success btn-lg" label="income"> -->
<!-- 					<span class="glyphicon glyphicon-plus" aria-hidden="true"> -->
<!-- 						Dochód -->
<!-- 				</p> -->

				<div class="form-group">
					<label for="value">Wartość</label>
					<form:input id="value" type="text" class="form-control"
						path="walletRecord.value" placeholder="${form.walletRecord.value}" />
				</div>

				<div class="form-group">
					<label for="value">Data</label>
					<div class='input-group date' id='datetimepicker'>
						<form:input id="date" type='text' class="form-control" data-date-format="YYYY-MM-DD HH:mm:ss.S"
							path="walletRecord.date" value="${form.walletRecord.date}"/>
						<span class="input-group-addon"><span
							class="glyphicon glyphicon-calendar"></span> </span>
					</div>
				</div>

				<div class="form-group" id="walletAccounts">
					<label for="value">Konto</label>
					<form:select path="destinationAccountId" class="form-control"
 						id="walletAccount"> 
 						<form:option value="${form.walletRecord.destinationWalletAccount.id}" label="${form.walletRecord.destinationWalletAccount.name}" /> 
 						<form:options items="${form.walletAccount}" itemValue="id" 
 							itemLabel="name" />
 					</form:select> 
				</div>

								<div class="form-group" id="walletTypes">
								<label for="value">Typ</label>
									<form:select path="walletTypeId" class="form-control"
				 						id="walletType"> 
				 						<form:option value="${form.walletRecord.walletType.id}" label="${form.walletRecord.walletType.name}" /> 
				 						<form:options items="${form.walletTypes}" itemValue="id" 
											itemLabel="name" /> 
				 					</form:select>
								</div>

							</div>
							<div class="form-group, col-md-6">
								<label for="value">Opis</label>
								<form:textarea type="text" class="form-control" rows="9"
				 					path="walletRecord.description" placeholder="Opis" /> 
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
	$("#walletAccounts").hide();

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

	$("#transferButton").click(function() {
		if ($("#transferButton").attr("class") == "btn btn-default btn-lg") {
			$("#transferButton").attr("class", "btn btn-danger btn-lg");
			$("#walletAccounts").show();
			$("#incomeButton").hide();
			$("#walletTypes").hide();
			$("#transfer").prop('value', 1);
		} else {
			$("#transferButton").attr("class", "btn btn-default btn-lg");
			$("#walletAccounts").hide();
			$("#incomeButton").show();
			$("#walletTypes").show();
			$("#transfer").prop('value', 0);
		}
	});

	$("#expenses").click(function() {
		$("#expenses").text("Dochód");
		$("#expenses").attr("id", "income");
	});

	$(function () {
        $('#datetimepicker').datetimepicker({
           
        });
    });

	$("#formEditRecord").submit(function(e) {
		var log = "transfer " + $("#transfer").val();
		console.log(log);
		var log = "income " + $("#income").val();
		console.log(log);
		var log = "value " + $("#value").val();
		console.log(log);
		var log = "date" + $("#date").val();
		console.log(log);
		var log = "walletAccount " + $("#walletAccount").val();
		console.log(log);
		var log = "walletType " + $("#walletType").val();
		console.log(log);
		return true;
	});
</script>
