<!DOCTYPE html>
<html lang="en">

<jsp:include page="parts/head.jsp" />

<body onload="getUser();">

    <jsp:include page="parts/navigation.jsp" />

	<main>
        <div class="welcomeBlock">
            <h1>Account</h1>
            <button class="buttonRound" onclick="toggleHide('helpPopup', false)">?</button>
			<button class="buttonRound" onclick="window.location.href='edit_account.jsp'">&#9998;</button>
        </div>

        <div class="block">

        	<div id="mainLoader" class="loaderBlock">
        		<div class="loader"></div>
        	</div>

	        <div class="blockHalf">
	        	<h2>Personal information</h2>
				<br>

				<div>
					<div class="pf">
						<button id="pfbutton" class="buttonImg hide" >Load</button>
					</div>
				</div>

				<div>
					<label for="username"> <b>Username</b>
					</label>
					<h3 id="username" class="call1" loading="true">Loading...</h3>
				</div>

				<div>
					<label for="name"> <b>Name</b>
					</label>
					<h3 id="name" class="call1" loading="true">Loading...</h3>
				</div>

				<div>
					<label for="birthdate"> <b>Date of birth</b>
					</label>
					<h3 id="birthdate" class="call1" loading="true">Loading...</h3>
				</div>

				<div>
					<label for="phone"> <b>Phone number</b>
					</label>
					<h3 id="phone" class="call1" loading="true">Loading...</h3>
				</div>

				<div>
					<label for="role"> <b>Role</b>
					</label>
					<h3 id="role" class="call1" loading="true">Loading...</h3>
				</div>

				<div>
					<label for="status"> <b>Status</b>
					</label>
					<h3 id="status" class="call1" loading="true">Loading...</h3>
				</div>
			</div>

			<div class="blockHalf">
				<h2>Address</h2>
				<br>

				<div>
					<label for="street"> <b>Street</b>
					</label>
					<h3 id="street" class="call1" loading="true">Loading...</h3>
				</div>

				<div>
					<label for="postal"> <b>Postal code</b>
					</label>
					<h3 id="postal" class="call1" loading="true">Loading...</h3>
				</div>

				<div>
					<label for="country"> <b>Country</b>
					</label>
					<h3 id="country" class="call1" loading="true">Loading...</h3>
				</div>

				<div>
					<label for="description"> <b>Description</b>
					</label>
					<h3 id="description" class="call1" loading="true">Loading...</h3>
				</div>

				<div>
					<label for="coordinates"> <b>Coordinates</b>
					</label>
					<h3 id="coordinates" class="call1" loading="true">Loading...</h3>
				</div>

				<div id="group" class="hide">

				<div id="subLoader" class="subLoaderBlock">
        			<div class="loaderSmall"></div>
        		</div>

					<h2>Group</h2>
					<br>

					<div>
						<label for="loanofficer">
							<b>Loan Officer</b>
							<button id="officerButton" class="buttonRound">&#8618;</button>
						</label>
						<h3 id="loanofficer" class="call2" loading="true">Loading...</h3>

					</div>

					<div>
						<label for="groupnumber">
							<b>Group number </b>
							<button id="groupButton" class="buttonRound">&#8618;</button>
						</label>
						<h3 id="groupnumber" class="call2" loading="true">Loading...</h3>
					</div>

					<div>
						<label for="groupnumber">
							<b>Loan number </b>
							<button id="loanButton" class="buttonRound">&#8618;</button>
						</label>
						<h3 id="loannumber" class="call2" loading="true">Loading...</h3>
					</div>
				</div>
			</div>
		</div>
    </main>

    <jsp:include page="parts/footer.jsp" />
    <script>

    function getUser() {
    	var hr = new XMLHttpRequest();
    	var id = undefined;

    	if(getParameterByName("id") === null) {
    		id = getCookie("userid");
    	} else {
    		id = getParameterByName("id")
    	}

    	hr.open("GET", "/bundlePWABackend/restservices/user/" + id, true);

    	hr.onreadystatechange = function() {
    		if (hr.readyState == 4 && hr.status == 200) {
    			$('#mainLoader').fadeOut('fast');
    			var userData = JSON.parse(hr.responseText);
    			var addressData = JSON.parse(userData[0].address);

    			$('.call1').attr("loading","false");
    			$('#username').text(checkValue(userData[0].username));
    			$('#name').text(checkValue(userData[0].firstName + " " + userData[0].lastName));
    			$('#phone').text(checkValue(userData[0].phonenumber));
    			$('#birthdate').text(checkValue(userData[0].dateofbirth));
    			$('#role').text(UCFirst(checkValue(userData[0].userType)));
    			$('#status').text(UCFirst(checkValue(userData[0].status)));
    			$('#street').text(UCFirst(checkValue(addressData[0].street + " " + addressData[0].number)));
    			$('#postal').text(checkValue(addressData[0].postalcode));
    			$('#country').text(checkValue(addressData[0].country));
    			$('#description').text(checkValue(addressData[0].description));
    			$('#coordinates').text(checkValue(addressData[0].location));

    			if(checkValue(userData[0].photo, 'no') !== 'no') {
    				$('#pfbutton').removeClass('hide');
    				$('#pfbutton').attr('onclick', "loadImage('"+userData[0].photo+"', '.pf', '#pfbutton');");
    			}

    			if (userData[0].userType == "applicant") {
    				$('#group').removeClass('hide');
    				var hr2 = new XMLHttpRequest();
    				hr2.open("GET", "/bundlePWABackend/restservices/user/" + userData[0].loanInformation[0].loanofficerid, true);
    				hr2.onreadystatechange = function() {
    					if (hr2.readyState == 4 && hr2.status == 200) {
    						$('#subLoader').fadeOut('fast');
    						var officerData = JSON.parse(hr2.responseText);
    						$('.call2').attr("loading","false");
    						$('#loanofficer').text(checkValue(officerData[0].firstName + " " + officerData[0].lastName));
    						$('#officerButton').attr("onclick", "window.location.href='account.jsp?id="+ userData[0].loanInformation[0].loanofficerid +"'");
    						$('#groupnumber').text(checkValue(userData[0].loanInformation[0].groupid));
    						$('#groupButton').attr("onclick", "window.location.href='group.jsp?id="+ userData[0].loanInformation[0].groupid +"'");
    						$('#loannumber').text(checkValue(userData[0].loanInformation[0].loanid));
    						$('#loanButton').attr("onclick", "window.location.href='loan.jsp?id="+ userData[0].loanInformation[0].loanid +"'");
    					} else if (hr2.readyState == 4) {
    						addNotification('Retrieving data failed with status ' + hr.status + '. Try again later.');
    					}
    				}
    				hr2.send(null);
    			}
    		} else if (hr.readyState == 4) {
    			addNotification('Retrieving data failed with status ' + hr.status + '. Try again later.');
    		}
    	}
    	hr.send(null);
    }</script>

    <div id="helpPopup" class="popup" style="display: none;">
		<div>
			<h2>Account explained</h2>
			<button class="buttonRound" onclick="toggleHide('helpPopup', true)">X</button>
			<p>This page will give you a overview of all your account details.</p>
		</div>
	</div>

</body>
</html>
