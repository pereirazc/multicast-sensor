/**
 * The app module, as both AngularJS as well as RequireJS module.
 * Splitting an app in several Angular modules serves no real purpose in Angular 1.0/1.1.
 * (Hopefully this will change in the near future.)
 * Splitting it into several RequireJS modules allows async loading. We cannot take full advantage
 * of RequireJS and lazy-load stuff because the angular modules have their own dependency system.
 */
define(["angular","user", "main", "dashboard", "sensor", "feed"], function(angular) {
  // We must already declare most dependencies here (except for common), or the submodules' routes
  // will not be resolved
    var app = angular.module("app", ["yourprefix.user", "yourprefix.main", "yourprefix.dashboard", "yourprefix.sensor", "yourprefix.feed"])

    app.factory('authInterceptor',['$injector', '$rootScope', '$q', '$window', '$location', function ($injector, $rootScope, $q, $window, $location) {
        return {
            request: function (config) {
                config.headers = config.headers || {};
                var token = $injector.get('userService').getToken();
                if (token != undefined) {
                    config.headers['X-AUTH-TOKEN'] = token;
                }
                return config;
            },

            responseError: function(response) {
                if (response.status === 401) {
                    // handle the case where the user is not authenticated
                    $injector.get('userService').cleanAuth();
                    $location.path('/login');
                }
                return response || $q.when(response);
            }
        };
    }]);


    app.config(['$httpProvider', function($httpProvider) {

        $httpProvider.interceptors.push('authInterceptor');


        /*$httpProvider.responseInterceptors.push(['$q', '$location', '$injector', function($q, $location, $injector) {

            // More info on $q: docs.angularjs.org/api/ng.$q
            // Of course it's possible to define more dependencies.

            return function(promise) {

                /*
                 The promise is not resolved until the code defined
                 in the interceptor has not finished its execution.
                 */

                //return promise.then(function(response) {

                    // response.status >= 200 && response.status <= 299
                    // The http request was completed successfully.

                    /*
                     Before to resolve the promise
                     I can do whatever I want!
                     For example: add a new property
                     to the promise returned from the server.
                     */



                    // ... or even something smarter.

                    /*
                     Return the execution control to the
                     code that initiated the request.
                     */

                    //return response;

                //}, function(response) {

                    // The HTTP request was not successful.

                    /*
                     It's possible to use interceptors to handle
                     specific errors. For example:
                     */

                   /* if (response.status === 401) {
                        // HTTP 401 Error:
                        // The request requires user authentication
                        response.data = {
                            status: false,
                            description: 'Authentication required!'
                        };
                        console.log('401 !!!!');
                        //http://stackoverflow.com/questions/20230691/injecting-state-ui-router-into-http-interceptor-causes-circular-dependency
                        $injector.get('userService').cleanAuth();
                        $location.path('/login');
                        return response;
                    }*/

                    /*
                     $q.reject creates a promise that is resolved as
                     rejectedwith the specified reason.
                     In this case the error callback will be executed.
                     */

                    //return $q.reject(response);

                //});

           // }

       // }]);

    }]);


	return app;
});
