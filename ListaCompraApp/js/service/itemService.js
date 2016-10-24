(function() {
    'use strict';
    angular
        .module('listaCompraApp')
        .factory('itensAPI', itensAPI);

    itensAPI.$inject = ['$resource'];

    function itensAPI ($resource) {
        var resourceUrl =  "http://127.0.0.1:8081/api/itens/:id";

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