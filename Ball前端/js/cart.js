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
				console.log(getEmail);
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
			loading: true,
			email: ''
		}
	},
	created() {
		this.isLogin()
	},
	methods: {
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
				that.getData();
			}
		},
		getData() {
			let that = this;
			axios({
				method: 'get',
				url: baseUrl + '/cart/getCarts?email=' + that.email,
				headers: {
					'token': $.cookie('token')
				}
			}).then(function(res) {
				that.tableData = res.data.data.viewCarts;
				that.loading = false;
			}, function(err) {
				console.log(err);
			})
		},
		changeNum(currentRow) {
			// console.log(currentRow);
			let that = this;
			// 更新表格数据
			var sum = Number(currentRow.price) * Number(currentRow.num);
			sum = Math.floor(sum * 100) / 100;
			currentRow.summary = sum;
			// 增加请求
			axios({
				method: 'post',
				url: baseUrl + '/cart/update',
				transformRequest: [
					function (data) {
						return qs.stringify(data);
					}
				],
				data: {
					cid: currentRow.cid,
					num: currentRow.num,
					email: that.email
				},
				headers: {
					'token': $.cookie('token')
				}
			}).then(function(res) {
				if (res.data.code != '200') {
					that.$message({
						message: '更新购物车时发生错误=====>' + res.data.data.msg,
						type: 'error'
					})
				}
			}, function(err) {
				console.log(err);
			})
		},
		deleteCur(currentRow, index) {
			let that = this;
			// console.log('删除cid为' + currentRow.cid + '的购物车记录');
			this.$confirm('确定从购物车删除此商品吗？', '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			}).then(() => {
				axios({
					method: 'post',
					url: baseUrl + '/cart/del',
					transformRequest: [
						function (data) {
							return qs.stringify(data);
						}
					],
					data: {
						cid: currentRow.cid,
						email: that.email
					},
					headers: {
						'token': $.cookie('token')
					}
				}).then(function(res) {
					if (res.data.code == '200') {
						that.tableData.splice(index, 1);
						that.$message({
							type: 'success',
							message: '删除成功'
						});
					} else {
						that.$message({
							type: 'error',
							message: res.data.data.msg
						});
					}
				}, function(err) {
					console.log(err);
					that.$message({
						type: 'error',
						message: '删除出错'
					});
				})
			}).catch(() => {
				that.$message({
					type: 'info',
					message: '已取消删除'
				});
			})
		},
		clearCart() {
			let that = this;
			this.$confirm('此操作无法恢复,确定清空购物车吗?', '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			}).then(() => {
				axios({
					method: 'post',
					url: baseUrl + '/cart/clearCart',
					transformRequest: [
						function (data) {
							return qs.stringify(data);
						}
					],
					data: {
						email: that.email
					},
					headers: {
						'token': $.cookie('token')
					}
				}).then(function(res) {
					if (res.data.code == '200') {
						that.tableData = [];
						// 请求后再显示
						that.$message({
							type: 'success',
							message: '清空购物车成功'
						});
					} else {
						that.$message({
							type: 'error',
							message: res.data.data.msg
						});
					}
				}, function(err) {
					console.log(err);
					that.$message({
						type: 'error',
						message: '清空购物车出错'
					});
				})
			}).catch(() => {
				this.$message({
					type: 'info',
					message: '已取消清空购物车'
				});
			});
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
		handleSelectionChange(val) {
			this.multipleSelection = val;
			var sum = 0;
			this.multipleSelection.filter( (item, i) => {
				sum = sum + Number(item.summary);
			})
			this.sumPrice = sum;
		},
		submitCart() {
			let that = this;
			// 如果没有选择任何商品,那么弹出提示
			if (!this.multipleSelection || this.multipleSelection.length <= 0) {
				this.$message({
					type: 'info',
					message: '当前没有选择任何商品哦'
				});
				return false;
			}
			// 进行逻辑处理
// 			this.$message({
// 				type: 'info',
// 				message: '应该结算跳转到订单页面了'
// 			});
			axios({
				method: 'post',
				url: baseUrl + '/order/submitOrder',
				transformRequest: [
					function (data) {
						return qs.stringify(data);
					}
				],
				data: {
					viewCartsStr: JSON.stringify(that.multipleSelection),
					email: that.email,
					orderAmount: that.sumPrice
				},
				headers: {
					'token': $.cookie('token')
				}
			}).then(function(res) {
				console.log('res', res);
				if (res.data.code == '200') {
					// 提交成功
					var url = 'fillOrder.html?oid=' + res.data.data.oid;
					window.location.href = url;
				} else {
					that.$message({
						type: 'error',
						message: res.data.messsage
					});
				}
			}, function(err) {
				console.log('err', err)
			})
		}
	}
})