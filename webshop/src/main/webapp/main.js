const app = angular.module("main", ["ngRoute"]);

app.config(function($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'main.html',
            controller: 'MainController'
        })
        .when('/products', {
            templateUrl: 'products.html',
            controller: 'ProductsController'
        });
});

app.controller('MainController', function($scope, $location) {
    $scope.go = function ( path ) {
        $location.path( path );
    };
});

app.controller('ProductsController', function ($scope) {});
