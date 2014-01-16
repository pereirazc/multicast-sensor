(function(requirejs) {
  "use strict";

  // -- PROD RequireJS config --
  requirejs.config({
    packages: ["common", "main", "user", "dashboard", "sensor", "feed"],
    shim: {
      "_" : {},
      "jquery": {
        exports: "$"
      },
      "angular" : {
        exports : "angular",
        deps: ["jquery"]
      },
      "google-maps": ["jquery"],
        "ui-event": ["angular"],
        "ui-map": ["angular"],
      "angular-cookies": ["angular"],
      "angular-route": ["angular"],
      "bootstrap" : ["jquery"],
      //"angular-strap" : { deps: ['angular'] },
      //"angular-ui-bootstrap" : { deps: ['angular'] },
      "highcharts":["jquery"],
      "highcharts-ng": ["angular", "highcharts"],

      "app" : ["angular"],
      "jsRoutes" : {
        deps : [],
        // it's not a RequireJS module, so we have to tell it what var is returned
        exports : "jsRoutes"
      }
    },
    paths: {
      // Map the dependencies to CDNs or WebJars directly
      "_" : "//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.1/underscore-min",
      // Use WebJars as a fallback
      "jquery": ["//code.jquery.com/jquery-1.10.2.min", "/webjars/jquery/1.10.2/jquery.min"],
      "bootstrap": "//netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min",
      "async": "https://raw.github.com/millermedeiros/requirejs-plugins/master/src/async",
      //"google-maps": "http://maps.google.com/maps/api/js?v=3&sensor=false",
      "ui-event": "https://rawgithub.com/angular-ui/ui-utils/master/modules/event/event",
      "ui-map": "http://angular-ui.github.io/ui-map/dist/ui-map.min",
      "angular": "//ajax.googleapis.com/ajax/libs/angularjs/1.2.6/angular.min",
      "angular-cookies": "//ajax.googleapis.com/ajax/libs/angularjs/1.2.6/angular-cookies.min",
      "angular-route": "//ajax.googleapis.com/ajax/libs/angularjs/1.2.6/angular-route.min",
      //"angular-strap": "//rawgithub.com/mgcrea/angular-strap/master/dist/angular-strap.min",
      //"angular-ui-bootstrap" : "//cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/0.9.0/ui-bootstrap-tpls.min",
      "highcharts": "//code.highcharts.com/highcharts",
      "highcharts-ng": "//rawgithub.com/pablojim/highcharts-ng/master/dist/highcharts-ng",
      "jsRoutes" : "/jsroutes"
    },
    priority: ["angular"]
  });

  requirejs.onError = function(err) {
    console.log(err);
  };

  define("google-maps", ['async!http://maps.google.com/maps/api/js?v=3&sensor=false'], function () {
    return google.maps;
  });

  // Make sure generic external scripts are loaded
  require(["angular", "app", "angular-cookies", "angular-route", "async", "jquery", "google-maps", "ui-event", "ui-map", "bootstrap", "highcharts-ng"], function(angular, app) {
    angular.bootstrap(document, ["app"]);
  });
})(requirejs);
