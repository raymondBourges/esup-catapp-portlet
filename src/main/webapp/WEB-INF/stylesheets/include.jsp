<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %> <%@
taglib prefix="rs" uri="http://www.jasig.org/resource-server" %>
<rs:aggregatedResources path="/resources.xml" />

<html>
	<script type="text/javascript">
		angular.element(document).ready(function() {
		catAppAdmin("<portlet:namespace/>", "${resourceURL}");
		angular.bootstrap(angular.element(document.getElementById("catAppAdmin-<portlet:namespace/>")), ["<portlet:namespace/>"]);
      });
    </script>
	<div id="catAppAdmin-<portlet:namespace/>" ng-view class="portlet-container">
		<portlet:defineObjects />