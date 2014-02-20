<%@ include file="/WEB-INF/stylesheets/include.jsp"%>

<portlet:actionURL var="actionUrl">
	<portlet:param name="action" value="save"></portlet:param>
</portlet:actionURL>

<form action="${actionUrl}" method="post" name="<portlet:namespace />fm">
	Identifiant du domaine racine: <input size="40"
		type="text" value="<%=renderRequest.getAttribute("idDomain") %>"
	name="idDomain"><br />
	<br /> <input type="submit">
</form>
