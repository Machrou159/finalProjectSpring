$(document).ready(function() {
	$('.ui.dropdown').dropdown();

	$('.ui.form')
		.form({
			fields: {
				password: {
					identifier: 'description',
					rules: [
						{
							type: 'length[0]',
							prompt: 'La description est obligatoire'
						}
					]
				}
			}
		});
});

function handleSubmit() {

	var description = $("#description").val();
	var status = $("#status-id").val();
	var urgence = $("#urgence-id").val();
	var software = $("#software-id").val();

	console.log(description);	
	console.log(status);	
	console.log(urgence);	
	console.log(software);
		
	var submit = isNotEmpty(description) && isNotEmpty(status) && isNotEmpty(urgence) && isNotEmpty(software);

	if (submit) {
		$("#creation-error-msg").hide();
		$("#sp-ticket-form").submit();
	}
	else {
		$("#creation-error-msg").show();
		return false;
	}
}

function isNotEmpty(value) {
	return value != null;
}

function redirect(url) {
	window.location.href = url;
}