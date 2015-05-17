<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tag"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>Bootstrap 101 Template</title>

<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/common.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="js/bootstrap.min.js"></script>
	<script src="js/common.js"></script>
	<!-- <script src="js/validator.js"></script> -->

	<%-- <tag:menu /> --%>


	<!-- <div style="width:300px; margin: 2px;">
		<form>
			<div class="form-group">
				<label for="windowsID">Windows Login ID</label>
				<input type="text" class="form-control" id="windowsID" placeholder="Input your windows Login ID">
			</div>
			<div class="form-group">
				<label for="email">Email</label>
				<input type="email" class="form-control" id="email" placeholder="Input your email">
			</div>
			<button type="submit" class="btn btn-default">Submit</button>
		</form>
	</div> -->
	
	<!-- multistep form -->
	<form id="msform" data-toggle="validator" role="form">
		<!-- progressbar -->
		<ul id="progressbar">
			<li class="active">Enter your email and login ID</li>
			<li>Social Profiles</li>
			<li>Personal Details</li>
		</ul>
		<!-- fieldsets -->
		<fieldset id="step_email_login">
			<h2 class="fs-title">Step 1</h2>
			<h3 class="fs-subtitle">Enter your email and login ID</h3>
			<div class="form-group">
				<input type="email" name="email" class="form-control" placeholder="Email" required/>
				<div class="help-block with-errors"></div>
			</div>
			<div class="form-group">
				<input type="text" name="loginId" class="form-control" placeholder="Windows Login Id" required/>
				<div class="help-block with-errors"></div> 
			</div>
			
			<input type="button" name="next" class="next action-button" value="Next"/>
		</fieldset>
		<fieldset id="email_otp">
			<h2 class="fs-title">Enter your OTP code from your email:</h2>
			<h3 class="fs-subtitle">Login to web mail <a href="https://sgmail" target="_blank">https://sgmail</a></h3>
			<div class="form-group">
				<input type="text" name="otp" placeholder="One time password" />
				<div class="help-block with-errors"></div> 
			</div>
			<input type="button" name="previous" class="previous action-button" value="Previous" />
			<input type="button" name="next" class="next action-button" value="Next" />
		</fieldset>
		<fieldset id="resetPassword">
			<h2 class="fs-title">Enter Your password</h2>
			<h3 class="fs-subtitle">Enter your password</h3>
			
			<div class="form-group">
				<input type="password" name="pass" placeholder="Password" />
				<input type="password" name="cpass" placeholder="Confirm Password" />
				<div class="help-block with-errors"></div> 
			</div>
			
			<input type="button" name="previous" class="previous action-button" value="Previous" />
			<input type="submit" name="submit" class="submit action-button" value="Submit" />
		</fieldset>
	</form>

	<!-- jQuery easing plugin -->
	<script src="http://thecodeplayer.com/uploads/js/jquery.easing.min.js" type="text/javascript"></script>
</body>
</html>