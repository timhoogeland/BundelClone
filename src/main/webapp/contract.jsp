<!DOCTYPE html>
<html lang="en">

<jsp:include page="parts/head.jsp" />

<body>

	<jsp:include page="parts/navigation.jsp" />

	<main>
	<div class="welcomeBlock long">
		<h1>Contract - Farming</h1>
	</div>

	<div class="blockHalf applicantinfo-contract">
		<h2>Applicant information</h2>
		<br>
		<div>
			<label for="firstname"> <b>First name</b>
			</label>
			<h3 id="firstname">Tim</h3>
		</div>

		<div>
			<label for="lastname"> <b>Last name</b>
			</label>
			<h3 id="lastname">Hoogeland</h3>
		</div>

		<div>
			<label for="birthdate"> <b>Date of birth</b>
			</label>
			<h3 id="birthdate">18-11-1985</h3>
		</div>

		<div>
			<label for="phone"> <b>Phone number</b>
			</label>
			<h3 id="phone">00456658483</h3>
		</div>

		<div>
			<label for="street"> <b>Street</b>
			</label>
			<h3 id="street">Mudway, 221</h3>
		</div>

		<div>
			<label for="postal"> <b>Postal code</b>
			</label>
			<h3 id="postal">5626 EM</h3>
		</div>

		<div>
			<label for="country"> <b>Country</b>
			</label>
			<h3 id="country">Zimbabwe</h3>
		</div>
	</div>


	<div class="blockHalf loaninfo_loan">
		<h2>Loan information</h2>
		<br>
		<div>
			<label for="loantype"> <b>Loan type</b>
			</label>
			<h3 id="loantype">Short-term</h3>
		</div>

		<div>
			<label for="sector"> <b>Sector</b>
			</label>
			<h3>Farming</h3>
		</div>

		<div>
			<label for="amount"> <b>Amount</b>
			</label>
			<h3 id="amount">1000</h3>
		</div>

		<div>
			<label for="duration"> <b>Duration(months)</b>
			</label>
			<h3 id="duration">12</h3>
		</div>
	</main>

	<jsp:include page="parts/footer.jsp" />

</body>
</html>