/**
 * 
 */
alert("!!!");
	var packageNr = 0;
	$( "#addNextPackage" ).click(function() {
		packageNr++;
		$("#addPackage").append( 
		"<div class='form-group col-md-3'>"+
"			<div id='divPackageName"+packageNr+"'>"+
"				<form:input type='text' class='form-control' path='observedLinksPackage["+packageNr+"].name' placeholder='Name' id='packageName"+packageNr+"' />"+
"			</div>"+
"		</div>"+
"		<div class='form-group col-md-5'>"+
"			<div id='divPackageUrl"+packageNr+"'>"+
"				<form:input type='text' class='form-control' path='observedLinksPackage["+packageNr+"].url' placeholder='Url' id='packageUrl"+packageNr+"' />"+
"			</div>"+
"		</div>"+
"		<div class='form-group, col-md-3'>"+
"			<div id='divPackageSiteType"+packageNr+"'>"+
"				<form:select path='observedLinksPackage["+packageNr+"].siteType.description' class='form-control' id='packageSiteType"+packageNr+"'>"+
"					<form:options items='${addLinksPackageToOldSiteForm.siteTypes}' />"+
"				</form:select>"+
"			</div>"+
"		</div>"+
"		<div class='form-group col-md-1'>"+
"			<button type='button' class='btn btn-danger'>-</button>"+
"		</div>" );
	});