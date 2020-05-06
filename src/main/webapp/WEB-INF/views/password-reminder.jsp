<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tags.tld"%>


<div class="row scroll">
	<div class="col">
		<div class="card" style="width: 30rem;">
			<div class="card-header">
				<mytag:message key="page.password_reminder.password_reminder" />
			</div>
			<div class="card-body">
				<form action="/forgot-password" method="post">
					<div class="form-group">
						<label for="exampleFormControlTextarea1"><mytag:message key="page.password_reminder.enter_your_email" /></label>
						<input type="email" class="form-control" id="exampleFormControlTextarea1" name="email"></input>
					</div>
					<a class="btn btn-outline-info pull-left" href="/login" role="button">
						<mytag:message key="page.manager.accident.back" />
					</a>
					<button type="submit" class="btn btn-primary" role="button">
						<mytag:message key="page.password_reminder.submit" />
					</button>
				</form>
			</div>
		</div>
	</div>
</div>


