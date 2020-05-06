<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="mytag" uri="/WEB-INF/tags.tld"%>


<div class="row scroll">
	<div class="col">
		<div class="card" style="width: 18rem;">
			<div class="card-header">
				<mytag:message key="page.manager.decline.decline_order_id" />
				${param.orderId}
			</div>
			<div class="card-body">
				<form action="/manager/declineOrder" method="post">
					<input type="hidden" name="orderId" value="${param.orderId}">
					<div class="form-group">
						<label for="exampleFormControlTextarea1"><mytag:message key="page.manager.decline.sign_decline_comment" /></label>
						<textarea class="form-control" id="exampleFormControlTextarea1" name="comment" rows="3"></textarea>
					</div>
					<a class="btn btn-outline-info pull-left" href="/manager/home" role="button">
						<mytag:message key="page.manager.decline.back" />
					</a>
					<button type="submit" class="btn btn-primary" role="button">
						<mytag:message key="page.manager.decline.submit" />
					</button>
				</form>
			</div>
		</div>
	</div>
</div>


