catAppPortlet = function(appName, resourceURL) {

	var app = angular.module(appName, ['ui.bootstrap']);


	app.controller('AppList2Ctrl', function($scope, $http, FavorisService) {

        $scope.alerts = [];

        $http
            .get(url(encodeResourceUrl(resourceURL), 'apps')).success(function (data) {
                angular.forEach(data.apps, function(app){
                    FavorisService.create(app, false);
                });
                angular.forEach(data.noAccessApps, function(app){
                    FavorisService.create(app, true);
                });
            $scope.applications = FavorisService.list();
//            $scope.applications = data.apps;
//            $scope.removedApps = data.removedApps;
        });

        $scope.delFromFav = function(idx) {
            var delApp = $scope.applications[idx];

            $http
                .get(url(encodeResourceUrl(resourceURL), 'deleteFavori', delApp.code))
                .success(function (data) {
                    $scope.alerts.push({type: 'success', msg: data.message});
                    $scope.applications.splice(idx, 1);
                })
                .error(function () {
                    $scope.alerts.push({type: 'danger', msg: "L'application "+delApp.code+" n'a pas pu être supprimée"});
                });
        };

        $scope.closeAlert = function(index) {
            $scope.alerts.splice(index, 1);
        };

        $http
            .get(url(encodeResourceUrl(resourceURL), 'doms')).success(function (data) {
                $scope.tasks = data.doms;
//                $scope.domains = data.doms;
            });

	});


    app.service('FavorisService', function () {

        //favoris array to hold list of all favoris
        var favoris = [{}];

        //save method create a new favori
        this.create = function (app, removed) {
            var favori = {"code" : app.code, "title" : app.title, "caption" : app.caption, "description" : app.description, "url" : app.url, "acces" : app.activation, "state" : removed};
            alert("create  "+favori);
            favoris.push(favori);
        }

        //simply search favoris list for given id
        //and returns the contact object if found
        this.find = function (code) {
            for (i in favoris) {
                if (favoris[i].code == code) {
                    return favoris[i];
                }
            }

        }

        //iterate through favoris list and delete
        //contact if found
        this.delete = function (code) {
            for (i in favoris) {
                if (favoris[i].code == code) {
                    favoris.splice(i, 1);
                }
            }
        }

        //simply returns the favoris list
        this.list = function () {
            alert("list  "+favoris.length);
            return favoris;
        }
    });

    app.directive('collection', function () {
        return {
            restrict: "E",
            replace: true,
            scope: {
                collection: '='
            },
            template: "<ul class='sub-menu'><member ng-repeat='member in collection' member='member'></member></ul>"
        }
    });

    app.directive('collection2', function () {
        return {
            restrict: "E",
            replace: true,
            scope: {
                collection: '='
            },
            template: "<ul class='sub-menu'><member2 ng-repeat='member in collection' member='member'></member2></ul>"
        }
    });

    app.directive('member', function ($compile) {
            return {
                restrict: "E",
                replace: true,
                scope: {
                    member: '='
                },
                template: "<li><a href='javascript:void(0)'>{{member.domain.caption}}</a></li>",
                link: function (scope, element, attrs) {
                    if ((scope.member.subDomains.length > 0)) {
                        element.append("<collection collection='member.subDomains'></collection>");
                        $compile(element.contents())(scope)
                    }
                    if (angular.isArray(scope.member.domain.applications)) {
                        element.append("<collection2 collection='member.domain.applications'></collection2>");
                        $compile(element.contents())(scope)
                    }
                }
            }
        });

    app.directive('member2', function ($compile) {
        return {
            restrict: "E",
            replace: true,
            scope: {
                member: '='
            },
            template: "<li class='ui-state-default dropdown'><a class='btn btn-link dropdown-toggle' href='{{member.url}}'>{{member.caption}}</a>" +
                "<div class='dropdown-menu'><span>{{member.description}}</span>" +
                "<span><a href='{{member.url}}'>Ouvrir l'application</a></span>"+
                "<span><a href='#' ng-click='delFromFav($index)'>Supprimer des favoris</a></span></div>"+
                "</li>"

        }
    });
};


// ************* utils *************


function encodeResourceUrl(resourceUrl) {
    return resourceUrl.
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