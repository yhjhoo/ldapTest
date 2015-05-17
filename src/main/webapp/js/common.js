$(document).ready(function() {
	var goToNextDiag = true; //flag to prevent go to next
	
	function _validate(obj){
		_isValid = true;
		var email = $("#step_email_login input[name='email']");
		var loginId = $("#step_email_login input[name='loginId']");
		var email_otp = $("#email_otp input[name='otp']");
		var pass = $("#resetPassword input[name='pass']")
		var cpass = $("#resetPassword input[name='cpass']")
		
		if(obj.attr("id")=="step_email_login"){
			if(email.val() =="" || email.val().indexOf("@") <=0){
				_isValid = false;
				email.siblings(".help-block").text("Please input a valid email!");
			}
			
			if(loginId.val() ==""){
				_isValid = false;
				loginId.siblings(".help-block").text("Please input a valid loginId!");
			}
			if(_isValid){
				$.ajax({
		            url: 'ldap/getUser',
		            dataType: 'json',
		            data: {
		            	email: email.val(),
		            	loginId: loginId.val()
		            },
		            success: function(data) {
		            	if(data.matchesUser){
		            		
		            	}else{
		            		_isValid = false;
		            		email.siblings(".help-block").text("Your email and Login Id is not mismatched!");
		            	}
		            	
					}
				});
			}
			
			goToNextDiag = _isValid;
		}else if(obj.attr("id")=="email_otp"){
			if(email_otp.val() ==""){
				_isValid = false;
				email_otp.siblings(".help-block").text("Please do not leave it empty!");
			}
			
			if(_isValid){
				$.ajax({
		            url: 'ldap/matchToken',
		            dataType: 'json',
		            data: {
		            	email: email.val(),
		            	loginId: loginId.val(),
		            	token: email_otp.val()
		            },
		            success: function(data) {
		            	if(data.tokenMatches){
		            		
		            	}else{
		            		_isValid = false;
		            		email_otp.siblings(".help-block").text("Your Token is not matched!" + data.errorMsg);
		            	}
		            	
					}
				});
			}
			
			
			goToNextDiag = _isValid;
		}else if(obj.attr("id")=="resetPassword"){
			if(pass.val() =="" || cpass.val()==""){
				_isValid = false;
				pass.siblings(".help-block").text("Please do not leave it empty!");
			}
			
			if(pass.val() != cpass.val() ){
				_isValid = false;
				pass.siblings(".help-block").text("Password is not matched!");
			}
			
			if(_isValid){
				$.ajax({
		            url: 'ldap/resetPassword',
		            dataType: 'json',
		            data: {
		            	email: email.val(),
		            	loginId: loginId.val(),
		            	token: email_otp.val(),
		            	newPassword: pass
		            },
		            success: function(data) {
		            	if(data.errorMsg != ""){
		            		alert("Password updated successfully!");
		            	}else{
		            		_isValid = false;
		            		pass.siblings(".help-block").text(data.errorMsg);
		            	}
		            	
					}
				});
			}
			
			
			goToNextDiag = _isValid;
		}
	}
	
	//jQuery time
	var current_fs, next_fs, previous_fs; //fieldsets
	var left, opacity, scale; //fieldset properties which we will animate
	
	$(".next").click(function(){
		current_fs = $(this).parent();
		_validate(current_fs);
		if(!goToNextDiag) return false; 
		
		next_fs = $(this).parent().next();
		
		//activate next step on progressbar using the index of next_fs
		$("#progressbar li").eq($("fieldset").index(next_fs)).addClass("active");
		
		//show the next fieldset
		next_fs.show(); 
		//hide the current fieldset with style
		current_fs.animate({opacity: 0}, {
			step: function(now, mx) {
				//as the opacity of current_fs reduces to 0 - stored in "now"
				//1. scale current_fs down to 80%
				scale = 1 - (1 - now) * 0.2;
				//2. bring next_fs from the right(50%)
				left = (now * 50)+"%";
				//3. increase opacity of next_fs to 1 as it moves in
				opacity = 1 - now;
				current_fs.css({'transform': 'scale('+scale+')'});
				next_fs.css({'left': left, 'opacity': opacity});
			}, 
			duration: 800, 
			complete: function(){
				current_fs.hide();
			}, 
			//this comes from the custom easing plugin
			easing: 'easeInOutBack'
		});
	});
	
	$(".previous").click(function(){
		current_fs = $(this).parent();
		previous_fs = $(this).parent().prev();
		
		//de-activate current step on progressbar
		$("#progressbar li").eq($("fieldset").index(current_fs)).removeClass("active");
		
		//show the previous fieldset
		previous_fs.show(); 
		//hide the current fieldset with style
		current_fs.animate({opacity: 0}, {
			step: function(now, mx) {
				//as the opacity of current_fs reduces to 0 - stored in "now"
				//1. scale previous_fs from 80% to 100%
				scale = 0.8 + (1 - now) * 0.2;
				//2. take current_fs to the right(50%) - from 0%
				left = ((1-now) * 50)+"%";
				//3. increase opacity of previous_fs to 1 as it moves in
				opacity = 1 - now;
				current_fs.css({'left': left});
				previous_fs.css({'transform': 'scale('+scale+')', 'opacity': opacity});
			}, 
			duration: 800, 
			complete: function(){
				current_fs.hide();
			}, 
			//this comes from the custom easing plugin
			easing: 'easeInOutBack'
		});
	});
	
	$(".submit").click(function(){
		return false;
	})
});
