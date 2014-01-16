/**
 * User package module.
 * Manages all sub-modules so other RequireJS modules only have to import the package.
 */
define(["angular", "./routes", "./controllers", "./services"], function(angular, routes, controllers, services) {
  var mod = angular.module("yourprefix.user", ["ngRoute", "ngCookies", "user.routes", "user.services"]);

  mod.controller("LoginCtrl", controllers.LoginCtrl);
  mod.controller("SignUpCtrl", controllers.DashboardCtrl);

  console.log(mod);



  return mod;




});
