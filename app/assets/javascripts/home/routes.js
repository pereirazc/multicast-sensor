/**
 * Home routes.
 */
define(['angular', './controllers', 'common'], function(angular, controllers) {
  'use strict';

  var mod = angular.module('home.routes', ['multicast.common']);
  mod.config(['$routeProvider', function($routeProvider) {
    $routeProvider
      .when("/",  {templateUrl: "/assets/templates/home/home.html", controller: controllers.HomeCtrl})
      .otherwise( {templateUrl: "/assets/templates/home/notFound2.html", controller: controllers.ErrorCtrl,
            resolve: {
                error: ['$rootScope', function ($rootScope) {
                    console.log('hide navbar');
                    $rootScope.error = true;
                }]
            } });
  }]);
  return mod;
});
