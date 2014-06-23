/**
 * The app module, as both AngularJS as well as RequireJS module.
 * Splitting an app in several Angular modules serves no real purpose in Angular 1.2.
 * (Hopefully this will change in the near future.)
 * Splitting it into several RequireJS modules allows async loading. We cannot take full advantage
 * of RequireJS and lazy-load stuff because the angular modules have their own dependency system.
 */
define(['angular', 'home', 'user', 'dashboard', 'sensor', 'feed'], function(angular) {
  'use strict';

  var app = angular.module('app', ['toaster', 'angular-loading-bar', 'multicast.home', 'multicast.user', 'multicast.dashboard', 'multicast.sensor', 'multicast.feed']);
  // We must already declare most dependencies here (except for common), or the submodules' routes
  // will not be resolved

    app.factory('authInterceptor',['$injector', '$rootScope', '$q', '$window', function ($injector, $rootScope, $q /*,$window*/) {
        return {
            request: function (config) {
                config.headers = config.headers || {};
                var token = $injector.get('userService').getToken();
                if (token !== undefined) {
                    console.log('adding token');
                    config.headers['X-AUTH-TOKEN'] = token;
                }
                return config;
            },

            responseError: function(err) {

                if (err.status === 401) {
                    // handle the case where the user is not authenticated
                    $injector.get('userService').cleanAuth();
                    $injector.get('$state').go('home.login');
                }

                if (err.status === 404) {
                    // handle the case where the user is not authenticated
                    $injector.get('$state').go('404', {}, {location:false});
                    //$window.location.replace('/error');
                }

                return $q.reject(err);
            }
        };
    }]);

    app.config(['$locationProvider' , '$httpProvider', function($locationProvider, $httpProvider) {

        // use the HTML5 History API
        $locationProvider.html5Mode(true);
        $httpProvider.interceptors.push('authInterceptor');

    }]);



  return app;
});
