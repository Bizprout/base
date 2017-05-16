baseApp.config(function($routeProvider){
    
    $routeProvider.when("/",{templateUrl: "html/Login.html", controller:"LoginController"
    }).when("/home",{templateUrl:"html/LandingPage.html", controller:"LandingController"
    }).when("/masters",{templateUrl:"html/MasterPage.html", controller:"MasterPageController"
    }).when("/mapping",{templateUrl:"html/MappingPage.html", controller:"MappingPageController"
    }).when("/reports",{templateUrl:"html/reports/Reports.html", controller:"ReportController"
    }).when("/newuser",{templateUrl:"html/usercreation/UserCreation.html", controller:"UserController"
    }).when("/newclient",{templateUrl:"html/clientmaster/ClientMaster.html", controller:"ClientController"
    }).when("/newcompany",{templateUrl:"html/companymaster/CompanyMaster.html", controller:"CompanyMasterController"
    }).when("/PPmasters",{templateUrl:"html/ppstandardmaster/CreatePPStandardMasters.html", controller:"PPMasterController"
    }).when("/PPmapping",{templateUrl:"html/ppstandardmapping/MapPPStandardwithTally.html", controller:"TallyMappingController"
    }).when("/mappingUsers",{templateUrl:"html/usermapping/UserMappingtoClients.html", controller:"UserMappingController"
    }).when("/changepassword",{templateUrl:"html/changepassword/ChangePassword.html", controller:"LoginController"
    });   
        
});