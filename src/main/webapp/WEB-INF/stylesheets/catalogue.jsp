<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="rs" uri="http://www.jasig.org/resource-server" %>
<rs:aggregatedResources path="/resources.xml"/>

<html>
<head>
    <script type="text/javascript">
        angular.element(document).ready(function () {
            catAppPortlet("<portlet:namespace/>", "${resourceURL}");
            angular.bootstrap(angular.element(document.getElementById("catAppPortlet-<portlet:namespace/>")), ["<portlet:namespace/>"]);
        });
    </script>
</head>
<div id="catAppPortlet-<portlet:namespace/>" ng-view>
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


        .dropdown { display: none;
                    position: absolute;
                    border:1px solid #ccc;
                    padding: 10px;
                    width: 300px;
                    font-weight: bold;
                    font-size: 0.5em;
                    color: #ffffff;
                    background-color: #2e6e9e;

        }

        .dropdown button { padding: .5em 1em; text-decoration: none; }
    </style>
    <script>
        $(function () {
            $("#sortable").sortable();
            $("#sortable").disableSelection();
        });

//        $(document).ready(function () {
//
//            $('.dropdown-toggle').click(function(){
//                $(this).next('.dropdown').fadeToggle("slow");
//            });
//
//            $(document).click(function(e) {
//                var target = e.target;
//                if (!$(target).is('.dropdown-toggle') && !$(target).parents().is('.dropdown-toggle')) {
//                    $('.dropdown').hide();
//                }
//            });
//        });
        //<![CDATA[

//                $("#select").click(function () {
//                    alert(toto);
//                    $(".dropdown").fadeOut("slow");
//                    $(this).next('.dropdown').fadeToggle("slow");
//                });
//                $('.dropdown-toggle').click(function(){
//                    $(this).next('.dropdown').toggle();
//                });
//
//                $(document).click(function(e) {
//                    var target = e.target;
//                    if (!$(target).is('.dropdown-toggle') && !$(target).parents().is('.dropdown-toggle')) {
//                        $('.dropdown').hide();
//                    }
//                });

    </script>
    <div class="container demo-3">
        <div ng-controller="AppList2Ctrl">
            <div id="dropdown-Menu">
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
                        <li ng-repeat="application in applications  | filter:searchText" class="ui-state-default">
                            <div ng-switch="{{application.state}}">
                                <div ng-switch-when="true">
                                    <a class="deactivated">{{application.caption}}</a>
                                    <div class="dropdown">
                                        <span>Vous n'avez pas les droits suffisants pour accéder à cette application</span>
                                        <p>
                                            <a ng-click="delFromFav($index)" class="ui-state-default ui-corner-all">Supprimer des
                                                favoris</a>
                                        </p>
                                    </div>
                                </div>
                                <div ng-switch-when="false">
                                    <div ng-switch="{{application.acces}}">
                                        <div ng-switch-when="true">
                                            <a>{{application.caption}}</a>
                                            <div class="dropdown">
                                                <span>{{application.description}}</span>
                                                <p>
                                                    <a href="{{application.url}}" class="ui-state-default ui-corner-all">Ouvrir
                                                        l'application</a>
                                                    <a ng-click="delFromFav($index)" class="ui-state-default ui-corner-all">Supprimer des
                                                        favoris</a>
                                                </p>
                                            </div>
                                        </div>
                                        <div ng-switch-when="false">
                                            <a class="deactivated">{{application.caption}}</a>
                                            <div class="dropdown">
                                                <alert>L'application est provisoirement indisponible</alert>
                                                <span>{{application.description}}</span>
                                                <p>
                                                    <a ng-click="delFromFav($index)" class="ui-state-default ui-corner-all">Supprimer des
                                                        favoris</a>
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </li>
                    </ul>
                </div>
            </div>
            <div>

                <%--<script type="text/ng-template" id="menu_renderer.html">--%>
                <%--<a href="#">{{domain.domain.caption}}</a>--%>
                <%--<ng-switch on="domain.domain.applications.length > 0">--%>
                <%--<ul class="sub-menu" ng-switch-when="true">--%>
                <%--<li ng-repeat="application in domain.domain.applications">--%>
                <%--<a href="{{application.url}}">{{application.title}}</a>--%>
                <%--</li>--%>
                <%--</ul>--%>
                <%--</ng-switch>--%>

                <%--<ng-switch on="domain.subDomains.length > 0">--%>
                <%--<ul class="sub-menu" ng-switch-when="true">--%>
                <%--<li ng-repeat="domain in domain.subDomains" ng-include="'menu_renderer.html'"></li>--%>
                <%--</ul>--%>
                <%--</ng-switch>--%>
                <%--</script>--%>

                <div class="sidebar">
                    <ul>
                        <li>
                            <a href="javascript:void(0)">{{tasks.domain.caption}}</a>
                            <collection collection='tasks.subDomains'></collection>
                            <%--<ul class="sub-menu">--%>
                            <%--<li ng-repeat="domain in tasks.subDomains" ng-include="'menu_renderer.html'"></li>--%>
                            <%--</ul>--%>
                        </li>

                    </ul>
                </div>
            </div>
        </div>
    </div>
    <script>
        var htmlString = "$(document).ready(function() {";
        htmlString += "$('.sidebar ul li a').click(function(ev) {";
        htmlString += "$('.sidebar .sub-menu').not($(this).parents('.sub-menu')).slideUp();";
        htmlString += "$(this).next('.sub-menu').slideToggle();";
        htmlString += "ev.stopPropagation();";
        htmlString += "});"
        htmlString += "$('#sortable li a').click(function(ev) {";
        htmlString += "$(this).next('.dropdown').fadeToggle('slow');";
//        htmlString += "$('#sortable .dropdown').not($(this).parents('.dropdown')).slideUp();";
        htmlString += "});"

        htmlString += "});";

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
            display: none;
        }
    </style>

</div>
</html>