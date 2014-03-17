catAppPortlet = function (appName, resourceURL) {

    var app = angular.module(appName, ['ui.bootstrap']);


    app.controller('AppList2Ctrl', function ($scope, $window, $http, FavorisService) {

        $scope.box1 = $scope.box2 = true;

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
//            $scope.applications = data.apps;
//            $scope.removedApps = data.removedApps;
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
        };

        $scope.addToFav = function (app) {
            alert(app.code);
            var favTodd = FavorisService.find(app);
            if (angular.isUndefined(favTodd) || favTodd == null) {
                $scope.alerts.push({type: 'danger', msg: "L'application " + app + " est déjà dans les favoris"});
                return
            }
            else {
                alert(app);
                $http
                    .get(url(encodeResourceUrl(resourceURL), 'addFavori', app))
                    .success(function (data) {
                        $scope.alerts.push({type: 'success', msg: data.message});
                        FavorisService.create(data.app, false);
                        $scope.applications = FavorisService.list();
                    })
                    .error(function () {
                        $scope.alerts.push({type: 'danger', msg: "L'application " + data.app.code + " n'a pas pu être ajoutée"});
                    });

            }

        };


        $scope.closeAlert = function (index) {
            $scope.alerts.splice(index, 1);
        };

        $http
            .get(url(encodeResourceUrl(resourceURL), 'doms')).success(function (data) {
                $scope.domaines = data.doms;
            });

    });

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

    app.directive('collection', function () {
        return {
            restrict: "E",
            replace: true,
            scope: {
                collection: '='
            },                    //dl-menu dl-menuopen
            template: "<ul class='sub-menu'><member ng-repeat='member in collection' member='member'></member></ul>"
        }
    });

    app.directive('collection3', function () {
        return {
            restrict: "E",
            replace: true,
            scope: {
                collection: '='
            },                    //dl-submenu
            template: "<ul class='sub-menu'><member ng-repeat='member in collection' member='member'></member></ul>"
        }
    });

    app.directive('collection2', function () {
        var postsTemplate = "<ul class='sub-menu menudrop'><member2 ng-repeat='member in collection' member='member'></member2></ul>";
        return {
            restrict: 'E',
            // Replace the div with our template
            replace: true,
            template: postsTemplate,
            scope: {
                domApps: '@'
            },
            // Specify a controller directly in our directive definition.
            controller: function ($scope, applicationFactory) {
                $scope.collection = [];
                if ($scope.domApps) {
                    return applicationFactory.apps($scope.domApps).success(function (data) {
                        return $scope.collection = data.appsByDom;
                    });
                }
            }
        };
    });

    app.directive('member', function ($compile) {
        return {
            restrict: "E",
            replace: true,
            scope: {
                member: '='
            },
            template: "<li><a href='#'>{{member.domain.caption}}</a></li>",
            link: function (scope, element, attrs) {
                if ((scope.member.subDomains.length > 0)) {
                    element.append("<collection3 collection='member.subDomains'></collection3>");
                    $compile(element.contents())(scope)
                }
                if ((scope.member.domain.applications.length > 0)) {
                    element.append("<collection2 dom-apps='{{member.domain.applications.join()}}'></collection2>");
                    $compile(element.contents())(scope)
                }
            }
        }
    });

    app.directive('member2', function ($parse) {
        return {
            restrict: "E",
            replace: true,
            scope: {member: '='},
            template: "<li class='ui-state-default'><a>{{member.caption}}</a>" +
                "<div class='dropdown'><span>{{member.description}}</span>" +
                "<p>" +
                "<a href='{{member.url}}' class='ui-state-default ui-corner-all'>Ouvrir l'application</a>" +
                "<a ng-click='pushApp()' class='ui-state-default ui-corner-all'>Ajouter aux favoris</a></p></div>" +
                "</li>",
            link: function (scope, element, attrs) {
                var getMemberCode = $parse(attrs.member);

                scope.$watch(getMemberCode, function (value) {
                    scope.code = value.code;
                });
                scope.pushApp = function () {
                    $scope.addToFav(scope.code);
                }
            }
        }
    });

    app.directive('codeapp', function ($parse) {
        return {
            restrict: 'A',
            //Child scope, not isolate
            scope: true,
            link: function (scope, element, attrs) {
                element.bind('click', function (e) {
                    if ((attrs.codeapp != nu)) {
                        scope.$apply(function () {
                            //$parse returns a getter function to be executed against an object
                            var fn = $parse(attrs.addAction);
                            //In our case, we want to execute the statement in confirmAction i.e. 'doIt()' against the scope which this directive is bound
                            //Because the scope is a child scope, not an isolated scope, the prototypical inheritance of scopes will mean that it will eventually find a 'doIt' function bound against a parent's scope
                            fn(scope, {app: codeapp, $event: e});
                        });
                    }
                });
            }
        };
    });

    app.directive('slideToggle', function () {
        return {
            restrict: 'A',
            scope: {
                isOpen: "=slideToggle"
            },
            link: function (scope, element, attr) {
                var slideDuration = parseInt(attr.slideToggleDuration, 10) || 200;
                scope.$watch('isOpen', function (newVal, oldVal) {
                    if (newVal !== oldVal) {
                        element.stop().slideToggle(slideDuration);
                    }
                });
            }
        };
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