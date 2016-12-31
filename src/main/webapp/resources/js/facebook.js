/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(window).load(function () {
    registerWithServer();
    location.hash = "#jogo";
});

// This function is called when someone finishes with the Login
// Button.  See the onlogin handler attached to it in the sample
// code below.
function checkLoginState() {
    FB.getLoginStatus(function (response) {
        statusChangeCallback(response);
    });
}

// This is called with the results from from FB.getLoginStatus().
function statusChangeCallback(response) {
    console.log('statusChangeCallback');
    console.log(response);
    // The response object is returned with a status field that lets the
    // app know the current login status of the person.
    // Full docs on the response object can be found in the documentation
    // for FB.getLoginStatus().
    if (response.status === 'connected') {
        // Logged into your app and Facebook.
        registerWithServer();
    } else if (response.status === 'not_authorized') {
        // The person is logged into Facebook, but not your app.
        logout();
    } else {
        // The person is not logged into Facebook, so we're not sure if
        // they are logged into this app or not.
        logout();
    }
}

function registerWithServer() {
    FB.getLoginStatus(function (response) {
        if (response.status === 'connected') {
            FB.api('/me', function (response) {
                logarComFacebook([{name:'nome',value:response.name},{name:'id', value:response.id}]);
            });
        } else {
            window.location.replace("http://"+window.location.host+"/quiz-app/login/");
        }
    });
}

function logoutFromServer() {
    FB.logout(function(response) {
        // Person is now logged out
    });
}

window.fbAsyncInit = function () {
    FB.init({
        appId: '703478009785240',
        cookie: true, // enable cookies to allow the server to access 
        // the session
        xfbml: true, // parse social plugins on this page
        version: 'v2.4' // use version 2.2
    });
};

// Load the SDK asynchronously
(function (d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id))
        return;
    js = d.createElement(s);
    js.id = id;
    js.src = "//connect.facebook.net/pt_BR/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));