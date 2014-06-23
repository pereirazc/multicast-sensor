/**
 * User service, exposes user model to the rest of the app.
 */
define(["angular", "common"], function(angular) {

  'use strict';

    var mod = angular.module('sensor.services', ['multicast.common']);

  mod.factory('sensorService', ['$http', '$q', 'playRoutes', function($http, $q, playRoutes) {

    return {

        getAllSensors: function() {
            return playRoutes.controllers.SensorCtrl.getAllSensors().get();
        },

        createSensor: function(data) {
          return playRoutes.controllers.SensorCtrl.createSensor().post(data);
        },

        getSensor: function(sensorId) {
            return playRoutes.controllers.SensorCtrl.getSensor(sensorId).get();
        },

        updateSensor: function(sensorId, data) {
            return playRoutes.controllers.SensorCtrl.updateSensor(sensorId).post(data);
        },

        deleteSensor: function(sensorId) {
          return playRoutes.controllers.SensorCtrl.deleteSensor(sensorId)['delete']();
        }

    };

    /*var SensorService = function() {

        var self = this;

        self.getAllSensors = function() {
            return playRoutes.controllers.SensorCtrl.getAllSensors().get();
        };

        self.createSensor = function(data) {
            return playRoutes.controllers.SensorCtrl.createSensor().post(data);
        };

        self.getSensor = function(sensorId) {
            return playRoutes.controllers.SensorCtrl.getSensor(sensorId).get();
        };

        self.updateSensor = function(sensorId, data) {
            return playRoutes.controllers.SensorCtrl.updateSensor(sensorId).post(data);
        };

        self.deleteSensor = function(sensorId) {
            return playRoutes.controllers.SensorCtrl.deleteSensor(sensorId)['delete']();
        };

    };

    return new SensorService();*/
  }]);

  return mod;
});
