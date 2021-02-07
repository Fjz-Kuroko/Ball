// 引入qs库
var qs = Qs;
// 引入自定义baseUrl
var baseUrl = getBaseUrl(); 
var message = new Vue({
	el: '#message',
	data: function() {
		return {
			isloading: false,
			form: {
			  email: '',
			  message: '',
			},
			rules: {
				email: [
					{ required: true, message: '请输入邮箱地址', trigger: 'blur' },
					{ type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
				],
				message: [
					{ required: true, message: '请填写留言信息', trigger: 'blur' }
				]
			}
		}
	},
	created() {
		this.getEmail()
	},
	methods: {
		getEmail() {
			let myEmail = $.cookie('email');
			if (myEmail == null || myEmail == '') {
				this.$alert('您暂时还没有登录，请先登录', {
					confirmButtonText: '确定',
					callback: action => {
						window.location.href = 'login.html'
					}
				})
			} else {
				this.form.email = myEmail;
			}
		},
		onSubmit(formName) {
			let that = this;
			that.isloading = true;
			this.$refs[formName].validate((valid) => {
			  if (valid) {
				axios({
					method: 'post',
					url: baseUrl + '/message/addMessage',
					transformRequest: [
						function (data) {
							return qs.stringify(data)
						}
					],
					data: {
						email: that.form.email,
						msg: that.form.message
					},
					headers: {
						'token': $.cookie('token')
					}
				}).then(function(res) {
					if (res.data.code == '200') {
						that.$message.success('留言成功！');
					} else {
						that.$message.error(res.data.data.msg);
					}
					that.isloading = false;
				}, function(err) {
					console.log(err);
					that.isloading = false;
				})
			  } else {
				console.log('表单格式错误！');
				that.isloading = false;
				return false;
			  }
			});
		}
	}
})