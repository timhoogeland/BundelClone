<!DOCTYPE html>
<html lang="en">

<jsp:include page="parts/head.jsp" />

<body >
    
    <jsp:include page="parts/navigation.jsp" />

	<main>
        <div class="welcomeBlock">
            <h1>Edit Loan</h1>
        </div>
        
        <div class="buttonBlock">
        </div>
        
        <div class="block">
            <form onsubmit="return false">
                <ul class="flex-outer">
                    
                    <li>
                        <label for="loan-type">Loan Type</label>
                        <select name="loan-type" id="loan-type">
                            <option value="ST">Short-term</option>
                            <option value="MT">Mid-term</option>
                            <option value="LT">Long-term</option>
                        </select>
                    </li>
                    <li>
                        <label for="loan-status">Loan Status</label>
                        <select name="loan-status" id="loan-status">
                            <option value="ACTIVE">Active</option>
                            <option value="DEFAULTED">Defaulted</option>
                            <option value="TERMINATED">Terminated</option>
                        </select>
                    </li>
                    <li>
                        <label for="paidamount">Paid Amount</label>
                        <input name="paidamount" id="paidamount" placeholder="Enter the loan-amount here"></input>
                    </li>
                    <li>
                        <label for="duration">Duration</label>
                        <input type="number" name="duration" id="duration" min="1" max="36" placeholder="Enter the loan-duration here"></input>
                    </li>
                    <li>
                        <label for="closing-date">Closing Date</label>
                        <input type="date" name="closing-date" id="closing-date"></input>
                    </li>
                    <li>
                        <label for="description">Description</label>
                        <input id="description" name="description" placeholder="Enter the loan-description here"></input>
                    </li>
                    <li>
                        <button type="submit">Submit</button>
                    </li>
                </ul>
            </form>
        </div>
    </main>

    <script type="text/javascript">
        //retrieve data to fill form
        $(document).ready(function() {
            $.ajax({
                url : "/bundlePWABackend/restservices/loan/"
                + getParameterByName('id'),
                type : "get",

                success : function(response) {

                        console.log(response);
                        $("#loan-status").val(response["status"]);
                        $("#loan-type").val(response["loantype"]);
                        $("#paidamount").val(response["paidamount"]);
                        $("#duration").val(response["duration"]);
                        $("#closing-date").val(response["closingdate"]);
                        $("#description").val(response["description"]);


                },
                error : function(response, textStatus, errorThrown) {
                    console.log("Failed.");
                    console.log("textStatus: " + textStatus);
                    console.log("errorThrown: " + errorThrown);
                    console.log("status: " + response.status);

                }
            });

            //post data when form is submitted
            $("form").submit(function() {
            $.ajax({
					url : "/bundlePWABackend/restservices/loan" + getParameterByName('id'),
					type : "put",
					data : $("form").serialize(),

					success : function(response) {

						alert("Loan updated succesfully.");

					},
					error : function(response, textStatus, errorThrown) {

						alert("Loan could not be updated.")

						console.log("textStatus: " + textStatus);
						console.log("errorThrown: " + errorThrown);
						console.log("status: " + response.status);

					}
				});
        });
        });
    </script>

    <jsp:include page="parts/footer.jsp" />

</body>
</html>