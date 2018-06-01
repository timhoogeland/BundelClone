<!DOCTYPE html>
<html lang="en">

<jsp:include page="parts/head.jsp" />

<body onload=getGroups();>

	<jsp:include page="parts/navigation.jsp" />

	<main>
	<div class="welcomeBlock">
		<h1>Groups</h1>
		<button class="buttonRound">?</button>
		<button class="buttonRound" onclick="toggleHide('helpPopup', false)">+</button>
	</div>

	<div class="block">
		<div id="mainLoader" class="loaderBlock">
			<div class="loader"></div>
		</div>

		<div id="groupsdiv" class="block">
		</div>
	</div>
	</main>

	<jsp:include page="parts/footer.jsp" />

	<div id="helpPopup" class="popup" style="display: none;">
		<div>
			<h2>Groups explained</h2>
			<button class="buttonRound" onclick="toggleHide('helpPopup', true)">X</button>
			<p>Lorem ipsum dolor sit amet, pretium leo sed, ac leo aenean
				tellus, orci amet maxime amet sed nunc pharetra, scelerisque
				tristique pretium morbi scelerisque mollis sed, vivamus pede irure
				ac lacus. Diam ante sit amet, blandit laoreet interdum sem
				pellentesque. Sit turpis ligula non, iaculis viverra. Lorem ipsum
				dolor sit amet, pretium leo sed, ac leo aenean tellus, orci amet
				maxime amet sed nunc pharetra, scelerisque tristique pretium morbi
				scelerisque mollis sed, vivamus pede irure ac lacus. Diam ante sit
				amet, blandit laoreet interdum sem pellentesque. Sit turpis ligula
				non, iaculis viverra.</p>
		</div>
	</div>
	</main>

	<jsp:include page="parts/footer.jsp" />

	<script>
		function getGroups() {

			var hr = new XMLHttpRequest();
			var loanofficerid = getCookie("userid");
			hr.open("GET",
					"/bundlePWABackend/restservices/loangroup/loanofficer/"
							+ loanofficerid, true);

			hr.onreadystatechange = function() {
				if (hr.readyState == 4 && hr.status == 200) {
					$('#mainLoader').fadeOut('fast');
					var data = JSON.parse(hr.responseText);
					var table = document.getElementById('groupsdiv');
					var datalength = data.length;

					for (var i = 0; i < datalength; i++) {
						var groupid = data[i].groupid;
						var totalAmount = 0;
						var totalPaid = 0;
						var innerlength = data[i].groupinformation.length;
						var groupdiv = ' <div class="blockHalf group"><h2  class="groupTitle"> Group '
								+ groupid.toString() + '</h2><div class="groupTotal"><label for="picture"> <b> Group Total </b></label><progress id="totall'+groupid+'"></progress></div><div class="blockGroup">';
						for (var y = 0; y < innerlength; y++) {
							totalAmount += data[i].groupinformation[y].amount;
							totalPaid += data[i].groupinformation[y].paidamount;
							
							groupdiv += [
									'<div> <label for="name"> <b>',
									data[i].groupinformation[y].firstname + " " + data[i].groupinformation[y].lastname,
									'</b><button class="buttonRound" onclick=window.location.href="loan.jsp?id='+ data[i].groupinformation[y].userid +'" >&#8618;</button></label>',
									' <progress value=' +data[i].groupinformation[y].paidamount+' max='+data[i].groupinformation[y].amount+'></progress></div>' ]
									.join('\n');
						}
						table.innerHTML += (groupdiv + '</div><button class="groupButton" onclick=window.location.href="group.jsp?id='+ groupid +'">View</button></div>');
						$("#totall"+ groupid).attr("max", totalAmount);
						$("#totall"+ groupid).attr("value", totalPaid);
					}

				} else if (hr.readyState == 4) {
					addNotification('Retrieving data failed with status '
							+ hr.status + '. Try again later.');
				}
			}

			hr.send(null);
		}
	</script>

</body>
</html>
