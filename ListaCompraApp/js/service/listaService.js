(function() {
    'use strict';
    angular
        .module('listaCompraApp')
        .factory('listasAPI', listasAPI);

    listasAPI.$inject = ['$resource'];

    function listasAPI ($resource) {
        var resourceUrl =  "http://127.0.0.1:8081/api/listas/:id";

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();