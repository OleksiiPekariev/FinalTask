<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tags.tld"%>

<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link rel="stylesheet" href="/static/css/bootstrap.css">
<link rel="stylesheet" href="/static/css/app.css">

<link rel="shortcut icon" href="/static/car.jpg" type="image/x-icon" />
<title>Car Rent</title>
</head>
<body>
	<nav class="navbar navbar-light bg-light my-header-class">
		<a class="navbar-brand" href="/CarRent">Car Rent</a>
		<nav class="nav nav-pills nav-fill">
			<c:choose>
				<c:when test="${not empty CURRENT_ACCOUNT}">
					<a class="nav-item nav-link disabled" href="#">
						<mytag:message key="page.template.hello" />
						, ${CURRENT_ACCOUNT.firstName }
					</a>
					<a class="nav-item nav-link" href="/${CURRENT_ACCOUNT.role.name }/home" tabindex="-1" aria-disabled="true">
						<mytag:message key="page.template.homepage" />
					</a>
					<a class="nav-item nav-link" href="/logout" tabindex="-1" aria-disabled="true">
						<mytag:message key="page.template.logout" />
					</a>
				</c:when>
				<c:otherwise>
					<a class="nav-item nav-link" href="/login" tabindex="-1" aria-disabled="true">
						<mytag:message key="page.template.login" />
					</a>
				</c:otherwise>
			</c:choose>
			<div class="nav-item dropdown">
				<a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">
					<mytag:message key="page.template.language" />
				</a>
				<div class="dropdown-menu">
					<a class="dropdown-item" href="?lang=en">English</a>
					<a class="dropdown-item" href="?lang=ru">Русский</a>
				</div>
			</div>
		</nav>
	</nav>
	<div class="container-fluid" style="min-height: 500px">
		<c:if test="${not empty SUCCESS_MESSAGE}">
			<div class="alert alert-success alert-dismissible fade show" role="alert">
				${SUCCESS_MESSAGE}
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
		</c:if>
		<c:if test="${not empty ERROR_MESSAGE}">
			<div class="alert alert-danger alert-dismissible fade show" role="alert">
				${ERROR_MESSAGE}
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
		</c:if>
		<jsp:include page="${currentPage}" />
	</div>
	<footer> </footer>
	<script src="/static/js/popper.js"></script>
	<script src="/static/js/jquery-3.4.1.js"></script>
	<script src="/static/js/bootstrap.js"></script>
	<script src="/static/js/app.js"></script>
</body>

</html>