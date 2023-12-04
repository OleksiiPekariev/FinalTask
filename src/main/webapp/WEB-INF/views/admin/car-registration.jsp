<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tags.tld"%>

<div class="card">
	<div class="card-header">
		<mytag:message key="page.admin.car_registration.registration" />
	</div>
	<div class="card-body">
		<form class="needs-validation" action="/admin/carRegistration" method="post" novalidate>
			<div class="form-row">
				<div class="col-md-6 mb-3">
					<label for="validationCustom01"><mytag:message key="page.admin.car_registration.car_name" /></label>
					<select class="form-control" id="validationCustom01" name="carName" required>
						<option selected><mytag:message key="page.admin.car_registration.choose" />...
						</option>
						<c:forEach items="${allCarNames}" var="carName">
							<option value="${carName.name}">${carName.name}</option>
						</c:forEach>
					</select>
					<div class="valid-feedback">
						<mytag:message key="page.admin.car_registration.ok" />
					</div>
					<div class="invalid-feedback">
						<mytag:message key="page.admin.car_registration.please_select_car_manufacturer" />
						!
					</div>
				</div>
				<div class="col-md-6 mb-3">
					<label for="validationCustom02"><mytag:message key="page.admin.car_registration.car_model" /></label>
					<select class="form-control" id="validationCustom02" name="carModel" required>
					</select>
					<div class="valid-feedback">
						<mytag:message key="page.admin.car_registration.ok" />
						!
					</div>
					<div class="invalid-feedback">
						<mytag:message key="page.admin.car_registration.please_select_car_model" />
						!
					</div>
				</div>
			</div>
			<div class="form-row">
				<div class="col-md-6 mb-3">
					<label for="validationCustom03"><mytag:message key="page.admin.car_registration.manufactured" /></label>
					<select class="form-control" id="validationCustom03" name="year" required>
						<c:forEach items="${years}" var="year">
							<option value="${year}">${year}</option>
						</c:forEach>
					</select>
					<div class="valid-feedback">
						<mytag:message key="page.admin.car_registration.ok" />
						!
					</div>
					<div class="invalid-feedback">
						<mytag:message key="page.admin.car_registration.please_select_manufacturing_year" />
						.
					</div>
				</div>
				<div class="col-md-6 mb-3">
					<label for="validationCustom04"><mytag:message key="page.admin.car_registration.state_number" /></label>
					<input type="text" class="form-control" id="validationCustom04" placeholder="<mytag:message key="page.admin.car_registration.state_number" />"
						name="stateNumber" required>
					<div class="valid-feedback">
						<mytag:message key="page.admin.car_registration.ok" />
						!
					</div>
					<div class="invalid-feedback">
						<mytag:message key="page.admin.car_registration.please_enter_the_state_number" />
						.
					</div>
				</div>
			</div>
			<div class="form-row mb-3">
				<label for="validationRentValue"><mytag:message key="page.admin.car_registration.rent_value" /></label>
				<div class="input-group">
					<span class="input-group-text" id="inputGroupPrepend">$</span>
					<input type="number" class="form-control" id="validationRentValue"
						placeholder="<mytag:message key="page.admin.car_registration.rent_value_per_day" />" name="rentValue" aria-describedby="inputGroupPrepend"
						required>
				</div>
			</div>
			<div class="form-row mb-3">
				<label for="validationTextarea"><mytag:message key="page.admin.car_registration.please_place_here_information_about_additional_equipment" /></label>
				<textarea class="form-control" id="validationTextarea"
					placeholder="<mytag:message key="page.admin.car_registration.please_place_here_information_about_additional_equipment" />"
					name="equipmentInformation"></textarea>
			</div>
			<div class="form-row mb-3">
				<div class="custom-control custom-checkbox my-1 mr-sm-2">
					<input type="checkbox" class="custom-control-input" id="acCheck" name="airConditioner" value="true">
					<label class="custom-control-label" for="acCheck"><mytag:message key="page.admin.car_registration.air_conditioner" />?</label>
				</div>
				<div class="custom-control custom-checkbox my-1 mr-sm-2">
					<input type="checkbox" class="custom-control-input" id="navigationCheck" name="navigation" value="true">
					<label class="custom-control-label" for="navigationCheck"><mytag:message key="page.admin.car_registration.navigation" />?</label>
				</div>
			</div>

			<a class="btn btn-outline-info pull-left" href="/admin/home" role="button">
				<mytag:message key="page.admin.car_registration.back" />
			</a>
			<button class="btn btn-primary pull-right" type="submit">
				<mytag:message key="page.admin.car_registration.register" />
			</button>
		</form>
	</div>
</div>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
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

	$(document).ready(function() {
		$("#validationCustom01").change(function() {
			var data = {};
			data = {
				"carName" : $("#validationCustom01").val()
			};
			//
			$.ajax({
				type : "GET",//Метод передачи
				data : data,//Передаваемые данные в JSON - формате
				url : '/ajax/sectionscontroller',//Название сервлета
				success : function(serverData)//Если запрос удачен
				{
					$("#validationCustom02").html(serverData.modelsDropdown);
					console.log(serverData.modelsDropdown);
				},
				error : function(e)//Если запрос не удачен
				{
					console.log(e);
				}
			});
		});
	});
</script>


