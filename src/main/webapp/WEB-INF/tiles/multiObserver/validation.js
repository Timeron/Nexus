/**
 * 
 */

function checkIfExist(value, id) {

	if (value == "") {
		var spanNode = document.createElement("span");
		spanNode.setAttribute("class",
				"glyphicon glyphicon-remove form-control-feedback");
		spanNode.setAttribute("name", "validation");

		document.getElementById(id).setAttribute("class",
				"form-group has-error has-feedback");
		document.getElementById(id).appendChild(spanNode);
		return false;
	} else {
		var spanNode = document.createElement("span");
		spanNode.setAttribute("class",
				"glyphicon glyphicon-ok form-control-feedback");
		spanNode.setAttribute("name", "validation");

		document.getElementById(id).setAttribute("class",
				"form-group has-success has-feedback");
		document.getElementById(id).appendChild(spanNode);
		return true;
	}
}

function removeValidation() {
	var validationNode = document.getElementsByName("validation");
	i = validationNode.length;
	for (var x = 0; x < i; x++) {
		validationNode[0].parentNode.removeChild(validationNode[0]);
	}
}