<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<!--[if lt IE 7]>
            <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->
	<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Project name</a>
			</div>
			<div class="navbar-collapse collapse">
				<form class="navbar-form navbar-right" role="form">
					<div class="form-group">
						<input type="text" placeholder="Email" class="form-control">
					</div>
					<div class="form-group">
						<input type="password" placeholder="Password" class="form-control">
					</div>
					<button type="submit" class="btn btn-success">Sign in</button>
				</form>
			</div>
			<!--/.navbar-collapse -->
		</div>
	</div>

	<!-- Main jumbotron for a primary marketing message or call to action -->
	<div class="jumbotron">
		<div class="container">
			<h1>Adding links</h1>
			<p>insert your link below</p>
			<p>
				<a class="btn btn-primary btn-lg" role="button"
					href='./multiobserver/'>Learn more &raquo;</a>
			</p>
		</div>
	</div>

	<div class="container">
		<!-- Example row of columns -->
		<div class="row">
			<form role="form" action="/nexus/multiobserver/admin/linkadded" method="get">
				<div class="form-group">
					<label for="exampleInputEmail1">Nowy link</label> <input
						type="text" class="form-control" id="exampleInputEmail1"
						placeholder="Podaj link">
				</div>

				<div class="form-group">
					<label for="typ">Typ produktów</label> <select
						class="form-control">

					</select>
				</div>

				<div class="form-group">
					<label for="kategoria">Kategoria produktów</label> <input
						type="text" class="form-control" id="kategoria"
						placeholder="Podaj kategoria produktów">
				</div>
				<div class="form-group">
					<label for="exampleInputPassword1">Typ produktów</label> <input
						type="text" class="form-control" id="typ"
						placeholder="Podaj typ produktów">
				</div>
				<div class="form-group">
					<label for="exampleInputFile">Załaduj z pliku</label> <input
						type="file" id="exampleInputFile">
					<p class="help-block">Zaznacz plik txt ze swojego komputera.</p>
				</div>
				<a href="./" class="btn btn-default" role="button">Cofnij</a>
				<button type="reset" class="btn btn-default">Wyczyść</button>
				<button type="submit" class="btn btn-default">Dalej</button>
			</form>
		</div>

		<hr>