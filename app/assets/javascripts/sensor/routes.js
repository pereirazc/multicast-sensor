/**
 * Sensor routes.
 */
define(["angular", "./controllers", "common"], function(angular, controllers) {
  var mod = angular.module("sensor.routes", ["ngRoute", "sensor.services", "yourprefix.common"]);
  mod.config(["$routeProvider", "userResolve", function($routeProvider, userResolve) {
    $routeProvider
      .when("/dashboard/:sensorId",  {templateUrl: "/assets/templates/sensor/sensor-dashboard.html",  controller:controllers.SensorCtrl, resolve:userResolve});
  }]);
  return mod;
});
