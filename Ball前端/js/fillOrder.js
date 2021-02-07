// 引入qs库
var qs = Qs;
// 引入自定义baseUrl
var baseUrl = getBaseUrl(); 
var app = new Vue({
	el: '#app',
	data: function() {
		return {
			email: '',
			oid: '',
			myOrder: null,
			shoplist: [],
			addresses:[],
			currentRow: null
		}
	},
	created() {
		this.getOid();
		// this.isLogin();
	},
	methods: {
		getAddresses() {
			let that = this;
			axios({
				method: 'get',
				url: baseUrl + '/address/getAllAddress?email=' + that.email,
				headers: {
					'token': $.cookie('token')
				}
			}).then(function(res) {
				// console.log(res);
				if (res.data.code == '200') {
					that.addresses = res.data.data.addresses;
				}
			}, function(err) {
				console.log(err);
			})
		},
		getOrder() {
			let that = this;
			axios({
				method: 'get',
				url: baseUrl + '/order/getOrderByOid',
				params: {
					oid: that.oid,
					email: that.email
				},
				headers: {
					'token': $.cookie('token')
				}
			}).then(function(res) {
				if (res.data.code == '200') {
					if (!(res.data.data.order.status == '未付款')) {
						that.$alert('该订单已完成支付，请勿重新进入！', {
							confirmButtonText: '确定',
							callback: action => {
								window.location.href = 'index.html'
							}
						})
					}
					that.myOrder = res.data.data.order;
				} else if (res.data.code == '401') {
					that.$alert('没有权限！', {
						confirmButtonText: '确定',
						callback: action => {
							window.location.href = 'index.html'
						}
					})
				} else {
					that.$alert('订单不存在！', {
						confirmButtonText: '确定',
						callback: action => {
							window.location.href = 'index.html'
						}
					})
				}
			}, function(err) {
				console.log(err)
			})
		},
		getShoplist() {
			let that = this;
			axios({
				method: 'get',
				url: baseUrl + '/order/getShoplistByOid',
				params: {
					oid: that.oid,
					email: that.email
				},
				headers: {
					'token': $.cookie('token')
				}
			}).then(function(res) {
				if (res.data.code == '200') {
					that.shoplist = res.data.data.shoplist;
				}
			}, function(err) {
				console.log(err)
			})
		},
		getOid() {
			var url = decodeURI(window.location.href);
			var myoid = url.split("?oid=")[1];
			if (!myoid || myoid == '') {
				this.$alert('订单不存在！', {
					confirmButtonText: '确定',
					callback: action => {
						window.location.href = 'index.html'
					}
				})
			} else {
				this.oid = myoid;
				this.isLogin();
			}
		},
		isLogin() {
			let that = this;
			var getEmail = $.cookie('email');
			if (getEmail == null || getEmail == '') {
				that.$alert('您暂时还没有登录，请先登录', {
					confirmButtonText: '确定',
					callback: action => {
						window.location.href = 'login.html'
					}
				})
			} else {
				that.email = getEmail;
				that.getAddresses();
				that.getOrder();
				that.getShoplist();
			}
		},
		submitOrder() {
			let that = this;
			if (!this.currentRow) {
				this.$message({
					type: 'info',
					message: '还没有选择收货地址哦'
				});
				return false;
			}
			axios({
				method: 'post',
				url: baseUrl + '/order/payOrder',
				transformRequest: [
					function (data) {
						return qs.stringify(data);
					}
				],
				data: {
					aid: that.currentRow.aid,
					oid: that.oid,
					email: that.email
				},
				headers: {
					'token': $.cookie('token')
				}
			}).then(function(res) {
				if (res.data.code == '200') {
					that.$alert('订单完成，跳转到首页', '提示', {
					  confirmButtonText: '确定',
					  callback: action => {
						window.location.href = 'index.html';
					  }
					});
				} else {
					that.$message.error('付款出错！');
				}
			}, function(err) {
				console.log(err);
			})
		},
		cancelOrder() {
			let that = this;
			this.$confirm('确定取消该订单吗?', '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			}).then(() => {
				axios({
					method: 'post',
					url: baseUrl + '/order/cancelOrderByOid',
					transformRequest: [
						function (data) {
							return qs.stringify(data);
						}
					],
					data: {
						oid: that.oid,
						email: that.email
					},
					headers: {
						'token': $.cookie('token')
					}
				}).then(function(res) {
					if (res.data.code == '200') {
						that.$alert('取消订单成功，跳转到首页', '提示', {
						  confirmButtonText: '确定',
						  callback: action => {
							window.location.href = 'index.html';
						  }
						});
					} else {
						that.$message.error(res.data.data.msg);
					}
				}, function(err) {
					console.log(err);
				})
			}).catch(() => {
				
			})
		},
		toAddress() {
			this.$message.info('跳转到管理地址页面');
		},
		handleCurrentChange(val) {
			this.currentRow = val;
		},
		arraySpanMethod({row, column, rowIndex, columnIndex}) {
			let that = this;
			if (columnIndex === 4 || columnIndex === 5) {
				if (rowIndex === 0) {
					return {
						rowspan: that.shoplist.length,
						colspan: 1
					};
				}
			}
		}
	}
})