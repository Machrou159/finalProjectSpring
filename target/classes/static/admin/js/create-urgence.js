$(document).ready(function() {
	$('.ui.dropdown').dropdown();	
	$('.ui.form')
		.form({
			fields: {
				password: {
					identifier: 'label',
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

	var description = $("#label").val();
	console.log(description);	
		
	var submit = isNotEmpty(description);

	if (submit) {
		$("#creation-error-msg").hide();
		$("#ref-form").submit();
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