<!DOCTYPE html>
<html lang="en">

<jsp:include page="parts/head.jsp" />

<body onload="getGroup();">

    <jsp:include page="parts/navigation.jsp" />

	<main>
    <h1>Group</h1>
    <button class="buttonRound" onclick="toggleHide('helpPopup', false)">?</button>
    <button class="buttonRound" onclick="window.location.href='new_contract.jsp'">+</button>


    <div class="block">



    <div id="GroupTable">
      <table id='contractstable' class='contracts_table'>
        <thead>
          <tr class="desktop">
            <th>Name</th>
            <th>Amount ($)</th>
            <th>Progress</th>
            <th>Time Remaining</th>
            <th>Status</th>


          </tr>
        </thead>
        <tbody>
        <tbody>
      </table>
    </div>
    </div>
    </main>

    <jsp:include page="parts/footer.jsp" />

    <div id="helpPopup" class="popup" style="display: none;">
    <div>
      <h2>Contracts explained</h2>
      <button class="buttonRound" onclick="toggleHide('helpPopup', true)">X</button>
      <p>
        Lorem ipsum dolor sit amet, pretium leo sed, ac leo aenean tellus, orci amet maxime amet sed nunc pharetra, scelerisque tristique pretium morbi scelerisque mollis sed, vivamus pede irure ac lacus. Diam ante sit amet, blandit laoreet interdum sem pellentesque. Sit turpis ligula non, iaculis viverra.
        Lorem ipsum dolor sit amet, pretium leo sed, ac leo aenean tellus, orci amet maxime amet sed nunc pharetra, scelerisque tristique pretium morbi scelerisque mollis sed, vivamus pede irure ac lacus. Diam ante sit amet, blandit laoreet interdum sem pellentesque. Sit turpis ligula non, iaculis viverra.
      </p>
    </div>
    </div>


  </main>

  <jsp:include page="parts/footer.jsp" />
<script>
function getGroup() {
  var hr = new XMLHttpRequest();
  hr.open("GET", "/bundlePWABackend/restservices/loangroup/"+1, true);

  hr.onreadystatechange = function() {
    if (hr.readyState == 4 && hr.status == 200) {
      var data = JSON.parse(hr.responseText);

      var datalength = data.length;
      $('#mainLoader').fadeOut('fast');

      console.log(data);
      for(var i = 0; i<datalength;i++){
      console.log(data[i].loaninformation);
      var id =data[i].loaninformation[0].useridfk.toString();
      console.log(id);



      var amount = data[i].loaninformation[0].amount;
      var paidamount =data[i].loaninformation[0].paidamount;
      var duration = data[i].loaninformation[0].duration;
      var status =data[i].loaninformation[0].status;
      var loanid =data[i].loaninformation[0].loanId;
      createCode(id,name,amount,paidamount,duration,status,loanid);

      }
  }
    else if (hr.readyState == 4) {
      addNotification('Retrieving data failed with status ' + hr.status
          + '. Try again later.');
    }
}
  hr.send(null);

}



function createCode(id,name,amount,paidamount,duration,status,loanid){

$.ajax({
            url : "/bundlePWABackend/restservices/user/"+id
          ,
            type : "get",

            success : function(response) {

                    console.log(response+"binnenste loop");
                    console.log(response[0]);
                    var data2= response;
                    var name = data2[0].firstName+" "+data2[0].lastName;
                    console.log(id+name);

                  var table = document.getElementById('GroupTable');
                  var tr = document.createElement('tr');
                  tr.innerHTML = '<td class="name" id="name" data-label="Name">'
                  + name + '</td>'
                  + '<td id ="amount" data-label="Amount">'
                  + amount + '</td>'
                  + '<td id ="progress" data-label="Progress">'
                  +'<progress value=' +paidamount+' max= '+  amount+ '> </progress></td>'
                  + '<td id = "duration" data-label="Time Remaining">'
                  + duration + " months" + '</td>'
                  + '<td id="status" data-label="Status">'
                  + status + '</td>'
                  + "<td class='tdHide'>  <button class='small' onclick='toViewContract("
                  + loanid + ");'>View</button> " +
                      "<button class='small' onclick='toEditContract("
                  + loanid + ");'>Edit</button> </td>";
              table.appendChild(tr);


            },
            error : function(response, textStatus, errorThrown) {
                console.log("Failed.");
                console.log("textStatus: " + textStatus);
                console.log("errorThrown: " + errorThrown);
                console.log("status: " + response.status);


            }
        });

}


</script>
</body>
</html>
