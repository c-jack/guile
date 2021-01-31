/**
 * Attempt Login
 */
function setConnection() {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "set_connection");
    xhr.onload = function (event) {
        const response = JSON.parse(event.target.response);
        if (xhr.status === 200) {
            // success
        } else {
            // show error
        }
    };
    var formData = new FormData(document.getElementById("connection"));
    xhr.send(formData);
}

/**
 * Attempt Login
 */
function setChangelog() {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "set_changelog");
    xhr.onload = function (event) {
        const response = JSON.parse(event.target.response);
        if (xhr.status === 200) {
            // success
        } else {
            // show error
        }
    };
    var formData = new FormData(document.getElementById("changelog"));
    xhr.send(formData);
}

/**
 * Refresh the infoBox so any 'new' errors/info can be displayed.
 * This is required due to not having a 'full' page reload on AJAX calls.
 */
function refreshInfoBox() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/infoBox');

    xhr.onload = function () {
        if (xhr.status === 200) {
            showInfoBox(xhr.responseText);
        }
    };
    xhr.send();
}

/**
 * Refresh the infoBox so any 'new' errors/info can be displayed.
 * This is required due to not having a 'full' page reload on AJAX calls.
 */
function newInfoBox(title, text, type) {
    const xhr = new XMLHttpRequest();
    const msg = [title, text];
    xhr.open('GET', '/infobox/show?msg=' + msg + '&type=' + type);

    xhr.onload = function () {
        if (xhr.status === 200) {
            showInfoBox(xhr.responseText);
        }
    };
    xhr.send();
}

/**
 * Spinner
 */
function setSpinner(container, type, msg) {

    return new Promise(function (resolve, reject) {
        const xhr = new XMLHttpRequest();
        xhr.open('GET', '/spinner?msg=' + msg + '&t=' + type);
        xhr.send();
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    resolve(xhr.responseText);
                } else {
                    reject(xhr.status);
                }
            }
        }
    });

}

/**
 * Refresh the infoBox so any 'new' errors/info can be displayed.
 * This is required due to not having a 'full' page reload on AJAX calls.
 */
function showInfoBox(text) {
    const infoBox = document.getElementById('infobox');
    infoBox.innerHTML = text;
}

/**
 * Sets content for the infoBox
 */
function setInfo(msg, type) {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/error/set?msg=' + msg + '&type=' + type);
    xhr.onload = function () {
        if (xhr.status === 200) {
            const infoBox = document.getElementById('infobox');
            infoBox.innerHTML = xhr.responseText;
        }
    };
    xhr.send();
}

/**
 * Displays the login error if auth fails
 * @param error
 */
function showLoginError(error) {
    var errorContainer = document.createElement("span");
    const loginForm = document.getElementById("login-form");

    errorContainer.classList.add("errorMsg");
    errorContainer.id = "errorMsg";
    loginForm.prepend(errorContainer);
    errorContainer.innerText = error;
    errorLoginFields();

}

/**
 * Sets error login fields to have/remove the 'error' class, and removes
 * the error message element.
 * @param type = unset to remove, default to set
 */
function errorLoginFields(type) {
    const loginFields = document.getElementsByClassName("login-field");

    if (type === "unset") {
        const errorMsg = document.getElementById("errorMsg");
        if (errorMsg) {
            errorMsg.parentNode.removeChild(errorMsg);
        }
        for (var i = 0; i < loginFields.length; i++) {
            const field = loginFields[i];
            if (field.classList.contains("error")) {
                field.classList.remove("error");
            }
        }
    } else {
        for (var j = 0; j < loginFields.length; j++) {
            loginFields[j].className += " error";
        }
    }
}



