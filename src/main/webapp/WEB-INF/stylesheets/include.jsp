<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="rs" uri="http://www.jasig.org/resource-server" %>
<rs:aggregatedResources path="/resources.xml" />

<html>
<head>
	<script type="text/javascript">
		angular.element(document).ready(function() {
		catAppPortlet("<portlet:namespace/>", "${resourceURL}");
		angular.bootstrap(angular.element(document.getElementById("catAppPortlet-<portlet:namespace/>")), ["<portlet:namespace/>"]);
      });
    </script>
</head>
	<div id="catAppPortlet-<portlet:namespace/>" ng-view>

     </div>