<template>
    <view class="app-cm">
        <view class="app-cm-header">
            <text>拉布拉卡</text>
            <view>
                <view @click="back">
                    <uni-icons type="back" size="26.92rpx" color="#8E8E8E"></uni-icons>
                    <text>返回</text>
                </view>
                <text>创建新打卡</text>
            </view>
        </view>

        <view class="app-cm-content">
            <InputBox class="app-cm-inputbox" label="打卡标题" type="text" v-model="mission.name"></InputBox>
            <InputBox class="app-cm-inputbox" label="打卡简述" type="textarea" v-model="mission.description"></InputBox>
            <InputBox class="app-cm-inputbox" label="打卡类型" type="select" v-model="mission.type" ol="每日打卡" or="长期打卡"></InputBox>
            <InputBox class="app-cm-inputbox" label="审批类型" type="select" v-model="mission.checkType" ol="需要审批" or="无需审批"></InputBox>
            <InputBox class="app-cm-inputbox" label="打卡积分奖励" type="number" v-model="mission.score"></InputBox>

            <view class="app-cm-img-picker" v-if="mission.img == ''">
                <text>背景图片</text>
                <view @click="selectImg">
                    <uni-icons type="plusempty" size="40" color="white"></uni-icons>
                </view>
            </view>
            
            <view class="app-cm-img-preview" v-else>
                <text>背景图片</text>
                <img :src="mission.img"  @click="selectImg"/>
            </view>
            <text class="app-cm-img-cancel" v-if="mission.img != ''" @click="removeImg">删除图片</text>

            <view class="app-cm-button" @click="createMission" v-if="isEdit == false">
                <text>创建新打卡</text>
            </view>

            <view class="app-cm-button" @click="saveMission" v-if="isEdit">
                <text>保存打卡</text>
            </view>

            <view class="app-cm-button app-cm-button-del" @click="deleteMission" v-if="isEdit">
                <text>删除打卡</text>
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

    data(){
        return {
            isEdit: false,
            mission: {
                name: "",
                description: "",
                type: "left",
                checkType: "left",
                score: 0,
                img: ""
            }
        }
    },

    onLoad(options){
        if(options.missionId !== undefined){
            this.isEdit = true;
            uni.request({
                url: `${app.globalData.apiUrl}/mission/getMissionById`,
                method: "GET",
                data: {
                    missionId: options.missionId
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
                    this.mission = res.data.data;
                }
            })
        }
    },
    
    methods: {
        checkField(){
            if(this.mission.name == "") {
                uni.showToast({
                    title: "请完整填写标题",
                    icon: "error",
                    mask: true,
                    position: "top"
                });
                return false;
            }
            if(this.mission.description == "") {
                uni.showToast({
                    title: "请完整填写简述",
                    icon: "error",
                    mask: true,
                    position: "top"
                });
                return false;
            }
            return true;
        },

        formatType(){
            this.mission.type = this.mission.type == "left" ? 0 : 1;
            this.mission.checkType = this.mission.checkType == "left" ? 0 : 1;
        },

        createMission(){
            if(!this.checkField()) return;
            this.formatType();
            uni.request({
                url: `${app.globalData.apiUrl}/mission/createMission`,
                method: "POST",
                data: {
                    creatorId: app.globalData.userInfo.userId,
                    id: this.mission.id,
                    name: this.mission.name,
                    description: this.mission.description,
                    type: this.mission.type,
                    checkType: this.mission.checkType,
                    score: this.mission.score,
                    img: this.mission.img
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
                    uni.showToast({
                        title: "创建打卡成功",
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

        saveMission(){
            if(!this.checkField()) return;
            this.formatType();
            uni.request({
                url: `${app.globalData.apiUrl}/mission/saveMission`,
                method: "POST",
                data: {
                    creatorId: app.globalData.userInfo.userId,
                    id: this.mission.id,
                    name: this.mission.name,
                    description: this.mission.description,
                    type: this.mission.type,
                    checkType: this.mission.checkType,
                    score: this.mission.score,
                    img: this.mission.img
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
                    uni.showToast({
                        title: "保存打卡成功",
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

        deleteMission(){
            uni.request({
                url: `${app.globalData.apiUrl}/mission/deleteMission`,
                method: "GET",
                data: {
                    userId: app.globalData.userInfo.userId,
                    missionId: this.mission.id
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
                    uni.showToast({
                        title: "删除打卡成功",
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
        
        selectImg(){
            uni.chooseImage({
                count: 1,
                sizeType: "original",
                success: (res) => {
                    uni.getFileSystemManager().readFile({
                        filePath: res.tempFiles[0].path,
                        encoding: "base64",
                        success: (res) => {
                            this.mission.img = 'data:image/jpeg;base64,' + res.data;
                        }
                    })
                }
            })
        },

        removeImg(){
            this.mission.img = "";
        },

        back(){
            uni.navigateBack();
        },
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

.app-cm-header{
    width: 100vw;
    height: 346.15rpx;
    display: flex;
    flex-direction: column;
    background-color: white;
    box-shadow: 0rpx 8rpx 10rpx -12rpx rgba(0,0,0,0.25);
}

.app-cm-header > text{
    font-size: 50rpx;
    height: 50rpx;
    line-height: 50rpx;
    font-weight: bold;
    margin-top: 173.08rpx;
    margin-left: 38.46rpx;
}

.app-cm-header > view {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-top: 76.92rpx;
    box-sizing: border-box;
    padding: 0 38.46rpx;
}

.app-cm-header > view > text {
    font-size: 26.92rpx;
    height: 26.92rpx;
    line-height: 26.92rpx;
    color: #8E8E8E;
    margin-left: auto;
}

.app-cm-header > view > view {
    display: flex;
    align-items: center;
}

.app-cm-header > view > view > text {
    font-size: 26.92rpx;
    height: 26.92rpx;
    line-height: 26.92rpx;
    color: #8E8E8E;
    margin-left: 9.62rpx;
}

.app-cm-header > view > view > uni-icons{
    font-weight: bold;
}

.app-cm-content {
    width: 100%;
    height: calc(100vh - 346.15rpx - 36.54rpx);
    display: flex;
    flex-direction: column;
    align-items: center;
    overflow-y: scroll;
}

.app-cm-inputbox { 
    margin-top: 36.54rpx;
}

.app-cm-img-picker {
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

.app-cm-img-picker > text {
    font-size: 27rpx;
    height: 27rpx;
    line-height: 27rpx;
    color: #8E8E8E;
}

.app-cm-img-picker > view {
    margin-top: 19.23rpx;
    background-color: #F7F7F7;
    width: 100%;
    height: 153.85rpx;
    display: flex;
    align-items: center;
    justify-content: center;
}

.app-cm-button {
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

.app-cm-button-del {
    background-color: rgb(219, 86, 86);
}

.app-cm-img-preview {
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

.app-cm-img-preview > text {
    font-size: 27rpx;
    height: 27rpx;
    line-height: 27rpx;
    color: #8E8E8E;
}

.app-cm-img-preview > img {
    width: 100%;
    margin-top: 19.23rpx;
    height: calc(100% - 19.23rpx - 19.23rpx);
}

.app-cm-img-cancel {
    margin-top: 19.23rpx;
    color: #8E8E8E;
    align-self: flex-start;
    margin-left: 40.38rpx;
}
</style>