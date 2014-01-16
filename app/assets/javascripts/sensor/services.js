/**
 * User service, exposes user model to the rest of the app.
 */
define(["angular", "common"], function(angular) {
  var mod = angular.module("sensor.services", ["ngRoute", "yourprefix.common"]);

  mod.factory("sensorService", ["$http", "$q", "playRoutes", function($http, $q, playRoutes) {
    var SensorService = function() {

        var self = this;

        setAuthHeader = function (authToken) {
            $http.defaults.headers.common['X-AUTH-TOKEN'] = authToken;
        }

        self.getAllSensors = function(token) {
            setAuthHeader(token);
            return playRoutes.controllers.SensorCtrl.getAllSensors().get();
        };

        self.createSensor = function(token, data) {
            setAuthHeader(token);
            return playRoutes.controllers.SensorCtrl.createSensor().post(data);
        };

        self.getSensor = function(token, sensorId) {
            setAuthHeader(token);
            return playRoutes.controllers.SensorCtrl.getSensor(sensorId).get();
        };

        self.updateSensor = function(token, sensorId, data) {
            setAuthHeader(token);
            return playRoutes.controllers.SensorCtrl.updateSensor(sensorId).post(data);
        };

        self.deleteSensor = function(token, sensorId) {
            setAuthHeader(token);
            return playRoutes.controllers.SensorCtrl.deleteSensor(sensorId)['delete']();
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
