/**
 * Dashboard controllers.
 */
define(["angular"], function(angular) {
  "use strict";

  var DashboardCtrl = function($rootScope, $scope, userService, sensorService, $location) {

    $rootScope.pageTitle = "Multicast Sensor";

    $scope.user = userService.getUser();

    $scope.sensors = [];

    sensorService.getAllSensors().then(
        function(response) {
            $scope.sensors = response;
        }
    );

    $scope.createSensor = function () {

        $scope.modal = {};
        $scope.sensor = {};
        $scope.modal.title = 'New Sensor...';

        $scope.modal.ok = function(sensor) {
            sensorService.createSensor(sensor).then(
                function(response) {
                    angular.element("#sensorModal").modal("hide");
                    angular.element("#sensorModal").on('hidden.bs.modal', function () {
                        $location.path('dashboard/'.concat(response.sensorId));
                        $scope.$apply();
                    });
                }
            );
        };
        $scope.modal.cancel = function() {
            angular.element("#sensorModal").modal("hide");
        };

    };

    $scope.sensorDetails = function(sensorId) {
        $location.path('dashboard/'.concat(sensorId));
    };

    $scope.deleteSensor = function(sensorId) {
        $scope.modal = {};

        $scope.modal.title = 'Confirmation';
        $scope.modal.content = 'Are you sure you want to remove the sensor?';

        $scope.modal.yes = function() {

            sensorService.deleteSensor(sensorId).then(
                function() {
                    angular.element("#confirmModal").modal("hide");
                    angular.element("#confirmModal").on('hidden.bs.modal', function () {
                        sensorService.getAllSensors().then(
                            function(response) {
                                $scope.sensors = response;
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
  DashboardCtrl.$inject = ["$rootScope", "$scope", "userService", "sensorService", "$location"];

  /*var AdminDashboardCtrl = function($scope, userService, sensorService, $location) {

  };
  AdminDashboardCtrl.$inject = ["$scope", "userService", "sensorService"];*/

  return {
    DashboardCtrl: DashboardCtrl/*,
    AdminDashboardCtrl: AdminDashboardCtrl*/
  };

});
