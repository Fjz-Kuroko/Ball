// 引入qs库
var qs = Qs;
// 引入自定义baseUrl
var baseUrl = getBaseUrl(); 
var app = new Vue({
	el: '#app',
	data: {
		flag: false,
		form: {
			email: '',
			name: '',
			password: '',
			phone: '',
			gender: '男',
			emailCode: '',
		},
		rules: {
			email: [{ required: true, message: "请输入用户email", trigger: "blur" },
					{type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change']}],
			name: [{ required: true, message: "请输入用户名", trigger: "blur" }],
			password: [{ required: true, message: "请输入密码", trigger: "blur" }],
			phone: [{ required: true, message: "请输入的手机号码", trigger: "blur" }],
			emailCode: [{ required: true, message: "请输入邮箱验证码", trigger: "blur" }],
		}
	},
	created() {
		
	},
	methods: {
		toHome: function() {
			window.location.href = 'index.html'
		},
		getEmailCode: function() {
			let that = this;
			if (that.form.email == null || that.form.email == '') {
				that.$message.error('邮箱不能为空！');
			} else {
				that.flag = true;
				axios({
					method: 'post',
					url: baseUrl + '/email/code',
					transformRequest: [
						function(data) {
							return qs.stringify(data);
						}
					],
					data: {
						email: that.form.email
					}
				}).then(function(res) {
					console.log(res);
					if (res.data.code == '200') {
						that.$message({
						  message: '验证码已经发送至您的邮箱，请注意查收~',
						  type: 'success'
						});
					}
					that.flag = false;
				}, function(err) {
					console.log(err);
				})
			}
		},
		onSubmit(formName) {
			let that = this;
			that.$refs[formName].validate((valid) => {
				if (valid) {
					axios({
						method: 'post',
						url: baseUrl + '/user/register',
						transformRequest: [
							function(data) {
								return qs.stringify(data);
							}
						],
						data: {
							email: that.form.email,
							uname: that.form.name,
							upwd: that.form.password,
							phone: that.form.phone,
							gender: that.form.gender,
							code: that.form.emailCode
						}
					}).then(function(response) {
						if (response.data.code == '200') {
							// 注册成功
							that.$message({
							  message: '注册成功！',
							  type: 'success'
							});
							window.location.href = 'login.html'
						} else {
							// 注册失败
							that.$message.error(response.data.data.msg);
							return false;
						}
					}, function(err) {
						console.log(err);
					})
				} else {
					that.$message.error("表单格式不正确！");
					that.getCaptcha();
					return false;
				}
			})
		},
	}
})