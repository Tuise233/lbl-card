<template>
    <view class="app-cr">
        <view class="app-cr-header">
            <text>拉布拉卡</text>
            <view>
                <view @click="back">
                    <uni-icons type="back" size="26.92rpx" color="#8E8E8E"></uni-icons>
                    <text>返回</text>
                </view>
                <text>创建新奖励</text>
            </view>
        </view>

        <view class="app-cr-content">
            <InputBox class="app-cr-inputbox" label="奖励名称" type="text" v-model="reward.name"></InputBox>
            <InputBox class="app-cr-inputbox" label="所需积分" type="number" v-model="reward.score"></InputBox>
            <InputBox class="app-cr-inputbox" label="奖励库存" type="number" v-model="reward.amount"></InputBox>
            <view class="app-cr-img-picker" v-if="reward.img == ''">
                <text>奖励图片</text>
                <view @click="selectImg">
                    <uni-icons type="plusempty" size="40" color="white"></uni-icons>
                </view>
            </view>

            <view class="app-cr-img-preview" v-else>
                <text>奖励图片</text>
                <img :src="reward.img" @click="selectImg"/>
            </view>
            <text class="app-cr-img-cancel" v-if="reawrd.img !== ''" @click="removeImg"></text>

            <view class="app-cr-button" v-if="isEdit == false" @click="createReward">
                <text>创建新奖励</text>
            </view>

            <view class="app-cr-button" v-if="isEdit" @click="saveReward">
                <text>保存奖励</text>
            </view>

            <view class="app-cr-button app-cr-button-del" v-if="isEdit" @click="deleteReward">
                <text>删除奖励</text>
            </view>
        </view>
    </view>
</template>

<script>
const app = getApp();
import InputBox from "@/components/input_box/input_box.vue";

export default {
    components: {
        InputBox
    },

    onLoad(options) {
        if(options.rewardId !== undefined) {
            this.isEdit = true;
            uni.request({
                url: `${app.globalData.apiUrl}/reward/getRewardById`,
                method: "GET",
                data: {
                    rewardId: options.rewardId
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
                    this.reward = res.data.data;
                }
            })
        }
    },

    data() {
        return {
            isEdit: false,
            reward: {
                name: "",
                score: 0,
                amount: 0,
                img: ""
            }
        }
    },

    methods: {
        back(){
            uni.navigateBack();
        },

        selectImg() {
            uni.chooseImage({
                count: 1,
                sizeType: "original",
                success: (res) => {
                    uni.getFileSystemManager().readFile({
                        filePath: res.tempFiles[0].path,
                        encoding: "base64",
                        success: (res) => {
                            this.reward.img = 'data:image/jpeg;base64,' + res.data;
                        }
                    })
                }
            });
        },

        checkField(){
            if(this.reward.name == "") {
                uni.showToast({
                    title: "请完整填写名字",
                    icon: "error",
                    mask: true,
                    position: "top"
                });
                return false;
            }
            return true;
        },

        removeImg() {
            this.reward.img = "";
        },

        createReward() {
            if(!this.checkField()) return;
            uni.request({
                url: `${app.globalData.apiUrl}/reward/createReward`,
                method: "POST",
                data: {
                    ownerId: app.globalData.userInfo.targetId,
                    creatorId: app.globalData.userInfo.userId,
                    name: this.reward.name,
                    score: this.reward.score,
                    amount: this.reward.amount,
                    img: this.reward.img
                },
                success: (res) => {
                    if(res.data.code !== 200) {
                        uni.showToast({
                            title: res.data.msg,
                            icon: "fail",
                            mask: true,
                            position: "top"
                        });
                        return;
                    }
                    uni.showToast({
                        title: "创建奖励成功",
                        icon: "success",
                        mask: true,
                        position: "top"
                    });
                    setTimeout(() => {
                        uni.navigateBack();
                    }, 1500);
                }
            })
        },

        saveReward() {
            if(!this.checkField()) return;
            uni.request({
                url: `${app.globalData.apiUrl}/reward/saveReward`,
                method: "POST",
                data: {
                    id: this.reward.id,
                    ownerId: this.reward.ownerId,
                    creatorId: this.reward.creatorId,
                    name: this.reward.name,
                    score: this.reward.score,
                    amount: this.reward.amount,
                    img: this.reward.img
                },
                success: (res) => {
                    if(res.data.code !== 200) {
                        uni.showToast({
                            title: res.data.msg,
                            icon: "fail",
                            mask: true,
                            position: "top"
                        });
                        return;
                    }
                    uni.showToast({
                        title: "保存奖励成功",
                        icon: "success",
                        mask: true,
                        position: "top"
                    });
                    setTimeout(() => {
                        uni.navigateBack();
                    }, 1500);
                }
            });
        },

        deleteReward() {
            uni.request({
                url: `${app.globalData.apiUrl}/reward/deleteReward`,
                method: "GET",
                data: {
                    creatorId: app.globalData.userInfo.userId,
                    rewardId: this.reward.id
                },
                success: (res) => {
                    if(res.data.code !== 200) {
                        uni.showToast({
                            title: res.data.msg,
                            icon: "fail",
                            mask: true,
                            position: "top"
                        });
                        return;
                    }
                    uni.showToast({
                        title: "删除奖励成功",
                        icon: "success",
                        mask: true,
                        position: "top"
                    });
                    setTimeout(() => {
                        uni.navigateBack();
                    }, 1500);
                }
            })
        }
    }
}
</script>

