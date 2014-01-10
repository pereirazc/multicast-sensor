/**
 * User service, exposes user model to the rest of the app.
 */
define(["angular", "common"], function(angular) {
  var mod = angular.module("dashboard.services", ["ngRoute", "yourprefix.common"]);

  mod.factory("dashboardService", ["$http", "$q", "playRoutes", function($http, $q, playRoutes) {
    var DashboardService = function() {


    };

    return new DashboardService();
  }]);

  /**
   * If the current route does not resolve, go back to the start page.
   */
  var handleRouteError = function($rootScope, $location) {
    $rootScope.$on("$routeChangeError", function(e, next, current) {
      //$location.path("/");
    });
  };
  handleRouteError.$inject = ["$rootScope", "$location"];
  mod.run(handleRouteError);
  return mod;
});
