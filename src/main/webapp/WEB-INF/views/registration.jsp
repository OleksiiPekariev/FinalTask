<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tags.tld"%>

<div class="card">
	<div class="card-header">
		<mytag:message key="page.registration.registration" />
	</div>
	<div class="card-body">
		<form class="needs-validation" action="/registration" method="post" novalidate>
			<div class="form-row">
				<div class="col-md-6 mb-3">
					<label for="validationCustom01"><mytag:message key="page.registration.firstname" /></label>
					<input type="text" class="form-control" id="validationCustom01" placeholder="<mytag:message key="page.registration.john"/>" name="firstName"
						required>
					<div class="valid-feedback">
						<mytag:message key="page.registration.ok" />
					</div>
					<div class="invalid-feedback">
						<mytag:message key="page.registration.please_enter_your_first_name" />
					</div>
				</div>
				<div class="col-md-6 mb-3">
					<label for="validationCustom02"><mytag:message key="page.registration.lastname" /></label>
					<input type="text" class="form-control" id="validationCustom02" placeholder="<mytag:message key="page.registration.dow"/>" name="lastName"
						required>
					<div class="valid-feedback">
						<mytag:message key="page.registration.ok" />
					</div>
					<div class="invalid-feedback">
						<mytag:message key="page.registration.please_enter_your_last_name" />
					</div>
				</div>
			</div>
			<div class="form-row mb-3">
				<label for="validationCustomEmail"><mytag:message key="page.registration.email" /></label>
				<input type="email" class="form-control" id="validationCustomEmail" placeholder="<mytag:message key="page.registration.email"/>" name="email"
					required>
				<div class="valid-feedback">
					<mytag:message key="page.registration.ok" />
				</div>
				<div class="invalid-feedback">
					<mytag:message key="page.registration.please_enter_your_email" />
				</div>
			</div>
			<div class="form-row">
				<div class="col-md-6 mb-3">
					<label for="validationCustom03"><mytag:message key="page.registration.password" /></label>
					<input type="password" class="form-control" id="validationCustom03"
						placeholder="<mytag:message key="page.registration.please_enter_your_password"/>" name="password" required>
					<div class="valid-feedback">
						<mytag:message key="page.registration.ok" />
					</div>
					<div class="invalid-feedback">
						<mytag:message key="page.registration.please_enter_your_password" />
					</div>
				</div>
				<div class="col-md-6 mb-3">
					<label for="validationCustom04"><mytag:message key="page.registration.confirm_password" /></label>
					<input type="password" class="form-control" id="validationCustom04" placeholder="<mytag:message key="page.registration.confirm_password"/>"
						name="confirmedPassword" required>
					<div class="valid-feedback">
						<mytag:message key="page.registration.ok" />
					</div>
					<div class="invalid-feedback">
						<mytag:message key="page.registration.please_confirm_your_password" />
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="form-check">
					<input class="form-check-input" type="checkbox" value="" id="invalidCheck" required>
					<label class="form-check-label" for="invalidCheck"><mytag:message key="page.registration.agree_to_terms_and_conditions" /></label>
					<div class="invalid-feedback">
						<mytag:message key="page.registration.you_must_agree_before_submitting" />
					</div>
				</div>
			</div>
			<button class="btn btn-primary" type="submit">
				<mytag:message key="page.registration.register" />
			</button>
		</form>
	</div>
</div>

<script>
	// Example starter JavaScript for disabling form submissions if there are invalid fields
	(function() {
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