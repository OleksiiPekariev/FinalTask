<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="row scroll">
	<div class="col">
		<div class="card">
			<div class="card-header">
				<div class="row">
					<div class="col-md-2">Orders</div>
					<div class="col">
						<ul class="nav nav-pills nav-justified">
							<li class="nav-item"><a
									class="nav-link status <c:if test="${(currenURI eq '/client/home')||(currenURI eq '/client/active')}">active</c:if>" data-status="0"
									href="/client/active">Active</a></li>
							<li class="nav-item"><a class="nav-link status <c:if test="${currenURI eq '/client/history'}">active</c:if>" data-status="1"
									href="/client/history">History</a></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="card-body">
				<form action="/client/home" id='form'>
					<div class="row scroll">
						<div class="col">
							<div class="card">
								<div class="card-header">
									<div class="form-inline">
										<div class="form-group col-md-6">
											<label class="my-1 mr-2" for="inlineFormCustomSelectPref">Sort By </label>
											<select onchange="document.getElementById('form').submit();" class="custom-select my-1 mr-sm-2" id="inlineFormCustomSelectPref"
												name="sortBy">
												<option <c:if test="${param.sortBy == 'carName_ASC'}">selected</c:if> value="carName_ASC">Car Name A-Z</option>
												<option <c:if test="${param.sortBy == 'carName_DESC'}">selected</c:if> value="carName_DESC">Car Name Z-A</option>
												<option <c:if test="${param.sortBy == 'expDate_ASC'}">selected</c:if> value="expDate_ASC">Expiration Date ▲</option>
												<option <c:if test="${param.sortBy == 'expDate_DESC'}">selected</c:if> value="expDate_DESC">Expiration Date ▼</option>
												<option <c:if test="${param.sortBy == 'startDate_ASC'}">selected</c:if> value="startDate_ASC">Starting Date ▲</option>
												<option <c:if test="${param.sortBy == 'startDate_DESC'}">selected</c:if> value="startDate_DESC">Starting Date ▼</option>
											</select>
										</div>
									</div>
								</div>
								<table class="table table-hover">
									<thead class="thead-light ">
										<tr>
											<th scope="col">#</th>
											<th scope="col">Name</th>
											<th scope="col">Model</th>
											<th scope="col">State<br>Number
											</th>
											<th scope="col">Created</th>
											<th scope="col">Confirmed</th>
											<th scope="col">Starts</th>
											<th scope="col">Ends</th>
											<th scope="col">Days<br>Amount
											</th>
											<th scope="col">Total<br>Price $
											</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${orders}" var="order">
											<tr onclick=""
												<c:choose>
												<c:when test="${order.status eq 1}">class="table-info"</c:when>
												<c:when test="${order.status eq 2}">class="table-success"</c:when>
												<c:when test="${order.status eq 4}">class="table-danger"</c:when>
												<c:when test="${order.status eq 8}">class="table-warning"</c:when>
												<c:when test="${order.status eq 16}">class="table-secondary"</c:when>
												</c:choose>>
												<th scope="row">${order.id}</th>
												<td>${order.car.manufacturer}</td>
												<td>${order.car.model}</td>
												<td>${order.car.stateNumber}</td>
												<td><fmt:formatDate value="${order.created}" pattern="dd.MM.yyyy HH:mm:ss" /></td>
												<td><fmt:formatDate value="${order.confirmed}" pattern="dd.MM.yyyy HH:mm:ss" /></td>
												<td><fmt:formatDate value="${order.starts}" pattern="dd.MM.yyyy HH:mm:ss" /></td>
												<td><fmt:formatDate value="${order.expires}" pattern="dd.MM.yyyy HH:mm:ss" /></td>
												<td>${order.daysAmount}</td>
												<td>${order.totalRentValue}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								<nav aria-label="...">
									<ul class="pagination justify-content-center">
										<c:if test="${lastPage > 1}">
											<c:choose>
												<c:when test="${(empty param.page) || (param.page eq 1)}">
													<li class="page-item disabled"><span class="page-link">First</span></li>
													<li class="page-item active"><a class="page-link" data-page="1" href="">1</a></li>
													<li class="page-item"><a class="page-link" data-page="2" href="">2</a></li>
													<li class="page-item"><a class="page-link" data-page="3" href="">3</a></li>
													<li class="page-item"><a class="page-link" data-page="${lastPage}" href="">Last</a></li>
												</c:when>
												<c:when test="${ param.page == lastPage && param.page != 1 }">
													<li class="page-item"><a class="page-link" data-page="1" href="">First</a></li>
													<li class="page-item"><a class="page-link" data-page="${param.page-2}" href="">${param.page-2}</a></li>
													<li class="page-item"><a class="page-link" data-page="${param.page-1}" href="">${param.page-1}</a></li>
													<li class="page-item active"><a class="page-link" data-page="${param.page}" href="">${param.page}</a></li>
													<li class="page-item disabled"><span class="page-link">Last</span></li>
												</c:when>
												<c:otherwise>
													<li class="page-item"><a class="page-link" data-page="1" href="">First</a></li>
													<li class="page-item"><a class="page-link" data-page="${param.page-1}" href="">${param.page-1}</a></li>
													<li class="page-item active" aria-current="page"><span class="page-link"> ${param.page} </span></li>
													<li class="page-item"><a class="page-link" data-page="${param.page + 1}" href="">${param.page + 1 }</a></li>
													<li class="page-item"><a class="page-link" data-page="${lastPage}" href="">Last</a></li>
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
	$(function() {
		$('[data-toggle="popover"]').popover()
	})
</script>

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
</script>


<script>
	console.log(document.getElementById('allInputs'));

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
		$("#zalupa").val($("a.nav-link.status.active").data("status"));
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