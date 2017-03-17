baseApp.config(function($routeProvider){
    
    $routeProvider.when("/login",
                       {
       templateUrl:"html/Login.html",
        controller:"LoginController"
    }).when("/Register",{
        templateUrl:"html/Register.html"
    }).when("/",{
        templateUrl: "html/Template.html"
    }).when("/home",
           {
        templateUrl:"html/Home.html"
    });
    
    
});