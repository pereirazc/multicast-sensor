/**
 * Dashboard routes.
 */
define(["angular", "./controllers", "common"], function(angular, controllers) {
  var mod = angular.module("dashboard.routes", ["ngRoute", "dashboard.services", "yourprefix.common"]);

  mod.config(["$routeProvider", function($routeProvider) {
    $routeProvider
      .when("/dashboard",  {templateUrl: "/assets/templates/dashboard/dashboard.html",  controller:controllers.DashboardCtrl});
      //.when("/admin/dashboard",  {templateUrl: "/assets/templates/dashboard/admin.html",  controller:controllers.AdminDashboardCtrl})
  }]);
  return mod;
});
