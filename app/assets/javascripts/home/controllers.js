/**
 * Home controllers.
 */
define([], function() {
    "use strict";

    /** Controls the index page */
    var HomeCtrl = function($scope, $rootScope, userService, $location) {
        $rootScope.pageTitle = "Welcome";

        $scope.$watch(function() {
            var user = userService.getUser();
            return user;
        }, function(user) {
            if (user===undefined) {
                $location.path("/login");
            } else {
                $location.path("/dashboard");
                $scope.user = user;
            }
        }, true);

        //$location.path("/dashboard");
    };
    HomeCtrl.$inject = ["$scope", "$rootScope", "userService", "$location"];

    /** Controls the header */
    var HeaderCtrl = function($scope, $rootScope, userService, helper, $location) {

        //$scope.user = $rootScope.user;
        // Wrap the current user from the service in a watch expression
        $scope.$watch(function() {
            var user = userService.getUser();
            return user;
        }, function(user) {
            if (user===undefined) {
                $location.path("/login");
            }
            $scope.user = user;
        }, true);

        $scope.logout = function() {
            userService.logout().then(
                function() {
                    $location.path("/login");
                }
            );

        };
    };
    HeaderCtrl.$inject = ["$scope","$rootScope", "userService", "helper", "$location", "$log"];

    /** Controls the footer */
    var FooterCtrl = function() {
    };
    FooterCtrl.$inject = ["$scope"];

    /** Controls the footer */
    var ErrorCtrl = function($scope, $location) {

        $scope.backHome = function () {
            $location.path('/dashboard');
        };

    };
    ErrorCtrl.$inject = ['$scope','$location'];

    return {
        HeaderCtrl: HeaderCtrl,
        FooterCtrl: FooterCtrl,
        HomeCtrl: HomeCtrl,
        ErrorCtrl: ErrorCtrl
    };

});