<style>
.app-cm{
    width: 100vw;
    height: 100vh;
    background-color: #FBFDFF;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.app-cm::-webkit-scrollbar {
    width: 0;
}

.app-cr-header{
    width: 100vw;
    height: 346.15rpx;
    display: flex;
    flex-direction: column;
    background-color: white;
    box-shadow: 0rpx 8rpx 10rpx -12rpx rgba(0,0,0,0.25);
}

.app-cr-header > text{
    font-size: 50rpx;
    height: 50rpx;
    line-height: 50rpx;
    font-weight: bold;
    margin-top: 173.08rpx;
    margin-left: 38.46rpx;
}

.app-cr-header > view {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-top: 76.92rpx;
    box-sizing: border-box;
    padding: 0 38.46rpx;
}

.app-cr-header > view > text {
    font-size: 26.92rpx;
    height: 26.92rpx;
    line-height: 26.92rpx;
    color: #8E8E8E;
    margin-left: auto;
}

.app-cr-header > view > view {
    display: flex;
    align-items: center;
}

.app-cr-header > view > view > text {
    font-size: 26.92rpx;
    height: 26.92rpx;
    line-height: 26.92rpx;
    color: #8E8E8E;
    margin-left: 9.62rpx;
}

.app-cr-header > view > view > uni-icons{
    font-weight: bold;
}

.app-cr-content {
    width: 100%;
    height: calc(100vh - 346.15rpx - 36.54rpx);
    display: flex;
    flex-direction: column;
    align-items: center;
    overflow-y: scroll;
}

.app-cr-inputbox { 
    margin-top: 36.54rpx;
}

.app-cr-img-picker {
    width: 673.08rpx;
    height: 238.46rpx;
    display: flex;
    flex-direction: column;
    background-color: white;
    border-radius: 10rpx;
    box-shadow: 0rpx 0rpx 6rpx 0rpx rgba(0,0,0,0.25);
    margin-top: 38.46rpx;
    flex-shrink: 0;
    box-sizing: border-box;
    padding: 19.23rpx;
}

.app-cr-img-picker > text {
    font-size: 27rpx;
    height: 27rpx;
    line-height: 27rpx;
    color: #8E8E8E;
}

.app-cr-img-picker > view {
    margin-top: 19.23rpx;
    background-color: #F7F7F7;
    width: 100%;
    height: 153.85rpx;
    display: flex;
    align-items: center;
    justify-content: center;
}

.app-cr-button {
    width: 673.08rpx;
    height: 76.92rpx;
    border-radius: 10rpx;
    background-color: #3EAFC8;
    color: white;
    font-size: 33rpx;
    line-height: 33rpx;
    font-weight: bold;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-top: 38.46rpx;
    flex-shrink: 0;
}

.app-cr-button-del {
    background-color: rgb(219, 86, 86);
}

.app-cr-img-preview {
    width: 673.08rpx;
    height: 673.08rpx;
    display: flex;
    flex-direction: column;
    background-color: white;
    border-radius: 10rpx;
    box-shadow: 0rpx 0rpx 6rpx 0rpx rgba(0,0,0,0.25);
    margin-top: 38.46rpx;
    flex-shrink: 0;
    box-sizing: border-box;
    padding: 19.23rpx;
}

.app-cr-img-preview > text {
    font-size: 27rpx;
    height: 27rpx;
    line-height: 27rpx;
    color: #8E8E8E;
}

.app-cr-img-preview > img {
    width: 100%;
    margin-top: 19.23rpx;
    height: calc(100% - 19.23rpx - 19.23rpx);
}

.app-cr-img-cancel {
    margin-top: 19.23rpx;
    color: #8E8E8E;
    align-self: flex-start;
    margin-left: 40.38rpx;
}
</style>