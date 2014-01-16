/**
 * Dashboard, shown after user is logged in.
 * dashboard/main.js is the entry module which serves as an entry point so other modules only have
 * to include a single module.
 */
define(["angular", "./routes", "./controllers", "./services"], function(angular, routes, controllers) {

    var mod = angular.module("yourprefix.dashboard", ["ngRoute", "dashboard.routes", "dashboard.services"]);

    mod.controller("DashboardCtrl", controllers.DashboardCtrl);

    return mod;

});