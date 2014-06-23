/**
 * Dashboard controllers.
 */
define(['angular'], function(angular) {
  'use strict';

  var DashboardCtrl = function($rootScope, $scope, userService, sensorService, $state, user, sensors) {

    console.log(user);
    console.log(sensors);
    $scope.user = user;
    $scope.sensors = sensors;

    $rootScope.pageTitle = "Multicast Sensor";

    //$scope.user = userService.getUser();

    //$scope.sensors = [];

	//$scope.sensors = sensorService.getAllSensors()
    /*sensorService.getAllSensors().success(
		function (data) {
			$scope.sensors = data;
		}
	);*/

    $scope.showSensorIdField = false;

    $scope.onKeyPress = function($event) {
        console.log($event);
        if ($event.ctrlKey && $event.shiftKey && ($event.charCode === 19)) {
            console.log('ok');
            $scope.showSensorIdField = !$scope.showSensorIdField;
            if (!$scope.showSensorIdField) {
                $scope.sensor.sensorId = undefined;
            }
        }
    };

    $scope.createSensor = function () {

        $scope.modal = {};
        $scope.sensor = {};
        $scope.modal.title = 'New Sensor...';

        $scope.modal.ok = function(sensor) {
            sensorService.createSensor(sensor).success(
				function (data/*, status, headers, response*/) {
                    angular.element("#sensorModal").modal("hide");
                    angular.element("#sensorModal").on('hidden.bs.modal', function () {
                        $scope.$apply();
                        $state.go('home.sensor', {sensorId: data.sensorId });
                    });
				}
			);
        };
        $scope.modal.cancel = function() {
            angular.element("#sensorModal").modal("hide");
        };

    };

    $scope.sensorDetails = function(sensorId) {

        $state.go('home.sensor', {sensorId: sensorId });

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
                        sensorService.getAllSensors($rootScope.authToken).success(
                            function(data/*, status, headers, response*/) {
                                $scope.sensors = data;
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
  DashboardCtrl.$inject = ["$rootScope", "$scope", "userService", "sensorService", "$state", "user", "sensors"];


  return {
    DashboardCtrl: DashboardCtrl
  };

});
