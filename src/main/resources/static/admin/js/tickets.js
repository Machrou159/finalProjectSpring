$(document).ready(function() {

	$('.ui.dropdown').dropdown();

	$('#users-table').DataTable({
		"ID": [[1, "desc"]]
	});
});

function deleteTicket(id) {
	redirect('/spring-boot-thymeleaf/admin/vues/tickets/delete-ticket?tid=' + id);
}

function updateTicket(id) {
	redirect('/spring-boot-thymeleaf/admin/vues/tickets/affect-developer?tid=' + id);
}

function redirect(url) {
	window.location.href = url;
}