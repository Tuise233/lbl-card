<template>
    <div class="app-profile">
        <div class="app-profile-header">
            <text>拉布拉卡</text>
            <text>设置</text>
        </div>
        <div class="app-profile-content">
            <div class="app-profile-userinfo">
                <img src="../../static/logo.png" />
                <div>
                    <div>
                        <text>{{ userInfo.name }}</text>
                        <text>(ID: {{ userInfo.userId }})</text>
                    </div>
                    <text>已绑定情侣: {{ targetInfo.name }}</text>
                </div>
            </div>

            <div class="app-profile-setting">
                <text>常用功能</text>
                <div>
                    <div>
                        <uni-icons type="medal-filled" size="25"></uni-icons>
                        <text>里程碑</text>
                    </div>
                    <div>
                        <uni-icons type="mail-open-filled" size="25"></uni-icons>
                        <text>反馈</text>
                    </div>
                    <div>
                        <uni-icons type="paperplane" size="25"></uni-icons>
                        <text>分享</text>
                    </div>
                    <div>
                        <uni-icons type="trash" size="25"></uni-icons>
                        <text>解绑</text>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
const app = getApp();

export default {
    data() {
        return {
            userInfo: null,
            targetInfo: null,
            openId: null
        }
    },

    created() {
        if(app.globalData.userInfo !== null) {
            console.log("123");
            this.userInfo = app.globalData.userInfo;
            uni.request({
                url: `${app.globalData.apiUrl}/user/getUserInfoById`,
                method: "GET",
                data: {
                    userId: app.globalData.userInfo.targetId
                },
                success: (res) => {
                    if(res.data.code !== 200){
                        uni.showToast({
                            title: res.data.msg,
                            icon: "fail",
                            mask: true,
                            position: "top"
                        });
                        return;
                    }
                    this.targetInfo = res.data.data;
                }
            });

        }
    },

    methods: {

    }
}

</script>

<style>
.app-profile {
    width: 100vw;
    height: 100vh;
    background-color: #FBFDFF;
    display: flex;
    flex-direction: column;
}

.app-profile-header {
    width: 100vw;
    height: 346.15rpx;
    display: flex;
    flex-direction: column;
    background-color: white;
    box-shadow: 0rpx 8rpx 10rpx -12rpx rgba(0,0,0,0.25);
}

.app-profile-header > text:first-child {
    font-size: 50rpx;
    height: 50rpx;
    line-height: 50rpx;
    font-weight: bold;
    margin-top: 173.08rpx;
    margin-left: 38.46rpx;
}

.app-profile-header > text:last-child {
    font-size: 35rpx;
    height: 35rpx;
    line-height: 35rpx;
    font-weight: 400;
    margin-left: 38.46rpx;
    margin-top: 69.23rpx;
    color: #575757;
}

.app-profile-content {
    width: 100vw;
    height: calc(100vh - 346.15rpx);
    display: flex;
    flex-direction: column;
    align-items: center;
    box-sizing: border-box;
    padding: 0 38.46rpx;
}

.app-profile-userinfo {
    width: 100%;
    height: 173.08rpx;
    display: flex;
    align-items: center;
    margin-top: 38.46rpx;
    background-color: white;
    box-shadow: 0rpx 0rpx 8rpx -2rpx rgba(0,0,0,0.25);
    border-radius: 10rpx;
}

.app-profile-userinfo > img {
    width: 134.62rpx;
    height: 134.62rpx;
    border-radius: 50%;
    margin-left: 19.23rpx;
    border: 1px solid black;
}

.app-profile-userinfo > div {
    display: flex;
    flex-direction: column;
    width: calc(100% - 183.08rpx);
    height: 100%;
}

.app-profile-userinfo > div > div:first-child {
    display: flex;
    align-items: flex-end;
    margin-top: 46.15rpx;
    margin-left: 19.23rpx;
}

.app-profile-userinfo > div > div:first-child > text:first-child {
    font-size: 35rpx;
    font-weight: bold;
}

.app-profile-userinfo > div > div:first-child > text:last-child {
    font-size: 19rpx;
    color: #8E8E8E;
    font-weight: 400;
    margin-left: 9.62rpx;
}

.app-profile-userinfo > div > text {
    font-size: 27rpx;
    margin-left: 19.23rpx;
    margin-top: 19.23rpx;
    font-weight: bold;
}

.app-profile-setting {
    width: 100%;
    height: 159.62rpx;
    display: flex;
    flex-direction: column;
    background-color: white;
    border-radius: 10rpx;
    box-shadow: 0rpx 0rpx 8rpx -2rpx rgba(0,0,0,0.25);
    margin-top: 38.46rpx;
}

.app-profile-setting > div {
    width: 100%;
    box-sizing: border-box;
    padding: 0 50rpx;
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.app-profile-setting > text {
    font-size: 23rpx;
    font-weight: bold;
    margin-top: 19.23rpx;
    margin-left: 19.23rpx;
}

.app-profile-setting > div > div {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 12rpx;
}

.app-profile-setting > div > div > text {
    font-size: 23rpx;
    font-weight: lighter;
    color: #575757;
    margin-top: 8rpx;
}
</style>