<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="rs" uri="http://www.jasig.org/resource-server" %>
<rs:aggregatedResources path="/resources.xml"/>

<html>
<head>
    <script type="text/javascript">
        angular.element(document).ready(function () {
            catAppPortlet("<portlet:namespace/>", "${resourceURL}", "${actionURL}");
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
        <div class="slide-header">
            <h3>Applications m&eacute;tiers et services num&eacute;riques</h3>
            <div class="alertContainer">
                <alert ng-repeat="alert in alerts" type="alert.type" close="closeAlert($index)">{{alert.msg}}
                </alert>
            </div>
        </div>
        <div id="slider-<portlet:namespace/>" class="liquid-slider">
            <div id="dropdown-Menu-<portlet:namespace/>">
                <span class="title">Favoris</span>
                <div class="row">
                    <div class="col-sm-12">
                        Filtrer: <input type="text" ng-model="filterText"/>
                    </div>
                </div>
                <div id="favs">
                    <ng-switch on="applications.length > 0">
                        <div class="ui-state-disabled" ng-switch-when="false">
                            S&eacute;lectionner les applications dans le catalogue pour les ajouter &agrave; la liste des favoris
                        </div>
                    </ng-switch>
                    <ul class="sortable" ui-sortable="sortableOptions" ng-model="applications">
                        <li ng-repeat="application in applications  | filter:filterText" class="ui-state-default">
                            <span class="ui-icon ui-icon-arrowthick-2-n-s" style="float:right"></span>
                            <div ng-switch="{{application.state}}">
                                <div ng-switch-when="true">
                                    <a class="deactivated item" ng-click="callTooltip($event)">{{application.title}}</a>
                                    <div class="dropdown">
                                        <div class="alert-dropdown">Vous n'avez pas les droits suffisants pour acc&eacute;der &agrave; cette application</div>
                                        <p>
                                            <a ng-click="delFromFav($index)" class="ui-state-default ui-corner-all addfav">
                                                Supprimer des favoris</a>
                                        </p>
                                    </div>
                                </div>
                                <div ng-switch-when="false">
                                    <div ng-switch="application.acces">
                                        <div ng-switch-when="Activated">
                                            <a class="item" ng-click="callTooltip($event)">{{application.title}}</a>
                                            <div class="dropdown">
                                                <span ng-bind-html="application.description"></span>
                                                <p>
                                                    <a href="{{application.url}}" class="ui-state-default ui-corner-all" >
                                                        Ouvrir l'application</a>
                                                    <a ng-click="delFromFav($index)" class="ui-state-default ui-corner-all addfav">
                                                        Supprimer des favoris</a>
                                                </p>
                                            </div>
                                        </div>
                                        <div ng-switch-when="Deactivated">
                                            <a class="deactivated item" ng-click="callTooltip($event)">{{application.title}}</a>
                                            <div class="dropdown">
                                                <div class="alert-dropdown">L'application est provisoirement indisponible</div>
                                                <span ng-bind-html="application.description"></span>
                                                <p>
                                                    <a ng-click="delFromFav($index)" class="ui-state-default ui-corner-all addfav">
                                                        Supprimer des favoris</a>
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
                <div class="row">
                    <div class="col-sm-12">
                        Rechecher : <input type="text" ng-model="searchText"/>
                    </div>
                </div>

                <div class="column">

                    <div ng-show="(filteredData = (allApplications | filter:searchText)) && searchText && searchText.length >= 2">
                        <span class="result-title">R&eacute;sultat de la recherche : </span>
                        <ul>
                            <li ng-repeat="application in filteredData" class="ui-state-default">
                                <a class="item" ng-click="callTooltip($event)">{{application.title}}</a>
                                <div class="dropdown">
                                    <span ng-bind-html="application.description"></span>
                                    <p>
                                        <a href="{{application.url}}" class="ui-state-default ui-corner-all" >
                                            Ouvrir l'application</a>
                                        <a ng-click="addToFav($index)" class="ui-state-default ui-corner-all">
                                            Ajouter aux favoris</a>
                                    </p>
                                </div>
                            </li>
                        </ul>
                    </div>

                    <div class="sidebar" ng-hide="searchText && searchText.length >= 2">
                        <span>{{domaines.domain.length}}</span>
                        <div ng-switch="domaines.domain.caption.length>0">
                            <div ng-switch-when="true">
                                <ul>
                                    <li>
                                        <a href="javascript:void(0)">{{domaines.domain.caption}}<span class="ui-icon ui-icon-play"></span></a>
                                        <ul class="sub-menu">
                                            <li ng-repeat="domaine in domaines.subDomains">
                                                <domaine member="domaine" add-fav="addFav"></domaine></li></ul>
                                            </li>
                                        </ul>
                                    </li>
                                </ul>
                            </div>
                            <div class="ui-state-disabled" ng-switch-when="false">
                                La liste des domaines et applications autoris&eacute;s est vide.
                            </div>
                        </div>
                    </div>
                    </div>
            </div>
        </div>
    </div>
</div>
<rs:aggregatedResources path="/footerResources.xml"/>
<script type="text/javascript">
    function launchJs() {
        var menu = document.createElement('script');
        menu.type = 'text/javascript';
        menu.innerHTML = htmlString;
        document.getElementById('dropdown-Menu-<portlet:namespace/>').appendChild(menu);
    }
    setTimeout("launchJs();", 2000)

    $(function () {
        $('#slider-<portlet:namespace/>').liquidSlider({
            autoHeight: false,
            slideEaseDuration: 1000,
            heightEaseDuration: 1000,
            animateIn: "lightSpeedOut",
            animateOut: "lightSpeedIn"
        });
    });
</script>
</html>