<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tags.tld"%>


<div class="row scroll">
	<div class="col-md-2">
		<div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical">
			<a class="nav-link " href="/admin/home">
				<mytag:message key="page.admin.accounts.cars" />
			</a>
			<a class="nav-link active" href="/admin/accounts">
				<mytag:message key="page.admin.accounts.accounts" />
			</a>
		</div>
	</div>
	<div class="col-md-10">
		<form action="/admin/accounts" id='form'>
			<div class="card">
				<div class="card-header">
					<div class="row">
						<div class="col">
							<ul class="nav nav-pills nav-justified">
								<li class="nav-item"><a class="nav-link role <c:if test="${empty param.role}">active</c:if>" href="/admin/accounts?">
										<mytag:message key="page.admin.accounts.all" />
									</a></li>
								<li class="nav-item"><a class="nav-link role <c:if test="${param.role eq 'admin'}">active</c:if>" href="?role=admin">
										<mytag:message key="page.admin.accounts.administrators" />
									</a></li>
								<li class="nav-item"><a class="nav-link role <c:if test="${param.role eq 'manager'}">active</c:if>" href="?role=manager">
										<mytag:message key="page.admin.accounts.managers" />
									</a></li>
								<li class="nav-item"><a class="nav-link role <c:if test="${param.role eq 'client'}">active</c:if>" href="?role=client">
										<mytag:message key="page.admin.accounts.users" />
									</a></li>
							</ul>
						</div>
					</div>
				</div>
				<div class="card-body">

					<div class="row scroll">
						<div class="col">
							<div class="card">
								<table class="table table-hover">
									<thead class="thead-light">
										<tr>
											<th scope="col">#</th>
											<th scope="col"><mytag:message key="page.admin.accounts.firstname" /></th>
											<th scope="col"><mytag:message key="page.admin.accounts.lastname" /></th>
											<th scope="col"><mytag:message key="page.admin.accounts.email" /></th>
											<th scope="col"><mytag:message key="page.admin.accounts.role" /></th>
											<th scope="col"><mytag:message key="page.admin.accounts.passport_data" /></th>
											<th scope="col"><mytag:message key="page.admin.accounts.address" /></th>
											<th scope="col"><mytag:message key="page.admin.accounts.phone" /></th>
											<th scope="col"><mytag:message key="page.admin.accounts.created" /></th>
											<th scope="col"></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${accounts}" var="account">
											<tr
												<c:choose>
													<c:when test="${account.role.name.toString() == 'manager'}">class="table-warning"</c:when>
													<c:when test="${account.role.name.toString() == 'admin'}">class="table-primary"</c:when>
												</c:choose>>
												<th scope="row">${account.id}</th>
												<td>${account.firstName}</td>
												<td>${account.lastName}</td>
												<td>${account.email}</td>
												<td>${account.role.name}</td>
												<td><c:choose>
														<c:when test="${not empty account.passportData}">${account.passportData}</c:when>
														<c:otherwise>
															<mytag:message key="page.admin.accounts.no_information" />
														</c:otherwise>
													</c:choose></td>
												<td><c:choose>
														<c:when test="${not empty account.address}">${account.address}</c:when>
														<c:otherwise>
															<mytag:message key="page.admin.accounts.no_information" />
														</c:otherwise>
													</c:choose></td>
												<td><c:choose>
														<c:when test="${account.phone ne '0'}">${account.phone}</c:when>
														<c:otherwise>
															<mytag:message key="page.admin.accounts.no_information" />
														</c:otherwise>
													</c:choose></td>
												<td><fmt:formatDate value="${account.created}" pattern="dd.MM.yyyy HH:mm:ss" /></td>
												<td><c:choose>
														<c:when test="${account.role.name.toString() == 'manager'}">
															<a class="btn btn-primary" href="/admin/assigneAdmin?id=${account.id}" role="button">
																<mytag:message key="page.admin.accounts.assign_as_administrator" />
															</a>
														</c:when>
														<c:when test="${account.role.name.toString() == 'client'}">
															<a class="btn btn-warning" href="/admin/assigneManager?id=${account.id}" role="button">
																<mytag:message key="page.admin.accounts.assign_as_manager" />
															</a>
														</c:when>
													</c:choose></td>

											</tr>
										</c:forEach>
									</tbody>
								</table>
								<nav aria-label="...">
									<ul class="pagination justify-content-center">
										<c:if test="${lastPage > 1}">
											<c:choose>
												<c:when test="${(empty param.page) || (param.page eq 1)}">
													<li class="page-item disabled"><span class="page-link"><mytag:message key="page.admin.accounts.first" /></span></li>
													<li class="page-item active"><a class="page-link" data-page="1" href="">1</a></li>
													<li class="page-item"><a class="page-link" data-page="2" href="">2</a></li>
													<li class="page-item"><a class="page-link" data-page="3" href="">3</a></li>
													<li class="page-item"><a class="page-link" data-page="${lastPage}" href="">
															<mytag:message key="page.admin.accounts.last" />
														</a></li>
												</c:when>
												<c:when test="${ param.page == lastPage && param.page != 1 }">
													<li class="page-item"><a class="page-link" data-page="1" href="">
															<mytag:message key="page.admin.accounts.first" />
														</a></li>
													<li class="page-item"><a class="page-link" data-page="${param.page-2}" href="">${param.page-2}</a></li>
													<li class="page-item"><a class="page-link" data-page="${param.page-1}" href="">${param.page-1}</a></li>
													<li class="page-item active"><a class="page-link" data-page="${param.page}" href="">${param.page}</a></li>
													<li class="page-item disabled"><span class="page-link"><mytag:message key="page.admin.accounts.last" /></span></li>
												</c:when>
												<c:otherwise>
													<li class="page-item"><a class="page-link" data-page="1" href="">
															<mytag:message key="page.admin.accounts.first" />
														</a></li>
													<li class="page-item"><a class="page-link" data-page="${param.page-1}" href="">${param.page-1}</a></li>
													<li class="page-item active" aria-current="page"><span class="page-link"> ${param.page} </span></li>
													<li class="page-item"><a class="page-link" data-page="${param.page + 1}" href="">${param.page + 1 }</a></li>
													<li class="page-item"><a class="page-link" data-page="${lastPage}" href="">
															<mytag:message key="page.admin.accounts.last" />
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

				</div>
			</div>
		</form>
	</div>
</div>

<script src="/static/js/jquery-3.4.1.js"></script>

<script>
	$("a.page-link").click(
			function(e) {
				e.preventDefault();
				$(this).attr(
						"href",
						$("a.role.active").attr("href") + "&page="
								+ $(this).data("page"));
				$(location).attr('href', $(this).attr("href"));
			});
</script>

<script>
	console.log($("a.role.active").attr("href"));

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


