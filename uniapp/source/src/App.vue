<script>
export default {
	onLaunch: function () {
		uni.login({
			success: (res) => {
				uni.request({
					url: `${this.globalData.apiUrl}/wx/getOriginData`,
					method: "GET",
					data: {
						code: res.code
					},
					success: (res) => {
						if (res.data.code !== 200) {
							uni.showToast({
								title: res.data.msg,
								icon: "fail",
								mask: true,
								position: "top"
							});
							return;
						}
						const openId = res.data.data;
						this.globalData.openId = openId;
						uni.request({
							url: `${this.globalData.apiUrl}/user/checkExist`,
							method: "GET",
							data: {
								open_id: openId
							},
							success: (res) => {
								if (res.data.code !== 200) {
									uni.showToast({
										title: res.data.msg,
										icon: "fail",
										mask: true,
										position: "top"
									});
									return;
								}
								if (res.data.data.exist === true) {
									uni.request({
										url: `${this.globalData.apiUrl}/user/getUserInfo`,
										method: "GET",
										data: {
											open_id: openId
										},
										success: (res) => {
											if (res.data.code !== 200) {
												uni.showToast({
													title: res.data.msg,
													icon: "fail",
													mask: true,
													position: "top"
												});
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
											if (res.data.code !== 200) {
												uni.showToast({
													title: res.data.msg,
													icon: "fail",
													mask: true,
													position: "top"
												});
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
	onShow: function () {
		// console.log('App Show')
	},
	onHide: function () {
		// console.log('App Hide')
	},

	globalData: {
		apiUrl: "http://localhost:8085",
		userInfo: null,
		openId: null
	}
}
</script>

<style>
/*每个页面公共css */
</style>
