<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<div class="container">
	<!-- Example row of columns -->
	<div class="row">
		<h1>Spalanie</h1>
		<form:form commandName="form" action="addRecordResult" id="form">
			<div class="form-group">
			<label>Przebieg w kilometrach</label>
				<form:input id="distance" type="text" class="form-control" path="record.distance"
					placeholder="Przebieg" />
			</div>
			<div class="form-group">
				<label for="opis">Litry paliwa</label>
				<form:input id="liters" type="text" class="form-control" path="record.liters"
					placeholder="Litry" />
			</div>
			<div class="form-group">
				<label for="value">Data</label>
				<div class='input-group date' id='datetimepicker1'>
					<form:input id="date" type='text' class="form-control"
						data-date-format="YYYY-MM-DD HH:mm:ss.S" path="record.date" />
					<span class="input-group-addon"><span
						class="glyphicon glyphicon-calendar"></span> </span>
				</div>
			</div>
			<form:hidden id="city" path="record.city" value="0" />
			<form:hidden id="mixed" path="record.mixed" value="1" />
			<div class="form-group">
				<p id="trafficButton" class="btn btn-success btn-lg, col-md-3" data-traffic="mixed">
						Mixed
				</p>
			</div>
			<br/>
			<div class="form-group, col-md-12">
				<a href="./" class="btn btn-default">Cofnij</a>
				<button type="reset" class="btn btn-default">Resetuj</button>
				<input id="submit" type="submit" value="Zapisz" class="btn btn-default" />
			</div>
		</form:form>
	</div>
</div>

<script>
$('#trafficButton').on('click', function() {
		var traffic = $(this).attr('data-traffic');
		if(traffic == 'mixed'){
			$(this).html('City');
			$(this).attr('data-traffic', 'city');
			$(this).attr('class', 'btn btn-warning btn-lg, col-md-3');
			$('#city').prop('value', 1);
			$('#mixed').prop('value', 0);
		}else if(traffic == 'city'){
			$(this).html('High way');
			$(this).attr('data-traffic', 'highWay');
			$(this).attr('class', 'btn btn-primary btn-lg, col-md-3');
			$('#city').prop('value', 0);
			$('#mixed').prop('value', 0);
		}else if(traffic == 'highWay'){
			$(this).html('Mixed');
			$(this).attr('data-traffic', 'mixed');
			$(this).attr('class', 'btn btn-success btn-lg, col-md-3');
			$('#city').prop('value', 0);
			$('#mixed').prop('value', 1);
		}
	}
	
);

$("#form").submit(function(){
	
	var distance = $("#distance").val();
	var liters = $("#liters").val();
	
	$('#distance').val(floatValue(distance));
	$('#liters').val(floatValue(liters));
});

$(function() {
	$('#datetimepicker1').datetimepicker();
});

function floatValue(value){
	var result = value.replace(',', '.');
	return result;
};

</script>