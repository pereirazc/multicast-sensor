// `main.js` is the file that sbt-web will use as an entry point
(function (requirejs) {
  'use strict';

  // -- RequireJS config --
  requirejs.config({
    // Packages = top-level folders; loads a contained file named 'main.js"
    packages: ['common', 'home', 'user', 'dashboard', 'sensor', 'feed'],
    shim: {
      'jsRoutes': {
        deps: [],
        // it's not a RequireJS module, so we have to tell it what var is returned
        exports: 'jsRoutes'
      },
      // Hopefully this all will not be necessary but can be fetched from WebJars in the future
      'angular': {
        deps: ['jquery'],
        exports: 'angular'
      },
      'angular-route': ['angular'],
      'angular-cookies': ['angular'],
      'angular-touch': ['angular'],
      'angular-animate': ['angular'],
      'highcharts': {
          deps: ['jquery'],
          exports: 'Highcharts'

        },
      'highcharts-ng': ['highcharts','angular'],
      'bootstrap': ['jquery'],
      "range-slider": ["angular", "jquery"],
      "bootstrap-switch": ["jquery"],
      "ng-switch": ["angular", "bootstrap-switch"],
      "loading-bar": ["angular"],
      "angular-toaster": ["angular", "angular-animate"]
    },
    paths: {
      'requirejs': ['../lib/requirejs/require'],
      'jquery': ['../lib/jquery/jquery'],
      'angular': ['../lib/angularjs/angular'],
      //'angular-route': ['../lib/angularjs/angular-route'],
      'angular-route': ['../lib/angular-ui-router/angular-ui-router.min'],
      'angular-touch': ['../lib/angularjs/angular-touch'],
      'angular-cookies': ['../lib/angularjs/angular-cookies'],
      'angular-animate': ['../lib/angularjs/angular-animate'],
      'highcharts': ['../lib/highcharts/highcharts'],
      'highcharts-ng': ['../lib/highcharts-ng/highcharts-ng'],
      'bootstrap': ['../lib/bootstrap/js/bootstrap'],
      "range-slider": ["//danielcrisp.github.io/angular-rangeslider/angular.rangeSlider"],
      "bootstrap-switch": ["../lib/bootstrap-switch/js/bootstrap-switch.min"],
      "ng-switch": ["../lib/angular-bootstrap-switch/angular-bootstrap-switch.min"],
      "loading-bar": ["../lib/loading-bar"],
      "angular-toaster": ["../lib/angular-toaster"],
      'jsRoutes': ['/jsroutes']
    }
  });

  requirejs.onError = function (err) {
    console.log(err);
  };

  // Load the app. This is kept minimal so it doesn't need much updating.
  require([ 'angular', 'angular-cookies', 'angular-touch', 'angular-route', 'jquery', 'bootstrap',
            'highcharts', 'highcharts-ng', 'range-slider', 'bootstrap-switch', 'ng-switch' , 'loading-bar', 'angular-toaster', './app'],
    function (angular) {

      console.log(angular);
      angular.bootstrap(document, ['app']);
    }
  );
})(requirejs);
