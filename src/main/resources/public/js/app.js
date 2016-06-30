var autoLoad = false;

const APIRoutes = {
    number: "/api/number/"
};


function autoReloadCheck(){
    if(document.getElementById('autoLoadChecker').checked){
        document.getElementById('calcButton').style.display = 'none';
        autoLoad = true;
    } else {
        document.getElementById('calcButton').style.display = '';
        autoLoad = false;
    }
}

function autoUpdate(){
    var numberField = document.getElementById('numberField');
    var errorField = document.getElementById('errorField');
    var errorMessage = document.getElementById('errorMessage');
    var solutionField = document.getElementById('solutionField');

    var newRoute = APIRoutes.number + encodeURIComponent(numberField.value).replace('/', '%2F');

    // Update Section
    APIReq(newRoute)
        .then(function(data){
            var apiObject = JSON.parse(data);
            if(apiObject.Status != 0){
                errorField.style.display = '';
                errorMessage.innerText = apiObject.Status;
                solutionField.innerText = '';
            } else {
                errorMessage.innerText = '';
                errorField.style.display = 'none';
                solutionField.innerText = apiObject.Number;
            }
        })
        .catch(function (error){
            throw error;
        });
}

window.onload = function() {
    document.getElementById('numberField').addEventListener("keyup", autoUpdate);
    document.getElementById('errorField').style.display = 'none';
};

/**
 * This function is a helper to get the API responses
 * @param api_route The API route to be requested
 */
function APIReq(api_route) {
    return new Promise(function(resolve,reject){
        var req = new XMLHttpRequest();

        if(typeof api_route !== 'string'){
            throw new TypeError("The API Route should be supplied as a String.");
        }

        req.open('GET', api_route);
        req.onload = function(){
            if(req.status >= 200  && req.status < 300){   //  Status codes from 200 to 300 are successful
                resolve(req.responseText);
            }else{
                reject(new Error("There was an error while requesting the content." + req.statusText));
            }
        };

        req.send();
    })
}