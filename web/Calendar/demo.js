'use strict';

angular
  .module('demo', ['mwl.calendar', 'ui.bootstrap'])
  .controller('MainCtrl', function ($scope, $modal, moment, $http) {


      //
    var currentYear = moment().year();
    var currentMonth = moment().month();
      var event = [];


      $http.get('/product').success(function(data){

        /*
        for (var i = 0; i < data.data.length; i++) {
          txt += data.data[i] + "\n";
        }
        */
        event = data.data;

       console.log(JSON.stringify(event));
          //Hiiiiiiii

      }).
          error(function(msg){
            console.log("This is erroe message: "+msg);

          });
    //These variables MUST be set as a minimum for the calendar to work
    $scope.calendarView = 'month';
    $scope.calendarDay = new Date();
    $scope.events = [

      {
        title: 'This is a really long event title',
        type: 'important',
        starts_at: new Date(currentYear,currentMonth,25,6,30),
        ends_at: new Date(currentYear,currentMonth,25,6,60)
      }
    ];



    function showModal(action, event) {
      $modal.open({
        templateUrl: 'modalContent.html',
        controller: function($scope, $modalInstance) {
          $scope.$modalInstance = $modalInstance;
          $scope.action = action;
          $scope.event = event;
        }
      });
    }

    $scope.eventClicked = function(event) {
      showModal('Clicked', event);
    };

    $scope.eventEdited = function(event) {
      showModal('Edited', event);
    };

    $scope.eventDeleted = function(event) {
      showModal('Deleted', event);
    };

    $scope.setCalendarToToday = function() {
      $scope.calendarDay = new Date();
    };

    $scope.toggle = function($event, field, event) {
      $event.preventDefault();
      $event.stopPropagation();

      event[field] = !event[field];
    };

  });
