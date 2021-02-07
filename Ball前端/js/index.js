var baseUrl = getBaseUrl(); 

var nav = new Vue({
		el:"#nav",
		data:{
			result:"",
			message:"login",
			username:"",
			condition:"",
		},
		methods:{
			serach:function(){
				var url = 'sort.html?condition='+this.condition;
				window.location.href=url;
			},
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
			    $.removeCookie('email', {domain: '127.0.0.1', path: '/ballballyou'});
			    $.removeCookie('token', {domain: '127.0.0.1', path: '/ballballyou'});
				window.location.reload();
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


var main = new Vue({
		el:"#main",
		data:{
			result:"",
			imgurl:{one:{url:"img/malong1.jpg"},
				two:{url:"img/lindan2.jpg"},
				three:{url:"img/zhuting3.jpg"},
				four:{url:"img/ouwen4.jpg"}
			},
		},
		methods:{
			
		},
		mounted() {
			
		},
})
