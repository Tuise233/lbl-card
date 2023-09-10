<template>
    <view class="com-inputbox">
        <text>{{ label }}</text>
        <input class="com-inputbox-default" :type="inputType" :value="value" @input="$emit('input', $event.target.value)" v-if="type == 'text'" />

        <view class="com-inputbox-default com-inputbox-number" v-else-if="type == 'number'">
            <uni-icons type="left" color="#8E8E8E" size="35rpx" @click="toggleNumber('decrease')"></uni-icons>
            <input type="number" :value="localValue" @input="$emit('input', $event.target.value)" />
            <uni-icons type="right" color="#8E8E8E" size="35rpx" @click="toggleNumber('increase')"></uni-icons>
        </view>
        
        <view class="com-inputbox-default com-inputbox-textarea" v-else-if="type == 'textarea'">
            <textarea :value="value" @input="$emit('input', $event.target.value)"></textarea>
        </view>

        <view class="com-inputbox-default com-inputbox-select" v-else-if="type == 'select'">
            <view :style="{ backgroundColor: localValue == 'left' ? '#3EAFC8' : '' }" @click="toggleSelect('left')">
                <text :style="{ color: localValue == 'left' ? 'white' : '' }">{{ ol }}</text>
            </view>
            <view :style="{ backgroundColor: localValue == 'right' ? '#3EAFC8' : '' }" @click="toggleSelect('right')">
                <text :style="{ color: localValue == 'right' ? 'white' : '' }">{{ or }}</text>
            </view>
            <input :value="localValue" @input="$emit('input', $event.target.value)"/>
        </view>
    </view>
</template>

<script>
export default {
    props: {
        type: String,
        label: String,
        value: String,
        ol: String,
        or: String
    },

    data() {
        return {
            localValue: this.value,
        }
    },

    computed: {
        inputType() {
            switch (this.type) {
                case 'number':
                    return 'digit';
                default:
                    return 'text';
            }
        }
    },

    methods: {
        toggleNumber(type){
            this.localValue = type == "increase" ? Number(this.localValue) + 1 : Number(this.localValue) - 1;
            this.$emit('input', this.localValue);
        },

        toggleSelect(type){
            this.localValue = type;
            this.$emit('input', this.localValue);
        }
    }
}
</script>

<style>
.com-inputbox {
    width: 673.08rpx;
    box-sizing: border-box;
    padding: 19.23rpx;
    display: flex;
    flex-direction: column;
    background-color: white;
    border-radius: 10rpx;
    box-shadow: 0rpx 0rpx 19rpx -8rpx rgba(0,0,0,0.25);
    align-self: center;
}

.com-inputbox > text {
    font-size: 27rpx;
    height: 27rpx;
    line-height: 27rpx;
    color: #8E8E8E;
}

.com-inputbox-default {
    margin-top: 9.62rpx;
    background-color: #F7F7F7;
    width: 100%;
    height: 57.69rpx;
    box-sizing: border-box;
    padding: 0 20rpx;
    font-size: 25rpx;
}

.com-inputbox-number {
    display: flex;
    align-items: center;
    justify-content: space-between;
}

.com-inputbox-number > input {
    text-align: center;
    font-size: 25rpx;
}

.com-inputbox-textarea {
    height: 194.23rpx;
    box-sizing: border-box;
    padding: 0;
}

.com-inputbox-textarea > textarea {
    width: 100%;
    height: 100%;
    box-sizing: border-box;
    padding: 20rpx;
    font-size: 25rpx;
}

.com-inputbox-select {
    background-color: transparent;
    display: flex;
    align-items: center;
    justify-content: space-between;
    box-sizing: border-box;
    padding: 0;
}

.com-inputbox-select > input {
    display: none;
}

.com-inputbox-select > view {
    width: 307.69rpx;
    height: 100%;
    border-radius: 10rpx;
    background-color: #F7F7F7;
    display: flex;
    align-items: center;
    justify-content: center;
}

.com-inputbox-select > view > text {
    font-size: 27rpx;
    height: 27rpx;
    line-height: 27rpx;
    color: #8E8E8E;
}

.com-input-select-active {
    background-color: #3EAFC8;
}

.com-input-select-active > text {
    color: white;
}
</style>