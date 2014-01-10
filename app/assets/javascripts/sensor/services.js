/**
 * User service, exposes user model to the rest of the app.
 */
define(["angular", "common"], function(angular) {
  var mod = angular.module("sensor.services", ["ngRoute", "yourprefix.common"]);

  mod.factory("sensorService", ["$http", "$q", "playRoutes", function($http, $q, playRoutes) {
    var SensorService = function() {

        var self = this;

        self.getAllSensors = function() {
            return playRoutes.controllers.SensorCtrl.getAllSensors().get().then(function(response) {
                return response.data;
            });
        };

        self.createSensor = function(data) {
            return playRoutes.controllers.SensorCtrl.createSensor().post(data).then(function(response) {
                return response.data;
            });
        };

        self.getSensor = function(sensorId) {
            return playRoutes.controllers.SensorCtrl.getSensor(sensorId).get().then(function(response) {
                return response.data;
            });
        };

        self.updateSensor = function(sensorId, data) {
            return playRoutes.controllers.SensorCtrl.updateSensor(sensorId).post(data).then(function(response) {
                return response.data;
            });
        };

        self.deleteSensor = function(sensorId) {
            return playRoutes.controllers.SensorCtrl.deleteSensor(sensorId)['delete']().then(function(response) {
                return response.data;
            });
        };

    };

    return new SensorService();
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
