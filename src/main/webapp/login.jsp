<!DOCTYPE html>
<html lang="en">

<jsp:include page="parts/head.jsp" />

<body onload="loadingText('...')">
    
    <jsp:include page="parts/navigation.jsp" />
	
    <main class="login">
      <div class="welcomeBlock">
      	<h1>Login</h1>
      </div>
      
      <div class="block">
	    <form id = "loginform" name = "loginform" action="javascript:validateLogin();"  class = "form">
		  <ul class="flex-outer">
			  <li>	
			  	<label for="uname"><b>Username</b></label>
			  	<input id = "username" type="text" placeholder="Enter Username" name="uname">
			  </li>
			  
			  <li>
			 	 <label for="psw"><b>Password</b></label>
			  	<input id = "pass" type="password" placeholder="Enter Password" name="psw">
			  </li>
			  
			  <li>
			  	<button id="loginbutton" type="submit" >Login</button>
			  </li>
		  </ul>
		
		  
	    </form>
	</div>
   </main>

    <jsp:include page="parts/footer.jsp" />

</body>
</html>