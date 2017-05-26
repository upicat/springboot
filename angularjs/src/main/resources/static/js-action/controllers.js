/**
 * 
 */

actionApp.controller('View1Controller', ['$rootScope', '$scope', '$http', function($rootScope, $scope,$http) {
    $scope.$on('$viewContentLoaded', function() {
    	console.log('v1页面加载完成');
    });
    
    
    $scope.search = function(){
      personName = $scope.personName;
      $http.get('search',{
    	  params:{personName:personName}
      }).then(function(response) {
          $scope.person = response.data;
      });
     
    };
}]);

actionApp.controller('View2Controller', ['$rootScope', '$scope',  function($rootScope, $scope) {
    $scope.$on('$viewContentLoaded', function() {
    	console.log('v2页面加载完成');
    });
}]);


