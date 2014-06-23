/**
 * Dashboard controllers.
 */
define(["angular"], function(angular) {
  "use strict";

  var SensorCtrl = function($rootScope, $scope, userService, sensorService, feedService, $state, $stateParams, sensor, toaster) {

      $scope.sensor = sensor;
      $rootScope.pageTitle = $scope.sensor.label;

    /*sensorService.getSensor($routeParams.sensorId).success(
      function(data) {
          $scope.sensor = data;
          $rootScope.pageTitle = $scope.sensor.label;
      }
    );*/

    $scope.editSensor = function(sensorId) {
        $scope.modal = {};
        $scope.modal.title = 'Edit Sensor...';
        $scope.modal.ok = function(sensor) {
            sensorService.updateSensor(sensorId, sensor).success(
                function() {
                    angular.element("#sensorModal").modal("hide");
                    $scope.sensor = sensor;
                }
            );
        };
        $scope.modal.cancel = function() {
            angular.element("#sensorModal").modal("hide");
        };
    };

    $scope.deleteSensor = function(sensorId) {

        $scope.modal = {};

        $scope.modal.title = 'Confirmation';
        $scope.modal.content = 'Are you sure you want to remove the sensor?';

        $scope.modal.yes = function() {

            sensorService.deleteSensor(sensorId).success(
                function() {
                    angular.element("#confirmModal").modal("hide");
                    angular.element("#confirmModal").on('hidden.bs.modal', function () {
                        var label = $scope.sensor.label;
                        toaster.clear();
                        $state.go('home.dashboard');
                        toaster.pop('warning', 'Deleted Sensor', 'sensor ' + label + ' (' + sensorId + ')' + ' removed succesfully.', 2000);
                        $scope.$apply();
                    });
                }
            );
        };
        $scope.modal.no = function() {
            angular.element("#confirmModal").modal("hide");
        };

    };

    $scope.createFeed = function(sensorId) {
        $scope.modal = {};

        $scope.feed = {};
        $scope.feed.alertConfig = {};
        $scope.feed.alertConfig.status = false;
        $scope.feed.alertConfig.min = 0;
        $scope.feed.alertConfig.max = 0;

        $scope.modal.title = 'New Feed...';
        $scope.modal.ok = function(feed) {
            feedService.createFeed(sensorId, feed).success(
                function(data/*, status, headers, response*/) {
                    angular.element("#feedModal").modal("hide");
                    angular.element("#feedModal").on('hidden.bs.modal', function () {

                        $state.go('home.feed', {sensorId: sensorId, feedId: data.feedId});
                        $scope.$apply();
                    });
                }
            );
        };
        $scope.modal.cancel = function() {
            angular.element("#feedModal").modal("hide");
        };
    };

    $scope.feedDetails = function(sensorId, feedId) {
        $state.go('home.feed', {sensorId: sensorId, feedId: feedId});
    };

    $scope.deleteFeed = function(sensorId, feedId) {

        $scope.modal = {};

        $scope.modal.title = 'Confirmation';
        $scope.modal.content = 'Are you sure you want to remove the feed?';

        $scope.modal.yes = function() {

            feedService.deleteFeed(sensorId, feedId).success(
                function() {
                    angular.element("#confirmModal").modal("hide");
                    angular.element("#confirmModal").on('hidden.bs.modal', function () {
                        sensorService.getSensor($stateParams.sensorId).success(
                            function(data/*, status, headers, response*/) {
                                $scope.sensor = data;
                                $rootScope.pageTitle = $scope.sensor.label;
                            }
                        );
                        $scope.$apply();
                    });
                }
            );
        };
        $scope.modal.no = function() {
            angular.element("#confirmModal").modal("hide");
        };

    };

  };
  SensorCtrl.$inject = ['$rootScope', '$scope', 'userService', 'sensorService', 'feedService', '$state', '$stateParams', 'sensor', 'toaster'];

  return {
    SensorCtrl: SensorCtrl
    //SensorModalCtrl: SensorModalCtrl
  };

});
