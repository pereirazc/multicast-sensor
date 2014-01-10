/**
 * Main controllers.
 */
define([], function() {
  "use strict";

  /** Controls the index page */
  var HomeCtrl = function($scope, $rootScope, $location, helper) {
    $location.path("/dashboard");
    console.log(helper.sayHi());
    $rootScope.pageTitle = "Welcome";
  };
  HomeCtrl.$inject = ["$scope", "$rootScope", "$location", "helper"];

  /** Controls the header */
  var HeaderCtrl = function($scope, $rootScope, userService, helper, $location) {

    //$scope.user = $rootScope.user;
    // Wrap the current user from the service in a watch expression
    $scope.$watch(function() {
      var user = userService.getUser();
      return user;
    }, function(user) {
        $scope.user = user;
        if (user===undefined) $location.path("/login");
    }, true);

    $scope.logout = function() {
      userService.logout();
      $scope.user = undefined;
      $location.path("/login");
    };
  };
  HeaderCtrl.$inject = ["$scope","$rootScope", "userService", "helper", "$location", "$log"];

  /** Controls the footer */
  var FooterCtrl = function($scope) {
  };
  FooterCtrl.$inject = ["$scope"];

  return {
    HeaderCtrl: HeaderCtrl,
    FooterCtrl: FooterCtrl,
    HomeCtrl: HomeCtrl
  };

});
