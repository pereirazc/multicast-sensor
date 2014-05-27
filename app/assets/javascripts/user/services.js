/**
 * User service, exposes user model to the rest of the app.
 */
define(["angular", "common"], function(angular) {
  var mod = angular.module("user.services", ["ngRoute", "ngCookies", "yourprefix.common"]);

  mod.factory("userService", ["$http", "$q", "playRoutes", "$cookieStore", "$cookies", function($http, $q, playRoutes, $cookieStore, $cookies) {
    var UserService = function() {
      var self = this;
      var user;
      var token;


        //self.setAuthHeader = function (authToken) {
        //    console.log('format header');
        //    $http.defaults.headers.common['X-AUTH-TOKEN'] = authToken;
        //}

      self.loginUser = function(credentials) {
        //return playRoutes.controllers.UserCtrl.login().post(credentials);

          return playRoutes.controllers.SecurityCtrl.login().post(credentials).then(function(response) {
              // return promise so we can chain easily
              self.token = response.data.authToken;
              //$cookieStore.remove('authToken');
              //$cookies.authToken = self.token;
              console.log('cookie token:');
              console.log($cookies);
              //console.log(self.token);
              // in a real app we could use the token to fetch the user data
              //self.setAuthHeader(self.token);
              return playRoutes.controllers.UserCtrl.getUser().get();
          }).then(function(response) {
                  //user = response.data; // Extract user data from user() request
                  //user.email = credentials.email;
                  self.user = response.data;
                  $cookieStore.put('user', self.user);
                  //$cookies.user = self.user;
                  console.log('cookie user:');
                  console.log($cookies);
                  return self.user;
          });
      };

      self.cleanAuth = function() {
          self.token  = undefined;
          self.user   = undefined;
          $cookieStore.remove('authToken');
          $cookieStore.remove('user');
      }

      self.logout = function() {
        return playRoutes.controllers.SecurityCtrl.logout().post().then(
            function() {
                self.cleanAuth();
            }
        )

      };

      self.getToken = function() {
        if (self.token===undefined) {
            self.token = $cookies.authToken;
        }
        return self.token;
      }

      self.getUser = function() {
          if (self.user===undefined) {
              self.user = $cookieStore.get('user');
          }
          return self.user;
      };
    };
    return new UserService();
  }]);

  mod.constant("userResolve", {
    checkAuth: ["$q", "userService", function($q, userService) {
      var deferred = $q.defer();
      if (userService.getUser()) {
        deferred.resolve();
      } else {
        deferred.reject();
      }
      return deferred.promise;
    }]
  });
  /**
   * If the current route does not resolve, go back to the start page.
   */
  var handleRouteError = function($rootScope, $location) {
    $rootScope.$on("$routeChangeError", function(e, next, current) {
      $location.path("/");
    });
  };
  handleRouteError.$inject = ["$rootScope", "$location"];
  mod.run(handleRouteError);
  return mod;
});
