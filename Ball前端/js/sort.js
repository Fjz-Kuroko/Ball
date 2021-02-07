var baseUrl = getBaseUrl(); 
var BallID = window.location.search;
var selectname = BallID.split("=");

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
				// console.log(getEmail);
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
		},
		methods:{
			getBallList:function(){
				var that = this;
				axios.get(baseUrl+"/product/list").then(function(res){
					if(res.data.code == 200)
					{
						that.result = res.data.data.products;
						// console.log(that.result);
					}
				},function(err){
				    alert("页面出错");
				})
			},
			getBallListByCon:function(){
				var that = this;
				axios.get(baseUrl+"/product/list?condition="+selectname[1]).then(function(res){
					if(res.data.code == 200)
					{
						that.result = res.data.data.products;
						// console.log(that.result);
					}
				},function(err){
				    alert("页面出错");
				})
			},
		},
		mounted() {
			if(selectname[1]==null || selectname[1]=='' || selectname[1]==undefined)
				this.getBallList();
			else
				this.getBallListByCon();
		}
})
