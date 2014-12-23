<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="container-fluid">
	<div class="row">
		<h1>Dodaj konto.</h1>
		<form:form commandName="form" action="addAccountResult"
			id="formAddAccount">
			<div class="form-group" id="divName">
				<label for="name">Nazwa</label>
				<form:input type="text" class="form-control"
					path="walletAccount.name" id="name" placeholder="Nazwa" />
			</div>
			<div class="form-group" id="divDesctiption">
				<label for="name">Opis</label>
				<form:input type="text" class="form-control"
					path="walletAccount.description" id="desctiption"
					placeholder="Opis" />
			</div>

			<div class="form-group, col-md-12">
				<a href="./" class="btn btn-default">Cofnij</a>
				<button type="reset" class="btn btn-default">Resetuj</button>
				<input type="submit" value="Zapisz" class="btn btn-default" />
			</div>
		</form:form>
	</div>
</div>