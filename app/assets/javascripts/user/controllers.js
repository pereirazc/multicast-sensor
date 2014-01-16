/**
 * User controllers.
 */
define(["angular"], function(angular) {
  "use strict";

  var LoginCtrl = function($rootScope, $scope, $cookieStore, $location, userService, $httpProvider) {
    $scope.credentials = {};


      $scope.$watch(function() {
          var user = userService.getUser();
          return user;
      }, function(user) {
          if (user!=undefined) {
              $location.path("/dashboard");
          }
      }, true);

    $scope.login = function(credentials) {

        userService.loginUser(credentials).then(function(user) {
            $location.path("/dashboard");
        });

    };
  };
  LoginCtrl.$inject = ["$rootScope", "$scope", "$cookieStore", "$location", "userService"];

    var SignUpCtrl = function($scope, $location, userService) {
        $scope.credentials = {};

        $scope.login = function(credentials) {
            userService.loginUser(credentials).then(function(user) {
                $location.path("/dashboard");
            });
        };
    };
    SignUpCtrl.$inject = ["$rootScope", "$scope", "$location", "userService"];


  return {
    LoginCtrl: LoginCtrl,
    SignUpCtrl: SignUpCtrl
  };

});
