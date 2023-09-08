<script>
	export default {
		onLaunch: function() {
			uni.login({
				success: (res) => {
					uni.request({
						url: `${this.globalData.apiUrl}/wx/getOriginData`,
						method: "GET",
						data: {
							code: res.code
						},
						success: (res) => {
							if(res.data.code !== 200){
								console.log(res.data.msg);
								return;
							}
							const openId = res.data.data;
							uni.request({
								url: `${this.globalData.apiUrl}/user/checkExist`,
								method: "GET",
								data: {
									open_id: openId
								},
								success: (res) => {
									if(res.data.code !== 200){
										console.log(res.data.msg);
										return;
									}
									if(res.data.data.exist === true){
										uni.request({
											url: `${this.globalData.apiUrl}/user/getUserInfo`,
											method: "GET",
											data: {
												open_id: openId
											},
											success: (res) => {
												if(res.data.code !== 200){
													console.log(res.data.msg);
													return;
												}
												this.globalData.userInfo = res.data.data;
											}
										})
									} else {
										uni.request({
											url: `${this.globalData.apiUrl}/user/createUser`,
											method: "GET",
											data: {
												open_id: openId,
												name: "拉布拉用户"
											},
											success: (res) => {
												if(res.data.code !== 200){
													console.log(res.data.msg);
													return;
												}
												this.globalData.userInfo = res.data.data;
											}
										})
									}
								}
							})
						}
					})
				}
			});
		},
		onShow: function() {
			// console.log('App Show')
		},
		onHide: function() {
			// console.log('App Hide')
		},

		globalData: {
			apiUrl: "http://localhost:8085",
			userInfo: null
		}
	}
</script>

<style>
	/*每个页面公共css */
</style>
