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
    <script type="text/javascript">
        var width = document.getElementById("catAppPortlet-<portlet:namespace/>").offsetWidth;

        google_ad_client = "ca-pub-3827380040284749";

        if (width > 800) {
            // Load the Leaderboard 728x90 Unit for wide screen
            google_ad_slot = "6954218739";
            google_ad_width = 728;
            google_ad_height = 90;
        } else if ((width < 800) && (width > 400)) {
            // Load the 336x280 Medium Rectangle
            google_ad_slot = "8430951934";
            google_ad_width = 336;
            google_ad_height = 280;
        } else {
            // For small screens, load the 468x60 banner
            google_ad_slot = "9907685131";
            google_ad_width = 468;
            google_ad_height = 60;
        }
    </script>
    <div ng-controller="AppList2Ctrl">
        <div id="slider-1" class="liquid-slider">
            <div id="dropdown-Menu">
                <span class="title">Favoris</span>
                <div id="favs">
                    <alert ng-repeat="alert in alerts" type="alert.type" close="closeAlert($index)">{{alert.msg}}
                    </alert>
                    <div class="row">
                        <div class="col-sm-12">
                            Filtrer: <input type="text" ng-model="searchText"/>
                        </div>
                    </div>
                    <ul id="sortable">
                        <li ng-repeat="application in applications  | filter:searchText" class="ui-state-default">
                            <div ng-switch="{{application.state}}">
                                <div ng-switch-when="true">
                                    <a class="'deactivated'">{{application.caption}}</a>
                                    <div class="dropdown">
                                        <span>Vous n'avez pas les droits suffisants pour accéder à cette application</span>
                                        <p>
                                            <a ng-click="delFromFav($index)" class="ui-state-default ui-corner-all">Supprimer des
                                                favoris</a>
                                        </p>
                                    </div>
                                </div>
                                <div ng-switch-when="false">
                                    <div ng-switch="application.acces">
                                        <div ng-switch-when="Activated">
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
                                        <div ng-switch-when="Deactivated">
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
            <div id="catalogue" class="main clearfix">
                <span class="title">Catalogue</span>

                <div class="column">
                    <div class="sidebar">
                        <ul>
                            <li>
                                <a href="javascript:void(0)">{{domaines.domain.caption}}</a>
                                <collection collection='domaines.subDomains'></collection>
                            </li>

                        </ul>
                    </div>
                    <%--<div id="dl-menu" class="dl-menuwrapper">--%>
                       <%--<collection collection='domaines.subDomains'></collection>--%>
                    <%--</div>--%>
                </div>
            </div>
        </div>
    </div>
</div>
<rs:aggregatedResources path="/footerResources.xml"/>
</html>