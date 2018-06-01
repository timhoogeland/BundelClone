<!DOCTYPE html>
<html lang="en">

<jsp:include page="parts/head.jsp" />

<body onload="loadLoanDetails()">

    <jsp:include page="parts/navigation.jsp" />

    <main>
    	<div class="welcomeBlock long">
    		<h1 id="loanName" >Loan - Loading...</h1>
    	</div>

    <div class="blockHalf">
      <h2> Applicant information </h2>
	  <br>

      <div>
        <label for="name">
          <b>Name</b>
        </label>
        <h3 id="name">Loading...</h3>
      </div>

      <div>
        <label for="dateofbirth">
          <b>Date Of Birth</b>
        </label>
        <h3 id="dateofbirth">Loading...</h3>
      </div>

      <div>
        <label for="userstatus">
          <b>Status</b>
        </label>
        <h3 id="userstatus">Loading...</h3>
      </div>

      <div>
        <label for="phone">
          <b>Phone number</b>
        </label>
        <h3 id="phone">Loading...</h3>
      </div>

      <div>
        <label for="role">
          <b>Role</b>
        </label>
        <h3 id="role">Loading...</h3>
      </div>

		<h2>ADRESS</h2>
		<br>
      <div>
        <label for="street">
          <b>Street</b>
        </label>
        <h3 id="street">Loading...</h3>
      </div>

      <div>
        <label for="postal">
          <b>Postal code</b>
        </label>
        <h3 id="postal">Loading...</h3>
      </div>

      <div>
        <label for="country">
          <b>Country</b>
        </label>
        <h3 id="country">Loading...</h3>
      </div>
      <div>
      </div>
      </div>

      	<div class="blockHalf">
          <h2>Loan progress</h2>
		  <br>

          <div id='statusDiv'>
            <label for="status">
              <b>Status</b>
            </label>
            <h3 id="status">Loading...</h3>
          </div>

          <div>
            <label for="remaining">
              <b>Remaining amount</b>
            </label>
            <h3 id="remaining">Loading...</h3>
            <progress id="remainingbar">
            </progress>
          </div>

          <h2> Loan information </h2>
		  <br>

          <div>
            <label for="loantype">
              <b>Loan type</b>
            </label>
            <h3 id="loantype">Loading...</h3>
          </div>

	      <div>
	        <label for="amount">
	          <b>Amount</b>
	        </label>
	        <h3 id="amount">Loading...</h3>
	      </div>

	      <div>
	        <label for="duration">
	          <b>Duration (months)</b>
	        </label>
	        <h3 id="duration">Loading...</h3>
	      </div>

	      <div>
	        <label for="duration">
	          <b>Start Date</b>
	        </label>
	        <h3 id="startdate">Loading...</h3>
	      </div>

	     <div>
	       <label for="duration">
	         <b>Closing Date</b>
	       </label>
	       <h3 id="closingdate">Loading...</h3>
	     </div>
	     </div>
  </main>

  <jsp:include page="parts/footer.jsp" />
<script>
function getLoans() {
	var hr = new XMLHttpRequest();
	hr.open("GET", "/bundlePWABackend/restservices/loan", true);

	hr.onreadystatechange = function() {
		if (hr.readyState == 4 && hr.status == 200) {
			var data = JSON.parse(hr.responseText);
			var table = document.getElementById('loanstable');
			data.forEach(function(object) {
				var tr = document.createElement('tr');
				tr.innerHTML = '<td class="id" id="loanid" data-label="ID">'
						+ object.loanId + '</td>'
						+ '<td id ="amount" data-label="Amount">'
						+ object.amount + '</td>'
						+ '<td id = "duration" data-label="Duration">'
						+ object.duration + " months" + '</td>'
						+ '<td id = "closingdate" data-label="End Date">'
						+ object.closingdate + '</td>'
						+ '<td id="status" data-label="Status">'
						+ object.status + '</td>'
						+ '<td id = "loantype" data-label="Loan Type">'
						+ object.loantype + '</td>'
						+ "<td class='tdHide'>  <button class='small' onclick='toViewLoan(" + object.loanId
						+ ");'>View</button> </td>"
						+ "<td class='tdHide'>  <button class='small' onclick='toEditLoan(" + object.loanId
						+ ");'>Edit</button> </td>";
				table.appendChild(tr);
			});
		} else if (hr.readyState == 4) {
			addNotification('Retrieving data failed with status ' + hr.status
					+ '. Try again later.');
		}
	}
	hr.send(null);
}
function loadLoanDetails() {
	var hr = new XMLHttpRequest();

	hr.open("GET", "/bundlePWABackend/restservices/loan/"
			+ getParameterByName('id'), true);

	hr.onreadystatechange = function() {
		if (hr.readyState == 4 && hr.status == 200) {
			var data = JSON.parse(hr.responseText);
			$('#status').text(UCFirst(data[0].status));
			$('#remaining').text("TODO");
			$('#loantype').text(UCFirst(data[0].loantype));
			$('#remainingbar').attr('value', 0);
			$('#remainingbar').attr('max', data[0].amount);
			$('#amount').text("$ " + data[0].amount);
			$('#duration').text(data[0].duration);
			$('#startdate').text(data[0].startdate);
			$('#closingdate').text(data[0].closingdate);

			var hr2 = new XMLHttpRequest();
			hr2.open("GET", "/bundlePWABackend/restservices/contract/"
					+ data[0].contractid, true);

			hr2.onreadystatechange = function() {
				if (hr2.readyState == 4 && hr2.status == 200) {
					var data = JSON.parse(hr2.responseText);

					var hr3 = new XMLHttpRequest();
					hr3.open("GET", "/bundlePWABackend/restservices/user/"
							+ data[0].useridfk, true);

					hr3.onreadystatechange = function() {
						if (hr3.readyState == 4 && hr3.status == 200) {
							var data = JSON.parse(hr3.responseText);
							$('#loanName').text(
									'Loan - ' + UCFirst(data[0].name));
							$('#name').text(UCFirst(data[0].name));
							;
							$('#dateofbirth').text(data[0].dateofbirth);
							$('#phone').text(data[0].phonenumber);
							$('#role').text(data[0].userType);
							$('#userstatus').text(data[0].status);

							var hr4 = new XMLHttpRequest();
							hr4.open("GET",
									"/bundlePWABackend/restservices/adress/"
											+ data[0].adresIDFK, true);

							hr4.onreadystatechange = function() {
								if (hr4.readyState == 4 && hr4.status == 200) {
									var data = JSON.parse(hr4.responseText);
									$('#street').text(
											data[0].street + " "
													+ data[0].number);
									$('#postal').text(data[0].postalcode);
									$('#country').text(data[0].country);
								} else if (hr4.readyState == 4) {
									addNotification('Retrieving data failed with status '
											+ hr4.status + '. Try again later.');
								}
							}
							hr4.send(null);
						} else if (hr3.readyState == 4) {
							addNotification('Retrieving data failed with status '
									+ hr3.status + '. Try again later.');
						}
					}
					hr3.send(null);

				} else if (hr2.readyState == 4) {
					addNotification('Retrieving data failed with status ' + hr2.status
							+ '. Try again later.');
				}

			}
			hr2.send(null);

		} else if (hr.readyState == 4) {
			addNotification('Retrieving data failed with status ' + hr.status
					+ '. Try again later.');
		}
	}
	hr.send(null);
}
</script>
</body>
</html>
