<%@ include file="/WEB-INF/stylesheets/include.jsp" %>
<style>
    #sortable {
        list-style-type: none;
        margin: 0;
        padding: 0;
        width: 60%;
    }

    #sortable li {
        margin: 0 3px 3px 3px;
        padding: 0.4em;
        padding-left: 1.5em;
        font-size: 1.4em;
        height: 18px;
    }

    #sortable li span div {
        position: absolute;
        margin-left: -1.3em;
    }
</style>
<script>
    $(function () {
        $("#sortable").sortable();
        $("#sortable").disableSelection();
    });
</script>
<div class="container demo-3">
    <div class="main clearfix" ng-controller="AppList2Ctrl">
        <div class="column">

            <%--<script type="text/ng-template" id="menu_renderer.html">--%>
            <%--<a href="#">{{domaine.domain.wording}}</a>--%>
            <%--<ng-switch on="domaine.domain.applications.length > 0">--%>
            <%--<ul class="sub-menu" ng-switch-when="true">--%>
            <%--<li ng-repeat="application in domaine.domain.applications">--%>
            <%--<a href="{{application.url}}">{{application.title}}</a>--%>
            <%--</li>--%>
            <%--</ul>--%>
            <%--</ng-switch>--%>

            <%--<ng-switch on="domaine.subDomains.length > 0">--%>
            <%--<ul class="sub-menu" ng-switch-when="true">--%>
            <%--<li ng-repeat="domaine in domaine.subDomains" ng-include="'menu_renderer.html'"></li>--%>
            <%--</ul>--%>
            <%--</ng-switch>--%>
            <%--</script>--%>

            <div class="sidebar">
                   <ul>
                    <li>
                        <a href="javascript:void(0)">{{tasks.domain.wording}}</a>
                        <collection collection='tasks.subDomains'></collection>
                        <%--<ul class="sub-menu">--%>
                        <%--<li ng-repeat="domaine in tasks.subDomains" ng-include="'menu_renderer.html'"></li>--%>
                        <%--</ul>--%>
                    </li>

                </ul>
            </div>
        </div>

        <div class="column" id="dropdown-Menu">
            <%--<div id="page" class="container-fluid">--%>
                <div id="favs">
                    <alert ng-repeat="alert in alerts" type="alert.type" close="closeAlert($index)">{{alert.msg}}
                    </alert>
                    <div class="row">
                        <div class="col-sm-12">
                            <h2>Mes favoris</h2>
                            <%--<span class="pull-right">--%>
                                 <%--&lt;%&ndash;<button class="dl-trigger">Open Menu</button>&ndash;%&gt;--%>
                             <%--&lt;%&ndash;<a href="#catalog" class="btn btn-link"><span class="fa fa-bars fa-2x"/><span&ndash;%&gt;--%>
                                     <%--&lt;%&ndash;class="sr-only">Ouvrir le catalogue</span></a>&ndash;%&gt;--%>
					        <%--</span>--%>

                            Search: <input type="text" ng-model="searchText"/>
                        </div>
                    </div>
                    <ul id="sortable">
                        <li ng-repeat="application in applications  | filter:searchText"
                            class="ui-state-default dropdown">
                            <%--<div id="div-{{application.code}}">--%>
                            <%--<div class="dropdown">--%>
                            <a class="btn btn-link dropdown-toggle">{{application.wording}}</a>

                            <div class="dropdown-menu">
                                <span>{{application.description}}</span>
                                <ul>
                                    <li><a href="{{application.url}}"><i class="fa fa-external-link"></i>Ouvrir
                                        l'application</a></li>
                                    <li><a ng-click="delFromFav($index)"><i class="fa fa-eraser"></i> Supprimer des
                                        favoris</a>
                                    </li>
                                </ul>
                            </div>
                            <%--</div>--%>
                            <%--</div>--%>
                        </li>
                    </ul>
                </div>
            <%--</div>--%>
        </div>
    </div>
</div>
<script>
   var htmlString = "$(document).ready(function() {";
   htmlString += "$('.sidebar ul li a').click(function(ev) {";
   htmlString += "$('.sidebar .sub-menu').not($(this).parents('.sub-menu')).slideUp();";
   htmlString += "$(this).next('.sub-menu').slideToggle();";
   htmlString += "ev.stopPropagation();";
   htmlString += "});});";
    function launchJs() {
        var menu = document.createElement('script');
        menu.type = 'text/javascript';
        menu.innerHTML = htmlString;
        document.getElementById('dropdown-Menu').appendChild(menu);
    }
</script>
<script>
    setTimeout("launchJs();", 500)
</script>
<style>
    .sidebar ul ul {
        display:none;
    }
</style>

<!-- </div> -->