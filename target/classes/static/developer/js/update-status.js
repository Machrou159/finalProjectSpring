$(document).ready(function() {
	$('.ui.dropdown').dropdown();
});

function handleSubmit() {

	var status = $("#status-id").val();
		
	var submit = isNotEmpty(status);

	if (submit) {
		$("#update-error-msg").hide();
		$("#sp-ticket-form").submit();
	}
	else {
		$("#update-error-msg").show();
		return false;
	}
}

function isNotEmpty(value) {
	return value != null;
}

function redirect(url) {
	window.location.href = url;
}