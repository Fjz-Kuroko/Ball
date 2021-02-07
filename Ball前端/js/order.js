// 引入qs库
var qs = Qs;
// 引入自定义baseUrl
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
			logout() {
			   this.$confirm('确定退出吗?', '提示', {
			    confirmButtonText: '确定',
			    cancelButtonText: '取消',
			    type: 'warning'
			   }).then(() => {
			    $.removeCookie('email', {domain: '127.0.0.1', path: '/Ball'});
			    $.removeCookie('token', {domain: '127.0.0.1', path: '/Ball'});
			   }).catch(() => {
			    this.$message({
			     type: 'info',
			     message: '已取消退出'
			    });
			   });
			},
		},
		created() {
			this.loginCheck();
		},
})

var app = new Vue({
	el: '#app',
	data: function() {
		return {
			sumPrice: 0,
			tableData: [],
			multipleSelection: [],
			loading: false,
			email: '',
			// gridData: [{
			//           oid: "",
			//           recipient: "",
			//           address: "",
			// 		  recipientPhone:"",
			//         }],
		}
	},
	mounted() {
		this.isLogin();
		this.getData();
	},
	methods: {
		comm(row){
			var that = this;
			// if(row.status!="已付款")
			// {
			// 	this.$message.error('出错了，你已经收货成功了！');
			// 	return false;
			// }
			axios({
			 headers: {
			  'token': $.cookie('token')
			 },
			 method: 'post',
			 url: baseUrl + '/order/receivingOrder',
			 transformRequest: [
			  function (data) {
			   return qs.stringify(data)
			  }
			 ],
			 data: {
			  email:$.cookie('email'),
			  oid:row.oid,
			 }
			}).then(function(response) {
			console.log(response);
			 if (response.data.code == '200') {
				that.$message({
				          message: '恭喜你，收货成功！',
				          type: 'success'
				});
			 } else {
			  that.$message.error(response.data.data.msg);
			  return false;
			 }
			}, function(err) {
			 console.log(err);
			})
			
		},
		more(row){
			// this.gridData.oid = row.oid;
			// this.gridData.recipient = row.recipient;
			// this.gridData.address = row.address;
			// this.gridData.recipientPhone = row.recipientPhone;
			this.$message({
			          showClose: true,
			          message: '订单编号：'+row.oid +' 地址：'+row.address+' 姓名：'+row.recipient+' 电话：'+row.recipientPhone,
			          type: 'success'
			        });
		},
		isLogin() {
			let that = this;
			var getEmail = $.cookie('email');
			if (getEmail == null || getEmail == '') {
				that.$alert('您暂时还没有登录，请先登录', {
					confirmButtonText: '确定',
					callback: action => {
						that.loading = false;
						window.location.href = 'login.html'
					}
				})
			} else {
				that.email = getEmail;
				that.getData();
			}
		},
		getData() {
			let that = this;
			axios({
			 headers: {
			  'token': $.cookie('token')
			 },
			 method: 'post',
			 url: baseUrl + '/order/getViewOrdersByEmail',
			 transformRequest: [
			  function (data) {
			   return qs.stringify(data)
			  }
			 ],
			 data: {
			  email:$.cookie('email'),
			 }
			}).then(function(response) {
			console.log(response);
			 if (response.data.code == '200') {
				that.tableData = response.data.data.orderList;
			 } else {
			  that.$message.error(response.data.data.msg);
			  return false;
			 }
			}, function(err) {
			 console.log(err);
			})
		},
		toggleSelection(rows) {
			if (rows) {
			  rows.forEach(row => {
				this.$refs.multipleTable.toggleRowSelection(row);
			  });
			} else {
			  this.$refs.multipleTable.clearSelection();
			}
		},
	}
})