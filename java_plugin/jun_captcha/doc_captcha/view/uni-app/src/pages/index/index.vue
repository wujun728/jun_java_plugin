<template>
	<view class="content">
		<view class="tab-menu">
			<view class="tab-item" :class="{active:showMode=='fixed'}" @click="fixedClick">滑动嵌入</view>
			<view class="tab-item" :class="{active:showMode=='pop'}" @click="popClick">滑动弹出</view>
		</view>
		
		<view v-if="showMode=='fixed'">
			<!-- <text class="title">滑动嵌入--行为验证码</text> -->
			<view class="form">
				<view class="form-item">
					<input type="text" value="用户名"  disabled=""/>
				</view>
				<view class="form-item">
					<input type="password" value="123456"  disabled=""/>
				</view>
				<Verify
					@success='success'
					:mode="'fixed'"
					:captchaType="'blockPuzzle'"
					ref="verify"
					:imgSize="{width:'340px',height:'175px'}"
				></Verify>
				<!-- <button type="primary" id="btn" class="verify-btn" @click="showVerify" disabled>登录测试</button> -->
			</view>
		</view>
		
		<view v-if="showMode=='pop'">
			<!-- <text class="title">滑动弹出--行为验证码</text> -->
			<view class="form">
				<view class="form-item">
					<input type="text" value="用户名"  disabled=""/>
				</view>
				<view class="form-item">
					<input type="password" value="123456"  disabled=""/>
				</view>
				<Verify
					@success='success'
					:mode="'pop'"
					:captchaType="'blockPuzzle'"
					ref="verify"
					:imgSize="{width:'310px',height:'155px'}"
				></Verify>
				<button type="primary" id="btn" class="verify-btn" @click="showVerify">登 &nbsp;&nbsp;&nbsp;录</button>
			</view>
		</view>
		
	</view>
</template>

<script>
	import Verify from "./../verify/verify"
	export default {
		data() {
			return {
				showMode:'fixed',
			}
		},
		components:{
			Verify
		},
		methods: {
			showVerify(){
				this.$refs.verify.show();
			},
			success(data){
				console.log(data,"成功返回");
			},
			fixedClick(){
				this.showMode = "fixed"
			},
			popClick(){
				this.showMode = "pop"
			}
		},
	}
</script>

<style>
	.tab-menu{
		display: flex;
	}
	.tab-item{
		width:50%;
		box-sizing: border-box;
		height: 50rpx;
		line-height: 50rpx;
		text-align: center;
		margin-bottom: 30rpx;
	}
	.tab-item:nth-child(1){
		border-right: 1rpx solid #ccc;
	}
	.active{
		color: #007AFF;
	}
	.content{
		width: 700rpx;
		margin: 100rpx auto;
		/* border: 1rpx solid #007AFF; */
		padding:10rpx;
	}
	.title{
		font-size: 35rpx;
		color: #808080;
	}
	.form{
		margin-top: 15rpx;
		padding: 10rpx;
	}
	.form-item{
		padding:10rpx 10rpx 10rpx 0;
		margin-top: 10rpx;
	}
	.form-item input{
		width:90%;
		border: 1rpx solid #ccc;
		height:50rpx;
		line-height: 50rpx;
		padding: 10rpx;
		color:#C8C7CC;
	}
	.verify-btn{
		margin: 10rpx 0;
		width:90%;
		padding-left: 10rpx;
	}
	.space{
		height: 5rpx;
		background-color: #ccc;
		margin: 30rpx 0;
	}
	
</style>


