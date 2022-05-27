$(document).ready(function() {

	$('.ui.dropdown').dropdown();

	$('#lovs-table').DataTable({
		"ID": [[1, "desc"]]
	});
});

function deleteLov(id) {
	alert("Delete -> " + id)
}

function redirect(url) {
	window.location.href = url;
}