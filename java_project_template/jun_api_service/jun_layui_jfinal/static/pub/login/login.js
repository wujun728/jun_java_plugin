
$(function(){
	    $('#signup_code').change(function(){
	    	$("#userCodeMsg").hide();
	    })
	    $('#signup_password').change(function(){
	    	$(".tip").hide();
	    })
	    $('#signup_verifyCode').change(function(){
	    	$("#verifyCodeMsg").hide();
	    })

	});

	function _loginKeyPress(event) {
		if (event.keyCode == 13) {
			login();
		}
	}

	//验证吗
	function reloadImage() {
		document.getElementById('imageId').src = _path+"/pub/verify?t=" + (new Date()).toTimeString();
	}
	function login(){
		
		var userCode=$("#signup_code").val();
		var password=$("#signup_password").val();
		var verifyCode=$("#signup_verifyCode").val();
		if (userCode == '') {
			layer.msg('用户名不能为空');
			return;
		}
		if (password == '') {
			layer.msg('用户密码不能为空');
			 
			return;
		}
		
		var loading = layer.load(2, {
            shade: false,
            time: 2000
        });
		
		
		//加密处理
		//bodyRSA();
	    //password = encryptedString(key, encodeURIComponent(password));
	    password=encrypt(encodeURIComponent(password))
		$('#signup_password').val(password);
		$('#signup_form').submit();
	}
	
	//加密key	
	var publicKey;
	function getPublicKey(){
		$.ajax({
			url:_path+"/pub/login/getPublicKey",
			type:"POST",
			success:function(ret){
				publicKey = ret.data;
			}
		})
	}
	
	function encrypt(word){
		const encrypt = new JSEncrypt();
        encrypt.setPublicKey(publicKey);
        word = encrypt.encrypt(word);
        return word;
    }
    
