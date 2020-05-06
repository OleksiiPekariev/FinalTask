<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tags.tld"%>

<form action="/cars" id='form'>
	<div class="row scroll">
		<div class="col-md-2">
			<div class="card">
				<div class="card-header">
					<mytag:message key="page.cars.select_manufacturer" />
				</div>
				<div class="card-body">
					<div class="custom-control custom-checkbox my-1 mr-sm-2">
						<input type="checkbox" class="custom-control-input" value="all" name="allNames" id="customControlInlineAllCars"
							<c:if test="${param.allNames == 'all'}">checked</c:if>>
						<label class="custom-control-label" for="customControlInlineAllCars"><mytag:message key="page.cars.all" /></label>
					</div>
					<c:forEach items="${allCarNames}" var="carName">
						<div class="custom-control custom-checkbox my-1 mr-sm-2">
							<input class="custom-control-input vert" type="checkbox" value="${carName.name}" name="carNames" <c:if test="${carName.checked}">checked</c:if>
								id="customControlInline${carName.name}">
							<label class="custom-control-label" for="customControlInline${carName.name}"> ${carName.name} </label>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
		<div class="col-md">
			<div class="card">
				<div class="card-header">
					<div class="form-inline">
						<label for="formClasses"><mytag:message key="page.cars.select_car_class" /> :</label>
						<div class="form-group col-md-9">
							<div class="custom-control custom-checkbox my-1 mr-sm-2">
								<input type="checkbox" class="custom-control-input" value="all" name="allClasses" id="customControlInlineClasses"
									<c:if test="${param.allClasses == 'all'}">checked</c:if>>
								<label class="custom-control-label" for="customControlInlineClasses"><mytag:message key="page.cars.all" /></label>
							</div>
							<c:forEach items="${allCarClasses}" var="carClass">
								<div class="custom-control custom-checkbox my-1 mr-sm-2">
									<input type="checkbox" class="custom-control-input horiz" value="${carClass.name}" name="carClasses"
										<c:if test="${carClass.checked}">checked</c:if> id="customControlInline${carClass.name}">
									<label class="custom-control-label" for="customControlInline${carClass.name}">${carClass.name}</label>
								</div>
							</c:forEach>
						</div>
						<div class="form-group col-md-2">
							<label class="my-1 mr-2" for="inlineFormCustomSelectPref"><mytag:message key="page.cars.sort_by" /></label>
							<select onchange="document.getElementById('form').submit();" class="custom-select my-1 mr-sm-2" id="inlineFormCustomSelectPref" name="sortBy">
								<option <c:if test="${param.sortBy == 'model_ASC'}">selected</c:if> value="model_ASC"><mytag:message key="page.cars.model" /> A-Z
								</option>
								<option <c:if test="${param.sortBy == 'model_DESC'}">selected</c:if> value="model_DESC"><mytag:message key="page.cars.model" /> Z-A
								</option>
								<option <c:if test="${param.sortBy == 'rent_value_ASC'}">selected</c:if> value="rent_value_ASC"><mytag:message key="page.cars.price" />
									▲
								</option>
								<option <c:if test="${param.sortBy == 'rent_value_DESC'}">selected</c:if> value="rent_value_DESC"><mytag:message
										key="page.cars.price" /> ▼
								</option>
							</select>
						</div>
					</div>
				</div>
				<br>
				<div class="row row-cols-1 row-cols-md-4">
					<c:forEach items="${cars}" var="car">
						<div class="col mb-4">
							<div class="card" style="width: 18rem;">
								<a href="/client/carOrder?carID=${car.id}">
									<img src="/static/focus.jpg" class="card-img-top" alt="...">
								</a>
								<div class="card-body">
									<div class="row">
										<h6 class="card-title col-md-9">${car.manufacturer}&nbsp;${car.model}</h6>
									</div>
									<table class="table table-sm">
										<tr>
											<th scope="row"><mytag:message key="page.cars.rent_value_per_day" /> :</th>
											<td>${car.rentValuePerDay}$</td>
										<tr>
										<tr>
											<th scope="row"><mytag:message key="page.cars.class" /> :</th>
											<td>${car.carClass}</td>
										<tr>
										<tr>
											<th scope="row"><mytag:message key="page.cars.year" /> :</th>
											<td>${car.year}</td>
										<tr>
										<tr>
											<th scope="row"><mytag:message key="page.cars.ac" /> :</th>
											<td><c:choose>
													<c:when test="${car.airConditioner}">
														<mytag:message key="page.cars.yes" />
													</c:when>
													<c:otherwise>
														<mytag:message key="page.cars.no" />
													</c:otherwise>
												</c:choose></td>
										<tr>
										<tr>
											<th scope="row"><mytag:message key="page.cars.navigation" /> :</th>
											<td><c:choose>
													<c:when test="${car.navigation}">
														<mytag:message key="page.cars.yes" />
													</c:when>
													<c:otherwise>
														<mytag:message key="page.cars.no" />
													</c:otherwise>
												</c:choose></td>
										<tr>
									</table>
								</div>
								<button type="button" class="btn btn-link" data-container="body" data-toggle="popover" data-placement="top"
									data-content="${car.equipmentInformation}">
									<mytag:message key="page.cars.additional_equipment_information" />
								</button>
								<div class="card-footer">
									<a href="/client/carOrder?carID=${car.id}" class="btn btn-primary">
										<mytag:message key="page.cars.rent_this_car" />
									</a>
								</div>

							</div>
						</div>
					</c:forEach>
				</div>
				<nav aria-label="...">
					<ul class="pagination justify-content-center">
						<c:if test="${lastPage ne 1}">
							<c:choose>
								<c:when test="${(empty param.page) || (param.page eq 1)}">
									<li class="page-item disabled"><span class="page-link"><mytag:message key="page.cars.first" /></span></li>
									<li class="page-item active"><a class="page-link" data-page="1" href="">1</a></li>
									<li class="page-item"><a class="page-link" data-page="2" href="">2</a></li>
									<li class="page-item"><a class="page-link" data-page="3" href="">3</a></li>
									<li class="page-item"><a class="page-link" data-page="${lastPage}" href="">
											<mytag:message key="page.cars.last" />
										</a></li>
								</c:when>
								<c:when test="${ param.page == lastPage && param.page != 1 }">
									<li class="page-item"><a class="page-link" data-page="1" href="">
											<mytag:message key="page.cars.first" />
										</a></li>
									<li class="page-item"><a class="page-link" data-page="${param.page-2}" href="">${param.page-2}</a></li>
									<li class="page-item"><a class="page-link" data-page="${param.page-1}" href="">${param.page-1}</a></li>
									<li class="page-item active"><a class="page-link" data-page="${param.page}" href="">
											<mytag:message key="page.cars.last" />
										</a></li>
									<li class="page-item disabled"><span class="page-link"><mytag:message key="page.cars.last" /></span></li>
								</c:when>
								<c:otherwise>
									<li class="page-item"><a class="page-link" data-page="1" href="">
											<mytag:message key="page.cars.first" />
										</a></li>
									<li class="page-item"><a class="page-link" data-page="${param.page-1}" href="">${param.page-1}</a></li>
									<li class="page-item active" aria-current="page"><span class="page-link"> ${param.page} </span></li>
									<li class="page-item"><a class="page-link" data-page="${param.page + 1}" href="">${param.page + 1 }</a></li>
									<li class="page-item"><a class="page-link" data-page="${lastPage}" href="">
											<mytag:message key="page.cars.last" />
										</a></li>
								</c:otherwise>
							</c:choose>
						</c:if>
					</ul>
				</nav>
			</div>
		</div>
	</div>
	<button type="submit" hidden="true" class="btn btn-primary hidden">Filter</button>
</form>
<script src="/static/js/jquery-3.4.1.js"></script>
<script>
	$(function() {
		$('[data-toggle="popover"]').popover()
	})

	$("a.page-link").click(
			function(e) {
				e.preventDefault();
				$(this).attr(
						"href",
						$('#form').serialize() + "&page="
								+ $(this).data("page"));
				$(location).attr('href', "/cars?" + $(this).attr("href"));
			});
</script>
<script>
	console.log(1);

	$(function() {
		$('.example-popover').popover({
			container : 'body'
		})
	})

	$("#customControlInlineAllCars").change(function() {
		$(".vert").removeAttr("checked");
		document.getElementById('form').submit();
	});

	$(".vert").change(function() {
		$("#customControlInlineAllCars").removeAttr("checked");
		document.getElementById('form').submit();
	});

	$("#customControlInlineClasses").change(function() {
		$(".horiz").removeAttr("checked");
		document.getElementById('form').submit();
	});

	$(".horiz").change(function() {
		$("#customControlInlineClasses").removeAttr("checked");
		document.getElementById('form').submit();
	});
</script>
