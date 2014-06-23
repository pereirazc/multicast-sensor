/**
 * User service, exposes user model to the rest of the app.
 */
define(['angular', 'common'], function(angular) {
  'use strict';

  var mod = angular.module('user.services', ['ngCookies',  'multicast.common']);
  mod.factory('userService', ['$http', '$q', '$cookies', '$cookieStore', 'playRoutes', function($http, $q, $cookies, $cookieStore, playRoutes) {

    var user, token;

    function clean() {
        // Logout on server in a real app
        token = undefined;
        user = undefined;
        $cookieStore.remove('authToken');
        $cookieStore.remove('user');
    }

    function getToken() {
        if (token===undefined) {
            token = $cookies.authToken;
        }
        return token;
    }

    return {
        logged: function () {
        return getToken() !== undefined;
        },

        login: function(credentials) {
            return playRoutes.controllers.SecurityCtrl.login().post(credentials).then(function(response) {
              // return promise so we can chain easily
              token = response.data.authToken;
              // in a real app we could use the token to fetch the user data
              return playRoutes.controllers.UserCtrl.getUser().get();
            }).then(function(response) {
              user = response.data; // Extract user data from user() request
              $cookieStore.put('user', user);
              return user;
            });
        },

        signup : function(registration) {
            return playRoutes.controllers.SecurityCtrl.signup().post(registration);
        },

        cleanAuth: function() {
            clean();
        },

        logout: function() {
            return playRoutes.controllers.SecurityCtrl.logout().post().then(
                function() {
                    clean();
                },
                function() {
                    clean();
                }
            );
        },

        getToken : function() {
            return getToken();
        },

        getUser : function() {
            if (user===undefined) {
                user = $cookieStore.get('user');
            }
            return user;
        }
    };
  }]);
  /**
   * Add this object to a route definition to only allow resolving the route if the user is
   * logged in. This also adds the contents of the objects as a dependency of the controller.
   */
  mod.constant('userResolve', {
    user: ['$q', 'userService', function($q, userService) {
      var deferred = $q.defer();
      var user = userService.getUser();
      if (user) {
        deferred.resolve(user);
      } else {
        deferred.reject();
      }
      return deferred.promise;
    }]
  });
  return mod;
});
