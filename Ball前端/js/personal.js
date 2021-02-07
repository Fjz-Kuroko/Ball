var baseUrl = getBaseUrl(); 

var nav = new Vue({
		el:"#nav",
		data:{
			result:"",
			message:"login",
			username:"",
		},
		methods:{
			loginCheck:function(){
				var that = this;
				var getEmail = $.cookie('email');
				if (getEmail == null || getEmail == '') {
				} else {
					this.message = "islogin";
					this.username=getEmail;
				
				}
			},
			loginout:function(){
				alert("退出登录");
			}
		},
		created() {
			this.loginCheck();
		},
})
// 引入qs库
var qs = Qs;
var main = new Vue({
		el:"#main",
		data:{
			result:"",
			userInfo:"",
			email:"",
			addresses:"",
			dialogFormVisible: false,
			form: {
			          recipient: '',
			          address: '',
			          recipientPhone: '',
			        },
			formLabelWidth: '120px',
		},
		methods:{
			addAddress:function(){
				this.dialogFormVisible = false;
				var that = this;
				axios({
				 headers: {
				  'token': $.cookie('token')
				 },
				 method: 'post',
				 url: baseUrl + '/address/add',
				 transformRequest: [
				  function (data) {
				   return qs.stringify(data)
				  }
				 ],
				 data: {
				  email: that.email,
				  address:that.form.address,
				  recipient:that.form.recipient,
				  recipientPhone:that.form.recipientPhone,
				 }
				}).then(function(response) {
				 if (response.data.code == '200') {
					window.location.reload();
				 } else {
				  that.$message.error(response.data.data.msg);
				  return false;
				 }
				}, function(err) {
				 console.log(err);
				})
			},
			getUser: function() {
			   this.email = $.cookie('email');
			   let that = this;
			   axios({
			    headers: {
			     'token': $.cookie('token')
			    },
			    method: 'post',
			    url: baseUrl + '/user/getUser',
			    transformRequest: [
			     function (data) {
			      return qs.stringify(data)
			     }
			    ],
			    data: {
			     email: that.email,
			    }
			   }).then(function(response) {
			    if (response.data.code == '200') {
					that.userInfo=response.data.data.user;
			    } else {
			     that.$message.error(response.data.data.msg);
			     return false;
			    }
			   }, function(err) {
			    console.log(err);
			   })
			},
			getAddresses:function(){
				var that = this;
				var token = $.cookie('token');
				
				axios({
					method: 'get',
					url: baseUrl + '/address/getAllAddress?email=' + that.email,
					headers: {
						'token': $.cookie('token')
					}
				}).then(function(res) {
					that.addresses = res.data.data.addresses;
					that.loading = false;
					
				}, function(err) {
					console.log(err);
				})
				
			},
			deleteAdd:function(aid){
				var that = this;
				axios({
				 headers: {
				  'token': $.cookie('token')
				 },
				 method: 'post',
				 url: baseUrl + '/address/del',
				 transformRequest: [
				  function (data) {
				   return qs.stringify(data)
				  }
				 ],
				 data: {
				  aid: aid,
				 }
				}).then(function(response) {
				console.log(response);
				 if (response.data.code == '200' || response.data.code == 200) {
					that.$alert('删除成功！', '成功');
				 } else {
				  this.$alert('请稍后重试！', '失败');
				  that.$message.error(response.data.data.msg);
				  return false;
				 }
				}, function(err) {
				 console.log(err);
				})
			},
			changeAdd:function(aid){
				this.$alert('修改成功！', '成功');
			},
		},
		mounted() {
			this.getUser();
			this.getAddresses();
		},
})
