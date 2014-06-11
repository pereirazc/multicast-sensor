/**
 * User controllers.
 */
define([], function() {
  'use strict';

  var LoginCtrl = function($rootScope, $scope, $location, userService) {

      $rootScope.pageTitle = 'Login';

      $scope.credentials = {};

      $scope.$watch(function() {
          var user = userService.getUser();
          return user;
      }, function(user) {
          if (user!==undefined) {
              $location.path("/dashboard");
          }
      }, true);

    $scope.login = function(credentials) {
      userService.loginUser(credentials).then(function() {
        $location.path('/dashboard');
      });
    };
  };
  LoginCtrl.$inject = ['$rootScope', '$scope', '$location', 'userService'];

  return {
    LoginCtrl: LoginCtrl
  };

});
