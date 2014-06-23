/**
 * Home controllers.
 */
define([], function() {
    "use strict";

    /** Controls the index page */
    var HomeCtrl = function($scope, $rootScope, userService, $state) {

        console.log('test');

        $rootScope.pageTitle = "Welcome";

        $scope.$watch(function() {
            var user = userService.getUser();
            return user;
        }, function(user) {
            if (user===undefined) {
                $state.go("home.login");
            } else {
                $state.go("home.dashboard");
                $scope.user = user;
            }
        }, true);

    };
    HomeCtrl.$inject = ["$scope", "$rootScope", "userService", "$state"];

    /** Controls the header */
    var HeaderCtrl = function($scope, $rootScope, userService, helper, $state, toaster) {

        //$scope.user = $rootScope.user;
        // Wrap the current user from the service in a watch expression
        $scope.$watch(function() {
            var user = userService.getUser();
            return user;
        }, function(user) {
            if (user===undefined) {
                $state.go("home.login");
            }
            $scope.user = user;
        }, true);

        $scope.logout = function() {

            var user = userService.getUser();

            userService.logout().then(
                function() {
                    toaster.clear();
                    $state.go("home.login");
                    toaster.pop('success', "Logout", "Bye " + user.firstName + " " + user.lastName, 1000);
                },
                function() {
                    toaster.pop('error', "Logout", "Failed to log out user " + user.firstName + " " + user.lastName, 30000);
                }
            );

        };
    };
    HeaderCtrl.$inject = ["$scope","$rootScope", "userService", "helper", "$state", "toaster"];

    /** Controls the footer */
    var FooterCtrl = function() {
    };
    FooterCtrl.$inject = ["$scope"];

    /** Controls the footer */
    var ErrorCtrl = function($scope, $state) {

        $scope.backHome = function () {
            $state.go('home.dashboard');
        };

    };
    ErrorCtrl.$inject = ['$scope','$state'];

    return {
        HeaderCtrl: HeaderCtrl,
        FooterCtrl: FooterCtrl,
        HomeCtrl: HomeCtrl,
        ErrorCtrl: ErrorCtrl
    };

});
