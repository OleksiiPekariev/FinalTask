<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" buffer="64kb"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tags.tld"%>


<div class="row scroll">
	<div class="col">
		<div class="card">
			<div class="card-header">
				<div class="row">
					<div class="col-md-2">
						<mytag:message key="page.manager.home.orders" />
					</div>
					<div class="col-md-10">
						<ul class="nav nav-pills nav-justified">
							<li class="nav-item"><a class="nav-link status <c:if test="${(empty param.status)||(param.status eq 0)}">active</c:if>" data-status="0"
									href="/manager/home">
									<mytag:message key="page.manager.home.all" />
								</a></li>
							<li class="nav-item"><a class="nav-link status <c:if test="${param.status eq 1}">active</c:if>" data-status="1" href="?status=1">
									<mytag:message key="page.manager.home.new" />
								</a></li>
							<li class="nav-item"><a class="nav-link status <c:if test="${param.status eq 2}">active</c:if>" data-status="2" href="?status=2">
									<mytag:message key="page.manager.home.confirmed" />
								</a></li>
							<li class="nav-item"><a class="nav-link status <c:if test="${param.status eq 4}">active</c:if>" data-status="4" href="?status=4">
									<mytag:message key="page.manager.home.finished" />
								</a></li>
							<li class="nav-item"><a class="nav-link status <c:if test="${param.status eq 8}">active</c:if>" data-status="8" href="?status=8">
									<mytag:message key="page.manager.home.declined" />
								</a></li>
							<li class="nav-item"><a class="nav-link status <c:if test="${param.status eq 16}">active</c:if>" data-status="16" href="?status=16">
									<mytag:message key="page.manager.home.closed" />
								</a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="card-body">
				<form action="/manager/home" id='form'>
					<div class="row scroll">
						<div class="col-2">
							<div class="card">
								<div class="card-header">
									<mytag:message key="page.manager.home.select_manufacturer" />
								</div>
								<div class="card-body">
									<div class="custom-control custom-checkbox my-1 mr-sm-2">
										<input type="checkbox" class="custom-control-input" value="all" name="allNames" id="customControlInlineAllCars"
											<c:if test="${param.allNames == 'all'}">checked</c:if>>
										<label class="custom-control-label" for="customControlInlineAllCars"><mytag:message key="page.manager.home.all" /></label>
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
										<label for="formClasses"><mytag:message key="page.manager.home.select_car_class" /> :</label>
										<div class="form-group col-md-4">
											<div class="custom-control custom-checkbox my-1 mr-sm-2">
												<input type="checkbox" class="custom-control-input" value="all" name="allClasses" id="customControlInlineClasses"
													<c:if test="${param.allClasses == 'all'}">checked</c:if>>
												<label class="custom-control-label" for="customControlInlineClasses"><mytag:message key="page.manager.home.all" /></label>
											</div>
											<c:forEach items="${allCarClasses}" var="carClass">
												<div class="custom-control custom-checkbox my-1 mr-sm-2">
													<input type="checkbox" class="custom-control-input horiz" value="${carClass.name}" name="carClasses"
														<c:if test="${carClass.checked}">checked</c:if> id="customControlInline${carClass.name}">
													<label class="custom-control-label" for="customControlInline${carClass.name}">${carClass.name}</label>
												</div>
											</c:forEach>
										</div>
										<div class="form-group col-md-6">
											<label class="my-1 mr-2" for="inlineFormOrderBy"><mytag:message key="page.manager.home.sort_by" /></label>
											<select class="custom-select my-1 mr-sm-2" id="inlineFormOrderBy" name="sortBy">
												<option <c:if test="${param.sortBy == 'lastName_ASC'}">selected</c:if> data-order="lastName_ASC" value="lastName_ASC"><mytag:message
														key="page.manager.home.last_name" />&nbsp;A-Z
												</option>
												<option <c:if test="${param.sortBy == 'lastName_DESC'}">selected</c:if> data-order="lastName_DESC" value="lastName_DESC"><mytag:message
														key="page.manager.home.last_name" />&nbsp;Z-A
												</option>
												<option <c:if test="${param.sortBy == 'expDate_ASC'}">selected</c:if> data-order="expDate_ASC" value="expDate_ASC"><mytag:message
														key="page.manager.home.expiration_date" />&nbsp;▲
												</option>
												<option <c:if test="${param.sortBy == 'expDate_DESC'}">selected</c:if> data-order="expDate_DESC" value="expDate_DESC"><mytag:message
														key="page.manager.home.expiration_date" />&nbsp;▼
												</option>
												<option <c:if test="${param.sortBy == 'startDate_ASC'}">selected</c:if> data-order="startDate_ASC" value="startDate_ASC"><mytag:message
														key="page.manager.home.starting_date" />&nbsp;▲
												</option>
												<option <c:if test="${param.sortBy == 'startDate_DESC'}">selected</c:if> data-order="startDate_DESC" value="startDate_DESC"><mytag:message
														key="page.manager.home.starting_date" />&nbsp;▼
												</option>
											</select>
										</div>
									</div>
								</div>
								<table class="table table-hover">
									<thead class="thead-light ">
										<tr>
											<th scope="col">#</th>
											<th scope="col"><mytag:message key="page.manager.home.name" /></th>
											<th scope="col"><mytag:message key="page.manager.home.model" /></th>
											<th scope="col"><mytag:message key="page.manager.home.state" /> <br> <mytag:message key="page.manager.home.number" /></th>
											<th scope="col"><mytag:message key="page.manager.home.first_name" /></th>
											<th scope="col"><mytag:message key="page.manager.home.last_name" /></th>
											<th scope="col"><mytag:message key="page.manager.home.created" /></th>
											<th scope="col"><mytag:message key="page.manager.home.confirmed" /></th>
											<th scope="col"><mytag:message key="page.manager.home.starts" /></th>
											<th scope="col"><mytag:message key="page.manager.home.ends" /></th>
											<th scope="col"><mytag:message key="page.manager.home.days" /></th>
											<th scope="col"><mytag:message key="page.manager.home.total" /> <br> <mytag:message key="page.manager.home.price" /> $
											<th scope="col"><mytag:message key="page.manager.home.paid" /></th>
											<c:if test="${param.status eq 8}">
												<th scope="col"><mytag:message key="page.manager.home.comment" /></th>
											</c:if>
											<th scope="col"></th>
											<th scope="col"></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${orders}" var="order">
											<tr
												<c:choose>
												<c:when test="${order.status eq 1}">class="table-info"</c:when>
												<c:when test="${order.status eq 2}">class="table-success"</c:when>
												<c:when test="${order.status eq 4}">class="table-warning"</c:when>
												<c:when test="${order.status eq 8}">class="table-danger"</c:when>
												<c:when test="${order.status eq 16}">class="table-secondary"</c:when>
												</c:choose>>
												<th scope="row">${order.id}</th>
												<td>${order.car.manufacturer}</td>
												<td>${order.car.model}</td>
												<td>${order.car.stateNumber}</td>
												<td>${order.account.firstName}</td>
												<td>${order.account.lastName}</td>
												<td><fmt:formatDate value="${order.created}" pattern="dd.MM.yyyy HH:mm:ss" /></td>
												<td><fmt:formatDate value="${order.confirmed}" pattern="dd.MM.yyyy HH:mm:ss" /></td>
												<td><fmt:formatDate value="${order.starts}" pattern="dd.MM.yyyy HH:mm:ss" /></td>
												<td><fmt:formatDate value="${order.expires}" pattern="dd.MM.yyyy HH:mm:ss" /></td>
												<td>${order.daysAmount}</td>
												<td>${order.totalRentValue}</td>
												<td><c:choose>
														<c:when test="${order.paid}">
															<mytag:message key="page.admin.accounts.yes" />
														</c:when>
														<c:otherwise>
															<mytag:message key="page.admin.accounts.no" />
														</c:otherwise>
													</c:choose></td>
												<c:if test="${param.status eq 8}">
													<td>${order.statusComment}</td>
												</c:if>
												<c:choose>
													<c:when test="${order.status eq 1}">
														<td><c:if test="${order.paid}">
																<a type="button" class="btn btn-success" href="/manager/confirmOrder?orderId=${order.id}">
																	<mytag:message key="page.manager.home.confirm" />
																</a>
															</c:if></td>
														<td><a type="button" class="btn btn-danger " href="/manager/declineOrder?orderId=${order.id}">
																<mytag:message key="page.manager.home.decline" />
															</a></td>
													</c:when>
													<c:when test="${(order.status eq 2) || (order.status eq 4)}">
														<td><a type="button" class="btn btn-primary" href="/manager/closeOrder?orderId=${order.id}">
																<mytag:message key="page.manager.home.close" />
															</a></td>
														<td><a type="button" class="btn btn-danger" href="/manager/accident?orderId=${order.id}">
																<mytag:message key="page.manager.home.accident" />
															</a></td>
													</c:when>
													<c:otherwise>
														<td></td>
														<td></td>
													</c:otherwise>
												</c:choose>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<nav aria-label="...">
									<ul class="pagination justify-content-center">
										<c:if test="${lastPage > 1}">
											<c:choose>
												<c:when test="${(empty param.page) || (param.page eq 1)}">
													<li class="page-item disabled"><span class="page-link"><mytag:message key="page.manager.home.first" />First</span></li>
													<li class="page-item active"><a class="page-link" data-page="1" href="">1</a></li>
													<li class="page-item"><a class="page-link" data-page="2" href="">2</a></li>
													<li class="page-item"><a class="page-link" data-page="3" href="">3</a></li>
													<li class="page-item"><a class="page-link" data-page="${lastPage}" href="">
															<mytag:message key="page.manager.home.last" />
														</a></li>
												</c:when>
												<c:when test="${ param.page == lastPage && param.page != 1 }">
													<li class="page-item"><a class="page-link" data-page="1" href="">
															<mytag:message key="page.manager.home.first" />
														</a></li>
													<li class="page-item"><a class="page-link" data-page="${param.page-2}" href="">${param.page-2}</a></li>
													<li class="page-item"><a class="page-link" data-page="${param.page-1}" href="">${param.page-1}</a></li>
													<li class="page-item active"><a class="page-link" data-page="${param.page}" href="">${param.page}</a></li>
													<li class="page-item disabled"><span class="page-link"><mytag:message key="page.manager.home.last" /></span></li>
												</c:when>
												<c:otherwise>
													<li class="page-item"><a class="page-link" data-page="1" href="">
															<mytag:message key="page.manager.home.first" />
														</a></li>
													<li class="page-item"><a class="page-link" data-page="${param.page-1}" href="">${param.page-1}</a></li>
													<li class="page-item active" aria-current="page"><span class="page-link"> ${param.page} </span></li>
													<li class="page-item"><a class="page-link" data-page="${param.page + 1}" href="">${param.page + 1 }</a></li>
													<li class="page-item"><a class="page-link" data-page="${lastPage}" href="">
															<mytag:message key="page.manager.home.last" />
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
					<input type="hidden" id="allInputs" name="status" value="">
				</form>
			</div>
		</div>
	</div>
