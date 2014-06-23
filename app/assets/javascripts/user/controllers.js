/**
 * User controllers.
 */
define([], function() {
  'use strict';

    var LoginCtrl = function($rootScope, $scope, $state, userService, toaster) {

      $rootScope.pageTitle = 'Login';

      $scope.credentials = {};

      $scope.$watch(function() {
          var user = userService.getUser();
          return user;
      }, function(user) {
          if (user!==undefined) {
              $state.go("home.dashboard");
          }
      }, true);

    $scope.login = function(credentials) {
        userService.login(credentials).then(
            function() {
                toaster.clear();
                $state.go('home.dashboard');
                toaster.pop('success', "Login", "Welcome back " + userService.getUser().firstName + " " + userService.getUser().lastName, 2000);
            },
            function() {
                toaster.pop('error', "Login", "Incorrect username/password", 30000);
            }
        );
    };
    };
    LoginCtrl.$inject = ['$rootScope', '$scope', '$state', 'userService', 'toaster'];


    var SignupCtrl = function($rootScope, $scope, $state, userService, toaster) {

        $rootScope.pageTitle = 'Sign Up';

        $scope.registration = {};

        $scope.$watch(function() {
            var user = userService.getUser();
            return user;
        }, function(user) {
            if (user!==undefined) {
                $state.go("home.dashboard");
            }
        }, true);

        $scope.signup = function(registration) {
            userService.signup(registration).then(
                function(res) {
                    toaster.clear();
                    $state.go('home.login');
                    toaster.pop('success', 'Sign Up', 'user ' + res.data.email + ' registered succesfully. You can log in now', 30000);
                },
                function(err) {
                    console.log(err);
                    toaster.pop('error', 'Sign Up', err.data, 10000);
                }
            );
        };
    };
    SignupCtrl.$inject = ['$rootScope', '$scope', '$state', 'userService', 'toaster'];

    return {
        LoginCtrl: LoginCtrl,
        SignupCtrl: SignupCtrl
    };

});
