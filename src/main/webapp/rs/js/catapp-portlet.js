//var catAppAdmin = angular.module('catappAdmin', []);
catAppAdmin = function(appName, resourceURL) {
	var app = angular.module(appName, []);

	app.controller('AppList2Ctrl', function($scope, $http) {

        $http
            .get(url(resourceURL, 'apps')).success(function (data) {
            $scope.applications = data.apps;
            $scope.removedApps = data.removedApps;
        });
	});

    app.controller('DomainListCtrl', function($scope, $http) {
        $http
            .get(url(resourceURL, 'doms')).success(function (data) {
                $scope.domaines = data.doms;
            });


        $scope.delFromFav = function(value) {
            $http
                .get(url(resourceURL, 'deleteFavori', value))
                .success(function () {
                    $scope.delMessage = "L'application "+value+" est supprimée des favoris";
                    })
                .error(function () {
                $scope.delMessage = "L'application "+value+" n'a pas pu être supprimée";
            });
        };


    });

};

// ************* utils *************

//forge a portlet resource url
function url(pattern, id, p1, p2, p3, p4) {
    return pattern.
        replace("@@id@@", id)
        .replace("__p1__", p1)
        .replace("__p2__", p2)
        .replace("__p3__", p3)
        .replace("__p4__", p4);
}