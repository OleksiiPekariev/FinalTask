<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tags.tld"%>

<div class="row scroll">
	<div class="col-md-9">
		<div class="card">
			<div class="card-header">
				<mytag:message key="page.client.neworder.fill_this_form_to_create_order" />
			</div>
			<div class="card-body">
				<form class="needs-validation" action="/client/carOrder" method="post" id="form" novalidate>
					<div class="form-row">
						<div class="col-md-6 mb-3">
							<label for="validationCustom01"><mytag:message key="page.client.neworder.firstname" /></label>
							<input type="text" class="form-control" id="validationCustom01" placeholder="<mytag:message key="page.client.neworder.john"/>" name="firstName"
								value="${CURRENT_ACCOUNT.firstName}" disabled>
							<div class="valid-feedback">
								<mytag:message key="page.client.neworder.ok" />
							</div>
							<div class="invalid-feedback">
								<mytag:message key="page.client.neworder.please_enter_your_first_name" />
							</div>
						</div>
						<div class="col-md-6 mb-3">
							<label for="validationCustom02"><mytag:message key="page.client.neworder.lastname" /></label>
							<input type="text" class="form-control" id="validationCustom02" placeholder="<mytag:message key="page.client.neworder.dow"/>" name="lastName"
								value="${CURRENT_ACCOUNT.lastName}" disabled>
							<div class="valid-feedback">
								<mytag:message key="page.client.neworder.ok" />
							</div>
							<div class="invalid-feedback">
								<mytag:message key="page.client.neworder.please_enter_your_last_name" />
							</div>
						</div>
					</div>
					<div class="form-row mb-3">
						<label for="validationCustom03"><mytag:message key="page.client.neworder.email" /></label>
						<input type="email" class="form-control" id="validationCustom03" placeholder="<mytag:message key="page.client.neworder.email"/>" name="email"
							value="${CURRENT_ACCOUNT.email}" disabled>
						<div class="valid-feedback">
							<mytag:message key="page.client.neworder.ok" />
						</div>
						<div class="invalid-feedback">
							<mytag:message key="page.client.neworder.please_enter_your_address" />
						</div>
					</div>
					<div class="form-row mb-3">
						<div class="col-md-6 mb-3">
							<label for="validationCustom04"><mytag:message key="page.client.neworder.passport_data" /></label>
							<input type="text" class="form-control" id="validationCustom04" placeholder="<mytag:message key="page.client.neworder.passport_data_format"/>"
								name="passportData" <c:if test="${not empty CURRENT_ACCOUNT.passportData}">value="${CURRENT_ACCOUNT.passportData}"</c:if>
								<c:choose>
								<c:when test="${not empty CURRENT_ACCOUNT.passportData}">disabled</c:when>
								<c:otherwise>required</c:otherwise>
							</c:choose>>
							<div class="valid-feedback">
								<mytag:message key="page.client.neworder.ok" />
							</div>
							<div class="invalid-feedback">
								<mytag:message key="page.client.neworder.passport_data_is_required" />
							</div>
						</div>
						<div class="col-md-6 mb-3">
							<label for="validationCustomPhoneNumber"><mytag:message key="page.client.neworder.phone_number" /></label>
							<div class="input-group">
								<div class="input-group-prepend">
									<span class="input-group-text" id="inputGroupPrepend">+</span>
								</div>
								<input type="number" class="form-control" id="validationCustomPhoneNumber"
									placeholder="<mytag:message key="page.client.neworder.phone_number"/>" value="${CURRENT_ACCOUNT.phone}" name="phone"
									aria-describedby="inputGroupPrepend"
									<c:choose>
								<c:when test="${not empty CURRENT_ACCOUNT.phone}">disabled</c:when>
								<c:otherwise>required</c:otherwise>
							</c:choose>>
							</div>
						</div>
					</div>
					<div class="form-row mb-3">
						<label for="validationCustom05"><mytag:message key="page.client.neworder.address" /></label>
						<input type="text" class="form-control" id="validationCustom05" placeholder="<mytag:message key="page.client.neworder.address"/>" name="address"
							value="${CURRENT_ACCOUNT.address}"
							<c:choose>
								<c:when test="${not empty CURRENT_ACCOUNT.address}">disabled</c:when>
								<c:otherwise>required</c:otherwise>
							</c:choose>>
						<div class="valid-feedback">
							<mytag:message key="page.client.neworder.ok" />
						</div>
						<div class="invalid-feedback">
							<mytag:message key="page.client.neworder.please_enter_your_address" />
						</div>
					</div>
					<div class="form-row mb-3">
						<div class="col-md-6 mb-3">
							<label for="validationCustom06"><mytag:message key="page.client.neworder.date_from" /></label>
							<input type="date" class="form-control" id="validationCustom06" placeholder="Strting date"
								min="<fmt:formatDate value="${currentDate}" pattern="yyyy-MM-dd"/>" name="startDate" required>
							<div class="valid-feedback">
								<mytag:message key="page.client.neworder.ok" />
							</div>
							<div class="invalid-feedback">
								<mytag:message key="page.client.neworder.please_enter_your_start_date" />
							</div>
						</div>
						<div class="col-md-6 mb-3">
							<label for="validationCustom07"><mytag:message key="page.client.neworder.date_to" /></label>
							<input type="date" class="form-control" id="validationCustom07" placeholder="Ending date"
								min="<fmt:formatDate value="${currentDate}" pattern="yyyy-MM-dd"/>" name="endDate" required>
							<div class="valid-feedback">
								<mytag:message key="page.client.neworder.ok" />
							</div>
							<div class="invalid-feedback">
								<mytag:message key="page.client.neworder.please_enter_the_end_date" />
							</div>
						</div>
					</div>
					<div class="form-row mb-3">
						<div class="custom-control custom-checkbox my-1 mr-sm-2">
							<input type="checkbox" class="custom-control-input" id="driverCheck" name="withDriver" value="true">
							<label class="custom-control-label" for="driverCheck"><mytag:message key="page.client.neworder.check_if_you_need_a_driver" /></label>
						</div>
					</div>
					<a class="btn btn-outline-info pull-left" href="/cars" role="button">
						<mytag:message key="page.admin.car_registration.back" />
					</a>
					<button type="submit" class="btn btn-primary" id="showModal">
						<mytag:message key="page.client.neworder.register" />
					</button>
				</form>
			</div>
		</div>
	</div>
	<div class="col-md-2">
		<div class="col mb-4">
			<div class="card" style="width: 18rem;">
				<div class="card-header">
					<mytag:message key="page.client.neworder.your_car" />
				</div>
				<img src="/static/focus.jpg" class="card-img-top" alt="...">
				<div class="card-body">
					<div class="row">
						<h6 class="card-title col-md-9">${car.manufacturer}&nbsp;${car.model}</h6>
					</div>
					<table class="table table-sm">
						<tr>
							<th scope="row"><mytag:message key="page.client.neworder.rent_value_per_day" /> :</th>
							<td>${car.rentValuePerDay}$</td>
						<tr>
						<tr>
							<th scope="row"><mytag:message key="page.client.neworder.class" /> :</th>
							<td>${car.carClass}</td>
						<tr>
						<tr>
							<th scope="row"><mytag:message key="page.client.neworder.year" /> :</th>
							<td>${car.year}</td>
						<tr>
						<tr>
							<th scope="row"><mytag:message key="page.client.neworder.ac" /> :</th>
							<td><c:choose>
									<c:when test="${car.airConditioner}">
										<mytag:message key="page.client.neworder.yes" />
									</c:when>
									<c:otherwise>
										<mytag:message key="page.client.neworder.no" />
									</c:otherwise>
								</c:choose></td>
						<tr>
						<tr>
							<th scope="row"><mytag:message key="page.client.neworder.navigation" /> :</th>
							<td><c:choose>
									<c:when test="${car.navigation}">
										<mytag:message key="page.client.neworder.yes" />
									</c:when>
									<c:otherwise>
										<mytag:message key="page.client.neworder.no" />
									</c:otherwise>
								</c:choose></td>
						<tr>
					</table>

					<button type="button" class="btn btn-link" data-container="body" data-toggle="popover" data-placement="top"
						data-content="${car.equipmentInformation}">
						<mytag:message key="page.client.neworder.additional_equipment_information" />
					</button>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>
	$(function() {
		$('[data-toggle="popover"]').popover()
	})

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
	})
</script>
