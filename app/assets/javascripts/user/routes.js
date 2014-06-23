/**
 * Configure routes of user module.
 */
define(['angular', './controllers', 'common'], function(angular, controllers) {
  'use strict';

  var mod = angular.module('user.routes', ['multicast.common']);
  mod.config(['$stateProvider', function($stateProvider) {
      $stateProvider
      .state('home.login',
          {url: 'login',
           views: {
               'content': {
                   templateUrl: '/assets/templates/user/login.html',
                   controller: controllers.LoginCtrl
               }
           }

          }
      )
      .state('home.signup',
          {url: 'signup',
              views: {
                  'content': {
                      templateUrl: '/assets/templates/user/signup.html',
                      controller: controllers.SignupCtrl
                  }
              }

          }
      );
      //.when('/users', {templateUrl:'/assets/templates/user/users.html', controller:controllers.UserCtrl})
      //.when('/users/:id', {templateUrl:'/assets/templates/user/editUser.html', controller:controllers.UserCtrl});
  }]);
  return mod;
});
