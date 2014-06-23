/**
 * Home routes.
 */
define(['angular', './controllers', 'common'], function(angular, controllers) {
  'use strict';

  var mod = angular.module('home.routes', ['toaster', 'multicast.common']);
  mod.config(['$stateProvider', '$urlRouterProvider', function($stateProvider) {

    $stateProvider.state('home', {
            url: "/",
            abstract:true,
            views: {
                'main': {
                    templateUrl: "/assets/templates/home/home.html",
                    controller: controllers.HomeCtrl
                }
            }
        }
    );

    /*$urlRouterProvider
      //.when("/",  {templateUrl: "/assets/templates/home/home.html", controller: controllers.HomeCtrl})
        .otherwise('/dashboard');*/
  }]);

    mod.run(['$rootScope', '$state', 'userService', function ($rootScope, $state, userService) {

        $rootScope.$on('$stateChangeSuccess',
            function(event, toState, toParams) {
                console.log('stateChangeSuccess to ' + toState.name);
                console.log(toParams);
                //toaster.clear();

            }

        );

        $rootScope.$on('$stateChangeError',
            function(event, toState, toParams, fromState, fromParams, error) {
                console.log('$stateChangeError to ' + toState.name);
                console.log(error);
            }
        );

        $rootScope.$on('$stateChangeStart', function(event, toState, toParams) {
            console.log('stateChangeStart:');
            console.log(toState);

            if (userService.logged()) {

                if (toParams.path !== undefined && toParams.path === "") {
                    event.preventDefault();
                    $state.go('home.dashboard');
                }

                if (toState.name === "home.login" || toState.name === "home.signup") {
                    event.preventDefault();
                    $state.go('home.dashboard');
                }

            } else {
                if (toState.name !== "home.login" && toState.name !== "home.signup") {
                    event.preventDefault();
                    $state.go('home.login');
                }
            }

        });

    }]);

  return mod;
});
