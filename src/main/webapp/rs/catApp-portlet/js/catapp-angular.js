catAppPortlet = function (appName, resourceURL, actionURL) {

    var app = angular.module(appName, ['ui.bootstrap', 'ui.sortable', 'ngSanitize']);

    app.controller('AppList2Ctrl', function ($scope, $http, FavorisService) {

        'use strict';

        $scope.alerts = [];
        $scope.applications = [];
        $scope.prefs = [];

        $http
            .get(url(encodeUrl(resourceURL), 'apps')).success(function (data) {
                angular.forEach(data.apps, function (app) {
                    $scope.applications.push(FavorisService.create(app, false));
                    $scope.prefs.push(app.code);
                });
                angular.forEach(data.noAccessApps, function (app) {
                    $scope.applications.push(FavorisService.create(app, true));
                    $scope.prefs.push(app.code);
                });
            });

        $scope.delFromFav = function (idx) {
            var delApp = $scope.applications[idx];
            $scope.prefs = FavorisService.delete(delApp.code, $scope.prefs);
            $scope.applications.splice(idx, 1);
            $scope.updateFavorite();
        };

        $scope.addFav = function (app) {
            var favTodd = FavorisService.find(app.code, $scope.prefs);
            if (!angular.isUndefined(favTodd) || favTodd != null) {
                $scope.alerts.push({type: 'danger', msg: "L'application " + app.code + " est déjà dans les favoris"});
            } else {
                $scope.applications.push(FavorisService.create(app, false));
                $scope.prefs.push(app.code);
                $scope.updateFavorite();
                $scope.alerts.push({type: 'success', msg: "L'application " + app.code + " a été ajoutée aux favoris"});
            }
            $(".dropdown").fadeOut("slow");
            setTimeout(function() {
                $(".alert").fadeOut("slow");
                $scope.alerts = [];
            }, 1000)
        };

        $scope.callTooltip = function(obj) {
            var idt = obj.target;
            $( idt ).next( '.dropdown').fadeToggle('slow');
        }

        $scope.updateFavorite =  function ()  {
            $http
                .get(actUrl(encodeUrl(actionURL), 'updateFavorite', $scope.prefs.join(",")));
        };

        $http
            .get(url(encodeUrl(resourceURL), 'doms')).success(function (data) {
                $scope.domaines = data.doms;
            });

        $scope.sortableOptions = {
            // called after a node is dropped
            stop: function() {
                var tmpList = [];
                angular.forEach($scope.applications, function (app) {
                    tmpList.push(app.code);
                });
                $scope.prefs = tmpList;
                $scope.updateFavorite();
            }
        };

    });

    app.service('FavorisService', function () {
        //save method create a new favori
        this.create = function (app, removed) {
            var favori = {"code": app.code, "title": app.title, "caption": app.caption, "description": app.description, "url": app.url, "acces": app.activation, "state": removed};
            return favori;
        }

        //simply search favoris list for given id
        //and returns the contact object if found
        this.find = function (code, favoris) {
            for (i in favoris) {
                if (favoris[i] == code) {
                    return favoris[i];
                }
            }
        }

        //iterate through favoris list and delete
        //contact if found
        this.delete = function (code, favoris) {
            for (i in favoris) {
                if (favoris[i] == code) {
                    favoris.splice(i, 1);
                }
            }
            return favoris;
        }
    });


    app.directive('domaine', function ($compile, $timeout) {
        return {
            restrict: "E",
            replace: true,
            scope: {
                member: '=',
                addFav: '&'
            },
            link: function (scope, element, attrs) {
                if ((scope.member.subDomains.length > 0)) {
                    element.append("<a href='#'>{{member.domain.caption}}</a><ul class='sub-menu'><li class='ui-state-default' ng-repeat='domaine in member.subDomains'>" +
                                   "<domaine member='domaine' add-fav='addFav()'></domaine></li></ul>");
                    $compile(element.contents())(scope)
                } else {
                    element.append("<a href='#'>{{member.domain.caption}}</a>");
                }
                if ((scope.member.domain.applications.length > 0)) {
                    element.append("<ul class='sub-menu menudrop'><application ng-repeat='member in member.domain.applications'  member='member' add-fav='addFav()'></application></ul>");
                    $compile(element.contents())(scope)
                }
            }
        }
    });

    app.directive('application', function ($parse) {
        return {
            restrict: "E",
            replace: true,
            scope: {member: '=',
                    addFav: '&'
            },
            template: "<li class='ui-state-focus'><a>{{member.title}}</a>" +
                "<div class='dropdown'><span ng-bind-html='member.description'></span>" +
                "<p>" +
                "<a href='{{member.url}}' class='ui-state-default ui-corner-all'>Ouvrir l'application</a>" +
                "<a href='#' ng-click='pushapp();' class='ui-state-default ui-corner-all addfav'>Ajouter aux favoris</a></p></div>" +
                "</li>",
            link: function (scope, element, attrs) {
                scope.pushapp = function () {
                        scope.addFav()(scope.member);
                };
            }
        }
    });
};

// ************* utils *************

function encodeUrl(requestUrl) {
    return requestUrl.
        replace(/%40/gi, '@').
        replace(/%3A/gi, ':').
        replace(/%24/g, '$').
        replace(/%2C/gi, ',');
}

//forge a portlet resource url
function url(pattern, id, p1, p2, p3, p4) {
    var pattern2 = pattern.
        replace("@@id@@", id)
        .replace("__p1__", p1)
        .replace("__p2__", p2)
        .replace("__p3__", p3)
        .replace("__p4__", p4);
    return pattern2;
}

//forge a portlet resource url
function actUrl(pattern, action, p1, p2, p3) {
    var pattern2 = pattern.
        replace("@@id@@", action)
        .replace("__p1__", p1)
        .replace("__p2__", p2)
        .replace("__p3__", p3);
    return pattern2;
}
