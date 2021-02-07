var baseUrl = getBaseUrl(); 
var BallID = window.location.search;
var BalliD = BallID.split("=");
// 引入qs库
var qs = Qs;

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
				
			}
		},
		created() {
			this.loginCheck();
		},
})

var main = new Vue({
		el:"#main",
		data:{
			result:"",
			radio1:"1",
			radio3:"广州",
			num:1,
		},
		methods:{
			getBallInfo:function(pid){
				var that = this;
				axios.get(baseUrl+"/product/selectByPid?pid="+BalliD[1]).then(function(res){
					if(res.data.code == 200)
					{
						that.result = res.data.data.product;
					}
				},function(err){
				    alert("页面出错");
				})
			},
			addCart:function(){
				var that = this;
				var getEmail = $.cookie('email');
				axios({
				 headers: {
				  'token': $.cookie('token')
				 },
				 method: 'post',
				 url: baseUrl + '/cart/add',
				 transformRequest: [
				  function (data) {
				   return qs.stringify(data)
				  }
				 ],
				 data: {
				  pid: BalliD[1],
				  num:that.num,
				  email:$.cookie('email'),
				 }
				}).then(function(response) {
					console.log(BalliD[1]);
					console.log(that.num);
					console.log(getEmail);
					console.log(response);
				 if (response.data.code == '200') {
					that.$message.success('加入成功！');
				 } else {
				  that.$message.error(response.data.data.msg);
				  return false;
				 }
				}, function(err) {
				 console.log(err);
				})
			},
			buyIm:function(){
				var that = this;
				var getEmail = $.cookie('email');
				axios({
				 headers: {
				  'token': $.cookie('token')
				 },
				 method: 'post',
				 url: baseUrl + '/cart/add',
				 transformRequest: [
				  function (data) {
				   return qs.stringify(data)
				  }
				 ],
				 data: {
				  pid: BalliD[1],
				  num:that.num,
				  email:$.cookie('email'),
				 }
				}).then(function(response) {
					console.log(BalliD[1]);
					console.log(that.num);
					console.log(getEmail);
					console.log(response);
				 if (response.data.code == '200') {
					that.$message.success('加入成功！');
					window.location.href="cart.html";
				 } else {
				  that.$message.error(response.data.data.msg);
				  return false;
				 }
				}, function(err) {
				 console.log(err);
				})
			},
		},
		mounted() {
			this.getBallInfo();
		}
})