angular.module("niceComponent", [])
/**
 * Nice导航栏
 */
.directive("niceNavbar", function() {
    return {
        restrict : "E",
        templateUrl : "component/navbar.html",
        scope: {
                // active: "=",
        },
    };
}).directive("niceFooter", function() {
    return {
        restrict : "E",
        templateUrl : "component/footer.html",
        scope: {
               
        },
    };
});