</div>

<script src="/static/js/jquery-3.4.1.js"></script>


<script>
	$("a.page-link").click(
			function(e) {
				e.preventDefault();
				$("#allInputs").val(
						$("a.nav-link.status.active").data("status"));

				$(this).attr(
						"href",
						"?" + $('#form').serialize() + "&page="
								+ $(this).data("page"));
				$(location).attr('href', $(this).attr("href"));
			});

	$("#customControlInlineAllCars").change(function() {
		$(".vert").removeAttr("checked");
		$("#allInputs").val($("a.nav-link.status.active").data("status"));
		document.getElementById('form').submit();
	});

	$(".vert").change(function() {
		$("#customControlInlineAllCars").removeAttr("checked");
		$("#allInputs").val($("a.nav-link.status.active").data("status"));
		document.getElementById('form').submit();
	});

	$("#customControlInlineClasses").change(function() {
		$(".horiz").removeAttr("checked");
		$("#allInputs").val($("a.nav-link.status.active").data("status"));
		document.getElementById('form').submit();
	});

	$(".horiz").change(function() {
		$("#customControlInlineClasses").removeAttr("checked");
		$("#allInputs").val($("a.nav-link.status.active").data("status"));
		document.getElementById('form').submit();
	});

	$("#inlineFormOrderBy").change(function() {
		$("#allInputs").val($("a.nav-link.status.active").data("status"));
		document.getElementById('form').submit();
	});
</script>