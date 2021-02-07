// 引入qs库
var qs = Qs;
// 引入自定义baseUrl
var baseUrl = getBaseUrl(); 
var app = new Vue({
	el: '#app',
	data: {
		uuid: '',
		captchaSrc: '',
		form: {
			email: "",
			password: "",
			captchaCode: "",
		},
		rules: {
			email: [{ required: true, message: "请输入用户邮箱", trigger: "blur" },
				 {type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change']}],
			password: [{ required: true, message: "请输入密码", trigger: "blur" }],
			captchaCode: [{ required: true, message: "请输入验证码", trigger: "blur" }],
		}
	},
	created() {
		this.isLogin();
		this.getCaptcha();
	},
	methods: {
		isLogin: function() {
			var that = this;
			var getEmail = $.cookie('email');
			// console.log(getEmail);
			if (getEmail == null || getEmail == '') {
			} else {
				that.$notify({
					title: '提示',
					message: '你当前已经登录过，再次登录将会覆盖上一次登录！',
					duration: 0
				});
			}
		},
		toHome: function() {
			window.location.href = 'index.html'
		},
		getCaptcha: function() {
			let that = this;
			axios({
				method: 'post',
				url: baseUrl + '/captcha/image',
				responseType: 'arraybuffer'
			}).then (res => {
				// console.log('从服务端获取请求头uuid = ' + res.headers['uuid']);
				// 从响应头获取uuid，通过此来验证验证码
				that.uuid = res.headers['uuid'];
				return 'data:image/png;base64,' + btoa(
					new Uint8Array(res.data).reduce((data, byte) => data + String.fromCharCode(byte), '')
				);
			}).then(data => {
				this.captchaSrc = data;
			})
		},
		onSubmit(formName) {
			var that = this;
			that.$refs[formName].validate((valid) => {
				// 表单参数验证
				if (valid) {
					axios({
						method: 'post',
						url: baseUrl + '/user/login',
						transformRequest: [
							function (data) {
								return qs.stringify(data)
							}
						],
						data: {
							email: that.form.email,
							upwd: that.form.password,
							uuid: that.uuid,
							checkCode: that.form.captchaCode
						}
					}).then(function(response) {
						// console.log(response);
						if (response.data.code == '200') {
							// 应该设置域名，但没有
							$.cookie('email', response.data.data.user.email);
							$.cookie('token', response.data.data.token);
							// 登录成功
							window.location.href = 'index.html'
						} else {
							that.$message.error(response.data.data.msg);
							that.getCaptcha();
							return false;
						}
					}, function(err) {
						console.log(err);
					})
				} else {
					this.$message.error('用户名或者密码格式不对！');
					return false;
				}
			})
		}
	}
})