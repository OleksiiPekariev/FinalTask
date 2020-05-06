<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tags.tld"%>

<div class="card">
	<div class="card-header">
		<mytag:message key="page.login.please_login" />
	</div>
	<div class="card-body">
		<form class="needs-validation" action="/login" method="POST">
			<div class="form-group">
				<label><mytag:message key="page.login.login" /></label>
				<input type="email" class="form-control" name="login" placeholder="<mytag:message key="page.login.enter_your_login"/>" required>
			</div>
			<div class="form-group">
				<label><mytag:message key="page.login.password" /></label>
				<input type="password" class="form-control" name="password" placeholder="<mytag:message key="page.login.password"/>" required>
			</div>
			<button type="submit" class="btn btn-primary">
				<mytag:message key="page.login.submit" />
			</button>
			<a class="btn btn-outline-info pull-right" href="/registration" role="button">
				<mytag:message key="page.login.registration" />
			</a>
			<a href="/forgot-password" class="btn btn-link">
				<mytag:message key="page.login.forgot_your_password" />
			</a>
		</form>
	</div>
</div>

<script>
	$(function() {
		'use strict';
		window.addEventListener('load', function() {
			// Fetch all the forms we want to apply custom Bootstrap validation styles to
			var forms = document.getElementsByClassName('needs-validation');
			// Loop over them and prevent submission
			var validation = Array.prototype.filter.call(forms, function(form) {
				form.addEventListener('submit', function(event) {
					if (form.checkValidity() === false) {
						event.preventDefault();
						event.stopPropagation();
					}
					form.classList.add('was-validated');
				}, false);
			});
		}, false);
	})();
</script>
