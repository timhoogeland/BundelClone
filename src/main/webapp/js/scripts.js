// BEGIN: PWA initialisation
if ('serviceWorker' in navigator) {
  console.log('CLIENT: service worker registration in progress.');
  navigator.serviceWorker.register('sw.js').then(function() {
    console.log('CLIENT: service worker registration complete.');
  }, function() {
    console.log('CLIENT: service worker registration failure.');
  });
} else {
  console.log('CLIENT: service worker is not supported.');
}
// END: PWA initialisation

// Login
function setCookie(name, value, days) {
	var expires = "";
	if (days) {
		var date = new Date();
		date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
		expires = "; expires=" + date.toUTCString();
	}
	document.cookie = name + "=" + (value || "") + expires + "; path=/";
}
function getCookie(name) {
	var nameEQ = name + "=";
	var ca = document.cookie.split(';');
	for (var i = 0; i < ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0) == ' ')
			c = c.substring(1, c.length);
		if (c.indexOf(nameEQ) == 0)
			return c.substring(nameEQ.length, c.length);
	}
	return null;
}

function eraseCookie(name) {
	document.cookie = name + '=; Max-Age=-99999999;path=/;';
}

function logOut() {
	eraseCookie("username");
	eraseCookie("password");
	eraseCookie("userid");
}

function validateLogin() {
	$('#loginbutton').attr('loading', 'true');
	var pass = document.getElementById('pass').value;
	var username = document.getElementById('username').value;

	if (username == '') {
		$('#loginbutton').attr('loading', 'false');
		$('#loginbutton').text('Try again');
		addNotification('Username can not be empty');
	} else if (pass == '') {
		$('#loginbutton').attr('loading', 'false');
		$('#loginbutton').text('Try again');
		addNotification('Password can not be empty');
	} else {
		var logRequest;
		try {
			logRequest = new XMLHttpRequest();
			logRequest.open('GET', "/bundlePWABackend/restservices/login", true);
			logRequest.setRequestHeader("username", username);
			logRequest.setRequestHeader("password", pass);
			logRequest.send(null);
			logRequest.onload = function() {
				if (logRequest.readyState === logRequest.DONE && logRequest.status === 200) {
					var response = JSON.parse(logRequest.response);
					if (response[0]['userid'] !== undefined) {
						$('#loginbutton').attr('loading', 'false');
						$('#loginbutton').text('Succes');
						setCookie('username', username, 1);
						setCookie('password', pass, 1);
						console.log(response[0]['session'])
						window.sessionStorage.setItem('sessionToken', response[0]['session']);
						window.sessionStorage.setItem('userType', response[0]['usertype']);
						setCookie('userid', response[0]['userid']);
						addNotification("Login successful", "green");
						window.location.replace("index.jsp");
					} else {
						$('#loginbutton').attr('loading', 'false');
						$('#loginbutton').text('Try again');
						addNotification(response[0]['error']);
					}
				} else {
					$('#loginbutton').attr('loading', 'false');
					$('#loginbutton').text('Try again');
					addNotification('Retrieving data failed with status '
							+ logRequest.status + '. Try again later.');
				}
			}

		} catch (exception) {
			alert("Request failed");
		}
	}
}

function checkCookie() {
	console.log(getCookie('loanofficerid'));
}

function loadImage(image, id, button) {
	$(id).css('background-image', 'url(' + image + ')');
	$(button).addClass('hide');
}

function checkValue(value, error = "Not supplied"){
	if (value === "" || value === undefined || value == null || !value || value === " ") {
		return error;
	}

	return value;
}

function toEditLoan(loanid) {
	window.location.replace("edit_loan.jsp?id=" + loanid);
}

function toViewLoan(loanId) {

	window.location.replace("loan.jsp?id=" + loanId);
}

function UCFirst(string) {
	return string.charAt(0).toUpperCase() + string.slice(1);
}

function getParameterByName(name, url) {
	if (!url)
		url = window.location.href;
	name = name.replace(/[\[\]]/g, "\\$&");
	var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"), results = regex
			.exec(url);
	if (!results)
		return null;
	if (!results[2])
		return '';
	return decodeURIComponent(results[2].replace(/\+/g, " "));
}

function toEditContract(loanid) {

	window.location.replace("edit_contract.jsp?id=" + loanid);

}

function toViewContract() {
	var loanid = document.getElementById('loanid');
	window.location.replace("contract.jsp");

}

function toEdit() {
	window.location.replace("edit_contract.jsp");
}
function newContract() {
	var firstname = document.getElementById('firstname').value;
	if (firstname == "henk") {
		document.getElementById("firstname_error").innerHTML = "Henk is ongeldig";

	}
	var lastname = document.getElementById('lastname').value;
	var birthdate = document.getElementById('birthdate').value;
	var phone = document.getElementById('phone').value;
	var street = document.getElementById('street').value;
	var postalcode = document.getElementById('postal').value;
	var country = document.getElementById('country').value;
	var picture = document.getElementById('picture').value;

	var loantype = document.getElementById('loantype').value;
	var sector = document.getElementById('sector').value;
	var amount = document.getElementById('amount').value;
	var duration = document.getElementById('duration').value;
	var description = document.getElementById('description').value;
	sendContract(firstname, lastname, birthdate, phone, street, postalcode,
			country, picture, loantype, sector, amount, duration, description)

}
function sendContract(firstname, lastname, birthdate, phone, street,
		postalcode, country, picture, loantype, sector, amount, duration,
		description) {
	console.log(firstname, lastname, birthdate, phone, street, postalcode,
			country, picture, loantype, sector, amount, duration, description);
}

function addNotification(text, color) {
	backgroundColor = "#ffffff";
	if (color != undefined && color === "green") {
		backgroundColor = "#5dbc5d";
	} else {
		backgroundColor = "#fa5858";
	}

	$('#notificationBlock').fadeIn().append(
			'<div class="notification hide" style="background-color:'+backgroundColor+'"><p id="notificationText">' + text
					+ '</div></div>');
	$('#notificationBlock .notification:last').hide().fadeIn();
	setTimeout(removeNotification, 3000);
}
function removeNotification() {
	$('#notificationBlock .notification:last').fadeOut(1000);
	setTimeout(function() {
		$('#notificationBlock .notification:last').remove();
	}, 1100)
}

function toggleHide(id, bool) {
	event.stopPropagation();
	if (bool) {
		$('#'+id).fadeOut('fast');
	} else {
		$('#'+id).fadeIn('fast');
	}
}

function loadingText(points) {
	if (points == '...') {
		points = '.  ';
	} else if (points == '.. ') {
		points = '...';
	} else if (points == '.  ') {
		points = '.. ';
	}

	$('[loading="true"]').text('Loading' + points);

	setTimeout(function() {
		loadingText(points);
	}, 1000);
}
