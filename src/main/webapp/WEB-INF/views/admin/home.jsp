<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" buffer="64kb"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tags.tld"%>

<div class="row justify-content-center">
	<ul class="nav nav-pills justify-content-center">
		<li class="nav-item"><a class="nav-link active" href="/admin/home">
				<mytag:message key="page.admin.home.cars" />
			</a></li>
		<li class="nav-item"><a class="nav-link" href="/admin/accounts">
				<mytag:message key="page.admin.home.accounts" />
			</a></li>
	</ul>
</div>
<div class="row scroll">
	<div class="col">
		<div class="card">
			<div class="card-header">
				<div class="row">
					<div class="col-2">
						<a class="btn btn-primary" href="/admin/carRegistration" role="button">
							<mytag:message key="page.admin.home.register_new_car" />
						</a>
					</div>
					<div class="col-md-10">
						<ul class="nav nav-pills justify-content-center">
							<li class="nav-item"><a class="nav-link active" href="">
									<mytag:message key="page.admin.home.all" />
								</a></li>
							<li class="nav-item"><a class="nav-link " href="/admin/cars/inOrders">
									<mytag:message key="page.admin.home.in_orders" />
								</a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="card-body">
				<form action="/admin/home" id='form'>
					<div class="row scroll">
						<div class="col-2">
							<div class="card">
								<div class="card-header">
									<mytag:message key="page.admin.home.select_manufacturer" />
								</div>
								<div class="card-body">
									<div class="custom-control custom-checkbox my-1 mr-sm-2">
										<input type="checkbox" class="custom-control-input" value="all" name="allNames" id="customControlInlineAllCars"
											<c:if test="${param.allNames == 'all'}">checked</c:if>>
										<label class="custom-control-label" for="customControlInlineAllCars"><mytag:message key="page.admin.home.all" /></label>
									</div>
									<c:forEach items="${allCarNames}" var="carName">
										<div class="custom-control custom-checkbox my-1 mr-sm-2">
											<input class="custom-control-input vert" type="checkbox" value="${carName.name}" name="carNames"
												<c:if test="${carName.checked}">checked</c:if> id="customControlInline${carName.name}">
											<label class="custom-control-label" for="customControlInline${carName.name}"> ${carName.name} </label>
										</div>
									</c:forEach>
								</div>
							</div>
						</div>
						<div class="col-10">
							<div class="card">
								<div class="card-header">
									<div class="form-inline">
										<label for="formClasses"><mytag:message key="page.admin.home.select_car_class" /> :</label>
										<div class="form-group col-md-9">
											<div class="custom-control custom-checkbox my-1 mr-sm-2">
												<input type="checkbox" class="custom-control-input" value="all" name="allClasses" id="customControlInlineClasses"
													<c:if test="${param.allClasses == 'all'}">checked</c:if>>
												<label class="custom-control-label" for="customControlInlineClasses"><mytag:message key="page.admin.home.all" /></label>
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
											<label class="my-1 mr-2" for="inlineFormCustomSelectPref"><mytag:message key="page.admin.home.sort_by" /> </label>
											<select onchange="document.getElementById('form').submit();" class="custom-select my-1 mr-sm-2" id="inlineFormCustomSelectPref"
												name="sortBy">
												<option <c:if test="${param.sortBy == 'model_ASC'}">selected</c:if> value="model_ASC"><mytag:message key="page.admin.home.model" />
													A-Z
												</option>
												<option <c:if test="${param.sortBy == 'model_DESC'}">selected</c:if> value="model_DESC"><mytag:message
														key="page.admin.home.model" /> Z-A
												</option>
												<option <c:if test="${param.sortBy == 'rent_value_ASC'}">selected</c:if> value="rent_value_ASC"><mytag:message
														key="page.admin.home.price" /> ▲
												</option>
												<option <c:if test="${param.sortBy == 'rent_value_DESC'}">selected</c:if> value="rent_value_DESC"><mytag:message
														key="page.admin.home.price" /> ▼
												</option>
											</select>
										</div>
									</div>
								</div>
								<table class="table table-hover">
									<thead class="thead-light ">
										<tr>
											<th scope="col">#</th>
											<th scope="col"><mytag:message key="page.admin.home.name" /></th>
											<th scope="col"><mytag:message key="page.admin.home.model" /></th>
											<th scope="col"><mytag:message key="page.admin.home.class" /></th>
											<th scope="col"><mytag:message key="page.admin.home.year" /></th>
											<th scope="col"><mytag:message key="page.admin.home.rent_value" /><br>$/Day</th>
											<th scope="col"><mytag:message key="page.admin.home.state" /><br> <mytag:message key="page.admin.home.number" /></th>
											<th scope="col"><mytag:message key="page.admin.home.ac" /></th>
											<th scope="col"><mytag:message key="page.admin.home.nav" /></th>
											<th scope="col"></th>
											<th scope="col"></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${cars}" var="car">
											<tr onclick=>
												<th scope="row">${car.id}</th>
												<td>${car.manufacturer}</td>
												<td>${car.model}</td>
												<td>${car.carClass}</td>
												<td>${car.year}</td>
												<td>${car.rentValuePerDay}</td>
												<td>${car.stateNumber}</td>
												<td><c:choose>
														<c:when test="${car.airConditioner}">
															<mytag:message key="page.admin.home.yes" />
														</c:when>
														<c:otherwise>
															<mytag:message key="page.admin.home.no" />
														</c:otherwise>
													</c:choose></td>
												<td><c:choose>
														<c:when test="${car.navigation}">
															<mytag:message key="page.admin.home.yes" />
														</c:when>
														<c:otherwise>
															<mytag:message key="page.admin.home.no" />
														</c:otherwise>
													</c:choose></td>
												<td><a href="/admin/carEdit?carId=${car.id}">&#9998;</a></td>
												<td><a href="/admin/carDelete?carId=${car.id}">&#10008;</a></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<nav aria-label="...">
									<ul class="pagination justify-content-center">
										<c:if test="${lastPage > 1}">
											<c:choose>
												<c:when test="${(empty param.page) || (param.page eq 1)}">
													<li class="page-item disabled"><span class="page-link"><mytag:message key="page.admin.home.first" /></span></li>
													<li class="page-item active"><a class="page-link" data-page="1" href="">1</a></li>
													<li class="page-item"><a class="page-link" data-page="2" href="">2</a></li>
													<li class="page-item"><a class="page-link" data-page="3" href="">3</a></li>
													<li class="page-item"><a class="page-link" data-page="${lastPage}" href="">
															<mytag:message key="page.admin.home.last" />
														</a></li>
												</c:when>
												<c:when test="${ param.page == lastPage && param.page != 1 }">
													<li class="page-item"><a class="page-link" data-page="1" href="">
															<mytag:message key="page.admin.home.first" />
														</a></li>
													<li class="page-item"><a class="page-link" data-page="${param.page-2}" href="">${param.page-2}</a></li>
													<li class="page-item"><a class="page-link" data-page="${param.page-1}" href="">${param.page-1}</a></li>
													<li class="page-item active"><a class="page-link" data-page="${param.page}" href="">${param.page}</a></li>
													<li class="page-item disabled"><span class="page-link"><mytag:message key="page.admin.home.last" /></span></li>
												</c:when>
												<c:otherwise>
													<li class="page-item"><a class="page-link" data-page="1" href="">
															<mytag:message key="page.admin.home.first" />
														</a></li>
													<li class="page-item"><a class="page-link" data-page="${param.page-1}" href="">${param.page-1}</a></li>
													<li class="page-item active" aria-current="page"><span class="page-link"> ${param.page} </span></li>
													<li class="page-item"><a class="page-link" data-page="${param.page + 1}" href="">${param.page + 1 }</a></li>
													<li class="page-item"><a class="page-link" data-page="${lastPage}" href="">
															<mytag:message key="page.admin.home.last" />
														</a></li>
												</c:otherwise>
											</c:choose>
										</c:if>
									</ul>
								</nav>
							</div>
						</div>
						<button type="submit" hidden="true" class="btn btn-primary hidden">Filter</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>
	$(function() {
		$('[data-toggle="popover"]').popover()
	})
</script>
<script>
	$("a.page-link")
			.click(
					function(e) {
						e.preventDefault();
						$(this).attr(
								"href",
								$('#form').serialize() + "&page="
										+ $(this).data("page"));
						$(location).attr('href',
								"/admin/home?" + $(this).attr("href"));
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
