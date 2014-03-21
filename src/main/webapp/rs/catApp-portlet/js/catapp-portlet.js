catAppPortlet = function (appName, resourceURL) {

    var app = angular.module(appName, ['ui.bootstrap']);


    app.controller('AppList2Ctrl', function ($scope, $http, FavorisService) {

        $scope.alerts = [];

        $http
            .get(url(encodeResourceUrl(resourceURL), 'apps')).success(function (data) {
                angular.forEach(data.apps, function (app) {
                    FavorisService.create(app, false);
                });
                angular.forEach(data.noAccessApps, function (app) {
                    FavorisService.create(app, true);
                });
                $scope.applications = FavorisService.list();
            });

        $scope.delFromFav = function (idx) {
            var delApp = $scope.applications[idx];

            $http
                .get(url(encodeResourceUrl(resourceURL), 'deleteFavori', delApp.code))
                .success(function (data) {
                    $scope.alerts.push({type: 'success', msg: data.message});
                    $scope.applications.splice(idx, 1);
                })
                .error(function () {
                    $scope.alerts.push({type: 'danger', msg: "L'application " + delApp.code + " n'a pas pu être supprimée"});
                });

            setTimeout(function() {
                $(".alert*").fadeOut("slow");
            }, 1000)
        };

        $scope.addFav = function (app) {
            var favTodd = FavorisService.find(app.code);
            if (!angular.isUndefined(favTodd) || favTodd != null) {
                $scope.alerts.push({type: 'danger', msg: "L'application " + app.code + " est déjà dans les favoris"});
            } else {
                $http
                    .get(url(encodeResourceUrl(resourceURL), 'addFavori', app.code))
                    .success(function (data) {
                        $scope.alerts.push({type: 'success', msg: data.message});
                        FavorisService.create(app, false);
                        $scope.applications = FavorisService.list();
                    })
                    .error(function () {
                        $scope.alerts.push({type: 'danger', msg: "L'application " + data.app.code + " n'a pas pu être ajoutée"});
                    });
            }
            $(".dropdown").fadeOut("slow");
            setTimeout(function() {
                $(".alert").fadeOut("slow");
            }, 1000)
        };

        $scope.callTooltip = function(obj) {
            var idt = obj.target;
            $( idt ).next( '.dropdown').fadeToggle('slow');
        }

        $scope.closeAlert = function (index) {
            $scope.alerts.splice(index, 1);
        };


        $scope.updateFavorite =  function ()  {
            $http
                .get(url(encodeResourceUrl(resourceURL), 'updateFavori', newValues))
                .success(function (data) {
                    $scope.alerts.push({type: 'success', msg: data.message});
                })
                .error(function () {
                    $scope.alerts.push({type: 'danger', msg: "Les préférencess n'ont pas pu être mises à jour"});
                }
            );
        };

        $http
            .get(url(encodeResourceUrl(resourceURL), 'doms')).success(function (data) {
                $scope.domaines = data.doms;
            });

//        $scope.$watchCollection('applications', function(newValue, oldValue) {
//            if (newValue === oldValue) {
//                console.log("Les memes newValue : "+newValue+"  oldValue  :"+oldValue);
//                return;
//            } else {
//                console.log("newValue : "+newValue+"  oldValue  :"+oldValue);
//            }
//        });

    });

//    app.directive('sortable', function () {
//        return {
//            restrict: 'A',
//            scope: {
//                domEl: '@',
//                sortable: '&'
//            },
//            link: function (scope, elm) {
//                scope.domEl.ondragend(function () {
//                    scope.sortable();
//                });
//
//            }
//        }
//    });

    app.factory('applicationFactory', function ($http) {
        var apps;
        apps = function (domid) {
            return $http.get(url(encodeResourceUrl(resourceURL), 'getAppsByDom', domid));
        };
        return {
            apps: apps
        };
    });

    app.service('FavorisService', function () {
        //favoris array to hold list of all favoris
        var favoris = [];

        //save method create a new favori
        this.create = function (app, removed) {
            var favori = {"code": app.code, "title": app.title, "caption": app.caption, "description": app.description, "url": app.url, "acces": app.activation, "state": removed};
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
            return favoris;
        }


    });

    app.directive('domaine', function ($compile, $timeout) {
        return {
            restrict: "E",
            replace: true,
            transclude: true,
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
                    element.append("<ul class='sub-menu menudrop'><application ng-repeat='member in collection   | filter:searchText'  member='member' add-fav='addFav()'></application></ul>");
                    $compile(element.contents())(scope)
                }
            },
            controller: function ($scope, applicationFactory) {
                $scope.collection = [];
                if ($scope.member.domain.applications.length > 0) {
                    $scope.domApps = $scope.member.domain.applications.join();
                    return applicationFactory.apps($scope.domApps).success(function (data) {
                        return $scope.collection = data.appsByDom;
                    });
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
                "<div class='dropdown'><span>{{member.description}}</span>" +
                "<p>" +
                "<a href='{{member.url}}' class='ui-state-default ui-corner-all'>Ouvrir l'application</a>" +
                "<a href='#' ng-click='pushapp(); togAction($(this).parent().parent());' class='ui-state-default ui-corner-all addfav'>Ajouter aux favoris</a></p></div>" +
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
function togAction(obj) {
    $(obj).fadeToggle('slow');
}

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