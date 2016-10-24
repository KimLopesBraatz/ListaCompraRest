angular.module("listaCompraApp").controller("itemCtrl", function($scope, itensAPI) {

  	$scope.loadAll = function () {
	  	$scope.itens = itensAPI.query();
	};


	$scope.save = function(item) {
	    $scope.isSaving = true;
	    if ($scope.item.id !== null) {
	        itensAPI.update(item, onSaveSuccess, onSaveError);
	        delete $scope.item;
	        $scope.editForm.$setPristine();
	        $scope.loadAll();
	    } else {
	        itensAPI.save(item, onSaveSuccess, onSaveError);
	        delete $scope.item;
	        $scope.editForm.$setPristine();
	        $scope.loadAll();
	    }
	}

    function onSaveSuccess (result) {
        $scope.$emit('listaCompraApp:itemUpdate', result);
        $scope.isSaving = false;
    }

    function onSaveError () {
        $scope.isSaving = false;
    }

    $scope.cancel = function () {
    	delete $scope.item;
	    $scope.editForm.$setPristine();
    }

});
