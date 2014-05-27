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
            //"google-maps": ["jquery"],
            //"ui-event": ["angular"],
            //"ui-map": ["angular"],
            "angular-cookies": ["angular"],
            "angular-route": ["angular"],
            "angular-touch": ["angular"],
            "bootstrap" : ["jquery"],
            //"angular-strap" : { deps: ['angular'] },
            //"angular-ui-bootstrap" : { deps: ['angular'] },
            "highcharts":["jquery"],
            "highcharts-ng": ["angular", "highcharts"],
            "range-slider": ["angular", "jquery"],
            "bootstrap-switch": ["jquery"],
            "ng-switch": ["angular", "bootstrap-switch"],
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
            "async": "https://raw.github.com/millermedeiros/requirejs-plugins/master/src/async",
            "jquery": ["//code.jquery.com/jquery-1.10.2.min", "/webjars/jquery/1.10.2/jquery.min"],
            "bootstrap": "//netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min",
            //"google-maps": "http://maps.google.com/maps/api/js?v=3&sensor=false",
            //"ui-event": "https://rawgithub.com/angular-ui/ui-utils/master/modules/event/event",
            //"ui-map": "http://angular-ui.github.io/ui-map/dist/ui-map.min",
            "angular": "//ajax.googleapis.com/ajax/libs/angularjs/1.2.6/angular.min",
            "angular-cookies": "//ajax.googleapis.com/ajax/libs/angularjs/1.2.6/angular-cookies.min",
            "angular-route": "//ajax.googleapis.com/ajax/libs/angularjs/1.2.6/angular-route.min",
            "angular-touch": "//ajax.googleapis.com/ajax/libs/angularjs/1.2.6/angular-touch.min",
            //"angular-strap": "//rawgithub.com/mgcrea/angular-strap/master/dist/angular-strap.min",
            //"angular-ui-bootstrap" : "//cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/0.9.0/ui-bootstrap-tpls.min",
            "highcharts": "//code.highcharts.com/highcharts",
            "highcharts-ng": "//raw.github.com/pablojim/highcharts-ng/master/dist/highcharts-ng",
            "range-slider": "//raw.githubusercontent.com/danielcrisp/angular-rangeslider/v0.0.7/angular.rangeSlider",
            "bootstrap-switch": "////raw.githubusercontent.com/nostalgiaz/bootstrap-switch/v3.0.0/dist/js/bootstrap-switch.min",
            "ng-switch": "//raw.githubusercontent.com/frapontillo/angular-bootstrap-switch/0.3.0-alpha.2/dist/angular-bootstrap-switch.min",
            "jsRoutes" : "/jsroutes"
        },
        priority: ["angular"]
    });

    requirejs.onError = function(err) {
        console.log(err);
    };

    //define("google-maps", ['async!http://maps.google.com/maps/api/js?v=3&sensor=false'], function () {
    //    return google.maps;
    //});

    // Make sure generic external scripts are loaded
    require(["angular", "app", "angular-cookies", "angular-route", "angular-touch", "async", "jquery", //"google-maps", "ui-event", "ui-map",
        "bootstrap", "range-slider", "highcharts-ng", "bootstrap-switch", "ng-switch"], function(angular, app) {
        angular.bootstrap(document, ["app"]);
    });
})(requirejs);
