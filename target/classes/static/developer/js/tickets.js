$(document).ready(function() {

	$('.ui.dropdown').dropdown();

	$('#users-table').DataTable({
		"ID": [[1, "desc"]]
	});
});

function updateStatus(id) {
	var sid = document.getElementById('s' + id).innerText;
	sid = sid.replace(/\s/g, '');
	redirect('/spring-boot-thymeleaf/developer/vues/tickets/update-status?tid=' + id + '&sid=' + sid);
}

function redirect(url) {
	window.location.href = url;
}